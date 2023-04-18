package com.example.app

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.databinding.DataBindingUtil
import com.arcgismaps.ApiKey
import com.arcgismaps.ArcGISEnvironment
import com.arcgismaps.mapping.ArcGISMap
import com.arcgismaps.mapping.BasemapStyle
import com.arcgismaps.mapping.Viewpoint
import com.arcgismaps.mapping.view.MapView
import com.example.app.databinding.ActivityMainBinding


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


    }

    private fun setupMap() {

        val map = ArcGISMap(BasemapStyle.ArcGISTopographic)

        // set the map to be displayed in the layout's MapView
        mapView.map = map

        mapView.setViewpoint(Viewpoint(34.0270, -118.8050, 72000.0))

    }

    private fun setApiKey() {

        // It is not best practice to store API keys in source code. We have you insert one here
        // to streamline this tutorial.

        ArcGISEnvironment.apiKey = ApiKey.create("You_API_KEY")

    }

}