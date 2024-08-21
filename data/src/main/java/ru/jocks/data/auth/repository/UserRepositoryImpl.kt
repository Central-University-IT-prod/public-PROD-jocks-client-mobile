package ru.jocks.data.auth.repository

import org.json.JSONException
import org.json.JSONObject
import org.koin.java.KoinJavaComponent
import ru.jocks.data.api.RetrofitClient
import ru.jocks.data.auth.models.TokensModel
import ru.jocks.domain.review.models.Creditionals
import ru.jocks.domain.review.models.PromoCode
import ru.jocks.domain.review.models.UserLoginModel
import ru.jocks.domain.review.models.UserRegistrationModel
import ru.jocks.domain.review.repository.UserRepository
import timber.log.Timber

class UserRepositoryImpl : UserRepository {
    private val apiClient = RetrofitClient.businessApiService
    private val localDataSource by KoinJavaComponent.inject<LocalDataSource>(
        LocalDataSource::class.java
    )

    override suspend fun register(email: String, username: String, password: String): Creditionals {
        try {
            val response = apiClient.registerUser(
                UserRegistrationModel(
                    email = email,
                    username = username,
                    password = password
                )
            )
            Timber.i("response registerUser $response")
            return if (response.isSuccessful) {
                val user = login(email, password)
                localDataSource.saveTokens(
                    TokensModel(
                        accessToken = user.accessToken,
                        userId = user.userId,
                        username = user.username
                    )
                )
                user
            } else {
                val encodedJson = response.errorBody()?.string()
                try {
                    var error = ""
                    if (encodedJson != null) {
                        error = JSONObject(encodedJson).get("reason") as String
                    }
                    throw Exception(error)
                } catch (e: JSONException) {
                    throw Exception(
                         "Unknown exception"
                    )
                }
            }
        } catch (e: Exception) {
            throw Exception(
                "Unknown exception"
            )
        }
    }

    override suspend fun login(email: String, password: String): Creditionals {
        val response = apiClient.loginUser(
            UserLoginModel(
                email,
                password
            )
        )

        Timber.i("response loginUser $response")

        val body = response.body() ?: throw Exception("Body is null")

        return if (response.isSuccessful) {
            Timber.i("loginBusiness business $body")
            localDataSource.saveTokens(
                TokensModel(
                    accessToken = body.accessToken,
                    userId = body.userId,
                    username = body.username
                )
            )

            Creditionals(
                body.accessToken,
                body.userId,
                body.username
            )

        } else {
            val encodedJson = response.errorBody()?.string()
            try {
                var error = ""
                if (encodedJson != null) {
                    error = JSONObject(encodedJson).get("reason") as String
                }
                throw Exception(error)
            } catch (e: JSONException) {
                throw e
            }
        }
    }

    override suspend fun getPromocodes(): List<PromoCode> {
        val user = localDataSource.getTokens()
        if ( user != null) {
            return apiClient.getPromocodes(user.userId).body() ?: emptyList()
        }

        return emptyList()
    }

    override fun getSavedUser(): Creditionals? {
        val user = localDataSource.getTokens()
        if ( user != null)
            return Creditionals(
                user.accessToken,
                user.userId,
                user.username
            )
        return null
    }

    override suspend fun logout() {
        localDataSource.deleteTokens()
    }

}