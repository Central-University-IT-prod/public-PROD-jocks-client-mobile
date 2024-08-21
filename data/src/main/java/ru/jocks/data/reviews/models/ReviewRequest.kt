package ru.jocks.data.reviews.models

import com.google.gson.annotations.SerializedName

data class ReviewRequest (
    @SerializedName("business_id")
    val businessId : Int,
    @SerializedName("customer_id")
    val customerId : Int,
    @SerializedName("check_id")
    val checkId : Int,
    @SerializedName("base_review_fields")
    val baseFields : List<ReviewField>,
    @SerializedName("products_review_fields")
    val goodsFields : List<ReviewField>,
)