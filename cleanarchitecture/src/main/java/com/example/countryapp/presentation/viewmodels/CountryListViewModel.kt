package com.example.countryapp.presentation.viewmodels

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.countryapp.domain.model.Country
import com.example.countryapp.domain.usecase.GetCountriesUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CountryListViewModel @Inject constructor(
    private val getCountriesUseCase: GetCountriesUseCase
) : ViewModel() {

    private val _state = MutableStateFlow(CountryListState())
    val state: StateFlow<CountryListState> = _state.asStateFlow()

    init {
        loadCountries()
    }

    fun loadCountries() {
        viewModelScope.launch {
            _state.value = _state.value.copy(isLoading = true, error = null)
            
            getCountriesUseCase().fold(
                onSuccess = { countries ->
                    _state.value = _state.value.copy(
                        countries = countries,
                        isLoading = false,
                        error = null
                    )
                },
                onFailure = { exception ->
                    _state.value = _state.value.copy(
                        isLoading = false,
                        error = exception.message ?: "An unknown error occurred"
                    )
                }
            )
        }
    }
}

data class CountryListState(
    val countries: List<Country> = emptyList(),
    val isLoading: Boolean = false,
    val error: String? = null
)
