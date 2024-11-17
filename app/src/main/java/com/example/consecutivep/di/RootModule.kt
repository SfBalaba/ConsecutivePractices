package com.example.consecutivep.di

import androidx.datastore.core.DataStore
import com.example.consecutivep.components.EditProfileViewModel
import com.example.consecutivep.data.mapper.MovieResponseToEntityMapper
import com.example.consecutivep.data.repository.MovieRepository
import com.example.consecutivep.domain.IMovieRepository
import com.example.consecutivep.components.MovieViewModel
import com.example.consecutivep.components.ProfileViewModel
import com.example.consecutivep.data.repository.ProfileRepository
import com.example.consecutivep.datastore.DataSourceProvider
import com.example.consecutivep.datastore.DataStoreManager
import com.example.consecutivep.domain.model.ProfileEntity
import com.example.consecutivep.domain.repository.IProfileRepository
import com.example.consecutivep.presentation.mapper.MovieUiMapper
import org.koin.core.module.dsl.viewModel
import org.koin.core.qualifier.named
import org.koin.dsl.module


val rootModule = module {
    single<IMovieRepository> { MovieRepository(get(), get()) }
    factory { MovieResponseToEntityMapper() }
    factory { MovieUiMapper() }
    factory<DataStore<ProfileEntity>>(named("profile")) { DataSourceProvider(get()).provide() }
    single<IProfileRepository> { ProfileRepository() }

    single { DataStoreManager(get()) }
    viewModel { MovieViewModel(get(), get(), get()) }
    viewModel { ProfileViewModel(get()) }
    viewModel { EditProfileViewModel(get()) }

}