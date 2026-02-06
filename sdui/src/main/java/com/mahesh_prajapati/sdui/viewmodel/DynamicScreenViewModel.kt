package com.mahesh_prajapati.sdui.viewmodel

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahesh_prajapati.sdui.model.ActionResult
import com.mahesh_prajapati.sdui.model.ActionType
import com.mahesh_prajapati.sdui.model.ComponentAction
import com.mahesh_prajapati.sdui.model.ComponentType
import com.mahesh_prajapati.sdui.model.UIComponent
import com.mahesh_prajapati.sdui.model.UIScreenData
import com.mahesh_prajapati.sdui.model.ValidationRuleType
import com.mahesh_prajapati.sdui.repository.UIRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class DynamicScreenViewModel @Inject constructor(
    private val repository: UIRepository
) : ViewModel() {
    
    private val _uiState = MutableStateFlow<UIState>(UIState.Loading)
    val uiState: StateFlow<UIState> = _uiState.asStateFlow()
    
    private val _fieldValues = MutableStateFlow<Map<String, String>>(emptyMap())
    val fieldValues: StateFlow<Map<String, String>> = _fieldValues.asStateFlow()
    
    private val _fieldErrors = MutableStateFlow<Map<String, String?>>(emptyMap())
    val fieldErrors: StateFlow<Map<String, String?>> = _fieldErrors.asStateFlow()
    
    private val _navigationEvent = MutableStateFlow<NavigationEvent?>(null)
    val navigationEvent: StateFlow<NavigationEvent?> = _navigationEvent.asStateFlow()
    
    private val _toastEvent = MutableStateFlow<String?>(null)
    val toastEvent: StateFlow<String?> = _toastEvent.asStateFlow()
    
    private val _isActionLoading = MutableStateFlow(false)
    val isActionLoading: StateFlow<Boolean> = _isActionLoading.asStateFlow()
    
    fun loadScreen(binId: String) {
        viewModelScope.launch {
            _uiState.value = UIState.Loading
            
            repository.getScreenData(binId).fold(
                onSuccess = { screenData ->
                    _uiState.value = UIState.Success(screenData)
                    initializeFieldValues(screenData.components)
                },
                onFailure = { error ->
                    _uiState.value = UIState.Error(error.message ?: "Unknown error")
                }
            )
        }
    }
    
    private fun initializeFieldValues(components: List<UIComponent>) {
        val initialValues = mutableMapOf<String, String>()
        components.filter { it.type == ComponentType.INPUT }.forEach { component ->
            initialValues[component.id] = ""
        }
        _fieldValues.value = initialValues
    }
    
    fun updateFieldValue(fieldId: String, value: String) {
        val currentValues = _fieldValues.value.toMutableMap()
        currentValues[fieldId] = value
        _fieldValues.value = currentValues
        
        // Clear error when user types
        clearFieldError(fieldId)
    }
    
    fun validateField(component: UIComponent): Boolean {
        val value = _fieldValues.value[component.id] ?: ""
        val validationRules = component.validation ?: return true
        
        for (rule in validationRules) {
            val error = when (rule.rule) {
                ValidationRuleType.NOT_EMPTY -> {
                    if (value.isEmpty()) rule.message else null
                }
                ValidationRuleType.EMAIL_FORMAT -> {
                    if (value.isNotEmpty() && !isValidEmail(value)) rule.message else null
                }
                ValidationRuleType.MIN_LENGTH -> {
                    val minLength = (rule.value as? Number)?.toInt() ?: 0
                    if (value.length < minLength) rule.message else null
                }
                ValidationRuleType.MAX_LENGTH -> {
                    val maxLength = (rule.value as? Number)?.toInt() ?: Int.MAX_VALUE
                    if (value.length > maxLength) rule.message else null
                }
                else -> null
            }
            
            if (error != null) {
                setFieldError(component.id, error)
                return false
            }
        }
        
        clearFieldError(component.id)
        return true
    }
    
    fun validateAllFields(components: List<UIComponent>): Boolean {
        var isValid = true
        components.filter { it.type == ComponentType.INPUT }.forEach { component ->
            if (!validateField(component)) {
                isValid = false
            }
        }
        return isValid
    }
    
    private fun setFieldError(fieldId: String, error: String) {
        val currentErrors = _fieldErrors.value.toMutableMap()
        currentErrors[fieldId] = error
        _fieldErrors.value = currentErrors
    }
    
    private fun clearFieldError(fieldId: String) {
        val currentErrors = _fieldErrors.value.toMutableMap()
        currentErrors[fieldId] = null
        _fieldErrors.value = currentErrors
    }
    
    fun handleAction(action: ComponentAction, components: List<UIComponent>) {
        viewModelScope.launch {
            when (action.type) {
                ActionType.API -> handleApiAction(action, components)
                ActionType.NAVIGATE -> handleNavigateAction(action)
                ActionType.DEEPLINK -> handleDeeplinkAction(action)
                else -> {}
            }
        }
    }
    
    private suspend fun handleApiAction(action: ComponentAction, components: List<UIComponent>) {
        // Validate all fields first
        if (!validateAllFields(components)) {
            return
        }
        
        _isActionLoading.value = true
        
        // Build request body with actual values
        val requestBody = action.body?.mapValues { (_, value) ->
            resolveValue(value)
        } ?: emptyMap()
        
        repository.performApiAction(
            endpoint = action.endpoint ?: "",
            method = action.method ?: "POST",
            body = requestBody
        ).fold(
            onSuccess = {
                _isActionLoading.value = false
                action.onSuccess?.let { handleActionResult(it) }
            },
            onFailure = { error ->
                _isActionLoading.value = false
                action.onError?.let { handleActionResult(it) }
                    ?: showToast(error.message ?: "An error occurred")
            }
        )
    }
    
    private fun handleNavigateAction(action: ComponentAction) {
        action.destination?.let { destination ->
            _navigationEvent.value = NavigationEvent.Navigate(destination)
        }
    }
    
    private fun handleDeeplinkAction(action: ComponentAction) {
        action.destination?.let { deeplink ->
            _navigationEvent.value = NavigationEvent.Deeplink(deeplink)
        }
    }
    
    private fun handleActionResult(result: ActionResult) {
        when (result.type) {
            ActionType.NAVIGATE -> {
                result.destination?.let { destination ->
                    _navigationEvent.value = NavigationEvent.Navigate(destination)
                }
            }
            ActionType.TOAST -> {
                result.message?.let { message ->
                    showToast(message)
                }
            }
        }
    }
    
    private fun resolveValue(template: String): Any {
        // Resolve template like "{{email_input.value}}"
        val regex = "\\{\\{(.+?)\\.value\\}\\}".toRegex()
        val match = regex.find(template)
        
        return if (match != null) {
            val fieldId = match.groupValues[1]
            _fieldValues.value[fieldId] ?: ""
        } else {
            template
        }
    }
    
    private fun isValidEmail(email: String): Boolean {
        return Patterns.EMAIL_ADDRESS.matcher(email).matches()
    }
    
    fun showToast(message: String) {
        _toastEvent.value = message
    }
    
    fun clearToast() {
        _toastEvent.value = null
    }
    
    fun clearNavigationEvent() {
        _navigationEvent.value = null
    }
}

sealed class UIState {
    object Loading : UIState()
    data class Success(val screenData: UIScreenData) : UIState()
    data class Error(val message: String) : UIState()
}

sealed class NavigationEvent {
    data class Navigate(val destination: String) : NavigationEvent()
    data class Deeplink(val uri: String) : NavigationEvent()
}
