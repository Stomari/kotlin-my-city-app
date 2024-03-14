package com.example.my_city_app.data

import com.example.my_city_app.R
import com.example.my_city_app.model.Category
import com.example.my_city_app.model.Recommendation

object CategoriesData {
    fun loadCategories(): List<Category> {
        return listOf(
            Category(
                id = 1,
                imgResource = R.drawable.restaurant,
                title = R.string.restaurants,
                recommendations = listOf(
                    Recommendation(
                        id = 1,
                        title = R.string.manga,
                        imgResource = R.drawable.acai,
                        description = R.string.lorem_ipsum
                    )
                )
            ),
            Category(
                id = 2,
                imgResource = R.drawable.bars,
                title = R.string.bars,
                recommendations = listOf(
                    Recommendation(
                        id = 2,
                        title = R.string.manga,
                        imgResource = R.drawable.restaurant,
                        description = R.string.lorem_ipsum
                    )
                )
            ),
            Category(
                id = 3,
                imgResource = R.drawable.mall,
                title = R.string.malls,
                recommendations = listOf(
                    Recommendation(
                        id = 3,
                        title = R.string.manga,
                        imgResource = R.drawable.restaurant,
                        description = R.string.lorem_ipsum
                    )
                )
            )
        )
    }
}