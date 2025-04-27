package com.example.wiwiwicourses.ui.screen.details

import android.util.Log
import com.example.wiwiwicourses.Data.Review
import com.google.firebase.firestore.FirebaseFirestore
import kotlinx.coroutines.tasks.await

class ReviewRepo {
    private val db = FirebaseFirestore.getInstance()

    suspend fun getReviews(courseId: String): Result<List<Review>> = try {

        val querySnapshot = db.collection("reviews")
            .whereEqualTo("courseKey", courseId)
            .get()
            .await()
        val reviews = querySnapshot.documents.mapNotNull { doc ->
            doc.toObject(Review::class.java)?.copy(id = doc.id)
        }
        Result.success(reviews)
    } catch (e: Exception) {
        Result.failure(e)
    }

    suspend fun addReview(review: Review): Result<Unit> {
        return try {
            db.collection("reviews")
                .add(review)
                .await()
            Result.success(Unit)
        } catch (e: Exception) {
            Result.failure(e)
        }
    }
}