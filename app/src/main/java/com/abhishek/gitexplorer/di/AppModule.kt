package com.abhishek.gitexplorer.di

import android.app.Application
import android.content.res.Resources
import dagger.Module
import dagger.Provides

@Module
class AppModule(private val app: Application) {

    @Provides
    fun provideResources(): Resources = app.resources
}