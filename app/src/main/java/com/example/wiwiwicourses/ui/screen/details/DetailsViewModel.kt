package com.example.wiwiwicourses.ui.screen.details

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.wiwiwicourses.Data.Course
import com.example.wiwiwicourses.Data.Review
import com.example.wiwiwicourses.Data.User
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch

class DetailsViewModel (
    private val courseKey: String,
    private val reviewRepository: ReviewRepo
) : ViewModel (){

    private val _reviews = mutableStateOf<List<Review>>(emptyList())
    val reviews: State<List<Review>> = _reviews

    private val _averageRating = mutableStateOf(0f)
    val averageRating: State<Float> = _averageRating

    private val _totalReviews = mutableStateOf(0)
    val totalReviews: State<Int> = _totalReviews


    private val _isLoading = MutableStateFlow(false)

    init {
        loadReviews()
    }

    fun loadReviews() {
        if (_isLoading.value) return

        _isLoading.value = true
        viewModelScope.launch {
            reviewRepository.getReviews(courseKey)
                .onSuccess { reviews ->
                    _isLoading.value = false
                    if (reviews.isEmpty()) {
                        Log.d("User info", "Empty List")
                        _averageRating.value = 0f
                        _totalReviews.value = 0
                    } else {
                        Log.d("User info", "$courseKey")
                        calculateAverageRating(reviews)
                        _reviews.value = reviews
                    }
                }
                .onFailure {
                    _isLoading.value = false
                    Log.d("User info", "Failed To Load Comments")
                }
        }
    }

    private fun calculateAverageRating(reviews: List<Review>) {
        val totalRating = reviews.sumOf { it.rating.toDouble() }
        _totalReviews.value = reviews.size
        _averageRating.value = if (reviews.isNotEmpty()) (totalRating / reviews.size).toFloat() else 0f
    }

    fun addReview(text: String, rating: Float, user: User?) {
        viewModelScope.launch {
            val review = Review(
                courseKey = courseKey,
                userKey = user?.id.toString(),
                userName =user?.name ?: "Anonim",
                rating = rating,
                text = text,
                timestamp = System.currentTimeMillis()
            )

            reviewRepository.addReview(review)
                .onSuccess {
                    loadReviews()
                }
                .onFailure {
                    Log.d("User info", "Failed To Add Comment")
                }
        }
    }


}