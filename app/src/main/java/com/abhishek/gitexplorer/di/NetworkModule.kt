package com.abhishek.gitexplorer.di

import com.abhishek.gitexplorer.repository.GithubApi
import com.abhishek.gitexplorer.repository.GithubRepository
import com.abhishek.gitexplorer.repository.GithubRepositoryImpl
import dagger.Module
import dagger.Provides
import retrofit2.Retrofit
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory

@Module
class NetworkModule {

    @Provides
    fun provideRetrofit(): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.github.com")
            .addConverterFactory(MoshiConverterFactory.create())
            .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
            .build()
    }

    @Provides
    fun provideGithubApi(retrofit: Retrofit): GithubApi {
        return retrofit.create(GithubApi::class.java)
    }

    @Provides
    fun provideGithubRepo(impl: GithubRepositoryImpl): GithubRepository = impl
}