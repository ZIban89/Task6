package com.example.task6.data.dto

import com.squareup.moshi.Json

private const val ID_FIELD = "info_id"
private const val GPS_X_FIELD = "gps_x"
private const val GPS_Y_FIELD = "gps_y"
private const val ADDRESS_TYPE_FIELD = "address_type"
private const val ADDRESS_FIELD = "address"
private const val HOUSE_NUMBER_FIELD = "house"

class BelarusbankInfoboxDto(
    @field:Json(name = ID_FIELD) val id: String,
    @field:Json(name = GPS_X_FIELD) val gpsX: String,
    @field:Json(name = GPS_Y_FIELD) val gpsY: String,
    @field:Json(name = ADDRESS_TYPE_FIELD) val addressType: String,
    @field:Json(name = ADDRESS_FIELD) val address: String,
    @field:Json(name = HOUSE_NUMBER_FIELD) val house: String,

)