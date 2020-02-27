package com.abhishek.gitexplorer.view

import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.inputmethod.InputMethodManager
import androidx.browser.customtabs.CustomTabsIntent
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.abhishek.gitexplorer.R
import com.abhishek.gitexplorer.databinding.LayoutHomeBinding
import com.abhishek.gitexplorer.viewmodel.HomeViewModel
import com.abhishek.gitexplorer.viewmodel.ViewModelFactory
import dagger.android.support.AndroidSupportInjection
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.rxkotlin.plusAssign
import io.reactivex.rxkotlin.subscribeBy
import javax.inject.Inject

class HomeFragment : Fragment() {

    @Inject
    lateinit var factory: ViewModelFactory
    private val viewModel: HomeViewModel by viewModels { factory }

    private val disposables = CompositeDisposable()

    override fun onAttach(context: Context) {
        AndroidSupportInjection.inject(this)
        super.onAttach(context)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = LayoutHomeBinding.inflate(inflater, container, false)
        binding.viewModel = viewModel
        disposables += viewModel.getEvents().subscribeBy { handleViewModelEvents(it) }
        return binding.root
    }

    private fun handleViewModelEvents(event: HomeViewModel.Event) {
        when (event) {
            HomeViewModel.Event.HideKeyboardEvent ->
                try {
                    (requireContext().getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager)
                        .hideSoftInputFromWindow(requireView().windowToken, 0)
                } catch (ex: Exception) {
                }
            is HomeViewModel.Event.ShowUrlEvent ->
                CustomTabsIntent.Builder()
                    .setShowTitle(true)
                    .setToolbarColor(ContextCompat.getColor(requireContext(), R.color.colorPrimary))
                    .build()
                    .launchUrl(requireContext(), Uri.parse(event.url))
        }
    }

    override fun onDestroyView() {
        disposables.clear()
        super.onDestroyView()
    }
}