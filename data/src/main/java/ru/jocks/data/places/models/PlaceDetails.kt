package ru.jocks.data.places.models

import com.google.gson.annotations.SerializedName
data class PlaceDetails(
    val id : Int,
    val name : String,
    @SerializedName("description_short")
    val description : String,

    @SerializedName("rating_average")
    val rating : Double,
    @SerializedName("rating_count")
    val ratingCount : Int,

    val image : String,
    val address : List<Address>,
    val items : List<PlaceGoods>
) {
    data class Address(
        val full : String,
        val lat : Double,
        val long : Double
    )

    data class PlaceGoods(
        val id : Int,
        val name : String,
        val rating : Double
    )
}
