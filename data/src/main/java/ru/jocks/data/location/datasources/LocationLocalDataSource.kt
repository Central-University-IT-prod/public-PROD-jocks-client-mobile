package ru.jocks.data.location.datasources

import ru.jocks.domain.location.LocationModel

class LocationLocalDataSource()  {

    private var location : LocationModel? = null

    fun getLocation() = location

    fun setLocation(location: LocationModel) { this.location = location }


}