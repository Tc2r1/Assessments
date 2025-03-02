package com.pilotflyingj.codechallenge.repository.models

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

/**
 * An Object that holds information on a store site.
 * [name] - the name of the store/site
 * [location] - the Latitude and Longitude of the store/site
 */
data class Site(
    val name: String,
    val location: LatLng,
    val address: String,
    val phoneNumber: String?
) : ClusterItem {
    override fun getPosition(): LatLng = location

    override fun getTitle(): String = name

    override fun getSnippet(): String = address
}
