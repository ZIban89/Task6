package com.example.task6.data

import com.example.task6.data.dto.BelarusbankAtmDto
import com.example.task6.data.dto.BelarusbankFilialDto
import com.example.task6.data.dto.BelarusbankInfoboxDto
import com.example.task6.domain.model.BelarusbankItem
import com.google.android.gms.maps.model.LatLng

private const val DESCRIPTION_ATM = "Банкомат"
private const val DESCRIPTION_INFOBOX = "Инфокиоск"
private const val DESCRIPTION_FILIAL = "Филиал"

/*
* Не знаю, лучше оставить маппер object, или сделать простым классом.
* Статика вроде ж не особо тру ООП.
* */
object BelarusbankMapper {
    fun toBelarusbankObject(dto: BelarusbankInfoboxDto) = BelarusbankItem(
        id = dto.id,
        latLng = LatLng(dto.gpsX.toDouble(), dto.gpsY.toDouble()),
        address = "${dto.addressType} ${dto.address}, ${dto.house}",
        DESCRIPTION_INFOBOX
    )

    fun toBelarusbankObject(dto: BelarusbankFilialDto) = BelarusbankItem(
        id = dto.id,
        latLng = toLatLng(dto.gpsX, dto.gpsY),
        address = "${dto.addressType} ${dto.address}, ${dto.house}",
        DESCRIPTION_FILIAL
    )

    fun toBelarusbankObject(dto: BelarusbankAtmDto) = BelarusbankItem(
        id = dto.id,
        latLng = LatLng(dto.gpsX.toDouble(), dto.gpsY.toDouble()),
        address = "${dto.addressType} ${dto.address}, ${dto.house}",
        DESCRIPTION_ATM
    )

    private fun toLatLng(gpsX: String, gpsY: String) = LatLng(gpsX.toDouble(), gpsY.toDouble())
}