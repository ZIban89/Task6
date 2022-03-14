package com.example.task6.data

import com.example.task6.domain.model.BelarusbankItem
import com.example.task6.domain.repo.BelarusbankRepo
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject


class BelarusbankRepoImpl @Inject constructor(private val api: BelarusbankApi): BelarusbankRepo {

    override fun getAtmList(city: String): Observable<BelarusbankItem> {
        return api.getAtmList(city).flatMapIterable { it }.map { BelarusbankMapper.toBelarusbankObject(it) }
    }

    override fun getFilialList(city: String): Observable<BelarusbankItem> {
        return api.getFilialList(city).flatMapIterable { it }.map { BelarusbankMapper.toBelarusbankObject(it) }
    }

    override fun getInfoboxList(city: String): Observable<BelarusbankItem> {
        return api.getInfoboxList(city).flatMapIterable { it }.map { BelarusbankMapper.toBelarusbankObject(it) }
    }
}