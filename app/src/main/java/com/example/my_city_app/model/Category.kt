package com.example.my_city_app.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class Category(
    val id: Int,
    @DrawableRes val imgResource: Int,
    @StringRes val title: Int,
    val recommendations: List<Recommendation?>
)
