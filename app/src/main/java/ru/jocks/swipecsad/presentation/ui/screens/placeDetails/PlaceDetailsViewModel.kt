package ru.jocks.swipecsad.presentation.ui.screens.placeDetails

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yandex.mapkit.geometry.Point
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent
import ru.jocks.data.location.LocationRepository
import ru.jocks.data.places.models.PlaceDetails
import ru.jocks.data.places.models.PlaceItem
import ru.jocks.data.places.repository.PlaceRepository
import ru.jocks.domain.location.LocationModel
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

sealed interface PlaceDetailsUiState {
    data class Success(val place: PlaceDetails) : PlaceDetailsUiState
    data object Loading : PlaceDetailsUiState

    data object Error : PlaceDetailsUiState
}

class PlaceDetailsViewModel : ViewModel()  {

    private val placesRepository : PlaceRepository by KoinJavaComponent.inject(PlaceRepository::class.java)

    var placeUiState : PlaceDetailsUiState by mutableStateOf(PlaceDetailsUiState.Loading)
        private set


    fun getPlace(id : Int) {
        viewModelScope.launch {
            placeUiState = PlaceDetailsUiState.Loading
            placeUiState = try {
                PlaceDetailsUiState.Success(placesRepository.getDetails(id))
            } catch (e : Exception) {
                PlaceDetailsUiState.Error
            }
        }
    }
}