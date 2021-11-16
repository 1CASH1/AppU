package com.hugo.moviesofmine.view.Fragment

import android.Manifest
import android.content.pm.PackageManager
import android.location.Location
import android.location.LocationManager
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationServices
import com.google.android.gms.maps.CameraUpdateFactory
import com.hugo.moviesofmine.R
import com.hugo.moviesofmine.databinding.FragmentMapBinding
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions


class MapFragment : Fragment(R.layout.fragment_map), OnMapReadyCallback,
    GoogleMap.OnMyLocationButtonClickListener,
    GoogleMap.OnMyLocationClickListener,
    ActivityCompat.OnRequestPermissionsResultCallback {
    private lateinit var locationManager: LocationManager
    private lateinit var binding: FragmentMapBinding
    private var mMap: GoogleMap? = null
    private var currenUbication: Location? = null

    private val LOCATION_PERMISSION_REQUEST_CODE = 101

    //private var flpc: FusedLocationProviderClient? = null
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest
    private lateinit var locationCallback: LocationCallback




    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding = FragmentMapBinding.bind(view)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
       // fusedLocationClient = LocationServices.getFusedLocationProviderClient(context!!)
    }





    companion object{
        @JvmStatic
        fun newInstance() = MapFragment()
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        if (currenUbication != null) {
            // Add a marker in Sydney and move the camera
            val sydney = LatLng(currenUbication!!.latitude, currenUbication!!.longitude)
            mMap!!.addMarker(
                MarkerOptions()
                    .position(sydney)
                    .title("Marker in Sydney")
            )
            mMap!!.moveCamera(CameraUpdateFactory.newLatLng(sydney))
            currenUbication
        }
    }




    private fun warningMessage(){
        Toast.makeText(context,"Es necesario aceptar los permisos", Toast.LENGTH_SHORT).show()
    }

    override fun onMyLocationButtonClick(): Boolean {
        TODO("Not yet implemented")
    }

    override fun onMyLocationClick(p0: Location) {
        TODO("Not yet implemented")
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        when(requestCode){
            LOCATION_PERMISSION_REQUEST_CODE ->{
                if (grantResults.size > 0 && grantResults[0] ==PackageManager.PERMISSION_GRANTED){
                }
            }
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
    }


}