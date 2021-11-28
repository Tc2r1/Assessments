package com.pilotflyingj.codechallenge.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.lifecycle.Observer
import androidx.navigation.ui.AppBarConfiguration
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.pilotflyingj.codechallenge.R
import com.pilotflyingj.codechallenge.databinding.FragmentMapsBinding
import com.pilotflyingj.codechallenge.utility.MarkerInfoWindowAdapter
import com.pilotflyingj.codechallenge.viewmodel.MapsViewModel
import com.pilotflyingj.codechallenge.viewmodel.MapsViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import timber.log.Timber

@AndroidEntryPoint
class MapsFragment : Fragment() {
    // Contains all the views
    private var _binding: FragmentMapsBinding? = null

    // This property is only valid between onCreateView and onDestroyView
    private val binding get() = _binding!!
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var drawerLayout: DrawerLayout

    private val mapsViewModel: MapsViewModel by viewModels()

    private lateinit var mapsViewModelFactory: MapsViewModelFactory

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapsBinding.inflate(inflater, container, false)
        binding.viewModel = mapsViewModel
        binding.lifecycleOwner = this

        setHasOptionsMenu(true)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }

    /**
     * A Callback when google has successfuly generated a map in background.
     * Once we know the map is created, we can begin making changes to the map.
     *
     */
    private val callback = OnMapReadyCallback { mMap ->
        /**
         * Manipulates the map once available.
         * This callback is triggered when the map is ready to be used.
         * This is where we can add markers or lines, add listeners or move the camera.
         * In this case, we just add a marker near Sydney, Australia.
         * If Google Play services is not installed on the device, the user will be prompted to
         * install it inside the SupportMapFragment. This method will only be triggered once the
         * user has installed Google Play services and returned to the app.
         */
        // center camera on the entire USA
        mMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                mapsViewModel.startPoint,
                mapsViewModel.startZoom
            )
        )

        // subscribe to live data for view model so that markers get added
        subscribeToViewModel(mMap)

        mMap.setInfoWindowAdapter(MarkerInfoWindowAdapter(this))
    }

    /**
     * Method to handle setting up an observer for
     * the [Sites] variable of [MapsViewModel]
     * When the api returns data to the ViewModel.
     * We are able to populate the Map with markers.
     */
    private fun subscribeToViewModel(mMap: GoogleMap) {
        Timber.e("subscribeToViewModel Called!")
        mapsViewModel.sites.observe(
            this,
            Observer { listOfSites ->
                for (site in listOfSites) {

                    val marker = mMap.addMarker(
                        MarkerOptions()
                            .position(site.location)
                            .title(site.name)
                            .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                    )

                    // Set place as the tag on the marker object so it can be referenced
                    // within the MarkerInfoWindowAdapter
                    marker?.tag = site
                }
            }
        )
    }

    /**
     * On Destroy, releases binding over objects
     * to ensure no memory leaks.
     */
    override fun onDestroy() {
        super.onDestroy()
        // prevents memory leaks
        _binding = null
    }
}
