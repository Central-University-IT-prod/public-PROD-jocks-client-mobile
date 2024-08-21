package ru.jocks.data.reviews.models

import com.google.gson.annotations.SerializedName
import ru.jocks.domain.review.models.PromoCode

data class ReviewResponse (
    val id : Int,
    @SerializedName("promocode")
    val promoCode: PromoCode,
)