package com.example.news.pojo.repository

import com.example.news.newsApi.WebServices
import com.example.news.newsApi.model.NewsResponse
import com.example.news.pojo.models.DataState
import io.reactivex.Single

interface NewsRepository {

    fun getArticles() : Single<DataState<NewsResponse>>

}