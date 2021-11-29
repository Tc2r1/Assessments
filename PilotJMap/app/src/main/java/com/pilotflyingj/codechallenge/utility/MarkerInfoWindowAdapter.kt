package com.pilotflyingj.codechallenge.utility

import android.content.Context
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.Marker
import com.pilotflyingj.codechallenge.R
import com.pilotflyingj.codechallenge.repository.models.Site

class MarkerInfoWindowAdapter(
    private val context: Context
) : GoogleMap.InfoWindowAdapter {
    override fun getInfoWindow(marker: Marker): View? {
        // Return null to indicate that the default window
        // (white bubble) should be used.
        return null
    }

    override fun getInfoContents(marker: Marker?): View? {

        // 2. Inflate view and set title, address, and rating
        val view = LayoutInflater.from(context).inflate(R.layout.marker_info_contents, null)

        // 1. Get Tag
        val site = marker?.tag as? Site ?: return null

        view.findViewById<TextView>(R.id.text_view_title).text = site.name
        view.findViewById<TextView>(R.id.text_view_address).text = site.address
        view.findViewById<TextView>(R.id.text_view_phone).text = site.phoneNumber
        return view
    }
}
