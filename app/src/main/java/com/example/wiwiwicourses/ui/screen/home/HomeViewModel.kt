package com.example.wiwiwicourses.ui.screen.home

import android.util.Log
import androidx.compose.runtime.State
import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import com.example.wiwiwicourses.Data.Course
import com.example.wiwiwicourses.Data.FavCourse
import com.google.firebase.Firebase
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import com.google.firebase.firestore.firestore
import com.example.wiwiwicourses.Data.User
import com.google.firebase.auth.auth

class HomeViewModel : ViewModel() {
    private val db: FirebaseFirestore = Firebase.firestore
    private val auth: FirebaseAuth = Firebase.auth
    private val _coursesByCategory = mutableStateOf<Map<String, List<Course>>>(emptyMap())
    val coursesByCategory: State<Map<String, List<Course>>> = _coursesByCategory
    private val _uid = mutableStateOf("")
    var uid = auth.currentUser?.uid ?: ""
    private val _favCourses = mutableStateOf<List<FavCourse>>(emptyList())
    private val _favCoursesKeys = mutableStateOf<List<String>>(emptyList())
    private val _allFavCourses = mutableStateOf<List<Course>>(emptyList())
    val allFavCourses: State<List<Course>> = _allFavCourses
    var currentUser: User? = null

    init {
        //loadInitialData()
        //loadUserInfo()
    }

    fun existingUser(){
        loadUserInfo()
        loadInitialData()
    }

    fun signUpUser(
        email: String,
        password: String,
        onComplete: (Boolean, String?) -> Unit
    ){
        auth.createUserWithEmailAndPassword(email, password)
            .addOnCompleteListener { authTask ->
                if(authTask.isSuccessful){
                    uid = authTask.result?.user?.uid ?: ""
                    val newUser = User(
                        id = uid,
                        email = email
                    )
                    db.collection("users").document(uid)
                        .set(newUser)
                        .addOnSuccessListener {
                            db.collection("users").document(uid)
                                .collection("favs")
                                .document("initial")
                                .set(mapOf("created" to System.currentTimeMillis()))
                                .addOnSuccessListener {
                                    loadUserInfo()
                                    loadInitialData()
                                    onComplete(true, null)
                                }
                                .addOnFailureListener {
                                    onComplete(false, "Failed To Create Doc In Favs Collection")
                                }
                        }
                        .addOnFailureListener {
                            onComplete(false, "Failed To Create Favs Collection")
                        }
                }
                else {
                    onComplete(false, authTask.exception.toString())
                }
            }
    }

    fun signInUser(
        email: String,
        password: String,
        onComplete: (Boolean, String?) -> Unit
    ) {

        auth.signInWithEmailAndPassword(email, password)
            .addOnCompleteListener { task ->
                if (task.isSuccessful) {
                    val user = task.result?.user
                    uid = user?.uid ?: ""
                    Log.d("Auth", "User signed in: $uid")
                    loadUserInfo()
                    loadInitialData()
                    onComplete(true, null)
                } else {
                    val errorMessage = task.exception?.message ?: "Unknown error"
                    Log.e("Auth", "Sign-in failed: $errorMessage")
                    onComplete(false, errorMessage)
                }
            }

    }

    fun loadUserInfo(){
        getUserInfo { user ->
            currentUser = user
            if (user != null) {
                Log.d("User Info", "Name: ${user.name}, Email: ${user.email}")
            } else {
                Log.d("User Info", "User not found or error occurred")
            }
        }
    }

    fun getUserInfo(callback: (User?) -> Unit) {
        if (uid != "") {
            Log.d("User Info", "User if != null ${uid}")
            val userRef = FirebaseFirestore.getInstance().collection("users").document(uid)
            Log.d("User Info", "AHAHA")
            userRef.get().addOnSuccessListener { document ->
                Log.d("User Info", "Document exists")
                if (document != null && document.exists()) {
                    val user = document.toObject(User::class.java)
                    Log.d("User Info", "Get User")
                    user?.id = uid
                    callback(user)
                } else {
                    callback(null)
                }
            }.addOnFailureListener { exception ->
                Log.w("User info", "Error getting user", exception)
                callback(null)
            }
        } else {
            callback(null)
        }
    }

    private fun loadInitialData() {
        loadFavKeys()
        loadCourses()
    }

    private fun loadCourses() {
        Firebase.firestore.collection("courses")
            .get()
            .addOnSuccessListener { task ->
                val allCourses = task.toObjects(Course::class.java)
                val updatedCourses = allCourses.map { course ->
                    course.copy(isFavorite = _favCoursesKeys.value.contains(course.key))
                }
                _coursesByCategory.value = updatedCourses.groupBy { it.category }
                updateFavCourses()
            }
    }

