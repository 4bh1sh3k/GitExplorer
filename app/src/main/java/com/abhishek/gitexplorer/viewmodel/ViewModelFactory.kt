package com.abhishek.gitexplorer.viewmodel

import android.content.res.Resources
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.abhishek.gitexplorer.repository.GithubRepository
import javax.inject.Inject

@Suppress("UNCHECKED_CAST")
class ViewModelFactory @Inject constructor(
    private val repository: GithubRepository,
    private val resources: Resources
) : ViewModelProvider.Factory {
    override fun <T : ViewModel?> create(modelClass: Class<T>): T {
        return when (modelClass) {
            HomeViewModel::class.java -> HomeViewModel(repository, resources) as T
            else -> throw IllegalArgumentException("Unable to create class ${modelClass.simpleName}")
        }
    }
}