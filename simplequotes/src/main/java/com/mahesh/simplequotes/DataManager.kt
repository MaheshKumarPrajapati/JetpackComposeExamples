package com.mahesh.simplequotes

import android.content.Context
import androidx.compose.runtime.mutableStateOf
import com.google.gson.Gson
import com.mahesh.simplequotes.model.QuoteData

object DataManager {
    var data = emptyArray<QuoteData>()
    var clickedQuote: QuoteData? = null

    var currentPage = mutableStateOf(Pages.MAIN)
    val isDataLoaded = mutableStateOf(false)

    fun loadDataFromAssets(context: Context) {
         context.assets.open("quotes.json").bufferedReader().use {
            var dataStr = it.readText()
            data = Gson().fromJson(dataStr, Array<QuoteData>::class.java)
            isDataLoaded.value = true
        }
    }

    fun switchPage(){
        currentPage.value = when(currentPage.value){
            Pages.MAIN -> Pages.DETAIL
            Pages.DETAIL -> Pages.MAIN
        }
    }
}