package com.mahesh_prajapati.jetpeckcompose

import android.os.Bundle
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview


class StateObjectScreen : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            notificationScreen()
        }
    }


    @Preview
    @Composable
    fun notificationScreen(){
        var count = rememberSaveable{mutableStateOf(0)}
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier.fillMaxSize(1f)
        ) {
            notificationCounter(count.value,{count.value++})
            messageBar(count.value)
        }

    }

    @Composable
    fun notificationCounter(count: Int, increment: () -> Int) {
        Column{
            Text(text = "You have ${count} new notifications")
            Button(onClick = {
                Log.d("StateObjectScreen", "Button clicked")
                increment()
            }) {
                Text(text = "Send notifications")
            }
        }
    }


    @Composable
    fun messageBar(value: Int) {
        Column{
            Text(text = "Messages sent so far: $value")
        }
    }
}