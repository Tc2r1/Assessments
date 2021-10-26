package com.pilotflyingj.codechallenge.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.drawerlayout.widget.DrawerLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import androidx.navigation.ui.AppBarConfiguration
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.BitmapDescriptorFactory
import com.google.android.gms.maps.model.MarkerOptions
import com.pilotflyingj.codechallenge.R
import com.pilotflyingj.codechallenge.databinding.FragmentMapsBinding
import com.pilotflyingj.codechallenge.repository.MapRepository
import com.pilotflyingj.codechallenge.viewmodel.MapsViewModel
import com.pilotflyingj.codechallenge.viewmodel.MapsViewModelFactory
import dagger.hilt.android.AndroidEntryPoint
import leakcanary.AppWatcher
import timber.log.Timber
import javax.inject.Inject

@AndroidEntryPoint
class MapsFragment : Fragment() {
    // Contains all the views
    private var _binding: FragmentMapsBinding? = null

    // This property is only valid between onCreateView and onDestoryView
    private val binding get() = _binding!!
    private lateinit var appBarConfiguration: AppBarConfiguration
    private lateinit var drawerLayout: DrawerLayout

    private lateinit var mapsViewModel: MapsViewModel
    private lateinit var mMap: GoogleMap
    private lateinit var mapsViewModelFactory: MapsViewModelFactory

    @Inject
    lateinit var mapRepository: MapRepository


    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapsBinding.inflate(inflater, container, false)
        mapsViewModelFactory = MapsViewModelFactory(mapRepository)
        mapsViewModel = ViewModelProvider(this, mapsViewModelFactory).get(MapsViewModel::class.java)

        binding.viewModel = mapsViewModel
        binding.lifecycleOwner = this

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
    private val callback = OnMapReadyCallback { googleMap ->
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
        mMap = googleMap
        mMap.moveCamera(
            CameraUpdateFactory.newLatLngZoom(
                mapsViewModel.startPoint,
                mapsViewModel.startZoom
            )
        );

        // subscribe to live data for view model so that markers get added
        subscribeToViewModel()
    }

    /**
     * Method to handle setting up an observer for
     * the [Sites] variable of [MapsViewModel]
     * When the api returns data to the ViewModel.
     * We are able to populate the Map with markers.
     */
    private fun subscribeToViewModel() {
        Timber.e("subscribeToViewModel Called!")
        mapsViewModel.sites.observe(this, Observer { listOfSites ->
            for (site in listOfSites) {

                mMap.addMarker(
                    MarkerOptions().position(site.location)
                        .title(site.name)
                        .icon(BitmapDescriptorFactory.defaultMarker(BitmapDescriptorFactory.HUE_AZURE))
                )

                /**
                 *  I would create a custom info adapter to have a custom info window. so that
                 *  the code commented out in [ApiSite] would look really amazing.
                 */
            }
        })
    }

    /**
     * On Destroy, releases binding over objects
     * to ensure no memory leaks.
     */
    override fun onDestroy() {
        super.onDestroy()
        // prevents memory leaks
        AppWatcher.objectWatcher.expectWeaklyReachable(
            _binding as Any,
            "AtivityMapsBinding was detected."
        )
        _binding = null

    }
}