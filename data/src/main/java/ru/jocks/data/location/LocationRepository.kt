package ru.jocks.data.location

import org.koin.java.KoinJavaComponent.inject
import ru.jocks.data.location.datasources.LocationDataSource
import ru.jocks.data.location.datasources.LocationLocalDataSource
import ru.jocks.domain.location.LocationModel

class LocationRepository  {

    private val dataSource : LocationDataSource by inject(
        LocationDataSource::class.java
    )
    private val localDataSource : LocationLocalDataSource by inject(
        LocationLocalDataSource::class.java
    )

     suspend fun getLocation() : LocationModel {
        val localLocation = localDataSource.getLocation()
        return if (localLocation!=null) {
            localLocation
        } else {
            val newLocation = dataSource.getLocation()
            localDataSource.setLocation(newLocation)
            newLocation
        }
    }
}