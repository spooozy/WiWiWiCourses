package com.example.wiwiwicourses.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wiwiwicourses.ui.components.BottomMenu
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Divider
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.input.VisualTransformation
import androidx.navigation.NavController
import com.example.wiwiwicourses.Data.FavCourse
import com.example.wiwiwicourses.R
import com.example.wiwiwicourses.ui.components.InputField
import com.example.wiwiwicourses.ui.components.SearchField
import com.example.wiwiwicourses.ui.theme.DarkBlue
import com.example.wiwiwicourses.ui.theme.TransparentDarkBlue

@Composable
fun HomeScreen(viewModel: HomeViewModel, navController: NavController) {
    val drawerState = rememberDrawerState(DrawerValue.Open)
    val coursesByCategory = viewModel.coursesByCategory.value
    val searchState = remember { mutableStateOf("") }

    ModalNavigationDrawer(
        drawerState = drawerState,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
        drawerContent = {}
    ) {
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            bottomBar = { BottomMenu(navController = navController) }
        ) { paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(paddingValues)
                    .padding(top = 10.dp)
            ) {
                item {
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
                }
                coursesByCategory.forEach { (category, courses) ->
                    item {
                        Box(
                            modifier = Modifier
                                .padding(18.dp)
                                .background(TransparentDarkBlue, shape = RoundedCornerShape(20.dp))
                                .padding(8.dp)
                        ) {
                            Text(
                                text = category.uppercase(),
                                fontSize = 14.sp,
                                fontWeight = FontWeight.Bold,
                                color = DarkBlue
                            )
                        }
                    }
                    item {
                        LazyRow(
                            modifier = Modifier
                                .fillMaxHeight()
                                .padding(bottom = 10.dp)
                        ) {
                            items(courses) { course ->
                                CourseListItem(course = course,
                                    modifier = Modifier.padding(horizontal = 10.dp),
                                    onFavClick = {
                                        val updatedCourses = courses.map { crs ->
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
                                        //viewModel.updateCourses(category, updatedCourses)
                                    },
                                    onCourseClick = { course ->
                                        navController.navigate("details/${course.key}")
                                    }
                                )
                            }
                        }
                    }
                    item {
                        Divider(
                            color = TransparentDarkBlue,
                            thickness = 1.dp,
                            modifier = Modifier.padding(horizontal = 50.dp)
                        )
                    }
                }
            }
        }
    }
}
