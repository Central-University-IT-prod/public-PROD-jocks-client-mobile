package ru.jocks.data.places

import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query
import ru.jocks.data.places.models.PlaceDetails
import ru.jocks.data.places.models.PlaceItem
import ru.jocks.data.places.models.ResponsePlaces

interface PlacesApi {
    @GET("api/business/")
    suspend fun getCompanyList(
        @Query("lat") lat: Double?,
        @Query("long") long: Double?,
        @Query("query") query: String?,
        @Query("limit") limit: Int?,
        @Query("offset") offset: Int?,
    ) : Response<ResponsePlaces>

    @GET("api/business/{id}/")
    suspend fun getCompanyDetails(
        @Path("id") id: Int,
    ) : Response<PlaceDetails>
}
