package com.abhishek.gitexplorer.di

import com.abhishek.gitexplorer.base.GitExplorerApplication
import dagger.Component
import dagger.android.support.AndroidSupportInjectionModule

@Component(
    modules = [
        AndroidSupportInjectionModule::class,
        AppModule::class,
        NetworkModule::class,
        FragmentsModule::class
    ]
)
interface AppComponent {

    fun inject(application: GitExplorerApplication)
}