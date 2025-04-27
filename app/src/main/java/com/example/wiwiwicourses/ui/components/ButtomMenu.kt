package com.example.wiwiwicourses.ui.components
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.navigation.NavController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.example.wiwiwicourses.ui.theme.Black
import com.example.wiwiwicourses.ui.theme.TransparentDarkBlue

@Composable
fun BottomMenu(navController: NavController){
    val items = listOf(
        BottomMenuItem.Fav,
        BottomMenuItem.Home,
        BottomMenuItem.Account
    )

    val selectedItem = remember { mutableStateOf("Home") }
    val navBackStackEntry = navController.currentBackStackEntryAsState()

    NavigationBar(containerColor = Color.White) {
        items.forEach { item ->
            val isSelected = navBackStackEntry.value?.destination?.route == item.route

            NavigationBarItem(
                selected = isSelected,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) {
                            saveState = true
                        }
                        launchSingleTop = true
                        restoreState = true
                    }
                },
                icon = {
                    Icon(
                        painter = painterResource(id = item.icon),
                        contentDescription = ""
                    )
                },
                label = {
                    Text(text = item.title, color = Black)
                },
                colors = NavigationBarItemDefaults.colors(
                    indicatorColor = TransparentDarkBlue
                )
            )
        }
    }
}