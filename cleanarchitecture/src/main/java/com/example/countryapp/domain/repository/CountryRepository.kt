package com.example.countryapp.domain.repository

import com.example.countryapp.domain.model.Country

interface CountryRepository {
    suspend fun getCountries(): Result<List<Country>>
}
