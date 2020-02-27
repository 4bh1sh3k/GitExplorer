package com.abhishek.gitexplorer.repository

import com.abhishek.gitexplorer.model.ServiceResponse
import io.reactivex.Single
import retrofit2.http.GET
import retrofit2.http.Query

interface GithubApi {
    @GET("/search/repositories?sort=stars")
    fun getRepositoriesByOrg(@Query("q") query: String): Single<ServiceResponse>
}