package ru.jocks.data.reviews

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import ru.jocks.data.places.models.PlaceDetails
import ru.jocks.data.reviews.models.ReviewFields
import ru.jocks.data.reviews.models.ReviewRequest
import ru.jocks.data.reviews.models.ReviewResponse

interface ReviewsApi {
    @GET("api/producer/check/{check_id}/")
    suspend fun getFields(
        @Path("check_id") id: Int,
    ) : Response<ReviewFields>

    @POST("api/user/review/")
    suspend fun sendReview(
        @Body body : ReviewRequest
    ) : Response<ReviewResponse>



}
