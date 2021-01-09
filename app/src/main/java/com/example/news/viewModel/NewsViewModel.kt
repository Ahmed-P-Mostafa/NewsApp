package com.example.news.viewModel

import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.news.newsApi.ApiManager
import com.example.news.newsApi.model.ArticlesItem
import com.example.news.newsApi.model.NewsResponse
import com.example.news.newsApi.model.Source
import com.example.news.newsApi.model.SourceResponse
import kotlinx.android.synthetic.main.fragment_news.*
import retrofit2.Call
import retrofit2.Callback
import retrofit2.Response

class NewsViewModel:ViewModel() {
    private  val TAG = "NewsViewModel"
    val sourcesLiveData = MutableLiveData<List<Source?>>()
    val newsLiveData = MutableLiveData<ArrayList<ArticlesItem?>>()
    val showProgressBar = MutableLiveData<Boolean>()


    fun getNewsResponse(sources:String, language: String) {
        showProgressBar.value = true
        ApiManager.getApis().getNews(sources = sources,language = language ).enqueue(object :
            Callback<NewsResponse> {
            override fun onResponse(call: Call<NewsResponse>, response: Response<NewsResponse>) {
                Log.d(TAG, "getNews onResponse: ")
                showProgressBar.value = false
                if (response.isSuccessful) {

                    Log.d(TAG, "onResponse: Succeed")
                    newsLiveData.value = response.body()?.articles

                    if(response.raw().networkResponse!= null){
                        Log.d(TAG, "onNewsResponse: response is from NETWORK...")
                    }
                    else if(response.raw().cacheResponse != null
                        ||response.raw().networkResponse == null){
                        Log.d(TAG, "onNewsResponse: response is from CACHE...");
                    }
                } else {
                    Log.d(TAG, "onNewsResponse: failed")
                }
            }

            override fun onFailure(call: Call<NewsResponse>, t: Throwable) {

                Log.d(TAG, "getNews onFailure: ")
                //Toast.makeText(context, "Failed to get Articles", Toast.LENGTH_LONG).show()
            }

        })
    }
    fun getSourcesResponse() {
        Log.d(TAG, "getApiResponse: entered")

        ApiManager.getApis().getSources("en").enqueue(object : Callback<SourceResponse> {
            override fun onResponse(
                call: Call<SourceResponse>,
                response: Response<SourceResponse>
            ) {
                if (response.isSuccessful && response.body()?.status.equals("ok")) {

                    // Toast.makeText(this@SportsFragment.context, "Succeed to get Articles", Toast.LENGTH_LONG).show()
                    Log.d(TAG, "getApiResponse onResponse: success")
                    //Log.d(TAG, "onResponse: ${response.body()?.sources?.size}")
                    /* Log.d(TAG, "onResponse: code : ${response.code()}")
                     Log.d(TAG, "onResponse: message : ${response.message().toString()}")*/
                    if(response.raw().networkResponse!= null){
                        Log.d(TAG, "onSourcesResponse: response is from NETWORK...");
                    }
                    else if(response.raw().cacheResponse != null
                        && response.raw().networkResponse == null){
                        Log.d(TAG, "onSourcesResponse: response is from CACHE...");
                    }
                    //showTabs(response.body()?.sources)
                    sourcesLiveData.value = response.body()?.sources


                } else {
                    // Log.d(TAG, "onResponse: ${response.code()}")
                   // Toast.makeText(context, "api response not successful", Toast.LENGTH_LONG).show()
                    Log.d(TAG, "onResponse: not succeed")
                }
            }

            override fun onFailure(call: Call<SourceResponse>, t: Throwable) {

            }
        })
    }
}