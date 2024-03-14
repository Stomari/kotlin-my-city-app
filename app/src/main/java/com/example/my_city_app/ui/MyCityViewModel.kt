package com.example.my_city_app.ui

import androidx.lifecycle.ViewModel
import com.example.my_city_app.data.CategoriesData
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.update

class MyCityViewModel : ViewModel() {

    private val _uiState = MutableStateFlow(MyCityUiState())
    val uiState: StateFlow<MyCityUiState> = _uiState

    private val categories = CategoriesData.loadCategories()

    fun selectCategory(id: Int) {
        val selectedCategory = categories.filter { it.id == id }[0]
        _uiState.update { currentState ->
            currentState.copy(
                selectedCategory = selectedCategory,
                selectedRecommendation = selectedCategory.recommendations[0]
            )
        }
    }

    fun selectRecommendation(id: Int) {
        _uiState.update { currentState ->
            currentState.copy(
                selectedRecommendation = _uiState.value.selectedCategory?.recommendations?.filter { it?.id == id }
                    ?.get(0)
            )
        }
    }
}