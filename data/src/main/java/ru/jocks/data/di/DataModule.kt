package ru.jocks.data.di

import org.koin.dsl.module
import ru.jocks.data.places.repository.PlaceRepository
import ru.jocks.data.reviews.repository.ReviewsRepository
import ru.jocks.data.auth.repository.LocalDataSource
import ru.jocks.data.auth.repository.UserRepositoryImpl
import ru.jocks.data.location.LocationRepository
import ru.jocks.data.location.datasources.LocationDataSource
import ru.jocks.data.location.datasources.LocationLocalDataSource
import ru.jocks.domain.review.repository.UserRepository

val dataModule = module {
    single { PlaceRepository() }
    single <UserRepository> { UserRepositoryImpl() }
    single<LocalDataSource> {
        LocalDataSource(get())
    }
    single<LocationRepository> {
        LocationRepository()
    }
    single<LocationDataSource> {
        LocationDataSource(get())
    }
    single<LocationLocalDataSource> {
        LocationLocalDataSource()
    }
    single { ReviewsRepository() }
}

