package com.example.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.arcgismaps.ApiKey
import com.arcgismaps.ArcGISEnvironment
import com.arcgismaps.Color
import com.arcgismaps.geometry.Point
import com.arcgismaps.geometry.SpatialReference
import com.arcgismaps.mapping.ArcGISMap
import com.arcgismaps.mapping.BasemapStyle
import com.arcgismaps.mapping.Viewpoint
import com.arcgismaps.mapping.symbology.SimpleMarkerSymbol
import com.arcgismaps.mapping.symbology.SimpleMarkerSymbolStyle
import com.arcgismaps.mapping.view.Graphic
import com.arcgismaps.mapping.view.GraphicsOverlay
import com.arcgismaps.mapping.view.MapView
import com.example.app.databinding.ActivityMainBinding
import android.content.pm.PackageManager
import android.util.Log
import kotlinx.android.synthetic.main.zoom_control.*
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch

class MainActivity : AppCompatActivity() {

    private lateinit var activityMainBinding: ActivityMainBinding

    private val mapView: MapView by lazy {
        activityMainBinding.mapView
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        activityMainBinding = DataBindingUtil.setContentView(this, R.layout.activity_main)
        lifecycle.addObserver(mapView)

        setApiKey()

        setupMap()

        addGraphics()


    }

    private fun setupMap() {

        val map = ArcGISMap(BasemapStyle.ArcGISStreets)

        // set the map to be displayed in the layout's MapView
        mapView.map = map

        mapView.setViewpoint(Viewpoint(1.293889, 103.793393, 4500.0))

        // Set up zoom control
        zoom_in_button.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                val currentScale = mapView.mapScale.value / 2
                Log.d("scale", currentScale.toString())
                mapView.setViewpointScale(currentScale)
            }
        }

        zoom_out_button.setOnClickListener {
            CoroutineScope(Dispatchers.Main).launch {
                val currentScale = mapView.mapScale.value * 2
                mapView.setViewpointScale(currentScale)
            }
        }

    }

    private fun setApiKey() {

        // It is not best practice to store API keys in source code. We have you insert one here
        // to streamline this tutorial.

        try {
            val activityInfo = packageManager.getActivityInfo(componentName, PackageManager.GET_META_DATA)
            val metaData = activityInfo.metaData
            val arcGisApiKey = metaData?.getString("ARC_GIS_API_KEY")
            if (arcGisApiKey != null) {
                ArcGISEnvironment.apiKey = ApiKey.create(arcGisApiKey)
            }

            // Use the metadata value as needed
        } catch (e: PackageManager.NameNotFoundException) {
            e.printStackTrace()
        }


    }

    private fun addGraphics() {

        // create a graphics overlay and add it to the graphicsOverlays property of the map view
        val graphicsOverlay = GraphicsOverlay()
        mapView.graphicsOverlays.add(graphicsOverlay)

        // create a point geometry with a location and spatial reference
        // Point(latitude, longitude, spatial reference)
        val point = Point(103.793393, 1.293889, SpatialReference.wgs84())

        // create a point symbol that is an small red circle
        val simpleMarkerSymbol = SimpleMarkerSymbol(SimpleMarkerSymbolStyle.Circle, Color.fromRgba(0, 0, 255), 10f)

        // create a graphic with the point geometry and symbol
        val pointGraphic = Graphic(point, simpleMarkerSymbol)

        // add the point graphic to the graphics overlay
        graphicsOverlay.graphics.add(pointGraphic)

    }

}