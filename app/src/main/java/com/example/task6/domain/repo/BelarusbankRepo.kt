package com.example.task6.domain.repo

import com.example.task6.domain.model.BelarusbankItem
import io.reactivex.rxjava3.core.Observable

private const val DEFAULT_CITY = "гомель"

interface BelarusbankRepo {

    fun getAtmList(city: String = DEFAULT_CITY): Observable<BelarusbankItem>

    fun getFilialList(city: String = DEFAULT_CITY): Observable<BelarusbankItem>

    fun getInfoboxList(city: String = DEFAULT_CITY): Observable<BelarusbankItem>
}