package com.example.wiwiwicourses.ui.container

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.*
import com.example.wiwiwicourses.ui.components.BottomMenuItem
import com.example.wiwiwicourses.ui.screen.signin.SignInScreen
import com.example.wiwiwicourses.ui.screen.signup.SignUpScreen
import com.example.wiwiwicourses.ui.screen.home.HomeScreen
import com.example.wiwiwicourses.ui.screen.home.FavScreen
import com.example.wiwiwicourses.ui.screen.home.HomeViewModel
import com.example.wiwiwicourses.ui.screen.details.DetailsScreen
import com.example.wiwiwicourses.ui.screen.details.DetailsViewModel
import com.example.wiwiwicourses.ui.screen.details.ReviewRepo
import com.example.wiwiwicourses.ui.screen.home.UserProfileScreen
import com.example.wiwiwicourses.ui.screen.home.EditProfileScreen
import com.example.wiwiwicourses.ui.screen.home.SearchScreen

@Composable
fun ScreenContainer() {
    val navHost = rememberNavController()
    val viewModel: HomeViewModel = viewModel()

    NavHost(
        navController = navHost,
        startDestination = NavGraph.Login.route
    ) {
        composable(NavGraph.Login.route) {
            SignInScreen(
                viewModel = viewModel,
                onLoginClicked = {
                    navHost.navigate(NavGraph.Home.route)
                },
                onRegistrationClicked = {
                    navHost.navigate(NavGraph.Registration.route)
                }
            )
        }
        composable(NavGraph.Registration.route) {
            SignUpScreen(
                viewModel = viewModel,
                onRegisterClicked = {
                    navHost.navigate(NavGraph.Home.route)
                },
                onLoginClicked = {
                    navHost.navigate(NavGraph.Login.route)
                }
            )
        }
        navigation(
            startDestination = BottomMenuItem.Home.route,
            route = "main"
        ) {
            composable(BottomMenuItem.Home.route) {
                HomeScreen(viewModel = viewModel, navController = navHost)
            }
            composable(BottomMenuItem.Fav.route) {
                FavScreen(viewModel = viewModel, navController = navHost)
            }
            composable(BottomMenuItem.Account.route) {
                UserProfileScreen(viewModel = viewModel, navController = navHost)
            }
            composable("details/{courseKey}") { backStackEntry ->
                val courseKey = backStackEntry.arguments?.getString("courseKey")
                val course = viewModel.getCourseByKey(courseKey)
                val user = viewModel.currentUser
                if (course != null) {
                    DetailsScreen(course, navController = navHost, user)
                }
            }
            composable("editProfile") {
                EditProfileScreen(viewModel, navController = navHost)
            }
            composable("searchResults/{query}") { backStackEntry ->
                val query = backStackEntry.arguments?.getString("query") ?: ""
                SearchScreen(query, viewModel, navController = navHost)
            }
        }
    }
}