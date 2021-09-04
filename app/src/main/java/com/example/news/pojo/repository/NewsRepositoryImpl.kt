package com.example.news.pojo.repository

import com.example.news.newsApi.WebServices
import com.example.news.newsApi.model.NewsResponse
import com.example.news.pojo.models.DataState
import com.example.news.pojo.models.ErrorHandler
import com.example.news.pojo.models.Failure
import io.reactivex.Single
import retrofit2.HttpException
import java.net.HttpURLConnection
import java.net.UnknownHostException
import javax.inject.Inject

class NewsRepositoryImpl(private val webService :WebServices):NewsRepository {
    override fun getArticles(): Single<DataState<NewsResponse>> {
         return webService.getArticles().map {
             DataState.Success(it)
         }

    }
}

class GetNews @Inject constructor(private val repository: NewsRepository):ErrorHandler{

    fun excute():Single<DataState<NewsResponse>>{
        return repository.getArticles().onErrorReturn {
            DataState.Failed(getError(it))
        }
    }

    override fun getError(throwable: Throwable): Failure {
        return when(throwable){
            is UnknownHostException -> Failure.NetworkConnection
            is HttpException -> {
                when(throwable.code()){
                    HttpURLConnection.HTTP_NOT_FOUND -> Failure.ServerError.NotFound
                    HttpURLConnection.HTTP_FORBIDDEN ->Failure.ServerError.AccessDenied
                    HttpURLConnection.HTTP_UNAVAILABLE ->Failure.ServerError.ServerUnavailable
                    else ->Failure.UnknownError
                }
            }
            else -> Failure.UnknownError
        }
    }
}