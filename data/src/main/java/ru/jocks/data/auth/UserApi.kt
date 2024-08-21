package ru.jocks.data.auth

import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import ru.jocks.data.auth.models.TokensModel
import ru.jocks.domain.review.models.PromoCode
import ru.jocks.domain.review.models.UserLoginModel
import ru.jocks.domain.review.models.UserProfileModel
import ru.jocks.domain.review.models.UserRegistrationModel

interface UserApi {
    @POST("/api/user/auth/register")
    suspend fun registerUser(@Body request: UserRegistrationModel): Response<UserProfileModel>

    @POST("/api/user/auth/signin")
    suspend fun loginUser(@Body request: UserLoginModel): Response<TokensModel>

    @GET("/api/user/{id}/promocodes")
    suspend fun getPromocodes(@Path("id") id: Int): Response<List<PromoCode>>

}