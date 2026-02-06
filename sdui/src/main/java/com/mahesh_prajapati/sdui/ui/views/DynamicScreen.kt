package com.mahesh_prajapati.sdui.ui.views

import android.content.Intent
import android.net.Uri
import android.widget.Toast
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mahesh_prajapati.sdui.model.ComponentAction
import com.mahesh_prajapati.sdui.model.ComponentType
import com.mahesh_prajapati.sdui.model.UIComponent
import com.mahesh_prajapati.sdui.model.UIScreenData
import com.mahesh_prajapati.sdui.viewmodel.DynamicScreenViewModel
import com.mahesh_prajapati.sdui.viewmodel.NavigationEvent
import com.mahesh_prajapati.sdui.viewmodel.UIState

@Composable
fun DynamicScreen(
    binId: String,
    viewModel: DynamicScreenViewModel = hiltViewModel(),
    onNavigate: (String) -> Unit = {}
) {
    val context = LocalContext.current
    val uiState by viewModel.uiState.collectAsState()
    val fieldValues by viewModel.fieldValues.collectAsState()
    val fieldErrors by viewModel.fieldErrors.collectAsState()
    val navigationEvent by viewModel.navigationEvent.collectAsState()
    val toastEvent by viewModel.toastEvent.collectAsState()
    val isActionLoading by viewModel.isActionLoading.collectAsState()
    
    // Load screen data
    LaunchedEffect(binId) {
        viewModel.loadScreen(binId)
    }
    
    // Handle navigation events
    LaunchedEffect(navigationEvent) {
        navigationEvent?.let { event ->
            when (event) {
                is NavigationEvent.Navigate -> {
                    onNavigate(event.destination)
                    viewModel.clearNavigationEvent()
                }
                is NavigationEvent.Deeplink -> {
                    try {
                        val intent = Intent(Intent.ACTION_VIEW, Uri.parse(event.uri))
                        context.startActivity(intent)
                    } catch (e: Exception) {
                        Toast.makeText(context, "Cannot open link", Toast.LENGTH_SHORT).show()
                    }
                    viewModel.clearNavigationEvent()
                }
            }
        }
    }
    
    // Handle toast events
    LaunchedEffect(toastEvent) {
        toastEvent?.let { message ->
            Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
            viewModel.clearToast()
        }
    }
    
    when (val state = uiState) {
        is UIState.Loading -> {
            LoadingScreen()
        }
        is UIState.Success -> {
            DynamicScreenContent(
                screenData = state.screenData,
                fieldValues = fieldValues,
                fieldErrors = fieldErrors,
                isActionLoading = isActionLoading,
                onValueChange = { fieldId, value ->
                    viewModel.updateFieldValue(fieldId, value)
                },
                onAction = { action ->
                    viewModel.handleAction(action, state.screenData.components)
                },
                onValidate = { component ->
                    viewModel.validateField(component)
                }
            )
        }
        is UIState.Error -> {
            ErrorScreen(message = state.message) {
                viewModel.loadScreen(binId)
            }
        }
    }
}

@Composable
private fun DynamicScreenContent(
    screenData: UIScreenData,
    fieldValues: Map<String, String>,
    fieldErrors: Map<String, String?>,
    isActionLoading: Boolean,
    onValueChange: (String, String) -> Unit,
    onAction: (ComponentAction) -> Unit,
    onValidate: (UIComponent) -> Boolean
) {
    val layout = screenData.layout
    val backgroundColor = parseColor(layout.backgroundColor)
    
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(backgroundColor)
            .padding(layout.padding.dp)
            .verticalScroll(rememberScrollState())
    ) {
        screenData.components.forEach { component ->
            when (component.type) {
                ComponentType.BUTTON -> {
                    // Special handling for buttons with enabledWhenValid
                    val isEnabled = if (component.data.enabledWhenValid == true) {
                        // Check if all input fields are valid
                        fieldErrors.values.all { it == null } &&
                        fieldValues.all { it.value.isNotEmpty() }
                    } else {
                        true
                    }

                    ButtonComponent(
                        component = component,
                        onAction = onAction,
                        modifier = Modifier.padding(vertical = 8.dp),
                        enabled = isEnabled,
                        isLoading = isActionLoading
                    )
                }
                else -> {
                    DynamicComponent(
                        component = component,
                        fieldValue = fieldValues[component.id] ?: "",
                        fieldError = fieldErrors[component.id],
                        onValueChange = { value ->
                            onValueChange(component.id, value)
                        },
                        onAction = onAction,
                        modifier = Modifier.padding(vertical = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
private fun LoadingScreen() {
    Box(
        modifier = Modifier.fillMaxSize(),
        contentAlignment = Alignment.Center
    ) {
        CircularProgressIndicator()
    }
}

@Composable
private fun ErrorScreen(
    message: String,
    onRetry: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Text(
                text = "Error",
                style = MaterialTheme.typography.headlineMedium,
                color = MaterialTheme.colorScheme.error
            )
            Text(
                text = message,
                style = MaterialTheme.typography.bodyMedium,
                color = MaterialTheme.colorScheme.onSurface
            )
            Button(onClick = onRetry) {
                Text("Retry")
            }
        }
    }
}

private fun parseColor(colorString: String): Color {
    return try {
        Color(android.graphics.Color.parseColor(colorString))
    } catch (e: Exception) {
        Color.White
    }
}
