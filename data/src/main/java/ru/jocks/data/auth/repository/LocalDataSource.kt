package ru.jocks.data.auth.repository

import android.content.Context
import android.content.SharedPreferences
import org.koin.java.KoinJavaComponent
import ru.jocks.data.auth.models.TokensModel
import ru.jocks.domain.review.models.Creditionals
import timber.log.Timber

class LocalDataSource(private val context : Context) {
    private val businessPreferences: SharedPreferences = context.getSharedPreferences("business", Context.MODE_PRIVATE)


    fun saveTokens(
        authTokenModel: TokensModel
    ) {
        businessPreferences.edit().putString(Constants.ACCESS_TOKEN, authTokenModel.accessToken).apply()
        businessPreferences.edit().putInt(Constants.BUSINESS_ID, authTokenModel.userId).apply()
        businessPreferences.edit().putString(Constants.USERNAME, authTokenModel.username).apply()
    }

    fun deleteTokens() {
        businessPreferences.edit().clear().apply()
    }

    fun getTokens(): Creditionals? {
        val accessToken = businessPreferences.getString(Constants.ACCESS_TOKEN, null)
        val clientId = businessPreferences.getInt(Constants.BUSINESS_ID, -12)
        val username = businessPreferences.getString(Constants.USERNAME, null)

        Timber.i("accessToken = $accessToken")
        Timber.i("clientId = $clientId")
        Timber.i("username = $username")

        if (accessToken == null || clientId == -12 || username == null) {
            return null
        }

        return Creditionals(
            accessToken = accessToken,
            userId = clientId,
            username = username
        )
    }

    internal object Constants {
        const val ACCESS_TOKEN = "accessToken"
        const val BUSINESS_ID = "clientId"
        const val USERNAME = "username"
    }
}