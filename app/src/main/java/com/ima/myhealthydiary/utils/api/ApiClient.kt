package com.ima.myhealthydiary.utils.api

import android.content.Context
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

class ApiClient {
    companion object{
        const val BASE_URL = "http://34.209.188.131:8000/"
        private var retrofit : Retrofit? = null

        private fun provideHttpLoggingInterceptor() = kotlin.run {
            HttpLoggingInterceptor().apply {
                apply {
                    level = HttpLoggingInterceptor.Level.BODY
                }
            }
        }

        fun getApiClient(mContext: Context) : Retrofit? {
            if (retrofit == null) {
                val okHttpClient = OkHttpClient
                    .Builder()
                    .addInterceptor(provideHttpLoggingInterceptor())
                    .connectTimeout(1, TimeUnit.MINUTES)
                    .readTimeout(1, TimeUnit.MINUTES)
                    .writeTimeout(1, TimeUnit.MINUTES)
                    .build()

                retrofit = Retrofit.Builder()
                    .baseUrl(BASE_URL)
                    .client(okHttpClient)
                    .addConverterFactory(GsonConverterFactory.create())
                    .build()
            }
            return retrofit
        }
    }
}