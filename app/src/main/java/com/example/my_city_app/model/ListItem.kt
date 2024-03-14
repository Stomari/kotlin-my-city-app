package com.example.my_city_app.model

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes

data class ListItem(
    val id: Int,
    @StringRes val stringRes: Int,
    @DrawableRes val imgRes: Int
)
