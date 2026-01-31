package com.mahesh.simplequotes.screens

import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.runtime.Composable
import com.mahesh.simplequotes.model.QuoteData

@Composable
fun QuoteList(data: Array<QuoteData>, mOnClick: (quote: QuoteData) -> Unit) {
    LazyColumn {
        items(data.size) { index ->
            val quote = data[index]
            QuoteListItem(quote,mOnClick)
        }
    }
}