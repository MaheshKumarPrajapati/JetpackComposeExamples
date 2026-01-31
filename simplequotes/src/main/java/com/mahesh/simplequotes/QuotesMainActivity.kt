package com.mahesh.simplequotes

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.lifecycleScope
import com.mahesh.simplequotes.screens.QuoteDetails
import com.mahesh.simplequotes.screens.QuoteMainScreen
import com.mahesh.simplequotes.ui.theme.QuoteTheme
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class QuotesMainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        lifecycleScope.launch(Dispatchers.IO) {
            DataManager.loadDataFromAssets(this@QuotesMainActivity)
        }
        setContent {
            QuoteTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    App()
                }
            }
        }
    }

    @Composable
    fun App(){
        if(DataManager.isDataLoaded.value) {
            if(DataManager.currentPage.value == Pages.MAIN){
                QuoteMainScreen(data = DataManager.data) {
                    DataManager.clickedQuote = it
                    DataManager.switchPage()
                }
            }else{
                DataManager.clickedQuote?.let {
                    QuoteDetails(quote = it)
                }
            }
        }
    }
}

enum class Pages {
    MAIN, DETAIL
}