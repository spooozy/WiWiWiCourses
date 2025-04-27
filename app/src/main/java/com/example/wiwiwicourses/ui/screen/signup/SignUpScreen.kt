package com.example.wiwiwicourses.ui.screen.signup

import android.util.Log
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.content.MediaType.Companion.Text
import androidx.compose.foundation.gestures.scrollBy
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.ime
import androidx.compose.foundation.layout.imePadding
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.systemBarsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.example.wiwiwicourses.R
import com.example.wiwiwicourses.ui.components.ActionButton
import com.example.wiwiwicourses.ui.components.Separator
import com.example.wiwiwicourses.ui.components.InputField
import com.example.wiwiwicourses.ui.components.Message
import com.example.wiwiwicourses.ui.screen.home.HomeViewModel
import com.example.wiwiwicourses.ui.theme.ColdWhite
import com.example.wiwiwicourses.ui.theme.LightOrange
import com.example.wiwiwicourses.ui.theme.LightPink
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import kotlinx.coroutines.launch

@Composable
fun SignUpScreen(
    modifier: Modifier = Modifier,
    viewModel: HomeViewModel,
    onRegisterClicked: () -> Unit,
    onLoginClicked: () -> Unit
){

    val email = remember { mutableStateOf("") }
    val password = remember { mutableStateOf("") }
    val errorState = remember { mutableStateOf("") }

    val scrollState = rememberScrollState()
    val coroutineScope = rememberCoroutineScope()
    val keyboardHeight = WindowInsets.ime.getBottom(LocalDensity.current)
    val backgroundGradient: Array<Pair<Float, Color>> = arrayOf(
        0f to ColdWhite,
        1f to LightPink
    )

    LaunchedEffect(keyboardHeight) {
        coroutineScope.launch {
            scrollState.scrollBy(keyboardHeight.toFloat())
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(Brush.verticalGradient(*backgroundGradient))
            .systemBarsPadding()
            .verticalScroll(scrollState)
            .imePadding(),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Image(
            painter = painterResource(R.drawable.cat_glasses),
            contentDescription = null,
            modifier = Modifier
                .size(320.dp)
                .padding(start = 20.dp)
        )
        Message(
            title = "Hi there!",
            subtitle = "Let's Get Started"
        )
        Spacer(
            modifier = Modifier.height(8.dp)
        )
        InputField(
            leadingIconRes = R.drawable.ic_person,
            placeholderText = "You email",
            modifier = Modifier.padding(horizontal = 24.dp),
            value = email.value,
            height = 62,
            roundedCornerShape = 30,
            containerColor = ColdWhite,
            onValueChange = {email.value = it}
        )
        Spacer(
            modifier = Modifier.height(10.dp)
        )
        InputField(
            leadingIconRes = R.drawable.ic_key,
            placeholderText = "Password",
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.padding(horizontal = 24.dp),
            value = password.value,
            height = 62,
            roundedCornerShape = 30,
            containerColor = ColdWhite,
            onValueChange = {password.value = it}
        )
        if (errorState.value.isNotEmpty()) {
            Spacer(
                modifier = Modifier.height(10.dp)
            )
            Text(
                text = errorState.value,
                color = LightOrange,
                textAlign = TextAlign.Center,
                fontWeight = FontWeight.Black
            )
        }
        Spacer(
            modifier = Modifier.height(10.dp)
        )
        ActionButton(
            text = "Create an Account",
            isNavigationArrowVisible = true,
            onClicked = {
                SignUpCheck(viewModel,
                    email.value,
                    password.value,
                    onRegisterClicked,
                    onFailure = { error ->
                        errorState.value = error
                    })
            },
            colors = ButtonDefaults.buttonColors(
                containerColor = LightOrange,
                contentColor = Color.White
            ),
            shadowColor = LightOrange,
            modifier = Modifier.padding(horizontal = 24.dp)
        )

        Separator(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = 40.dp)
                .height(62.dp)
        )
        ActionButton(
            text = "Log In",
            isNavigationArrowVisible = false,
            onClicked = onLoginClicked,
            colors = ButtonDefaults.buttonColors(
                containerColor = LightOrange,
                contentColor = Color.White
            ),
            shadowColor = LightOrange,
            modifier = Modifier
                .padding(horizontal = 24.dp)
                .padding(bottom = 24.dp)
        )
    }
}

private fun SignUpCheck(viewModel: HomeViewModel,
                        email:String,
                        password:  String,
                        onSuccess: () -> Unit,
                        onFailure: (String) -> Unit) {

    if(email.isBlank() || password.isBlank())
    {
        onFailure("Empty fields")
        return
    }

    viewModel.signUpUser(
        email, password, { success, errorMessage ->
            if(success){
                onSuccess()
            }
            else{
                onFailure(errorMessage.toString())
            }
        }
    )
}
