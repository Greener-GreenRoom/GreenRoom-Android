package com.greener.data.di

import com.greener.data.repository.SignRepositoryImpl
import com.greener.data.service.SignService
import com.greener.domain.repository.SignRepository
import dagger.Binds
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import retrofit2.Retrofit
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal abstract class SignModule {
    @Binds
    abstract fun bindSignRepository(
        repository: SignRepositoryImpl
    ): SignRepository


}