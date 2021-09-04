package com.example.news.viewModel

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.example.news.base.BaseViewModel
import com.example.news.newsApi.model.ArticlesItem
import com.example.news.newsApi.model.NewsResponse
import com.example.news.pojo.models.DataState
import com.example.news.pojo.repository.GetNews
import com.example.news.ui.Navigator
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.android.schedulers.AndroidSchedulers
import io.reactivex.schedulers.Schedulers
import javax.inject.Inject

@HiltViewModel
class NewsViewModel @Inject constructor(private val getNews: GetNews) : BaseViewModel<Navigator>() {
    private val TAG = "NewsViewModel"

    val articlesLiveData = MutableLiveData<DataState<NewsResponse>>()
    val articleList = MutableLiveData<List<ArticlesItem?>>()

    fun getArticles() {
        articlesLiveData.value = DataState.Loading

        getNews.excute().observeOn(AndroidSchedulers.mainThread()).subscribeOn(Schedulers.io())
            .subscribe { restaurants ->
                Log.d(TAG, "getArticles: $restaurants")
                if (restaurants != null)
                    articlesLiveData.value = restaurants
            }.also { compositeDisposable.add(it) }
    }

    fun searchInArticles(text: String): LiveData<List<ArticlesItem>> {
        Log.d(TAG, "searchInArticles: $text")
        val list = mutableListOf<ArticlesItem>()
        val liveData = MutableLiveData<List<ArticlesItem>>()

        articleList.value?.forEach {
            Log.d(TAG, "searchInArticles: ${it?.source?.name?.contains(text)}")
            if (it?.title?.toLowerCase()?.contains(text)!!) {
                Log.e(TAG, "searchInArticles: ${it.source?.name}")
                list.add(it)
            }
        }
        liveData.value = list
        return liveData
    }

}