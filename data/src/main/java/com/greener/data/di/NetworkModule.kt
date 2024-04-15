package com.greener.data.di

import com.greener.data.interceptor.AuthInterceptor
import com.greener.data.repository.PlantRegisterRepositoryImpl
import com.greener.data.service.PlantRegisterService
import com.greener.data.service.SignService
import com.greener.data.source.local.AuthDataSource
import com.greener.data.source.remote.PlantRegisterDataSource
import com.greener.domain.repository.PlantRegisterRepository
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import retrofit2.create
import javax.inject.Singleton

private const val BASE_URL = "http://dev.greener-greenroom.r-e.kr/"

@Module
@InstallIn(SingletonComponent::class)
object NetworkModule {
    @Singleton
    @Provides
    fun provideRetrofitClient(moshi: Moshi, okHttp: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttp)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()
    }

    @Provides
    @Singleton
    fun provideSignService(retrofit: Retrofit): SignService {
        return retrofit.create(SignService::class.java)
    }

    @Singleton
    @Provides
    fun provideOkHttp(authInterceptor: AuthInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(authInterceptor)
            .build()
    }

    @Singleton
    @Provides
    fun provideAuthInterceptor(dataStore: AuthDataSource): AuthInterceptor {
        return AuthInterceptor(dataStore)
    }


    @Singleton
    @Provides
    fun provideMoshi(): Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    @Provides
    @Singleton
    fun providePlantRegisterService(retrofit: Retrofit): PlantRegisterService =
        retrofit.create(PlantRegisterService::class.java)
}
