package com.ybg.av.at.http.body


import com.ybg.av.at.http.body.RequestProgressBody
import com.ybg.av.at.http.body.ResponseProgressBody
import com.ybg.av.at.http.listener.ProgressListener

import java.io.IOException

import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.RequestBody
import okhttp3.Response


object BodyWrapper {

    fun addProgressResponseListener(client: OkHttpClient, progressListener: ProgressListener): OkHttpClient {
        return client.newBuilder().addNetworkInterceptor { chain ->
            val originalResponse = chain.proceed(chain.request())
            originalResponse.newBuilder().body(ResponseProgressBody(originalResponse.body(), progressListener)).build()
        }.build()
    }

    fun addProgressRequestListener(requestBody: RequestBody, progressRequestListener: ProgressListener): RequestProgressBody {
        return RequestProgressBody(requestBody, progressRequestListener)
    }
}