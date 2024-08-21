package ru.jocks.data.places.models

import com.google.gson.annotations.SerializedName

data class ResponsePlaces(val items : List<PlaceItem>)
data class PlaceItem(
    val id : Int,
    val name : String,
    @SerializedName("description_short")
    val description : String,
    val rating : Double,
    @SerializedName("rating_count")
    val ratingCount : Int,
    val image : String,
    val address : Address
) {
    data class Address(
        val full : String,
        val lat : Double,
        val long : Double
    )
}
