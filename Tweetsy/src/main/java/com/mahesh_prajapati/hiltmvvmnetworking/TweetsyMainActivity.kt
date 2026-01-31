package com.mahesh_prajapati.hiltmvvmnetworking

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.navArgument
import com.mahesh_prajapati.hiltmvvmnetworking.screens.CategoryScreen
import com.mahesh_prajapati.hiltmvvmnetworking.screens.TweetsScreen
import com.mahesh_prajapati.hiltmvvmnetworking.ui.theme.TweetsyTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class TweetsyMainActivity : ComponentActivity() {

    @OptIn(ExperimentalMaterial3Api::class)
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TweetsyTheme {
                Scaffold(
                    topBar = {
                        CenterAlignedTopAppBar(
                            title = { Text(text = "Tweetsy") },
                            modifier = Modifier.background(Color.Gray),
                        )
                    }
                ) { paddingValues ->
                    Box(modifier = Modifier.padding(paddingValues)) {
                        App()
                    }
                }
            }
        }
    }

    @Composable
    fun App() {

        val navController = rememberNavController()

        NavHost(
            navController = navController,
            startDestination = "category"
        ) {

            composable(route = "category") {
                CategoryScreen { category ->
                    navController.navigate("detail/$category")
                }
            }

            composable(
                route = "detail/{category}",
                arguments = listOf(
                    navArgument("category") {
                        type = NavType.StringType
                    }
                )
            ) {
                TweetsScreen()
            }
        }
    }

}
