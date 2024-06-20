package com.greener.data.di

import android.content.Context
import com.greener.data.repository.MyPageRepositoryImpl
import com.greener.data.service.MyPageService
import com.greener.data.source.remote.MyPageDataSource
import com.greener.domain.repository.MyPageRepository
import com.squareup.moshi.Moshi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object MyPageModule {
    @Provides
    @Singleton
    fun provideMyPageDataSource(service: MyPageService): MyPageDataSource =
        MyPageDataSource(service)

    @Provides
    @Singleton
    fun provideMyPageRepository(
        dataSource: MyPageDataSource,
        moshi: Moshi,
        context: Context,
    ): MyPageRepository =
        MyPageRepositoryImpl(dataSource, moshi, context)
}
