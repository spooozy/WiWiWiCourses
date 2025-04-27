package com.example.wiwiwicourses

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import com.example.wiwiwicourses.ui.container.ScreenContainer
import com.example.wiwiwicourses.ui.screen.signin.SignInScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent{
            ScreenContainer()
        }
    }
}