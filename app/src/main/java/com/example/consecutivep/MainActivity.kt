package com.example.consecutivep

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.padding
import androidx.compose.material.BadgedBox
import androidx.compose.material3.Icon
import androidx.compose.material.BottomNavigation
import androidx.compose.material.BottomNavigationItem
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material3.Badge
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
import androidx.core.app.NotificationManagerCompat
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.NavController
import androidx.paging.compose.collectAsLazyPagingItems
import com.example.consecutivep.utils.LocalUtils.isFilter

import com.example.consecutivep.components.MovieViewModel
import com.example.consecutivep.presentation.model.MovieUiModel
import com.example.consecutivep.presentation.profile.channelManager.NotificationChannelManager
import com.example.consecutivep.presentation.profile.screen.EditProfileScreen
import com.example.consecutivep.presentation.profile.screen.ProfileScreen
import com.example.consecutivep.screens.FavoritesScreen
import com.example.consecutivepracts.screens.HomeScreen
import com.example.consecutivepracts.screens.MovieDetailScreen
import com.example.consecutivep.screens.MovieListScreen
import com.example.consecutivepracts.screens.SettingsScreen
import org.koin.androidx.compose.koinViewModel

class MainActivity : ComponentActivity() {
    private val channelManager: NotificationChannelManager by lazy {
        NotificationChannelManager(NotificationManagerCompat.from(this), this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        channelManager.createNotificationChannels()

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
    val viewModel: MovieViewModel = koinViewModel<MovieViewModel>()
    val state = viewModel.viewState
    viewModel.viewState.error?.let {
        Text(text = it)
    }
    Scaffold(
        topBar = {
            if (currentDestination != "profile" && currentDestination != "edit") {
                TopAppBar(

                    colors = TopAppBarDefaults.topAppBarColors(
                        containerColor = MaterialTheme.colorScheme.primaryContainer,
                        titleContentColor = MaterialTheme.colorScheme.primary,
                    ),
                    title = {
                        Text("Фильмы")
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

            }
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
                MovieListScreen(viewModel) { movieId ->
                    navController.navigate("movie_detail/$movieId") {
                    }
                }
            }
            composable("movie_detail/{movieId}") { backStackEntry ->
                currentDestination = "movie_detail"
                val id = backStackEntry.arguments?.getString("movieId")?.toLong()?: 0L

                val pagingItems = viewModel.pagedMovies.collectAsLazyPagingItems()
                val movie = (0 until pagingItems.itemCount)
                        .mapNotNull { index -> pagingItems[index] }
                    .find { it.id == id }

                if (movie != null) {
                    MovieDetailScreen(movie)
                    }
            }

            composable("home") {
                currentDestination = "home"
                HomeScreen()
            }
            composable("settings") {
                currentDestination = "settings"
                SettingsScreen(viewModel)
            }
            composable("favorites"){
                currentDestination = "favorites"
                FavoritesScreen(navController)
            }
            composable("profile"){
                currentDestination = "profile"
                ProfileScreen(navController)
            }
            composable("edit"){
                currentDestination = "edit"
                EditProfileScreen(navController)
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
            BottomNavItem("Settings", "settings", R.drawable.settings),
            BottomNavItem("favorites", "favorites", R.drawable.favorite),
            BottomNavItem("profile", "profile", R.drawable.profile)

        )

        items.forEach { item ->
            BottomNavigationItem(

                    icon = {
                        BadgedBox(
                            badge=  {
                                if(isFilter.value && item.route == "movies"){
                                    Badge()
                                }
                            }
                        ) {
                            Icon(painter = painterResource(id = item.iconResId), contentDescription = item.title)
                        }
                    },
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