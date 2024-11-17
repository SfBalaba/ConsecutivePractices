package com.example.consecutivep.presentation.profile.screen


import android.widget.EditText
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.ArrowBack
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material.icons.filled.Done
import androidx.compose.material.icons.filled.Edit
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import coil.compose.AsyncImage
import com.example.consecutivep.R
import com.example.consecutivep.components.EditProfileViewModel

import org.koin.androidx.compose.koinViewModel

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun EditProfileScreen(navController: NavHostController) {

        val viewModel = koinViewModel<EditProfileViewModel>()
        val state = viewModel.viewState

        Scaffold(
            contentWindowInsets = WindowInsets(0.dp),
            topBar = {
                TopAppBar(
                    title = {
                        Text(text = "Реактирование")
                    },
                    navigationIcon = {
                        Icon(
                            imageVector = Icons.AutoMirrored.Filled.ArrowBack,
                            contentDescription = null,
                            Modifier
                                .padding(end = 8.dp)
                                .clickable { navController.popBackStack() }
                        )
                    },
                    actions = {
                        Icon(
                            imageVector = Icons.Default.Done,
                            contentDescription = null,
                            Modifier
                                .padding(end = 8.dp)
                                .clickable {
                                    navController.popBackStack()
                                    viewModel.onDoneClicked()
                                }
                        )
                    },
                    modifier = Modifier.shadow(elevation = 1.dp)
                )
            }) { padding ->
            Column(
                modifier = Modifier
                    .padding(padding)
                    .padding(horizontal = 16.dp)
                    .fillMaxWidth()
            ) {
                AsyncImage(
                    model = state.photoUri,
                    contentDescription = null,
                    modifier = Modifier
                        .clip(CircleShape)
                        .padding(top = 16.dp)
                        .size(128.dp),
                    error = painterResource(R.drawable.local_phone)
                )
                TextField(
                    value = state.name,
                    onValueChange = { viewModel.onNameChanged(it) },
                    label = { Text(stringResource(R.string.name)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                )
                //Text(text = "Старший разработчик", style = Typography.labelMedium)
                TextField(
                    value = state.url,
                    onValueChange = { viewModel.onUrlChanged(it) },
                    label = { Text(stringResource(R.string.link)) },
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(top = 16.dp)
                )
            }
        }
    }
