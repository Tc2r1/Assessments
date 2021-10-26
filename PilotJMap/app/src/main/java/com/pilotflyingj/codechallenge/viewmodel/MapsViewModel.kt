package com.pilotflyingj.codechallenge.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.google.android.gms.maps.model.LatLng
import com.pilotflyingj.codechallenge.network.models.ApiSite
import com.pilotflyingj.codechallenge.repository.MapRepository
import com.pilotflyingj.codechallenge.repository.models.Site
import com.pilotflyingj.codechallenge.utility.transform
import dagger.hilt.android.lifecycle.HiltViewModel
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.plugins.RxJavaPlugins.onError
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.*
import timber.log.Timber
import javax.inject.Inject
import kotlin.random.Random

@HiltViewModel
class MapsViewModel @Inject constructor(
    private val mapRepository: MapRepository
) : ViewModel() {
    //Encapsulation using Backing Properties.
    private var _listOfLocations = MutableLiveData<List<ApiSite>>()

    private var _sites = MutableLiveData<List<Site>>()
    val sites: LiveData<List<Site>> get() = _sites

    val startPoint = LatLng(37.09024, -95.712891)
    val startZoom: Float = 3.0f
    var job: Job? = null

    init {
        Timber.i("View Model Created!")
        when (Random.nextBoolean()) {
            true -> fetchSitesWithoutCoroutines()
            false -> fetchSitesWithCoroutines()
        }
    }

    /**
     * [fetchSitesWithoutCoroutines] calls the menu repository in order to get
     * a [List] of [ApiSite] objects, if successful
     * I pass along the data for storage inside the MapsViewModel
     *
     * On Error a log is recorded and app halts :( (I'm tired!)
     */
    //TODO Put Kotlin Coroutines Here
    private fun fetchSitesWithCoroutines() {
        Timber.i("FETCHING WITH COROUTINE")
        job = CoroutineScope(Dispatchers.IO).launch {
            val response = mapRepository.getCRSites()
            withContext(Dispatchers.Main) {
                if (response.isSuccessful) {
                    response.body()?.let { storeResponse(it) }
                    // additionally, here we'd set switch to remove loading gui information

                    // check for empty/null body, handle error with GUI
                } else {
                    onError(Throwable(response.message()))
                    // additionally, here we'd set switch to remove loading gui information
                }
            }
        }
    }

    private fun fetchSitesWithoutCoroutines() {
        Timber.i("FETCHING WITHOUT COROUTINE")
        mapRepository.getSites()
            .subscribeOn(Schedulers.newThread())
            .observeOn(AndroidSchedulers.mainThread())
            .subscribe(
                { apiSites ->
                    storeResponse(apiSites)
                    Timber.i("The List of Sites is: \n\n ${_listOfLocations.value}")
                    // additionally, here we'd set switch to remove loading gui information
                },
                {
                    // additionally, here we'd set switch to remove loading gui information
                    Timber.e(it)
                    onError(it)
                }
            )
    }

    /**
     * [storeResponse] saves the response from [MapRepository]
     * to the [MapsViewModel] transforming as well using an extension.
     */
    private fun storeResponse(apiSites: List<ApiSite>) {
        _listOfLocations.postValue(apiSites)
        _sites.postValue(apiSites.transform())
    }

    override fun onCleared() {
        super.onCleared()
        job?.cancel()
    }
}