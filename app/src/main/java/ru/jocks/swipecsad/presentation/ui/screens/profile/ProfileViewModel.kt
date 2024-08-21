package ru.jocks.swipecsad.presentation.ui.screens.profile

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch
import ru.jocks.domain.review.models.PromoCode
import ru.jocks.domain.review.repository.UserRepository

sealed interface PromocodesUiState {
    data class Success(val promocodes: List<PromoCode>) : PromocodesUiState
    data object Loading : PromocodesUiState

    data object Error : PromocodesUiState
}

class ProfileViewModel(private val userRepository: UserRepository) : ViewModel() {

    val userName : String? = userRepository.getSavedUser()?.username

    var promocodesUiState : PromocodesUiState by mutableStateOf(PromocodesUiState.Loading)
        private set

    init {
        getPromocodes()
    }

    fun getPromocodes() = viewModelScope.launch {
        promocodesUiState = PromocodesUiState.Loading
        promocodesUiState = try {
            PromocodesUiState.Success(userRepository.getPromocodes())
        } catch (_ : Exception) {
            PromocodesUiState.Error
        }
    }

    fun logout() = viewModelScope.launch {
        userRepository.logout()
    }
}