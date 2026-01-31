package com.example.countryapp.domain.usecase

import com.example.countryapp.domain.model.Country
import com.example.countryapp.domain.repository.CountryRepository
import javax.inject.Inject

class GetCountriesUseCase @Inject constructor(
    private val repository: CountryRepository
) {
    suspend operator fun invoke(): Result<List<Country>> {
        return repository.getCountries()
    }
}
