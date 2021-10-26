package com.pilotflyingj.codechallenge.network

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import timber.log.Timber
import java.util.concurrent.TimeUnit
import javax.inject.Inject
import javax.inject.Singleton

@Singleton
class OkHttpProvider @Inject constructor(
) {
    companion object {

        const val WRITE_TIMEOUT = 60
        const val READ_TIMEOUT = 180
        const val CONNECT_TIMEOUT = 90
    }

    fun getOkHttpClient(): OkHttpClient {
        return getClient()
    }

    private fun getClient(): OkHttpClient {
        val okBuilder = OkHttpClient.Builder()
            .writeTimeout(WRITE_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .readTimeout(READ_TIMEOUT.toLong(), TimeUnit.SECONDS)
            .connectTimeout(CONNECT_TIMEOUT.toLong(), TimeUnit.SECONDS)

        val loggingInterceptor = HttpLoggingInterceptor { message ->
            try {
                Timber.tag("OkHttp").v(message)
            } catch (e: Exception) {
                throw e
            }
        }
        loggingInterceptor.level = HttpLoggingInterceptor.Level.BODY
        okBuilder.addInterceptor(loggingInterceptor)
        return okBuilder.build()

    }
}