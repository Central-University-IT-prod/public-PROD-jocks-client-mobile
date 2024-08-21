package ru.jocks.swipecsad.presentation.di

import org.koin.android.ext.koin.androidApplication
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import ru.jocks.swipecsad.presentation.ui.screens.login.LoginViewModel
import ru.jocks.swipecsad.presentation.ui.screens.main.MainViewModel
import ru.jocks.swipecsad.presentation.ui.screens.main.review.ReviewViewModel
import ru.jocks.swipecsad.presentation.ui.screens.map.MapViewModel
import ru.jocks.swipecsad.presentation.ui.screens.profile.ProfileViewModel
import ru.jocks.swipecsad.presentation.ui.screens.registration.RegistrationViewModel

val presentationModule = module {
    viewModel { MainViewModel() }
    viewModel { LoginViewModel(get()) }
    viewModel { RegistrationViewModel(get()) }
    viewModel { ProfileViewModel(get()) }
    single { ReviewViewModel(androidApplication()) }
    viewModel { MapViewModel() }
}