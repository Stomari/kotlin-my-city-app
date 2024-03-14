package com.example.my_city_app

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.my_city_app.data.CategoriesData
import com.example.my_city_app.model.ListItem
import com.example.my_city_app.ui.ListScreen
import com.example.my_city_app.ui.MyCityViewModel

enum class MyCityScreens() {
    Start, Recommendation, Details
}

@Composable
fun MyCityApp(
    navController: NavHostController = rememberNavController(),
    viewModel: MyCityViewModel = viewModel(),
    modifier: Modifier = Modifier
) {
    val uiState = viewModel.uiState.collectAsState()
    val categories = CategoriesData.loadCategories()

    Scaffold(modifier = modifier) { innerPadding ->
        NavHost(navController = navController, startDestination = MyCityScreens.Start.name) {
            composable(route = MyCityScreens.Start.name) {
                val categoriesList = categories.map {
                    ListItem(it.id, it.title, it.imgResource)
                }
                ListScreen(
                    itemsList = categoriesList,
                    onCardClick = {
                        viewModel.selectCategory(it)
                        navController.navigate(MyCityScreens.Recommendation.name)
                    },
                    modifier = Modifier
                        .padding(innerPadding)
                        .fillMaxSize()
                )
            }
            composable(route = MyCityScreens.Recommendation.name) {
                val recommendationList = uiState.value.selectedCategory?.recommendations?.map {
                    ListItem(it!!.id, it.title, it.imgResource)
                } ?: listOf()
                ListScreen(itemsList = recommendationList, onCardClick = {})
            }
        }
    }
}