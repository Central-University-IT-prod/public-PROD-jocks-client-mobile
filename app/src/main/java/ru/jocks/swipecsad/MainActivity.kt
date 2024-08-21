package ru.jocks.swipecsad

import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.navigation.compose.rememberNavController
import com.yandex.mapkit.MapKitFactory
import ru.jocks.swipecsad.presentation.ui.navigation.AppScreens
import ru.jocks.swipecsad.presentation.ui.screens.HomeScreen
import ru.jocks.swipecsad.presentation.ui.theme.SwipeCSADTheme
import timber.log.Timber

class MainActivity : ComponentActivity() {

    override fun onStart() {
        super.onStart()
        MapKitFactory.getInstance().onStart()
    }

    override fun onStop() {
        MapKitFactory.getInstance().onStop()
        super.onStop()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        MapKitFactory.initialize(this)


        if (ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_FINE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(
                this,
                android.Manifest.permission.ACCESS_COARSE_LOCATION
            ) != PackageManager.PERMISSION_GRANTED
        ){
            val permissions = arrayOf(android.Manifest.permission.ACCESS_FINE_LOCATION, android.Manifest.permission.ACCESS_COARSE_LOCATION)
            val requestPermissionLauncher = registerForActivityResult(
                ActivityResultContracts.RequestMultiplePermissions()
            ) { permissionsMap ->
                var allGranted = true
                for (entry in permissionsMap.entries.iterator()) {
                    if (!entry.value) {
                        allGranted = false
                    }
                }
                if (allGranted) {
                    // TODO
                }
            }
            requestPermissionLauncher.launch(permissions)
        }

        var uri: Uri? = null
        var userNameFromUrl: String? = null

        if (intent.action === Intent.ACTION_VIEW) {
            uri = intent.data
            val regex = """swipe://csat/(\d+)""".toRegex()
            userNameFromUrl = regex.matchEntire(uri.toString())?.groups?.get(1)?.value
            Timber.tag("Intent").d(userNameFromUrl.toString())
            Timber.tag("Intent").d(userNameFromUrl)
            Timber.tag("Intent").d(uri.toString())
            Timber.tag("Intent").d("Haha")
        }

        setContent {
            val navController = rememberNavController()

            SwipeCSADTheme {
                HomeScreen(navController = navController)
            }
        }
    }
}

