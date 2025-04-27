package com.example.wiwiwicourses.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.FavoriteBorder
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.example.wiwiwicourses.Data.FavCourse
import com.example.wiwiwicourses.R
import com.example.wiwiwicourses.ui.components.BottomMenu
import com.example.wiwiwicourses.ui.components.InputField
import com.example.wiwiwicourses.ui.components.SearchField
import com.example.wiwiwicourses.ui.theme.DarkBlue
import com.example.wiwiwicourses.ui.theme.TransparentDarkBlue

@Composable
fun FavScreen(viewModel: HomeViewModel, navController: NavController) {
    val drawerState = rememberDrawerState(DrawerValue.Closed)
    val searchState = remember { mutableStateOf("") }
    val allFavCourses = viewModel.allFavCourses.value


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
                    .padding(top = 10.dp)
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

                if (allFavCourses.isEmpty()) {
                    EmptyFavorites(
                        modifier = Modifier.fillMaxSize(),
                        onExploreClick = {
                            navController.navigate("home_screen")
                        }
                    )
                } else {
                    LazyVerticalGrid(
                        columns = GridCells.Fixed(2),
                        modifier = Modifier
                            .fillMaxSize()
                            /*.padding(horizontal = 5.dp)*/,
                        contentPadding = PaddingValues(bottom = 16.dp)
                    ) {
                        items(allFavCourses.size) { index ->
                            val course = allFavCourses[index]
                            CourseListItem(course = course,
                                modifier = Modifier.padding(horizontal = 3.dp),
                                onFavClick = {
                                    val updatedCourses = allFavCourses.map { crs ->
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
}

@Composable
fun EmptyFavorites(
    modifier: Modifier = Modifier,
    onExploreClick: () -> Unit = {}
) {
    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        Icon(
            imageVector = Icons.Outlined.FavoriteBorder,
            contentDescription = null,
            modifier = Modifier.size(64.dp),
            tint = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.6f)
        )

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = "No favorite courses",
            style = MaterialTheme.typography.titleMedium,
            color = MaterialTheme.colorScheme.onSurface.copy(alpha = 0.8f)
        )

        Spacer(modifier = Modifier.height(24.dp))

        Button(
            onClick = onExploreClick,
            modifier = Modifier.width(200.dp),
            colors = ButtonDefaults.buttonColors(containerColor = DarkBlue)
        ) {
            Text("Add course")
        }
    }
}