    private fun loadFavKeys(){
        Firebase.firestore.collection("users")
            .document(uid.toString())
            .collection("favs")
            .get()
            .addOnSuccessListener { task ->
                _favCourses.value = task.toObjects(FavCourse::class.java)
                val favKeys = _favCourses.value.map { it.key }
                _favCoursesKeys.value = favKeys
            }
    }

    private fun updateFavCourses() {
        _favCoursesKeys.value = _favCourses.value.map { it.key }
        _allFavCourses.value = _coursesByCategory.value
            .flatMap { (_, courses) -> courses }
            .filter { _favCoursesKeys.value.contains(it.key) }
    }

    fun updateCourses(category: String, updatedCourses: List<Course>) {
        _coursesByCategory.value = _coursesByCategory.value.toMutableMap().apply {
            this[category] = updatedCourses
        }
    }

    fun getCourseByKey(courseKey: String?): Course? {
        return _coursesByCategory.value.values.flatten().firstOrNull { it.key == courseKey }
    }

    fun onFavs(
        isFav: Boolean,
        fav: FavCourse
    ){
        if (isFav){
            db.collection("users")
                .document(uid.toString())
                .collection("favs")
                .document(fav.key)
                .set(fav)
                .addOnSuccessListener {
                    _favCourses.value = _favCourses.value + fav
                    updateAllCoursesFavoriteStatus(fav.key, true)
                    updateFavCourses()
                }
        }
        else{
            db.collection("users")
                .document(uid.toString())
                .collection("favs")
                .document(fav.key)
                .delete()
                .addOnSuccessListener {
                    _favCourses.value = _favCourses.value.filter { it.key != fav.key }
                    updateAllCoursesFavoriteStatus(fav.key, false)
                    updateFavCourses()
                }
        }
    }

    private fun updateAllCoursesFavoriteStatus(courseKey: String, isFavorite: Boolean) {
        _coursesByCategory.value = _coursesByCategory.value.mapValues { (_, courses) ->
            courses.map { course ->
                if (course.key == courseKey) course.copy(isFavorite = isFavorite) else course
            }
        }
    }

    fun updateUserProfile(
        name: String,
        email: String,
        age: Int,
        about: String,
        mem: String,
        datebirth: String,
        telegram: String
    ) {
        if (uid != "") {
            val userRef = db.collection("users").document(uid.toString())
            val updatedUser = mapOf(
                "name" to name,
                "email" to email,
                "age" to age,
                "about" to about,
                "mem" to mem,
                "datebirth" to datebirth,
                "telegram" to telegram
            )
            userRef.update(updatedUser)
                .addOnSuccessListener {
                    Log.d("User Info", "User profile updated successfully")
                    loadUserInfo()
                    updateUserReviews(name)
                }
                .addOnFailureListener { e ->
                    Log.w("User Info", "Error updating user profile", e)
                }
        }
    }

    private fun updateUserReviews(newName: String) {
        val reviewsRef = db.collection("reviews").whereEqualTo("userKey", uid.toString())

        reviewsRef.get()
            .addOnSuccessListener { querySnapshot ->
                for (document in querySnapshot.documents) {
                    document.reference.update("userName", newName)
                        .addOnSuccessListener {
                            Log.d("Review Update", "User name updated in review: ${document.id}")
                        }
                        .addOnFailureListener { e ->
                            Log.w("Review Update", "Error updating user name in review", e)
                        }
                }
            }
            .addOnFailureListener { e ->
                Log.w("Review Update", "Error fetching reviews", e)
            }
    }

    fun logout() {
        FirebaseAuth.getInstance().signOut()
        freeUserData()
        Log.d("User Info", "User logged out")
    }

    fun deleteUser() {
        val user = auth.currentUser
        Log.d("User Info", "${uid}")
        db.collection("users").document(uid)
            .delete()
            .addOnSuccessListener {
                user?.delete()
                    ?.addOnSuccessListener {
                        freeUserData()
                        Log.d("User Info", "Deleted user")
                    }
                    ?.addOnFailureListener { e ->
                        Log.d("User Info", "Deleted collection, not user")
                    }
            }
            .addOnFailureListener { e ->
                Log.d("User Info", "Nope")
            }

    }

    fun freeUserData(){
        uid = ""
        currentUser = null
        _favCourses.value = emptyList()
        _favCoursesKeys.value = emptyList()
        _allFavCourses.value = emptyList()
    }

    fun searchCourses(query: String): List<Course> {
        val terms = query.lowercase().split(" ").filter { it.isNotBlank() }

        return _coursesByCategory.value.values.flatten().filter { course ->
            val title = course.title.lowercase()
            val desc = course.description?.lowercase() ?: ""

            terms.all { term ->
                title.contains(term) || desc.contains(term)
            }
        }
    }
}