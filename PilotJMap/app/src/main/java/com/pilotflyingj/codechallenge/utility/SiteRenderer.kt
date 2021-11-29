package com.pilotflyingj.codechallenge.utility

import android.content.Context
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.BitmapDescriptor
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.clustering.view.DefaultClusterRenderer
import com.pilotflyingj.codechallenge.repository.models.Site

/*
*  A custom cluster renderer for place objects*/
class SiteRenderer(
    private val context: Context,
    map: GoogleMap,
    clusterManager: ClusterManager<Site>
) : DefaultClusterRenderer<Site>(context, map, clusterManager) {

    /**
     * The icon to use for each cluster item
     */
    private val mapIcon: BitmapDescriptor by lazy {
        BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_RED)
    }

    /**
     * Method called before the cluster item (the marker) is rendered.
     * This is where marker options should be set.
     */
    override fun onBeforeClusterItemRendered(item: Site, markerOptions: MarkerOptions) {
        markerOptions.apply {
            title(item.title)
            position(item.location)
            icon(mapIcon)
        }
    }

    override fun onClusterItemRendered(clusterItem: Site, marker: Marker) {
        marker.tag = clusterItem
    }
}
