package com.greener.data.di

import android.content.Context
import com.greener.data.repository.ImageRepositoryImpl
import com.greener.domain.repository.ImageRepository
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.scopes.ActivityScoped

@Module
@InstallIn(ActivityComponent::class)
class ImageModule {
    @ActivityScoped
    @Provides
    fun provideImageRepository(@ActivityContext context: Context): ImageRepository =
        ImageRepositoryImpl(context)
}
