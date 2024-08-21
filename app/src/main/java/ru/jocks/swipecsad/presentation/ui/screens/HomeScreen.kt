package ru.jocks.swipecsad.presentation.ui.screens

import android.content.Intent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.navArgument
import androidx.navigation.navDeepLink
import org.koin.java.KoinJavaComponent
import ru.jocks.domain.review.repository.UserRepository
import ru.jocks.swipecsad.presentation.ui.navigation.AppScreens
import ru.jocks.swipecsad.presentation.ui.navigation.BottomNavigationBar
import ru.jocks.swipecsad.presentation.ui.navigation.Destinations
import ru.jocks.swipecsad.presentation.ui.screens.login.LoginScreen
import ru.jocks.swipecsad.presentation.ui.screens.main.MainScreen
import ru.jocks.swipecsad.presentation.ui.screens.main.review.ReviewGoodsScreen
import ru.jocks.swipecsad.presentation.ui.screens.main.review.ReviewReport
import ru.jocks.swipecsad.presentation.ui.screens.main.review.ReviewScreen
import ru.jocks.swipecsad.presentation.ui.screens.map.MapScreen
import ru.jocks.swipecsad.presentation.ui.screens.placeDetails.PlaceDetailsScreen
import ru.jocks.swipecsad.presentation.ui.screens.profile.ProfileScreen
import ru.jocks.swipecsad.presentation.ui.screens.registration.RegistrationScreen
import ru.jocks.swipecsad.presentation.ui.screens.scanner.QrScanner
import timber.log.Timber

@Composable
fun HomeScreen(navController: NavHostController) {
    val userRepository : UserRepository by KoinJavaComponent.inject(UserRepository::class.java)

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = {
            val navBackStackEntry by navController.currentBackStackEntryAsState()
            val currentDestination = navBackStackEntry?.destination
            if (currentDestination?.route !in AppScreens.entries.map { it.name }) {
                BottomNavigationBar(navController = navController)
            }
        }
    ) { innerPadding ->
        NavHost(
            navController = navController,
            startDestination = if (userRepository.getSavedUser()!=null) { Destinations.Main.route } else { AppScreens.Intro.name },
            Modifier.padding(innerPadding)
        ) {
            composable(AppScreens.Intro.name) { IntroScreen(navController)}
            composable(AppScreens.Login.name) { LoginScreen(navController) }
            composable(AppScreens.Registration.name) { RegistrationScreen(navController) }

            composable(Destinations.Main.route) { MainScreen(navController = navController) }

            composable(Destinations.Map.route) { MapScreen(navController) }
            composable(Destinations.Profile.route) { ProfileScreen(navController) }
            composable("${AppScreens.PlaceDetails.name}/{id}", arguments = listOf(navArgument("id") { type = NavType.IntType })) { backStackEntry ->
                val id = backStackEntry.arguments?.getInt("id")
                PlaceDetailsScreen(navController, id!!)
            }
            composable(AppScreens.QrScanner.name) {
                QrScanner(navController = navController)
            }
            composable(
                route="${AppScreens.Review.name}/{id}/",
                arguments = listOf(navArgument("id") { type = NavType.IntType }),
                deepLinks = listOf(
                    navDeepLink {
                        uriPattern = "swipe://csat/qr/{id}/"
                        action = Intent.ACTION_VIEW
                    }
                )
            ) { backStackEntry ->
                val link = backStackEntry.arguments?.getInt("id")
                Timber.i("link $link")
                if (userRepository.getSavedUser()!=null) {
                    ReviewScreen(navController = navController, link = link!!)
                } else {
                    navController.navigate(AppScreens.Intro.name)
                }
            }
            composable(AppScreens.ReviewGoods.name) {
                ReviewGoodsScreen(navController = navController)
            }
            composable(AppScreens.ReviewReport.name) {
                ReviewReport(navController = navController)
            }

        }
    }
}