package com.abhishek.gitexplorer.di

import com.abhishek.gitexplorer.view.HomeFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
interface FragmentsModule {

    @ContributesAndroidInjector
    fun contributeHomeFragment(): HomeFragment
}