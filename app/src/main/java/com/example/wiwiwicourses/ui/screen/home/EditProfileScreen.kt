package com.example.wiwiwicourses.ui.screen.home

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.DrawerValue
import androidx.compose.material3.ModalNavigationDrawer
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.rememberDrawerState
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.wiwiwicourses.R
import com.example.wiwiwicourses.ui.components.BottomMenu
import com.example.wiwiwicourses.ui.components.InputField
import com.example.wiwiwicourses.ui.theme.DarkBlue
import com.example.wiwiwicourses.ui.theme.TransparentDarkBlue

@Composable
fun EditProfileScreen(viewModel: HomeViewModel, navController: NavController) {
    val user = viewModel.currentUser
    val drawerState = rememberDrawerState(DrawerValue.Open)
    var name = remember { mutableStateOf(user?.name ?:"" ) }
    var email = remember { mutableStateOf(user?.email ?: "") }
    var age = remember { mutableStateOf(user?.age.toString()) }
    var about = remember { mutableStateOf(user?.about ?: "") }
    var mem = remember { mutableStateOf(user?.mem ?: "") }
    var datebirth = remember { mutableStateOf(user?.datebirth ?: "") }
    var telegram = remember { mutableStateOf(user?.telegram ?: "") }

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

                    Spacer(modifier = Modifier.height(10.dp))

                    Box(
                        modifier = Modifier
                            .fillMaxSize(),
                        contentAlignment = Alignment.Center
                    )
                    {
                        Text(
                            text = "Edit Profile",
                            fontSize = 20.sp,
                            fontWeight = FontWeight.Bold,
                            color = Color.Black,
                            textAlign = TextAlign.Center,
                            modifier = Modifier.padding(16.dp)
                        )
                    }

                    Divider(
                        color = TransparentDarkBlue,
                        thickness = 4.dp,
                        modifier = Modifier.padding(horizontal = 150.dp)
                    )
                    Spacer(modifier = Modifier.height(10.dp))

                    InputField(
                        leadingIconRes = R.drawable.short_arrow_r,
                        placeholderText = "Name",
                        visualTransformation = VisualTransformation.None,
                        modifier = Modifier.padding(horizontal = 16.dp),
                        value = name.value,
                        height = 60,
                        roundedCornerShape = 20,
                        containerColor = TransparentDarkBlue,
                        onValueChange = {name.value = it}
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    InputField(
                        leadingIconRes = R.drawable.short_arrow_r,
                        placeholderText = "Age",
                        visualTransformation = VisualTransformation.None,
                        modifier = Modifier.padding(horizontal = 16.dp),
                        value = age.value,
                        height = 60,
                        roundedCornerShape = 20,
                        containerColor = TransparentDarkBlue,
                        onValueChange = {age.value = it}
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    InputField(
                        leadingIconRes = R.drawable.short_arrow_r,
                        placeholderText = "My datebirth",
                        visualTransformation = VisualTransformation.None,
                        modifier = Modifier.padding(horizontal = 16.dp),
                        value = datebirth.value,
                        height = 60,
                        roundedCornerShape = 20,
                        containerColor = TransparentDarkBlue,
                        onValueChange = {datebirth.value = it}
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    InputField(
                        leadingIconRes = R.drawable.short_arrow_r,
                        placeholderText = "About me",
                        visualTransformation = VisualTransformation.None,
                        modifier = Modifier.padding(horizontal = 16.dp),
                        value = about.value,
                        height = 60,
                        roundedCornerShape = 20,
                        containerColor = TransparentDarkBlue,
                        onValueChange = {about.value = it}
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    InputField(
                        leadingIconRes = R.drawable.short_arrow_r,
                        placeholderText = "My favorite mem",
                        visualTransformation = VisualTransformation.None,
                        modifier = Modifier.padding(horizontal = 16.dp),
                        value = mem.value,
                        height = 60,
                        roundedCornerShape = 20,
                        containerColor = TransparentDarkBlue,
                        onValueChange = {mem.value = it}
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    InputField(
                        leadingIconRes = R.drawable.short_arrow_r,
                        placeholderText = "Telegram",
                        visualTransformation = VisualTransformation.None,
                        modifier = Modifier.padding(horizontal = 16.dp),
                        value = telegram.value,
                        height = 60,
                        roundedCornerShape = 20,
                        containerColor = TransparentDarkBlue,
                        onValueChange = {telegram.value = it}
                    )

                    Spacer(modifier = Modifier.height(10.dp))

                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(horizontal = 16.dp),
                        horizontalArrangement = Arrangement.SpaceAround
                    ) {
                        Button(
                            onClick = {
                                viewModel.updateUserProfile(name.value,
                                    user?.email ?: "", age.value.toInt(), about.value, mem.value, datebirth.value, telegram.value)
                                navController.navigate("home_screen")
                            },
                            modifier = Modifier.padding(end = 8.dp),
                            colors = ButtonDefaults.buttonColors(containerColor = DarkBlue)
                        ) {
                            Text("Save changes")
                        }

                        Button(
                            onClick = {
                                viewModel.deleteUser()
                                navController.navigate("registration_screen")
                            },
                            colors = ButtonDefaults.buttonColors(containerColor = DarkBlue),
                            modifier = Modifier.padding(start = 8.dp)
                        ) {
                            Text("Delete account")
                        }
                    }
                }
            }
        }
    }
}