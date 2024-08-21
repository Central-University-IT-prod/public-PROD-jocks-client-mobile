package ru.jocks.domain.review.models

data class RatingFormGoodItem(
    val name: String,
    var rating: Double? = null,
)