package com.example.consecutivep.di

import com.example.consecutivep.data.api.MovieApi
import okhttp3.OkHttpClient
import org.koin.dsl.module
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory

val networkModule = module {
    factory { provideRetrofit() }
    single { provideNetworkApi(get()) }
}

fun provideRetrofit(): Retrofit {
    return Retrofit.Builder()
        .baseUrl("https://api.kinopoisk.dev/v1.4/")
        .addConverterFactory(GsonConverterFactory.create())
        .client(OkHttpClient.Builder().build())
        .build()

}

fun provideNetworkApi(retrofit: Retrofit): MovieApi =
    retrofit.create(MovieApi::class.java)