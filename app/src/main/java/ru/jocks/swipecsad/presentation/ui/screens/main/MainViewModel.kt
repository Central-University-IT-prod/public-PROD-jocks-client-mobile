package ru.jocks.swipecsad.presentation.ui.screens.main

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.cachedIn
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.FlowPreview
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.debounce
import kotlinx.coroutines.flow.flatMapLatest
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import org.koin.java.KoinJavaComponent.inject
import ru.jocks.data.location.LocationRepository
import ru.jocks.data.places.repository.PlaceRepository
import ru.jocks.swipecsad.presentation.ui.screens.map.PlacesUiState
import ru.jocks.swipecsad.presentation.ui.screens.placeDetails.PlaceDetailsUiState
import kotlin.time.Duration.Companion.milliseconds

class MainViewModel : ViewModel() {
    private val locationRepository : LocationRepository by inject(LocationRepository::class.java)

    private val placesRepository : PlaceRepository by inject(PlaceRepository::class.java)

    private var search = ""

    var recommendationsUiState : PlacesUiState by mutableStateOf(PlacesUiState.NothingToLoad)
        private set

    init {
        getPlaces()
    }


    fun getPlaces() {
        viewModelScope.launch {
            recommendationsUiState = PlacesUiState.Loading

            val location = locationRepository.getLocation()

            recommendationsUiState = PlacesUiState.Success(
               placesRepository.getPlaces(
                   offset=0,
                   limit=100,
                   query = search.ifEmpty { null },
                   lat = if (location.lat!=0.0) location.lat else null,
                   long = if (location.long!=0.0) location.long else null
               )
           )
        }
    }

    fun setSearch(query: String) {
        search = query
        getPlaces()
    }

}