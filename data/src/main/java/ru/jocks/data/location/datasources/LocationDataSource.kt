package ru.jocks.data.location.datasources

import android.annotation.SuppressLint
import android.content.Context
import com.google.android.gms.location.LocationServices
import com.google.android.gms.tasks.Tasks
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
import ru.jocks.domain.location.LocationModel

class LocationDataSource(context : Context) {

    private val fusedLocationClient = LocationServices.getFusedLocationProviderClient(context)

    @SuppressLint("MissingPermission")
     suspend fun getLocation() = withContext(Dispatchers.IO) {
        val location = Tasks.await(fusedLocationClient.lastLocation)
            ?: return@withContext LocationModel(0.0, 0.0)
        return@withContext LocationModel(location.latitude, location.longitude)
    }

}