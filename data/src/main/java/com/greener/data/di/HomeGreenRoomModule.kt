package com.greener.data.di

import com.greener.data.repository.HomeGreenRoomRepositoryImpl
import com.greener.domain.repository.HomeGreenRoomRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class HomeGreenRoomModule {

    @Binds
    abstract fun bindHomeGreenRoomRepository(
        repository: HomeGreenRoomRepositoryImpl,
    ): HomeGreenRoomRepository
}
