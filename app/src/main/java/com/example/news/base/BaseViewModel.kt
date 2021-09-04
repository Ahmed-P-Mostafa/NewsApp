package com.example.news.base

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import io.reactivex.disposables.CompositeDisposable
import javax.inject.Inject

open class BaseViewModel<N> @Inject constructor() :ViewModel() {

    val compositeDisposable = CompositeDisposable()


    override fun onCleared() {
        super.onCleared()
        if (!compositeDisposable.isDisposed)
            compositeDisposable.dispose()
    }
}