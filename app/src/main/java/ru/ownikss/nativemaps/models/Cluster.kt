package ru.ownikss.nativemaps.models

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.clustering.ClusterItem

class Cluster(val lat: Double, val lon: Double) : ClusterItem {
    override fun getSnippet(): String {
        return "snippet"
    }

    override fun getTitle(): String {
        return "title"
    }

    override fun getPosition(): LatLng {
        return LatLng(lat, lon)
    }
}
