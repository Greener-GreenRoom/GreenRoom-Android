package com.greener.data.di

import com.greener.data.repository.DataStoreRepositoryImpl
import com.greener.data.repository.SignRepositoryImpl
import com.greener.domain.repository.DataStoreRepository
import com.greener.domain.repository.SignRepository
import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
internal abstract class RepositoryModule {

    @Binds
    abstract fun bindSignRepository(
        repository: SignRepositoryImpl
    ): SignRepository

    @Binds
    abstract fun bindDataStoreRepository(
        repository: DataStoreRepositoryImpl
    ): DataStoreRepository
}