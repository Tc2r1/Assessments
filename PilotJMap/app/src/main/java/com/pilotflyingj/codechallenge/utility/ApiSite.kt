@file:JvmName("ApiSite")
@file:JvmMultifileClass // Add Extensions this way in order to reduce the number  of generated files.
package com.pilotflyingj.codechallenge.utility

import com.google.android.gms.maps.model.LatLng
import com.pilotflyingj.codechallenge.network.models.ApiSite
import com.pilotflyingj.codechallenge.repository.models.Site
import java.lang.StringBuilder

/**
 * Extensions for [ApiSite] to convert into [Site] objects
 * Using [ApiSite.latitude] and [ApiSite.longitude] to create [Site.location]
 * Using [ApiSite.storeName] to create [Site.name]
 */
fun ApiSite.transform(): Site {
    this.apply {
//        For this to look great I would have to make a custom adapter and I'm tired.
//        return Site(
//            StringBuilder()
//                .append(storeName)
//                .append("\n\n")
//                .append(this.city)
//                .append(", $state")
//                .toString()
//            , LatLng(latitude, longitude)
//        )

        return Site(storeName, LatLng(latitude, longitude))
    }
}

fun List<ApiSite>.transform(): List<Site> {
    return this.map {
        it.transform()
    }
}