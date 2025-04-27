package com.example.wiwiwicourses.ui.screen.details

import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.pager.HorizontalPager
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.example.wiwiwicourses.ui.components.BottomMenu
import androidx.compose.foundation.pager.rememberPagerState
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Divider
import androidx.compose.material3.Text
import androidx.compose.runtime.collectAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import coil.compose.AsyncImage
import com.example.wiwiwicourses.Data.Course
import com.example.wiwiwicourses.ui.theme.TransparentDarkBlue
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.text.TextPainter
import com.example.wiwiwicourses.Data.User

@Composable
fun DetailsScreen(
    course: Course,
    navController: NavController,
    user: User?
    )
{
    val drawerState = rememberDrawerState(DrawerValue.Open)
    val images = mutableListOf(course.images.cover).apply {
        addAll(course.images.additional)
    }

    val detailsViewModel: DetailsViewModel = remember {
        DetailsViewModel(course.key, reviewRepository = ReviewRepo())
    }

    var reviewText = remember { mutableStateOf("") }
    var rating = remember { mutableStateOf(0f) }

    val averageRating = detailsViewModel.averageRating
    val totalReviews = detailsViewModel.totalReviews

    val reviews = detailsViewModel.reviews.value


    ModalNavigationDrawer(
        drawerState = drawerState,
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White),
        drawerContent = {}
    ){
        Scaffold(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.White),
            bottomBar = { BottomMenu(navController = navController) }
        ){ paddingValues ->
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.White)
                    .padding(paddingValues)
                    .padding(top = 10.dp)
            ){
                item{
                    CourseImageCarousel(images)
                }
                item{
                    Spacer(modifier = Modifier.height(15.dp))
                    Divider(
                        color = TransparentDarkBlue,
                        thickness = 4.dp,
                        modifier = Modifier.padding(horizontal = 150.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))
                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    )
                    {
                        Text(
                            text = course.title,
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(16.dp)
                        )
                    }

                    Spacer(modifier = Modifier.height(10.dp))

                    Divider(
                        color = TransparentDarkBlue,
                        thickness = 1.dp,
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("Authors: ")
                            }
                            append(course.authors)
                        },
                        fontSize = 16.sp,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Divider(
                        color = TransparentDarkBlue,
                        thickness = 1.dp,
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("Category: ")
                            }
                            append(course.category)
                        },
                        fontSize = 16.sp,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Divider(
                        color = TransparentDarkBlue,
                        thickness = 1.dp,
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("Duration: ")
                            }
                            append(course.duration)
                        },
                        fontSize = 16.sp,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Divider(
                        color = TransparentDarkBlue,
                        thickness = 1.dp,
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("Price: ")
                            }
                            append(course.price)
                        },
                        fontSize = 16.sp,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Divider(
                        color = TransparentDarkBlue,
                        thickness = 1.dp,
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Text(
                        text = buildAnnotatedString {
                            withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                append("Description: ")
                            }
                            append(course.description)
                        },
                        fontSize = 18.sp,
                        modifier = Modifier.padding(horizontal = 16.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Divider(
                        color = TransparentDarkBlue,
                        thickness = 1.dp,
                        modifier = Modifier.padding(horizontal = 10.dp)
                    )

                    Spacer(modifier = Modifier.height(10.dp))
                }
                item {
                    Column {
                        ReviewForm(
                            onReviewChange = { reviewText.value = it },
                            onRatingChange = { rating.value = it },
                            onCommentButtonClick = {
                                Log.d("User info", "${reviewText.value}, ${rating.value}, ${user?.id}, ${user?.name}")
                                detailsViewModel.addReview(reviewText.value, rating.value, user)
                            }
                        )
                        if (reviews.isEmpty()) {
                            Text(
                                text = "No reviews yet",
                                modifier = Modifier.padding(horizontal = 16.dp),
                                fontSize = 16.sp
                            )
                        } else {
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                        append("Average rating: ")
                                    }
                                    append(averageRating.value.toString())
                                },
                                fontSize = 16.sp,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                            Text(
                                text = buildAnnotatedString {
                                    withStyle(style = SpanStyle(fontWeight = FontWeight.Bold)) {
                                        append("Total reviews: ")
                                    }
                                    append(totalReviews.value.toString())
                                },
                                fontSize = 16.sp,
                                modifier = Modifier.padding(horizontal = 16.dp)
                            )
                            Column {
                                reviews.forEach { review ->
                                    ReviewItem(review)
                                    Spacer(modifier = Modifier.height(8.dp))
                                }
                            }
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun CourseImageCarousel(images: List<String>) {
    val pagerState = rememberPagerState() {images.size}
    HorizontalPager(
        state = pagerState,
        modifier = Modifier.fillMaxWidth()
    ) { page ->
        AsyncImage(
            model = images[page],
            contentDescription = "Course image",
            modifier = Modifier
                .fillMaxWidth()
                .height(350.dp),
            contentScale = ContentScale.Crop
        )
    }
}