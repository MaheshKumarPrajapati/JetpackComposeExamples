package com.mahesh_prajapati.hiltmvvmnetworking.repository

import com.mahesh_prajapati.hiltmvvmnetworking.api.TweetsyApi
import com.mahesh_prajapati.hiltmvvmnetworking.model.Tweet
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class TweetsyRepository @Inject constructor(
    private val tweetsyAPI: TweetsyApi
) {

    private val _tweets = MutableStateFlow<List<Tweet>>(emptyList())
    val tweets: StateFlow<List<Tweet>>
        get() = _tweets

    private val _categories = MutableStateFlow<List<String>>(emptyList())
    val categories: StateFlow<List<String>>
        get() = _categories

    suspend fun getTweets(category: String) {
        val result = tweetsyAPI.getTweets("tweets[?(@.category==\"$category\")]")
        if (result.isSuccessful && result.body() != null) {
            val filteredTweets = result.body()!!
                .filter { it.category == category }
            _tweets.emit(filteredTweets)
        }
    }

    suspend fun getCategories() {
        val result = tweetsyAPI.getCategories()
        if (result.isSuccessful && result.body() != null) {
            _categories.emit(result.body()!!)
        }
    }
}
