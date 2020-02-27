package com.abhishek.gitexplorer.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
class ServiceResponse(
    @Json(name = "total_count") val repositoryCount: Int,
    val items: List<GithubRepo>
)

@JsonClass(generateAdapter = true)
class GithubRepo(
    val name: String,
    @Json(name = "full_name") val fullName: String,
    @Json(name = "html_url") val url: String,
    val description: String,
    @Json(name = "stargazers_count") val starCount: Int,
    @Json(name = "watchers") val watcherCount: Int
)

class GithubResult(
    val isNetworkError: Boolean,
    val items: List<GithubRepo>
)