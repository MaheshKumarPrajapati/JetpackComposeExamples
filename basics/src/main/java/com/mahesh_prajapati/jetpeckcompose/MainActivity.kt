package com.mahesh_prajapati.jetpeckcompose

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.countryapp.presentation.CleanMainActivity
import com.mahesh_prajapati.hiltmvvmnetworking.TweetsyMainActivity
//import com.mahesh_prajapati.hiltmvvmnetworking.TweetsyMainActivity
import com.mahesh.simplequotes.QuotesMainActivity

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Column( horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(5.dp),
                modifier = Modifier.verticalScroll(rememberScrollState()).padding(5.dp)) {
                screenList.map{
                    buttonDraw(it.screenTitle, it.screenID)
                }
            }
        }
    }

    @Composable
    fun buttonDraw( screenTitle: String,  id: Int){
        val context = LocalContext.current
        Button(onClick = {
            if(id==1){
                context.startActivity(Intent(context, BasicComposableActivity::class.java))
            } else if(id==2){
                context.startActivity(Intent(context, ScrollableViews::class.java))
            }else if(id==3){
                context.startActivity(Intent(context, RecompositionScreen::class.java))
            }else if(id==4){
                context.startActivity(Intent(context, StateObjectScreen::class.java))
            }else if(id==5){
                context.startActivity(Intent(context, QuotesMainActivity::class.java))
            }else if(id==6){
                context.startActivity(Intent(context, TweetsyMainActivity::class.java))
            }else if(id==7){
                context.startActivity(Intent(context, CleanMainActivity::class.java))
            }
        }) {
            Text(screenTitle, fontSize = 20.sp, modifier = Modifier
                .padding(10.dp)
                .weight(10f) )
        }
    }

    val screenList = listOf(
        Screens("Basics Composable", 1),
        Screens("Scrollable Views", 2),
        Screens("Recomposition", 3),
        Screens("State Object", 4),
        Screens("Quotes App (Simple UI)", 5),
        Screens("Tweetsy (Hilt + MVVM + Retrofit)", 6),
        Screens("CountryApp (Clean Architecture + Hilt + Retrofit)", 7)
    )
}

data class Screens(val screenTitle: String, val screenID: Int)

