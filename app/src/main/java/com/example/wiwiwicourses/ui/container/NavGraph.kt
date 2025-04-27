package com.example.wiwiwicourses.ui.container

sealed class NavGraph(val route: String) {
    data object Login: NavGraph(route = "login_screen")
    data object Registration: NavGraph(route = "registration_screen")
    data object Home: NavGraph(route = "home_screen")
    data object Favorites: NavGraph(route = "fav_screen")
    data object Account: NavGraph(route = "acc_screen")
}