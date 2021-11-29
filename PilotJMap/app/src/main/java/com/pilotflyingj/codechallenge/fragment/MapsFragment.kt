package com.pilotflyingj.codechallenge.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.lifecycleScope
import androidx.navigation.ui.AppBarConfiguration
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLngBounds
import com.google.maps.android.clustering.ClusterManager
import com.google.maps.android.ktx.awaitMap
import com.google.maps.android.ktx.awaitMapLoad
import com.pilotflyingj.codechallenge.R
import com.pilotflyingj.codechallenge.databinding.FragmentMapsBinding
import com.pilotflyingj.codechallenge.repository.models.Site
import com.pilotflyingj.codechallenge.utility.MarkerInfoWindowAdapter
import com.pilotflyingj.codechallenge.utility.SiteRenderer
import com.pilotflyingj.codechallenge.viewmodel.MapsViewModel
import com.pilotflyingj.codechallenge.viewmodel.MapsViewModelFactory
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MapsFragment : Fragment() {
    // Contains all the views
    private var _binding: FragmentMapsBinding? = null

    // This property is only valid between onCreateView and onDestroyView
    private val binding get() = _binding!!

    private val mapsViewModel: MapsViewModel by viewModels()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        _binding = FragmentMapsBinding.inflate(inflater, container, false)
        binding.viewModel = mapsViewModel
        binding.lifecycleOwner = this

        setHasOptionsMenu(true)
        return binding.root
    }

    @SuppressLint("PotentialBehaviorOverride")
    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as? SupportMapFragment

        lifecycleScope.launchWhenCreated {
            // Get the Map
            mapFragment?.let { fragment ->
                val googleMap = fragment.awaitMap()

                // Wait for the map to finish loading
                googleMap.awaitMapLoad()

                mapsViewModel.sites.observe(
                    viewLifecycleOwner,
                    { listOfSites ->
                        // Ensure all places are visible in the map
                        val bounds = LatLngBounds.builder()
                        listOfSites.forEach { bounds.include(it.location) }
                        googleMap.moveCamera(
                            CameraUpdateFactory.newLatLngBounds(
                                bounds.build(),
                                20
                            )
                        )
                        addClusteredMarkers(googleMap, listOfSites)
                    }
                )
            }
        }
    }

    /**
     * Adds markers to the map with clustering support.
     */
    private fun addClusteredMarkers(googleMap: GoogleMap, listOfSites: List<Site>) {

        val clusterManager = ClusterManager<Site>(requireContext(), googleMap)
        clusterManager.renderer = SiteRenderer(
            requireContext(),
            googleMap,
            clusterManager
        )

        // Set custom info window adapter
        clusterManager.markerCollection.setInfoWindowAdapter(MarkerInfoWindowAdapter(requireContext()))

        // Add the sites to the ClusterManager.
        clusterManager.addItems(listOfSites)
        clusterManager.cluster()

        // Set ClusterManager as the OnCameraIdleListener so that it
        // can re-cluster when zooming in and out.
        googleMap.setOnCameraIdleListener {
            clusterManager.markerCollection.markers.forEach { it.alpha = 1.0f }
            clusterManager.clusterMarkerCollection.markers.forEach { it.alpha = 1.0f }
            clusterManager.onCameraIdle()
        }

        googleMap.setOnCameraMoveStartedListener {
            clusterManager.markerCollection.markers.forEach { it.alpha = 0.3f }
            clusterManager.clusterMarkerCollection.markers.forEach { it.alpha = 0.3f }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        // prevents memory leaks
        _binding = null
    }
}
