package com.abhishek.gitexplorer

import io.reactivex.android.plugins.RxAndroidPlugins
import io.reactivex.plugins.RxJavaPlugins
import io.reactivex.schedulers.Schedulers
import org.junit.rules.TestRule
import org.junit.runner.Description
import org.junit.runners.model.Statement

class RxSchedulerRule : TestRule {
    private val scheduler = Schedulers.trampoline()
    override fun apply(base: Statement, description: Description): Statement {
        return object : Statement() {
            override fun evaluate() {
                RxJavaPlugins.reset()
                RxJavaPlugins.setIoSchedulerHandler { scheduler }
                RxJavaPlugins.setNewThreadSchedulerHandler { scheduler }
                RxJavaPlugins.setComputationSchedulerHandler { scheduler }

                RxAndroidPlugins.reset()
                RxAndroidPlugins.setInitMainThreadSchedulerHandler { scheduler }

                base.evaluate()

                RxJavaPlugins.reset()
                RxAndroidPlugins.reset()
            }

        }
    }
}