package ru.ownikss.nativemaps.models

import android.content.Context
import com.google.android.gms.maps.model.LatLng
import org.json.JSONArray

class MarkersFactory private constructor(context_: Context) {
    private var context: Context = context_

    private var markers = Array(5000) { LatLng(0.0, 0.0) }

    init {
        initMarkers()
    }

    private fun addFromResource(index: Int) {
        val res = context.resources.getIdentifier(
            "json$index",
            "string",
            context.packageName
        )
        context.packageName
        val arr = JSONArray(context.getString(res))
        for (i in 0 until arr.length()) {
            val point = arr.getJSONObject(i).getJSONObject("location")
            val loc = LatLng(
                point.getDouble("latitude"),
                point.getDouble("longitude")
            )
            markers[index * 100 + i] = loc
        }
    }

    private fun initMarkers() {
        for (i in 0 until 50) {
            addFromResource(i)
        }
    }

    companion object {
        private var instance: MarkersFactory? = null

        fun initialize(context: Context) {
            instance = MarkersFactory(context)
        }

        fun getMarkers(count: Int): Array<LatLng> {
            return instance!!.markers.sliceArray(0 until count)
        }
    }
}
