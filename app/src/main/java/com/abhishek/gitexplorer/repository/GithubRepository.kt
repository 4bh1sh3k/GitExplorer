package com.abhishek.gitexplorer.repository

import com.abhishek.gitexplorer.model.GithubResult
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

interface GithubRepository {
    fun getReposByOrg(org: String): Single<GithubResult>
}

class GithubRepositoryImpl @Inject constructor(
    private val api: GithubApi
) : GithubRepository {

    override fun getReposByOrg(org: String): Single<GithubResult> {
        return api.getRepositoriesByOrg("org:$org")
            .map { GithubResult(false, it.items) }
            .onErrorReturnItem(GithubResult(true, emptyList()))
            .subscribeOn(Schedulers.io())
    }
}