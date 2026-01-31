package com.example.countryapp.data.remote.dto

import com.google.gson.annotations.SerializedName

data class CountryResponse(
    @SerializedName("record")
    val countries: List<CountryDto>,
    @SerializedName("metadata")
    val metadata: MetadataDto
)

data class CountryDto(
    @SerializedName("country")
    val country: String,
    @SerializedName("capital")
    val capital: String,
    @SerializedName("code")
    val code: String,
    @SerializedName("currency")
    val currency: String,
    @SerializedName("language")
    val language: String,
    @SerializedName("flag")
    val flag: FlagDto
)

data class FlagDto(
    @SerializedName("svg")
    val svg: String,
    @SerializedName("png")
    val png: String
)

data class MetadataDto(
    @SerializedName("id")
    val id: String,
    @SerializedName("private")
    val isPrivate: Boolean,
    @SerializedName("createdAt")
    val createdAt: String
)
