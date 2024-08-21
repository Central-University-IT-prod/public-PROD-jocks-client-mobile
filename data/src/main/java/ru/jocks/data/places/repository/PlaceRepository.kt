package ru.jocks.data.places.repository

import ru.jocks.data.api.RetrofitClient
import ru.jocks.data.places.models.PlaceDetails
import ru.jocks.data.places.models.PlaceItem

class PlaceRepository {

    private val placesApi = RetrofitClient.placesApiService

    suspend fun getPlaces(
        lat : Double? = null,
        long : Double? = null,
        query : String? = null,
        limit : Int? = null,
        offset : Int? = null
    ) : List<PlaceItem> {
        placesApi.getCompanyList(
            lat = lat,
            long = long,
            query = query,
            limit = limit,
            offset = offset
        ).let {
            return it.body()?.items ?: emptyList()
        }
    }


    suspend fun getDetails(
        id : Int
    ) : PlaceDetails {
        return placesApi.getCompanyDetails(
            id
        ).body()!!
    }

}