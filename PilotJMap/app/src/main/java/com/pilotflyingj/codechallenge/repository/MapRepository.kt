package com.pilotflyingj.codechallenge.repository

import com.pilotflyingj.codechallenge.network.LocationService
import com.pilotflyingj.codechallenge.network.models.ApiSite
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response
import javax.inject.Inject

class MapRepository @Inject constructor(
    private val service: LocationService
) {
    /**
     * Pulls the service in, run business logic
     * supplied with a reference to [MapRepository]
     */
    fun getSites(): Observable<List<ApiSite>> = service.getLocationsObservables()

    suspend fun getCRSites(): Response<List<ApiSite>> = service.getLocationsResponses()
}