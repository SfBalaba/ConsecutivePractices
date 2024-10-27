package com.example.consecutivep

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.navigation.compose.rememberNavController
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.sp
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavController
import com.example.consecutivepracts.screens.HomeScreen
import com.example.consecutivepracts.screens.MovieDetailScreen
import com.example.consecutivepracts.screens.MovieListScreen
import com.example.consecutivepracts.screens.SettingsScreen

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
                MainScreen()
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreen() {
    val navController = rememberNavController()
    var currentDestination by remember { mutableStateOf("movies") }

    Scaffold(
        topBar = {
            TopAppBar(
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    androidx.compose.material3.Text("Фильмы")
                },
                actions = {
                    IconButton(onClick = { navController.popBackStack() }) {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = "Назад"
                        )
                    }
                },
            )
        },
        bottomBar = {
            if (!currentDestination.startsWith("movie_detail")) {
                BottomNavigationBar(navController = navController, currentDestination = currentDestination) {
                    currentDestination = it
                }
            }
        }
    ) { innerPadding ->
        NavHost(navController = navController, startDestination = "home", Modifier.padding(innerPadding)) {
            composable("movies") {
                currentDestination = "movies"
                MovieListScreen { movieId ->
                    navController.navigate("movie_detail/$movieId") {
                    }
                }
            }
            composable("movie_detail/{movieId}") { backStackEntry ->
                val movieId = backStackEntry.arguments?.getString("movieId")?.toInt() ?: 0
                currentDestination = "movie_detail"
                MovieDetailScreen(movieId, navController)
            }

            composable("home") {
                currentDestination = "home"
                HomeScreen()
            }
            composable("settings") {
                currentDestination = "settings"
                SettingsScreen()
            }

        }
    }
}

@Composable
fun BottomNavigationBar(navController: NavController, currentDestination: String, onDestinationChanged: (String) -> Unit) {
    BottomNavigation(
        backgroundColor = Color(0xFFD3D3D3),
        contentColor = Color(0xFF000000)
    ) {
        val items = listOf(
            BottomNavItem("Home", "home", R.drawable.home),
            BottomNavItem("Movies", "movies", R.drawable.list),
            BottomNavItem("Settings", "settings", R.drawable.settings)
        )

        items.forEach { item ->
            BottomNavigationItem(
                icon = { Icon(painter = painterResource(id = item.iconResId), contentDescription = item.title) },
                label = { Text(item.title) },
                selected = currentDestination == item.route,
                onClick = {
                    navController.navigate(item.route) {
                        popUpTo(navController.graph.startDestinationId) { saveState = true }
                        launchSingleTop = true
                        restoreState = true
                    }
                    onDestinationChanged(item.route)
                }
            )
        }
    }
}

data class BottomNavItem(val title: String, val route: String, val iconResId: Int)