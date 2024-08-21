package ru.jocks.swipecsad.presentation.ui.screens.map

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.yandex.mapkit.geometry.Point
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent
import ru.jocks.data.location.LocationRepository
import ru.jocks.data.places.models.PlaceItem
import ru.jocks.data.places.repository.PlaceRepository
import ru.jocks.domain.location.LocationModel
import timber.log.Timber
import kotlin.math.atan2
import kotlin.math.cos
import kotlin.math.sin
import kotlin.math.sqrt

sealed interface LocationUiState {
    data class Success(val location: LocationModel) : LocationUiState
    data object Error : LocationUiState
    data object Loading : LocationUiState
}

sealed interface PlacesUiState {
    data class Success(val places: List<PlaceItem>) : PlacesUiState
    data object NothingToLoad : PlacesUiState
    data object Loading : PlacesUiState
}

class MapViewModel : ViewModel()  {

    private val placesRepository : PlaceRepository by KoinJavaComponent.inject(PlaceRepository::class.java)

    private val locationRepository : LocationRepository by KoinJavaComponent.inject(
        LocationRepository::class.java
    )

    private var lastZoomPosition = Point(0.0, 0.0)

    var locationUiState : LocationUiState by mutableStateOf(LocationUiState.Loading)
        private set

    var recommendationsUiState : PlacesUiState by mutableStateOf(PlacesUiState.NothingToLoad)
        private set


    init {
        getLocation()
    }

    fun getRecommendations(lat : Double, long: Double) {
        viewModelScope.launch {
            recommendationsUiState = PlacesUiState.Loading
            recommendationsUiState = try {
                PlacesUiState.Success(placesRepository.getPlaces(lat= lat, long = long))
            } catch (e : Exception) {
                PlacesUiState.NothingToLoad
            }
        }
    }

    fun getLocation() {
        viewModelScope.launch {
            locationUiState = LocationUiState.Loading
            locationUiState = try {
                LocationUiState.Success(locationRepository.getLocation())
            } catch (e : Exception) {
                Timber.e(e)
                LocationUiState.Error
            }
        }
    }

    private fun distanceInMeters(point1: Point, point2: Point): Double {
        val earthRadius = 6371000 // Радиус Земли в метрах
        val dLat = Math.toRadians(point2.latitude - point1.latitude)
        val dLon = Math.toRadians(point2.longitude - point1.longitude)

        val a = sin(dLat/2) * sin(dLat/2) +
                cos(Math.toRadians(point1.latitude)) * cos(Math.toRadians(point2.latitude)) *
                sin(dLon/2) * sin(dLon/2)
        val c = 2 * atan2(sqrt(a), sqrt(1-a))

        return earthRadius * c
    }

    private fun isInRadius(point1: Point, point2: Point, radiusInMeters: Double): Boolean {
        val distance = distanceInMeters(point1, point2)
        return distance <= radiusInMeters
    }


    fun checkNeedLoad(newPosition : Point): Boolean {
        val need = !isInRadius(lastZoomPosition, newPosition, 5000.0)
        if (need) {
            lastZoomPosition = newPosition
        }
        return need
    }
}