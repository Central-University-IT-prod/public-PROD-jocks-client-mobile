package ru.jocks.data.reviews.models

import com.google.gson.annotations.SerializedName

data class ReviewFields(
    @SerializedName("base_review_fields")
    val baseFields : List<ReviewField>,
    @SerializedName("products_review_fields")
    val goodsFields : List<ReviewField>,
    @SerializedName("business_id")
    val businessId : Int
)

data class ReviewField(
    val id : Int,
    val name : String,
    val type : FieldType,
    @SerializedName("value")
    var rating: Int = 0
) {
    enum class FieldType { STAR }
}
