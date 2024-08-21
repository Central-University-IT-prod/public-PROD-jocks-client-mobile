package ru.jocks.data.reviews.repository

import org.json.JSONException
import org.json.JSONObject
import org.koin.java.KoinJavaComponent
import ru.jocks.data.api.RetrofitClient
import ru.jocks.data.auth.models.TokensModel
import ru.jocks.data.places.models.PlaceItem
import ru.jocks.data.reviews.models.ReviewField
import ru.jocks.data.reviews.models.ReviewFields
import ru.jocks.data.reviews.models.ReviewRequest
import ru.jocks.data.reviews.models.ReviewResponse
import ru.jocks.domain.review.models.Creditionals
import ru.jocks.domain.review.repository.UserRepository
import timber.log.Timber

class ReviewsRepository {
    private val reviewsApi = RetrofitClient.reviewsApiService
    private val userRepository : UserRepository by KoinJavaComponent.inject(UserRepository::class.java)

    suspend fun getFields(
       check : Int
    ) : ReviewFields? {
        return reviewsApi.getFields(check).body()
    }

    suspend fun sendReview(
        baseReviewFields: List<ReviewField>,
        goodsReviewFields: List<ReviewField>,
        businessId : Int,
        checkId : Int
    ) : ReviewResponse? {
        val response =  reviewsApi.sendReview(ReviewRequest(
            baseFields = baseReviewFields,
            goodsFields = goodsReviewFields,
            businessId = businessId,
            customerId = userRepository.getSavedUser()!!.userId,
            checkId = checkId
        ))

        return if (response.isSuccessful) {
            response.body()
        } else {
            val encodedJson = response.errorBody()?.string()
            try {
                var error = ""
                if (encodedJson != null) {
                    error = JSONObject(encodedJson).get("error") as String
                }
                throw Exception(error)
            } catch (e: JSONException) {
                throw e
            }
        }
    }
}