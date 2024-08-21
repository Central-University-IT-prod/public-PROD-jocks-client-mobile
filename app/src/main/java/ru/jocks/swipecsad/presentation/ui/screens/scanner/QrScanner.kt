package ru.jocks.swipecsad.presentation.ui.screens.scanner

import androidx.camera.core.CameraSelector
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.Preview
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.PreviewView
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController
import com.google.accompanist.permissions.ExperimentalPermissionsApi
import com.google.accompanist.permissions.isGranted
import com.google.accompanist.permissions.rememberPermissionState
import com.google.accompanist.permissions.shouldShowRationale
import ru.jocks.swipecsad.R
import ru.jocks.swipecsad.presentation.ui.navigation.AppScreens
import ru.jocks.swipecsad.presentation.ui.navigation.Destinations
import ru.jocks.uikit.TopBarTitle
import timber.log.Timber
import java.util.concurrent.Executors

@OptIn(ExperimentalPermissionsApi::class)
@Composable
fun QrScanner(navController: NavController) {
    val cameraPermissionState = rememberPermissionState(android.Manifest.permission.CAMERA)

    Column {
       TopBarTitle(title = stringResource(id = R.string.scan_qr)) {
           navController.popBackStack()
       }

       if (cameraPermissionState.status.isGranted) {
           CameraScreen(navController)
       } else if (cameraPermissionState.status.shouldShowRationale) {
           Text("Разрешение на камеру перманентно запрещено")
       } else {
           SideEffect {
               cameraPermissionState.run { launchPermissionRequest() }
           }
           Text("Нужно разрешение на камеру")
       }
   }
}

@Composable
fun CameraScreen(navController : NavController) {
    val localContext = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current
    val cameraProviderFuture = remember {
        ProcessCameraProvider.getInstance(localContext)
    }
    val executor = remember { Executors.newSingleThreadExecutor() }
    AndroidView(
        modifier = Modifier.fillMaxSize(),
        factory = { context ->
            val previewView = PreviewView(context)
            val preview = Preview.Builder().build()
            val selector = CameraSelector.Builder()
                .requireLensFacing(CameraSelector.LENS_FACING_BACK)
                .build()

            val imageAnalysis = ImageAnalysis.Builder()
                .setBackpressureStrategy(ImageAnalysis.STRATEGY_KEEP_ONLY_LATEST)
                .build()
            imageAnalysis.setAnalyzer(executor, BarcodeAnalyzer(localContext) {
                val check =   it.split("/check/")[1].replace("/", "")
                Timber.d("Check: $check")
                navController.navigate("${AppScreens.Review.name}/${check.toInt()}/")
            })


            preview.setSurfaceProvider(previewView.surfaceProvider)

            runCatching {
                cameraProviderFuture.get().bindToLifecycle(
                    lifecycleOwner,
                    selector,
                    imageAnalysis,
                    preview,
                )
            }.onFailure {

            }
            previewView
        }
    )
}
