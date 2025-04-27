package com.example.wiwiwicourses.Data

data class Course(
    val title:String = "",
    val description: String= "",
    val price: String = "",
    val category: String = "",
    val duration: String ="",
    val authors: String = "",
    val id: Int = 0,
    val images: CourseImages = CourseImages(),
    val isFavorite: Boolean = false,
    val key: String = "",
    val averageRating: Float = 0f,
    val totalReviews: Int = 0
)
