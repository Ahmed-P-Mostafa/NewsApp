package com.example.news.newsApi

import android.content.Context
import android.net.ConnectivityManager
import android.net.NetworkInfo
import android.util.Log
import com.example.news.MainActivity
import com.example.news.ui.NewsFragment
import okhttp3.*
import okhttp3.CacheControl
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.io.File
import java.io.IOException
import java.util.concurrent.TimeUnit


class ApiManager{

    companion object{

        private val TAG = "ApiManager"
        private val HEADER_CACHE_CONTROL = "Cache-Control"
        private val HEADER_PRAGMA = "Pragma"
        private const val cacheSize = 50 * 1024 * 1024.toLong()

        private var retrofit : Retrofit?= null

        private val loggingInterceptor = HttpLoggingInterceptor(object :
            HttpLoggingInterceptor.Logger {
            override fun log(message: String) {

                Log.e(TAG, "Interceptor logging : ${message}")

            }

        }).setLevel(HttpLoggingInterceptor.Level.BODY)

        fun cachingInterceptor():Interceptor{
            return object : Interceptor {
                override fun intercept(chain: Interceptor.Chain): Response {
                    var originalResponse :Response = chain.proceed(chain.request())
                    if (hasNetwork(NewsFragment().requireContext())){
                        val maxAge = 60
                        return originalResponse.newBuilder().header(
                            "Cache-Control",
                            "public, max-age=" + maxAge
                        )
                            .build()
                    }
                    else{
                        val maxStale = 60 * 60 * 24 * 28 // tolerate 4-weeks stale

                        return originalResponse.newBuilder()
                            .header("Cache-Control", "public, only-if-cached, max-stale=$maxStale")
                            .build()
                    }
                }

            }
        }

        /**
         * This interceptor will be called ONLY if the network is available
         * @return
         */
        private fun networkInterceptor(): Interceptor {
            return object : Interceptor {
                @Throws(IOException::class)
                override fun intercept(chain: Interceptor.Chain): Response {
                    Log.d(TAG, "network interceptor: called.")
                    val response: Response = chain.proceed(chain.request())
                    val cacheControl = CacheControl.Builder()
                        .maxAge(5, TimeUnit.MINUTES)
                        .build()
                    return response.newBuilder()
                        .removeHeader(HEADER_PRAGMA)
                        .removeHeader(HEADER_CACHE_CONTROL)
                        .header(HEADER_CACHE_CONTROL, cacheControl.toString())
                        .build()
                }
            }
        }

        /**
         * This interceptor will be called both if the network is available and if the network is not available
         * @return
         */
        private fun offlineInterceptor(): Interceptor {
            return object : Interceptor {
                @Throws(IOException::class)
                override fun intercept(chain: Interceptor.Chain): Response {
                    Log.d(TAG, "offline interceptor: called.")
                    var request: Request = chain.request()

                    // prevent caching when network is on. For that we use the "networkInterceptor"
                    if (!MainActivity.hasNetwork()) {
                        val cacheControl = CacheControl.Builder()
                            .maxStale(7, TimeUnit.DAYS)
                            .build()
                        request = request.newBuilder()
                            .removeHeader(HEADER_PRAGMA)
                            .removeHeader(HEADER_CACHE_CONTROL)
                            .cacheControl(cacheControl)
                            .build()
                    }
                    return chain.proceed(request)
                }
            }
        }

        private fun cache(): Cache {
            return Cache(
                File(NewsFragment().context?.cacheDir, "someIdentifier"),
                cacheSize
            )
        }




        private fun getInstance():Retrofit{

            val okHttpClient =OkHttpClient
                .Builder()
                .cache(cache())
                .addInterceptor(loggingInterceptor)
                .addInterceptor(offlineInterceptor())
                .addNetworkInterceptor(networkInterceptor())
                .build()

            if (retrofit==null){
                retrofit = Retrofit.Builder()
                    .baseUrl("https://newsapi.org/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .client(okHttpClient)
                    .build()
            }
            return retrofit!!
        }

        fun getApis():WebServices{
            return getInstance().create(WebServices::class.java)

        }
        fun hasNetwork(context: Context):Boolean{
            var isOnline : Boolean = false
            var connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
            val activeNetowrk: NetworkInfo? = connectivityManager.activeNetworkInfo
            if (activeNetowrk!=null&&activeNetowrk.isConnected) isOnline = true

            return isOnline
        }


    }


}