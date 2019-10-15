package ru.ownikss.nativemaps.ui

import android.os.Bundle
import androidx.fragment.app.Fragment
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.databinding.DataBindingUtil
import androidx.lifecycle.ViewModelProviders
import androidx.navigation.fragment.NavHostFragment
import com.google.android.gms.maps.CameraUpdate
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import ru.ownikss.nativemaps.R
import com.google.maps.android.clustering.ClusterManager
import ru.ownikss.nativemaps.databinding.MapFragmentBinding
import ru.ownikss.nativemaps.models.Cluster
import ru.ownikss.nativemaps.models.MainViewModel
import ru.ownikss.nativemaps.models.MarkersFactory

class ClustersFragment : Fragment(), OnMapReadyCallback {
    private var useClusters = false
    private var _map: GoogleMap? = null
    private var mClusterManager: ClusterManager<Cluster>? = null
    lateinit var binding: MapFragmentBinding

    override fun onPause() {
        super.onPause()
        binding.map.onPause()
    }

    override fun onStart() {
        super.onStart()
        binding.map.onStart()
    }

    override fun onDestroy() {
        super.onDestroy()
        binding.map.onDestroy()
    }

    override fun onStop() {
        super.onStop()
        binding.map.onStop()
    }

    override fun onResume() {
        super.onResume()
        binding.map.onResume()
    }

    private fun getDefaultCameraPosition(): CameraUpdate {
        return CameraUpdateFactory.newLatLngZoom(
            LatLng(
                50.11715412909729,
                30.609242901292077
            ), 10f
        )
    }

    override fun onMapReady(p0: GoogleMap?) {
        _map = p0
        if (_map != null) {
            getMap().moveCamera(getDefaultCameraPosition())
        }
        if (useClusters) {
            mClusterManager = ClusterManager(activity, getMap())
            getMap().setOnCameraIdleListener(mClusterManager)
            getMap().setOnMarkerClickListener(mClusterManager)
        }
        addMarkers()
    }

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        val arg = arguments
        if (arg != null) {
            useClusters = arg.getBoolean("useClusters")
        }
        binding = DataBindingUtil.inflate(inflater, R.layout.map_fragment, container, false)
        binding.store = ViewModelProviders.of(activity!!).get(MainViewModel::class.java)
        initListeners()
        binding.map.onCreate(savedInstanceState)
        binding.map.getMapAsync(this)
        return binding.root
    }

    private fun initListeners() {
        binding.seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(p0: SeekBar?, p1: Int, p2: Boolean) {
                binding.store!!.count.set(p1)
            }

            override fun onStartTrackingTouch(p0: SeekBar?) {
            }

            override fun onStopTrackingTouch(p0: SeekBar?) {
                mClusterManager!!.clearItems()
                addMarkers()
                getMap().moveCamera(getDefaultCameraPosition())
            }
        })
        binding.toolbar.setNavigationOnClickListener { goBack() }
    }

    private fun goBack() {
        NavHostFragment.findNavController(this).popBackStack()
    }

    private fun getMap(): GoogleMap {
        return _map!!
    }


    private fun addMarker(location: LatLng) {
        if (useClusters) {
            mClusterManager!!.addItem(
                Cluster(location.latitude, location.longitude)
            )
        } else {
            val obj = MarkerOptions()
            obj.position(location)
            getMap().addMarker(obj)
        }
    }

    private fun addMarkers() {
        val count = binding.store!!.count.get()!!
        val markers = MarkersFactory.getMarkers(count)
        for (i in 0 until count) {
            addMarker(markers[i])
        }
    }
}
