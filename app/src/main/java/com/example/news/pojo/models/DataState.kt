package com.example.news.pojo.models

sealed class DataState<out T> {

    data class Success<out T>(val data :T):DataState<T>()
    data class Failed(val error:Failure):DataState<Nothing>()
    object Loading:DataState<Nothing>()
}