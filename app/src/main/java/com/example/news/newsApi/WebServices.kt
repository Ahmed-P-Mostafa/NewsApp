package com.example.news.newsApi

import com.example.news.newsApi.model.NewsResponse
import com.example.news.newsApi.model.SourceResponse
import io.reactivex.Single
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WebServices {

    @GET("v2/top-headlines")
    fun getArticles(): Single<NewsResponse>


}