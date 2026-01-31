package com.example.countryapp.presentation.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.countryapp.domain.model.Country
import com.example.countryapp.presentation.countrydetail.CountryDetailScreen
import com.example.countryapp.presentation.countrylist.CountryListScreen

sealed class Screen(val route: String) {
    object CountryList : Screen("country_list")
    object CountryDetail : Screen("country_detail")
}

@Composable
fun NavigationGraph() {
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = Screen.CountryList.route
    ) {
        composable(Screen.CountryList.route) {
            CountryListScreen(
                onCountryClick = { country ->
                    navController.currentBackStackEntry?.savedStateHandle?.set("country", country)
                    navController.navigate(Screen.CountryDetail.route)
                }
            )
        }
        composable(Screen.CountryDetail.route) {
            val country = navController.previousBackStackEntry?.savedStateHandle?.get<Country>("country")
            if (country != null) {
                CountryDetailScreen(
                    country = country,
                    onBackClick = { navController.navigateUp() }
                )
            }
        }
    }
}
