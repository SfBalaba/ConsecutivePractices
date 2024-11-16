package com.example.consecutivep.di

import com.example.consecutivep.data.mapper.MovieResponseToEntityMapper
import com.example.consecutivep.data.repository.MovieRepository
import com.example.consecutivep.domain.IMovieRepository
import com.example.consecutivep.components.MovieViewModel
import com.example.consecutivep.presentation.mapper.MovieUiMapper
import org.koin.core.module.dsl.viewModel
import org.koin.dsl.module


val rootModule = module {
    single<IMovieRepository> { MovieRepository(get(), get()) }
    factory { MovieResponseToEntityMapper() }
    factory { MovieUiMapper() }

    viewModel { MovieViewModel(get(), get(), get()) }
}