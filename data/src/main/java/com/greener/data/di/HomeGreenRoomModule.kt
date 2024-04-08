package com.greener.data.di

import com.greener.data.repository.HomeGreenRoomRepositoryImpl
import com.greener.data.service.HomeGreenRoomService
import com.greener.data.service.SignService
import com.greener.domain.repository.HomeGreenRoomRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class HomeGreenRoomModule {

    @Binds
    abstract fun bindHomeGreenRoomRepository(
        repository: HomeGreenRoomRepositoryImpl
    ): HomeGreenRoomRepository

}