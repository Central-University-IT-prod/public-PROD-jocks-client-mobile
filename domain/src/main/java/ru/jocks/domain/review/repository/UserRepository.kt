package ru.jocks.domain.review.repository

import ru.jocks.domain.review.models.Creditionals
import ru.jocks.domain.review.models.PromoCode

interface UserRepository {
    suspend fun register(
        email: String,
        username: String,
        password: String,
    ) : Creditionals

    suspend fun login(
        email: String,
        password: String
    ) : Creditionals

    suspend fun getPromocodes(
    ) : List<PromoCode>

    fun getSavedUser() : Creditionals?

    suspend fun logout()
}