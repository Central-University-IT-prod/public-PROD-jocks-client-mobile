package ru.jocks.domain.review.models

data class PromoCode(
    val token : String,
    val name: String,
    val description: String,
    val activated : Boolean = false
)
