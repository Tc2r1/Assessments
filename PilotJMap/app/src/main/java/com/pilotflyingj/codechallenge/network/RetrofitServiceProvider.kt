package com.pilotflyingj.codechallenge.network

import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import hu.akarnokd.rxjava3.retrofit.RxJava3CallAdapterFactory
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import javax.inject.Inject
import javax.inject.Singleton

/**
 * Though this provider I use an Instance of OkHTTPClient
 * to Create the Retrofit instance using the configured values.
 */
@Singleton
class RetrofitServiceProvider @Inject constructor(
    var okHttpProvider: OkHttpProvider
) {
    companion object {
        // TODO: create the API service lazily and retain in memory
        const val HOST = "raw.githubusercontent.com"
        const val PROTOCOL = "https"
        const val PATH = "/PFJCodeChallenge/pfj-locations/master/"
    }

    private val moshi: Moshi = Moshi.Builder().add(KotlinJsonAdapterFactory()).build()
    private val moshiConverterFactory: MoshiConverterFactory = MoshiConverterFactory.create(moshi)

    fun getRetrofitInstance(): Retrofit {
        val okHttpClient = okHttpProvider.getOkHttpClient()

        val baseUrl = "$PROTOCOL://${Companion.HOST}$PATH"

        return Retrofit.Builder()
            .client(okHttpClient)
            .baseUrl(baseUrl)
            .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
            .addConverterFactory(moshiConverterFactory)
            .build()
    }
}