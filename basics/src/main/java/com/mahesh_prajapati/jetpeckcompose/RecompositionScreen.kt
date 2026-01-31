package com.mahesh_prajapati.jetpeckcompose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember

class RecompositionScreen : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            recomposable()
        }
    }


    @Composable
    fun recomposable(){
         val state = remember { mutableStateOf(0.0) }
         Log.d("RecompositionScreen", "Initial Composable")
        Button(onClick = {
            state.value = Math.random()
        }) {
            Log.d("RecompositionScreen", "Recomposition: ${state.value}")
            Text(text = state.value.toString())
        }

    }
}