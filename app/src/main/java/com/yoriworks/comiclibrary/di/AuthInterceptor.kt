package com.yoriworks.comiclibrary.di

import com.yoriworks.comiclibrary.utils.Contains
import okhttp3.Interceptor
import okhttp3.Response
import javax.inject.Inject

class AuthInterceptor @Inject constructor() : Interceptor {

    override fun intercept(chain: Interceptor.Chain): Response {
        val requestBuilder = chain.request().newBuilder()
        requestBuilder.addHeader(
            Contains.HEADERNAMES_CONTENT_TYPE,
            Contains.HEADERNAMES_APPLICATION_JSON
        )
        return chain.proceed(requestBuilder.build())
    }
}