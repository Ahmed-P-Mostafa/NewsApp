package com.example.news.newsApi

import com.example.news.newsApi.model.NewsResponse
import com.example.news.newsApi.model.SourceResponse
import retrofit2.Call
import retrofit2.http.GET
import retrofit2.http.Query

interface WebServices {


    @GET("v2/top-headlines?country=eg&apiKey=f33b3f54cce14a05bc58221886980ab2&category=sports")
    fun getSportNews():Call<NewsResponse>

    @GET("v2/sources?apiKey=f33b3f54cce14a05bc58221886980ab2")
    fun getSources(@Query("language")language:String):Call<SourceResponse>

    @GET("v2/everything?apiKey=f33b3f54cce14a05bc58221886980ab2")
    fun getNews(@Query("sources")sources:String,@Query("language")language:String):Call<NewsResponse>

   /* @GET("v2/top-headlines?country=eg&apiKey=f33b3f54cce14a05bc58221886980ab2&category=business")
    fun getBusinessNews():Call<ArticlesItem>

    @GET("v2/top-headlines?country=eg&apiKey=f33b3f54cce14a05bc58221886980ab2&category=entertainment")
    fun getEntertainmentNews():Call<ArticlesItem>

    @GET("v2/top-headlines?country=eg&apiKey=f33b3f54cce14a05bc58221886980ab2&category=general")
    fun getGeneralNews():Call<ArticlesItem>

    @GET("v2/top-headlines?country=eg&apiKey=f33b3f54cce14a05bc58221886980ab2&category=health")
    fun getHealthNews():Call<ArticlesItem>*/
}