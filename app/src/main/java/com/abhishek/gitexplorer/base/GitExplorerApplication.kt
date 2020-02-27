package com.abhishek.gitexplorer.base

import android.app.Application
import com.abhishek.gitexplorer.di.AppModule
import com.abhishek.gitexplorer.di.DaggerAppComponent
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasAndroidInjector
import javax.inject.Inject

class GitExplorerApplication : Application(), HasAndroidInjector {

    @Inject
    lateinit var dispatchingAndroidInjector: DispatchingAndroidInjector<Any>

    override fun androidInjector() = dispatchingAndroidInjector

    override fun onCreate() {
        super.onCreate()
        DaggerAppComponent.builder()
            .appModule(AppModule(this))
            .build()
            .inject(this)
    }
}