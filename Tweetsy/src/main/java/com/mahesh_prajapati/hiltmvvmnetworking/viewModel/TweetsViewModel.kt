package com.mahesh_prajapati.hiltmvvmnetworking.viewModel

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.mahesh_prajapati.hiltmvvmnetworking.model.Tweet
import com.mahesh_prajapati.hiltmvvmnetworking.repository.TweetsyRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class TweetsViewModel @Inject constructor(private val repository: TweetsyRepository,
private val saveStateHandle: SavedStateHandle
) : ViewModel() {

    val tweets: StateFlow<List<Tweet>>
        get() = repository.tweets

    init {
        viewModelScope.launch {
            val category: String = saveStateHandle["category"]?:""
            repository.getTweets(category)
        }
    }
}
