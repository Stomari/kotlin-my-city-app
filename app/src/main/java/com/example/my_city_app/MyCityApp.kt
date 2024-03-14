package com.example.my_city_app

import androidx.annotation.StringRes
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.example.my_city_app.data.CategoriesData
import com.example.my_city_app.model.ListItem
import com.example.my_city_app.ui.DetailsScreen
import com.example.my_city_app.ui.ListScreen
import com.example.my_city_app.ui.ListScreenWithDetails
import com.example.my_city_app.ui.MyCityViewModel

enum class MyCityScreens(@StringRes val title: Int) {
    Start(title = R.string.categories), Recommendation(title = R.string.recommendations), Details(
        title = R.string.details
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun TopBar(
    currentScreens: MyCityScreens,
    canNavigateBack: Boolean,
    navigateUp: () -> Unit,
    modifier: Modifier = Modifier
) {
    TopAppBar(title = { Text(text = stringResource(id = currentScreens.title)) },
        modifier = modifier,
        navigationIcon = {
            if (canNavigateBack) {
                IconButton(onClick = navigateUp) {
                    Icon(
                        imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                        contentDescription = stringResource(
                            id = R.string.back_button
                        )
                    )
                }
            }
        })
}

@Composable
fun MyCityApp(
    navController: NavHostController = rememberNavController(),
    viewModel: MyCityViewModel = viewModel(),
    windowSize: WindowWidthSizeClass,
    modifier: Modifier = Modifier
) {
    val uiState = viewModel.uiState.collectAsState()
    val categories = CategoriesData.loadCategories()

    val backStackEntry by navController.currentBackStackEntryAsState()
    val currentScreen = MyCityScreens.valueOf(
        backStackEntry?.destination?.route ?: MyCityScreens.Start.name
    )

    val showRecommendationWithDetails: Boolean
    when (windowSize) {
        WindowWidthSizeClass.Expanded -> {
            showRecommendationWithDetails = true
        }

        else -> {
            showRecommendationWithDetails = false
        }
    }

    Scaffold(
        topBar = {
            TopBar(currentScreens = currentScreen,
                canNavigateBack = navController.previousBackStackEntry != null,
                navigateUp = { navController.navigateUp() })
        }, modifier = modifier
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = MyCityScreens.Start.name,
            modifier = Modifier.padding(innerPadding)
        ) {
            composable(route = MyCityScreens.Start.name) {
                val categoriesList = categories.map {
                    ListItem(it.id, it.title, it.imgResource)
                }
                ListScreen(
                    itemsList = categoriesList, onCardClick = {
                        viewModel.selectCategory(it)
                        navController.navigate(MyCityScreens.Recommendation.name)
                    }, modifier = Modifier.fillMaxSize()
                )
            }
            composable(route = MyCityScreens.Recommendation.name) {
                val recommendationList = uiState.value.selectedCategory?.recommendations?.map {
                    ListItem(it!!.id, it.title, it.imgResource)
                } ?: listOf()

                if (showRecommendationWithDetails) {
                    ListScreenWithDetails(itemsList = recommendationList, onCardClick = {
                        viewModel.selectRecommendation(it)
                    }, recommendation = uiState.value.selectedRecommendation!!)
                } else {
                    ListScreen(itemsList = recommendationList, onCardClick = {
                        viewModel.selectRecommendation(it)
                        navController.navigate(MyCityScreens.Details.name)
                    })
                }
            }
            composable(route = MyCityScreens.Details.name) {
                DetailsScreen(recommendation = uiState.value.selectedRecommendation)
            }
        }
    }
}