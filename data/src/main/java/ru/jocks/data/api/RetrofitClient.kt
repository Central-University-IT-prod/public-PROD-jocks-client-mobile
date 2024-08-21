package ru.jocks.data.api

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import ru.jocks.data.auth.UserApi
import ru.jocks.data.api.Consts.BASE_API_URL
import ru.jocks.data.places.PlacesApi
import ru.jocks.data.reviews.ReviewsApi
import timber.log.Timber


object RetrofitClient {
    private val retrofit: Retrofit by lazy {
        val httpClient = OkHttpClient.Builder()

        val logging = HttpLoggingInterceptor(object : HttpLoggingInterceptor.Logger {
            override fun log(message: String) {
                Timber.d(message)
            }
        })
        logging.setLevel(HttpLoggingInterceptor.Level.BODY)
        httpClient.addInterceptor(logging)

        Retrofit.Builder()
            .baseUrl(BASE_API_URL)
            .client(httpClient.build())
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }


    val placesApiService: PlacesApi by lazy {
        retrofit.create(PlacesApi::class.java)
    }


    val businessApiService: UserApi by lazy {
        retrofit.create(UserApi::class.java)
    }

    val reviewsApiService : ReviewsApi by lazy {
        retrofit.create(ReviewsApi::class.java)
    }

}