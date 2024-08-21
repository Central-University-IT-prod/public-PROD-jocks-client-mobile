package ru.jocks.swipecsad.presentation.ui.screens.login

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.jocks.domain.review.repository.UserRepository


sealed interface LoginState {
    data object None : LoginState
    data object Success : LoginState
    data object Loading : LoginState
    data class Error(val message : String) : LoginState
}
class LoginViewModel(private val businessRepository: UserRepository) : ViewModel() {
    var loginState : LoginState by mutableStateOf(LoginState.None)
        private set


    fun login(
        email: String,
        password: String
    ) = viewModelScope.launch {
        loginState = LoginState.Loading

        loginState = try {
            businessRepository.login(
                email,
                password
            )

            LoginState.Success
        } catch (e: Exception) {
            LoginState.Error(e.message ?: "Unknown error")
        }
    }
}