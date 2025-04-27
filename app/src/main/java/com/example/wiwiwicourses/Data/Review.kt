package com.example.wiwiwicourses.Data

data class Review(
    val id: String = "",
    val courseKey: String = "",
    val userKey: String = "",
    val userName: String = "",
    val rating: Float = 0f,
    val text: String = "",
    val timestamp: Long = 0L
)
