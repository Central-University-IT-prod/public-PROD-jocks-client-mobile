package ru.jocks.swipecsad.presentation.ui.screens.registration

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.jocks.domain.review.repository.UserRepository


sealed interface RegistrationState {
    data object None : RegistrationState
    data object Success : RegistrationState
    data object Loading : RegistrationState
    data class Error(val message : String) : RegistrationState
}

class RegistrationViewModel(private val businessRepository: UserRepository) : ViewModel() {
    var registerState : RegistrationState by mutableStateOf(RegistrationState.None)
        private set


    fun register(
        email: String,
        username: String,
        password: String
    ) = viewModelScope.launch {
        registerState = RegistrationState.Loading

        registerState = try {
            businessRepository.register(
                email,
                username,
                password,
            )

            RegistrationState.Success
        } catch (e: Exception) {
            RegistrationState.Error(e.message ?: "Unknown error")
        }
    }
}