package com.example.consecutivep.components


import android.net.Uri
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.example.consecutivep.domain.repository.IProfileRepository
import com.example.consecutivep.presentation.profile.model.state.EditProfileState
import kotlinx.coroutines.launch


class EditProfileViewModel(
    private val repository: IProfileRepository
): ViewModel() {

    private val mutableState = MutableEditProfileState()
    val viewState = mutableState as EditProfileState

    init {
        viewModelScope.launch {
            repository.getProfile()?.let {
                mutableState.name = it.name
                mutableState.url = it.url
                mutableState.photoUri = Uri.parse(it.photoUri)
            }
        }
    }

    fun onNameChanged(name: String) {
        mutableState.name = name
    }

    fun onUrlChanged(url: String) {
        mutableState.url = url
    }

    fun onDoneClicked() {
        viewModelScope.launch {
            repository.setProfile("", viewState.name, viewState.url)
        }
    }

    private class MutableEditProfileState : EditProfileState {
        override var photoUri: Uri by mutableStateOf(Uri.EMPTY)
        override var name by mutableStateOf("")
        override var url by mutableStateOf("")
    }
}