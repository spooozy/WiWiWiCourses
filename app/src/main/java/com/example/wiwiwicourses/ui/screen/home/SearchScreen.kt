package com.example.wiwiwicourses.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wiwiwicourses.Data.FavCourse
import com.example.wiwiwicourses.R
import com.example.wiwiwicourses.ui.components.BottomMenu
import com.example.wiwiwicourses.ui.components.SearchField
import com.example.wiwiwicourses.ui.theme.TransparentDarkBlue

@Composable
fun SearchScreen(query: String, viewModel: HomeViewModel, navController: NavController) {

    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val searchState = remember { mutableStateOf(query) }
    val results = viewModel.searchCourses(query)

    ModalNavigationDrawer(
        drawerState = drawerState,
        modifier = Modifier.fillMaxWidth().background(Color.White),
        drawerContent = {}
    ) {
        Scaffold(
            modifier = Modifier.fillMaxSize().background(Color.White),
            bottomBar = { BottomMenu(navController = navController) }
        ) { paddingValues ->
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues)
                    .background(
                        Color.White
                    )
            ) {
                SearchField(
                    modifier = Modifier.padding(horizontal = 16.dp),
                    visualTransformation = VisualTransformation.None,
                    trailingActionIconRes = R.drawable.ic_search,
                    placeholderText = "Search",
                    value = searchState.value,
                    height = 60,
                    roundedCornerShape = 20,
                    containerColor = TransparentDarkBlue,
                    onValueChange = {
                        searchState.value = it
                    },
                    onTrailingIconClick = {
                        if (searchState.value.isNotBlank()) {
                            navController.navigate("searchResults/${searchState.value}")
                        }
                    }
                )

                LazyVerticalGrid(
                    columns = GridCells.Fixed(2),
                    modifier = Modifier
                        .fillMaxSize(),
                    contentPadding = PaddingValues(bottom = 16.dp)
                ) {
                    items(results.size) { index ->
                        val course = results[index]
                        CourseListItem(course = course,
                            modifier = Modifier.padding(horizontal = 3.dp),
                            onFavClick = {
                                val updatedCourses =results.map { crs ->
                                    if (crs.id == course.id) {
                                        viewModel.onFavs(
                                            !crs.isFavorite,
                                            FavCourse(crs.key)
                                        )
                                        crs.copy(isFavorite = !crs.isFavorite)
                                    } else {
                                        crs
                                    }
                                }
                            },
                            onCourseClick = { course ->
                                navController.navigate("details/${course.key}")
                            }
                        )
                    }
                }
            }
        }
    }
}