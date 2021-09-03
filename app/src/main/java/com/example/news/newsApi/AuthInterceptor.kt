package com.example.news.newsApi

import android.content.Context
import com.example.news.R
import com.example.news.pojo.AppConstants.API_KEY_KEY
import com.example.news.pojo.AppConstants.COUNTRY_KEY
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor(private val context: Context) :Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val request = chain.request()
        val originalUrl = request.url
        val modifiedUrl = originalUrl.newBuilder()
            .addQueryParameter(API_KEY_KEY,context.resources.getString(R.string.api_key) )
            .addQueryParameter(COUNTRY_KEY,context.getString(R.string.country_value))
            .build()
        val modifiedRequest = request.newBuilder().url(modifiedUrl).build()

        return chain.proceed(modifiedRequest)
    }

}