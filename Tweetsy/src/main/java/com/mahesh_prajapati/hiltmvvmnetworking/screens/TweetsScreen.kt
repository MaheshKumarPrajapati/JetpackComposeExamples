package com.mahesh_prajapati.hiltmvvmnetworking.screens

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.mahesh_prajapati.hiltmvvmnetworking.model.Tweet
import com.mahesh_prajapati.hiltmvvmnetworking.viewModel.TweetsViewModel


@Composable
fun TweetsScreen() {
    val detailViewModel: TweetsViewModel = hiltViewModel()
    val tweets = detailViewModel.tweets.collectAsState()

    LazyColumn(content = {
        items(tweets.value) { it: Tweet ->
            TweetListItem(tweet = it.text)
        }
    })
}

@Composable
fun TweetListItem(tweet: String){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(10.dp, 5.dp),
        border = BorderStroke(1.dp, Color(color = 0xFFCCCCCC)),
        content = {
            Text(
                text = tweet,
                modifier = Modifier.padding(16.dp),
                style = MaterialTheme.typography.bodyMedium
            )
        }
    )
}