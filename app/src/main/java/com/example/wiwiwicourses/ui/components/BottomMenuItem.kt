package com.example.wiwiwicourses.ui.components

import com.example.wiwiwicourses.R

sealed class BottomMenuItem(
    val route:String,
    val title:String,
    val icon: Int
) {
    object Fav : BottomMenuItem(
        route = "fav_screen",
        title = "Favs",
        icon = R.drawable.ic_favs
    )
    object Home : BottomMenuItem(
        route = "home_screen",
        title = "Home",
        icon = R.drawable.ic_home
    )
    object Account : BottomMenuItem(
        route = "acc_screen",
        title = "Account",
        icon = R.drawable.ic_me
    )
}