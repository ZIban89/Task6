package com.example.task6.di

import com.example.task6.data.BELARUSBANK_API_URL
import com.example.task6.data.BelarusbankApi
import com.example.task6.data.BelarusbankRepoImpl
import com.example.task6.domain.repo.BelarusbankRepo
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.adapter.rxjava3.RxJava3CallAdapterFactory
import retrofit2.converter.moshi.MoshiConverterFactory
import java.util.concurrent.TimeUnit
import javax.inject.Singleton


@InstallIn(SingletonComponent::class)
@Module
object AppModule {

    @Provides
    @Singleton
    fun provideOkHttpClient() = OkHttpClient.Builder()
        .callTimeout(10, TimeUnit.SECONDS)
        .addInterceptor(HttpLoggingInterceptor().apply {
                level = HttpLoggingInterceptor.Level.HEADERS
        })
        .build()

    @Provides
    @Singleton
    fun provideAtmBelarusbankApi(client: OkHttpClient) = Retrofit.Builder()
        .baseUrl(BELARUSBANK_API_URL)
        .client(client)
        .addCallAdapterFactory(RxJava3CallAdapterFactory.create())
        .addConverterFactory(MoshiConverterFactory.create())
        .build()
        .create(BelarusbankApi::class.java)

    @Provides
    @Singleton
    fun povideBelarusbankRepo(api: BelarusbankApi): BelarusbankRepo = BelarusbankRepoImpl(api)

}
