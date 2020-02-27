package com.abhishek.gitexplorer.viewmodel

import android.content.res.Resources
import android.view.inputmethod.EditorInfo
import androidx.databinding.ObservableArrayList
import androidx.databinding.ObservableBoolean
import androidx.databinding.ObservableField
import androidx.lifecycle.ViewModel
import com.abhishek.gitexplorer.R
import com.abhishek.gitexplorer.model.GithubRepo
import com.abhishek.gitexplorer.model.GithubResult
import com.abhishek.gitexplorer.repository.GithubRepository
import io.reactivex.Observable
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import io.reactivex.subjects.PublishSubject
import io.reactivex.subjects.Subject

class HomeViewModel(
    private val repository: GithubRepository,
    private val resources: Resources
) : ViewModel() {
    val isBusy = ObservableBoolean(false)
    val items = ObservableArrayList<ItemViewModel>()
    val errorMessage = ObservableField<String>()

    private val disposables = CompositeDisposable()

    private val events: Subject<Event> = PublishSubject.create()
    fun getEvents(): Observable<Event> = events.hide()

    fun performSearch(organization: String) {
        disposables += repository.getReposByOrg(organization)
            .observeOn(AndroidSchedulers.mainThread())
            .doOnSubscribe { isBusy.set(true) }
            .subscribeBy { handleResult(it) }
    }

    private fun handleResult(response: GithubResult) {
        isBusy.set(false)
        items.clear()

        when {
            response.isNetworkError ->
                errorMessage.set(resources.getString(R.string.label_error_network))
            response.items.isEmpty() ->
                errorMessage.set((resources.getString(R.string.label_error_no_repo)))
            else -> {
                errorMessage.set("")
                items.addAll(response.items.map { ItemViewModel(it, resources, events) })
            }
        }
    }

    fun onSearch(query: CharSequence, actionId: Int): Boolean {
        if (actionId == EditorInfo.IME_ACTION_SEARCH) {
            events.onNext(Event.HideKeyboardEvent)
            performSearch(query.toString())
            return true
        }
        return false
    }

    override fun onCleared() {
        disposables.clear()
    }

    sealed class Event {
        class ShowUrlEvent(val url: String) : Event()
        object HideKeyboardEvent : Event()
    }

    class ItemViewModel(
        val item: GithubRepo,
        resources: Resources,
        private val events: Subject<Event>
    ) {
        val starsLabel = resources.getQuantityString(
            R.plurals.label_stars,
            item.starCount,
            item.starCount
        )

        val watchersLabel = resources.getQuantityString(
            R.plurals.label_watchers,
            item.watcherCount,
            item.watcherCount
        )

        fun onClick() {
            events.onNext(Event.ShowUrlEvent(item.url))
        }
    }
}