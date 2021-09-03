package com.example.news.di

import com.example.news.newsApi.WebServices
import com.example.news.pojo.repository.NewsRepository
import com.example.news.pojo.repository.NewsRepositoryImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@InstallIn(SingletonComponent::class)
@Module
class RepositoryModule {

    @Singleton
    @Provides
    fun provideRestaurantRepository(apis:WebServices):NewsRepository{
        return NewsRepositoryImpl(apis)
    }
}