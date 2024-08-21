package ru.jocks.data.auth.models

import com.google.gson.annotations.SerializedName

data class TokensModel(
    @SerializedName("access_token")
    val accessToken: String,
    @SerializedName("user_id")
    val userId: Int,
    @SerializedName("username")
    val username : String
)
