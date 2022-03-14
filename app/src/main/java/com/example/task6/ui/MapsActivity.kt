package com.example.task6.ui

import android.os.Bundle
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.example.task6.R
import com.example.task6.databinding.ActivityMapsBinding
import com.example.task6.common.BitmapHelper
import com.example.task6.common.distanceTo
import com.example.task6.domain.model.BelarusbankItem
import com.example.task6.domain.repo.BelarusbankRepo
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions
import com.google.android.material.snackbar.Snackbar
import dagger.hilt.android.AndroidEntryPoint
import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
import io.reactivex.rxjava3.disposables.Disposable
import io.reactivex.rxjava3.observers.DisposableObserver
import io.reactivex.rxjava3.schedulers.Schedulers
import kotlinx.coroutines.CancellationException
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import javax.inject.Inject

private const val DEFAULT_LATITUDE = 52.425163
private const val DEFAULT_LONGITUDE = 31.015039

@AndroidEntryPoint
class MapsActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var binding: ActivityMapsBinding

    private var searchPoint = LatLng(DEFAULT_LATITUDE, DEFAULT_LONGITUDE)

    @Inject
    lateinit var repo: BelarusbankRepo

    private var job: Job? = null

    private val belarusbankItems by lazy {
        repo.getAtmList().mergeWith(repo.getFilialList()).mergeWith(repo.getInfoboxList())
            .subscribeOn(Schedulers.io())
            .observeOn(Schedulers.computation())
            .sorted { item, other ->
                item.latLng.distanceTo(searchPoint) - other.latLng.distanceTo(searchPoint)
            }
            .take(10)
            .observeOn(AndroidSchedulers.mainThread())
    }

    private val observer
        get() = object : DisposableObserver<BelarusbankItem>() {
            override fun onNext(t: BelarusbankItem) {
                val item = t.latLng
                mMap.addMarker(
                    MarkerOptions().position(item).title("${t.description}: ${t.address}")
                )
            }

            override fun onError(e: Throwable) {
                Log.d("TAGGG", "${e.message}")
                Snackbar.make(
                    binding.root,
                    getString(R.string.internet_error_message),
                    Snackbar.LENGTH_LONG
                ).show()

                job = lifecycleScope.launch {
                    try {
                        delay(10000)
                        drawMarkers()
                    } catch (e: CancellationException) {
                    }
                }
            }

            override fun onComplete() {
                Log.d("TAGGG", "onComplite")
            }
        }

    private var disposable: Disposable? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMapsBinding.inflate(layoutInflater)
        setContentView(binding.root)

        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.map) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap
        drawMarkers()
    }

    private fun drawMarkers() {
        mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(searchPoint, 13f))
        mMap.addMarker(
            MarkerOptions()
                .position(searchPoint)
                .title(getString(R.string.search_point_title))
                .icon(
                    BitmapHelper.vectorToBitmap(
                        this,
                        R.drawable.map_marker_star,
                        ContextCompat.getColor(this, R.color.purple_500)
                    )
                ).zIndex(100f)
        )
        disposable = belarusbankItems.subscribeWith(observer)
    }

    override fun onDestroy() {
        job?.cancel()
        disposable?.dispose()
        super.onDestroy()
    }
}