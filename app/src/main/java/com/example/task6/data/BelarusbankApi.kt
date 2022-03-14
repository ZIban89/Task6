package com.example.task6.data

import com.example.task6.data.dto.BelarusbankAtmDto
import com.example.task6.data.dto.BelarusbankFilialDto
import com.example.task6.data.dto.BelarusbankInfoboxDto
import io.reactivex.rxjava3.core.Observable
import retrofit2.http.GET
import retrofit2.http.Query

private const val ATM_QUERY_PATH = "atm/"
private const val FILIALS_QUERY_PATH = "filials_info/"
private const val INFOBOX_QUERY_PATH = "infobox/"
private const val ATM_CITY_QUERY = "city"
const val BELARUSBANK_API_URL = "https://belarusbank.by/api/"

interface BelarusbankApi {
    @GET(ATM_QUERY_PATH)
    fun getAtmList(@Query(ATM_CITY_QUERY) city: String): Observable<List<BelarusbankAtmDto>>

    @GET(FILIALS_QUERY_PATH)
    fun getFilialList(@Query(ATM_CITY_QUERY) city: String): Observable<List<BelarusbankFilialDto>>

    @GET(INFOBOX_QUERY_PATH)
    fun getInfoboxList(@Query(ATM_CITY_QUERY) city: String): Observable<List<BelarusbankInfoboxDto>>
}