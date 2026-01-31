package com.example.countryapp.data.repository

import com.example.countryapp.data.remote.api.CountryApi
import com.example.countryapp.data.remote.dto.CountryDto
import com.example.countryapp.domain.model.Country
import com.example.countryapp.domain.repository.CountryRepository
import javax.inject.Inject

class CountryRepositoryImpl @Inject constructor(
    private val api: CountryApi
) : CountryRepository {
    
    override suspend fun getCountries(): Result<List<Country>> {
        return try {
            val response = api.getCountries()
            val countries = response.countries.map { it.toDomain() }
            Result.success(countries)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
    
    private fun CountryDto.toDomain(): Country {
        return Country(
            country = country,
            capital = capital,
            code = code,
            currency = currency,
            language = language,
            flagSvg = flag.svg,
            flagPng = flag.png
        )
    }
}
