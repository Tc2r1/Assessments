package com.pilotflyingj.codechallenge

import com.pilotflyingj.codechallenge.network.LocationService
import com.pilotflyingj.codechallenge.network.RetrofitServiceProvider
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent

@Module
@InstallIn(SingletonComponent::class)
class AppModule {

    /**
     * [providesLocationService] supplies us with a [LocationService]
     * Generated from Retrofit.
     *
     */
    @Provides
    fun providesLocationService(retrofitServiceProvider: RetrofitServiceProvider): LocationService {
        return retrofitServiceProvider.getRetrofitInstance().create(LocationService::class.java)
    }
}