package com.example.countryapp.data.remote.api

import com.example.countryapp.data.remote.dto.CountryResponse
import retrofit2.http.GET

interface CountryApi {
    @GET("v3/b/697df6da43b1c97be95a92c2")
    suspend fun getCountries(): CountryResponse
}
