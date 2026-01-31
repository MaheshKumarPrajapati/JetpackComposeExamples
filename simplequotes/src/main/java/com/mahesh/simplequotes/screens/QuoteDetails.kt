package com.mahesh.simplequotes.screens

import androidx.activity.compose.BackHandler
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.FormatQuote
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.mahesh.simplequotes.DataManager
import com.mahesh.simplequotes.R
import com.mahesh.simplequotes.model.QuoteData

@Preview
@Composable
fun QuoteDetails(quote: QuoteData) {
    BackHandler { 
        DataManager.switchPage()
    }
    Box(contentAlignment = Alignment.Center,
        modifier = Modifier
            .fillMaxSize(1f)
            .background(Brush.sweepGradient(
                colors = listOf(
                    Color(0xFFffffff),
                    Color(0xFFE3E3E3),
                )
            )
            )
    ) {
        Card(
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            modifier = Modifier.padding(32.dp)
        ) {
            Column(
                verticalArrangement = Arrangement.Center,
                // Add horizontalAlignment to center the content inside the Column
                modifier = Modifier.align(Alignment.CenterHorizontally)
                    .padding(16.dp, 24.dp)
            ) {
                // The content inside this Column will now be centered.
                // For example:
                Image(
                    imageVector = Icons.Filled.FormatQuote,
                    contentDescription = "Quote",
                    modifier = Modifier.size(80.dp).rotate(180f)
                )
                Text(text = quote.text,
                    fontFamily = FontFamily(Font(resId = R.font.montserrat_regular)),
                    style = MaterialTheme.typography.headlineMedium
                )
                Spacer(modifier = Modifier.height(16.dp))
                Text(text = quote.author,
                    fontFamily = FontFamily(Font(resId = R.font.montserrat_regular)),
                    style = MaterialTheme.typography.bodyMedium)
            }
        }

    }
}