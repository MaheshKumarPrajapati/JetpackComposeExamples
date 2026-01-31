package com.mahesh.simplequotes.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.mahesh.simplequotes.R
import com.mahesh.simplequotes.model.QuoteData

@Composable
fun QuoteMainScreen(data: Array<QuoteData>, mOnClick: (quote: QuoteData) -> Unit) {
    Column {
        Text(text = "Quote App",
             textAlign = TextAlign.Center,
              modifier = Modifier.fillMaxWidth(1f).padding(8.dp,24.dp),
              style = MaterialTheme.typography.headlineLarge,
              fontFamily = FontFamily(Font(R.font.montserrat_regular))
        )
        QuoteList(data,mOnClick)
    }
}