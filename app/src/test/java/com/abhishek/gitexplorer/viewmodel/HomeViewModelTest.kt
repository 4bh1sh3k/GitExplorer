package com.abhishek.gitexplorer.viewmodel

import android.content.res.Resources
import com.abhishek.gitexplorer.R
import com.abhishek.gitexplorer.RxSchedulerRule
import com.abhishek.gitexplorer.getStubGithubRepo
import com.abhishek.gitexplorer.model.GithubResult
import com.abhishek.gitexplorer.repository.GithubRepository
import io.mockk.MockKAnnotations
import io.mockk.every
import io.mockk.impl.annotations.RelaxedMockK
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test

private const val NETWORK_ERROR = "Network Error"
private const val NO_REPOSITORIES = "No Repositories"

class HomeViewModelTest {

    @get:Rule
    val rule = RxSchedulerRule()

    @RelaxedMockK
    lateinit var repository: GithubRepository

    @RelaxedMockK
    lateinit var resources: Resources

    @Before
    fun setup() {
        MockKAnnotations.init(this)

        every { resources.getString(R.string.label_error_network) } returns NETWORK_ERROR
        every { resources.getString(R.string.label_error_no_repo) } returns NO_REPOSITORIES
    }

    @Test
    fun when_networkError_then_showError() {
        every { repository.getReposByOrg(any(), any()) } returns Single.just(
            GithubResult(true, emptyList())
        )

        val viewModel = HomeViewModel(repository, resources)
        viewModel.performSearch("testOrg")

        assertEquals(NETWORK_ERROR, viewModel.errorMessage.get())
        assertEquals(0, viewModel.items.size)
    }

    @Test
    fun when_zeroRepositories_then_showError() {
        every { repository.getReposByOrg(any(), any()) } returns Single.just(
            GithubResult(false, emptyList())
        )

        val viewModel = HomeViewModel(repository, resources)
        viewModel.performSearch("testOrg")

        assertEquals(NO_REPOSITORIES, viewModel.errorMessage.get())
        assertEquals(0, viewModel.items.size)
    }

    @Test
    fun when_foundRepositories_then_showItems() {
        every { repository.getReposByOrg(any(), any()) } returns Single.just(
            GithubResult(false, listOf(getStubGithubRepo()))
        )

        val viewModel = HomeViewModel(repository, resources)
        viewModel.performSearch("testOrg")

        assertEquals("", viewModel.errorMessage.get())
        assertEquals(1, viewModel.items.size)
    }
}