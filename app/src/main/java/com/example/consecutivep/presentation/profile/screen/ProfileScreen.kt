package com.example.consecutivep.presentation.profile.screen


import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.consecutivep.R
import com.example.consecutivep.components.ProfileViewModel

import org.koin.androidx.compose.koinViewModel


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ProfileScreen(navController: NavHostController)  {

        val viewModel = koinViewModel<ProfileViewModel>()
        val state = viewModel.viewState

        Scaffold(
            contentWindowInsets = WindowInsets(0.dp),
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = stringResource(R.string.profile))
                    },
                    actions = {
                        Icon(
                            imageVector = Icons.Default.Edit,
                            contentDescription = null,
                            Modifier
                                .padding(end = 8.dp)
                                .clickable { navController.navigate("edit")/*viewModel.onFavoritesClicked()*/ }
                        )
                    }
                )
            }) { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .fillMaxSize(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                AsyncImage(
                    model = state.photoUri,
                    contentDescription = null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .size(128.dp),
                    error = painterResource(R.drawable.photo)
                )
                Text(text = state.name, style = MaterialTheme.typography.bodyLarge)
                Text(text = "Старший разработчик", style = MaterialTheme.typography.bodyMedium)
                Button(onClick = { /**/ }) {
                    Text(text = "прикрепить резюме")
                }
            }
        }


}