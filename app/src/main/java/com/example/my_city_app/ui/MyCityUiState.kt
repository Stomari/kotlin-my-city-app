package com.example.my_city_app.ui

import com.example.my_city_app.model.Category
import com.example.my_city_app.model.Recommendation

data class MyCityUiState(
    val selectedCategory: Category? = null,
    val selectedRecommendation: Recommendation? = null,
) {
}