package com.example.detail.ui

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.navigation.fragment.navArgs
import com.example.common.BaseFragment
import com.example.detail.R
import com.example.detail.databinding.FragmentMapsBinding
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap.CancelableCallback
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapsFragment : BaseFragment() {

    private val _arg: MapsFragmentArgs by navArgs()
    private lateinit var  _binding: FragmentMapsBinding

    private val callback = OnMapReadyCallback { googleMap ->

        val valledupar = LatLng(_arg.mapInfo.location.lat, _arg.mapInfo.location.lng)
        googleMap.addMarker(MarkerOptions().position(valledupar).title(_arg.mapInfo.name))
        googleMap.animateCamera(
            CameraUpdateFactory.newLatLng(
                valledupar
            ), object : CancelableCallback {
                override fun onCancel() {}
                override fun onFinish() {
                    googleMap.animateCamera(CameraUpdateFactory.zoomTo(12f))
                }
            })
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentMapsBinding.inflate(inflater)
        return _binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)
    }
}