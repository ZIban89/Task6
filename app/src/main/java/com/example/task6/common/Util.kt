package com.example.task6.common

import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.SphericalUtil

fun LatLng.distanceTo(point: LatLng) =
    (SphericalUtil.computeDistanceBetween(this, point)*1000).toInt()