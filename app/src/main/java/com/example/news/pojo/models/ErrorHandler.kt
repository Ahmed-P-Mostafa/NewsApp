package com.example.news.pojo.models

interface ErrorHandler {
    fun getError(throwable: Throwable):Failure
}