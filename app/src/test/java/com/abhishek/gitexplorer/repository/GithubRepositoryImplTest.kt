package com.abhishek.gitexplorer.repository

import com.abhishek.gitexplorer.RxSchedulerRule
import com.abhishek.gitexplorer.getStubGithubRepo
import com.abhishek.gitexplorer.model.ServiceResponse
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.reactivex.Single
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class GithubRepositoryImplTest {
    @get:Rule
    val rule = RxSchedulerRule()

    @RelaxedMockK
    lateinit var api: GithubApi

    lateinit var repository: GithubRepositoryImpl

    @Before
    fun setup() {
        MockKAnnotations.init(this)
        repository = GithubRepositoryImpl(api)
    }

    @Test
    fun when_networkError_then_returnError() {
        every { api.getRepositoriesByOrg(any(), any()) } returns Single.error(Throwable())

        val observer = repository.getReposByOrg("testOrg", 3).test()

        observer.assertValue { it.isNetworkError }
        observer.assertTerminated()
    }

    @Test
    fun when_networkSuccess_then_returnSuccess() {
        every { api.getRepositoriesByOrg(any(), any()) } returns Single.just(
            ServiceResponse(1, listOf(getStubGithubRepo()))
        )

        val observer = repository.getReposByOrg("testOrg", 3).test()

        observer.assertValue { !it.isNetworkError && it.items.size == 1 }
        observer.assertTerminated()
    }
}
