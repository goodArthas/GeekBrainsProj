package ru.geekbrainsproj.view

import android.Manifest
import android.content.pm.PackageManager
import android.graphics.Color
import android.location.Address
import android.location.Geocoder
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.*
import ru.geekbrainsproj.R
import ru.geekbrainsproj.databinding.FragmentMapsBinding
import java.io.IOException

class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var map: GoogleMap
    private val markers: ArrayList<Marker> = arrayListOf()

    lateinit var binding: FragmentMapsBinding

    lateinit var searchText: String

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = FragmentMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        searchText = intent.getStringExtra("COUNTRY") ?: "Moscow"

        val mapFragment = supportFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
        initSearchByAddress()
    }

    private fun initSearchByAddress() {
        val geoCoder = Geocoder(this)
        Thread {
            try {
                val addresses = geoCoder.getFromLocationName(searchText, 1)
                if (addresses.size > 0) {
                    goToAddress(addresses, searchText)
                }
            } catch (e: IOException) {
                e.printStackTrace()
            }
        }.start()
    }

    private fun goToAddress(
            addresses: MutableList<Address>,
            searchText: String
    ) {
        val location = LatLng(
                addresses[0].latitude,
                addresses[0].longitude
        )
        runOnUiThread {
            setMarker(location, searchText, android.R.drawable.ic_menu_mapmode)
            map.moveCamera(
                    CameraUpdateFactory.newLatLngZoom(
                            location,
                            15f
                    )
            )
        }
    }

    private fun addMarkerToArray(location: LatLng) {
        val marker = setMarker(location, markers.size.toString(), android.R.drawable.ic_menu_mapmode)
        markers.add(marker)
    }

    private fun setMarker(
            location: LatLng,
            searchText: String,
            resourceId: Int
    ): Marker {
        return map.addMarker(
                MarkerOptions()
                        .position(location)
                        .title(searchText)
                        .icon(BitmapDescriptorFactory.fromResource(resourceId))
        )
    }

    private fun drawLine() {
        val last: Int = markers.size - 1
        if (last >= 1) {
            val previous: LatLng = markers[last - 1].position
            val current: LatLng = markers[last].position
            map.addPolyline(
                    PolylineOptions()
                            .add(previous, current)
                            .color(Color.RED)
                            .width(5f)
            )
        }
    }

    private fun activateMyLocation(googleMap: GoogleMap) {
        val isPermissionGranted =
                ContextCompat.checkSelfPermission(this, Manifest.permission.ACCESS_FINE_LOCATION) ==
                        PackageManager.PERMISSION_GRANTED
        googleMap.isMyLocationEnabled = isPermissionGranted
        googleMap.uiSettings.isMyLocationButtonEnabled = isPermissionGranted

    }

    override fun onMapReady(p0: GoogleMap?) {
        p0?.let {
            map = it
            val initialPlace = LatLng(52.52000659999999, 13.404953999999975)

            map.moveCamera(CameraUpdateFactory.newLatLng(initialPlace))
            map.setOnMapLongClickListener { latLng ->
                addMarkerToArray(latLng)
                drawLine()
            }
            activateMyLocation(map)
        }

    }

}