package ru.jocks.swipecsad.presentation.ui.navigation

import androidx.annotation.DrawableRes
import androidx.annotation.StringRes
import ru.jocks.swipecsad.R

sealed class Destinations(val route: String, @DrawableRes val icon: Int, @StringRes val name: Int) {
    data object Main : Destinations("main", R.drawable.main, R.string.main)
    data object Map : Destinations("map", R.drawable.map, R.string.map)
    data object Profile : Destinations("profile", R.drawable.user, R.string.profile)
}

enum class AppScreens {
    PlaceDetails,
    QrScanner,
    Review,
    ReviewGoods,
    ReviewReport,
    Registration,
    Login,
    Intro
}