package com.pilotflyingj.codechallenge.network

import com.pilotflyingj.codechallenge.network.models.ApiSite
import io.reactivex.rxjava3.core.Observable
import retrofit2.Response
import retrofit2.http.GET

interface LocationService {
    // TODO: define the API endpoint here, use the models in network layer with Kotlin Serialization

    @GET("locations.json")
    fun getLocationsObservables(): Observable<List<ApiSite>>

    @GET("locations.json")
    suspend fun getLocationsResponses(): Response<List<ApiSite>>

    // The Correct request url  is
    // https://raw.githubusercontent.com/PFJCodeChallenge/pfj-locations/master/locations.json

    // project provided url is broken.
    // https://raw.githubusercontent.com/simonsickle/pfj-locations/master/locations.json
}