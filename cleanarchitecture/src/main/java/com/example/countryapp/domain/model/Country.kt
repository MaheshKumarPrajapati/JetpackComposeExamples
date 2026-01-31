package com.example.countryapp.domain.model

import android.os.Parcelable
import kotlinx.parcelize.Parcelize

@Parcelize
data class Country(
    val country: String,
    val capital: String,
    val code: String,
    val currency: String,
    val language: String,
    val flagSvg: String,
    val flagPng: String
) : Parcelable