package com.egenvall.skeppsklocka.di

import com.egenvall.skeppsklocka.network.FirebaseInteractor
import com.egenvall.skeppsklocka.network.FirebaseInteractorImpl
import com.egenvall.skeppsklocka.network.FirebaseService
import dagger.Module
import dagger.Provides
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Converter
import retrofit2.Retrofit
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory
import retrofit2.converter.gson.GsonConverterFactory
import rx.schedulers.Schedulers
import javax.inject.Named
import javax.inject.Singleton


@Module
class NetworkModule {

    @Provides
    @Named("baseUrl")
    @Singleton
    fun provideBaseUrl(): String {
        return "https://fcm.googleapis.com/"
    }

    @Provides
    @Named("accessKey")
    @Singleton
    fun provideAccessKey(): String {
        return "key=YourKey";
    }

    @Provides
    @Singleton
    fun provideHeaderInterceptor(@Named("accessKey") accessKey: String): Interceptor {
        return Interceptor { chain ->
            val originalRequest = chain.request()
            val accessRequest = originalRequest.newBuilder().addHeader("Authorization", accessKey)
                    .addHeader("Content-Type","application/json")
                    .build()
            chain.proceed(accessRequest)
        }
    }

    @Provides
    @Named("Firebase")
    @Singleton
    fun provideOkHttpClient(interceptor: Interceptor): OkHttpClient {
        val interceptorLog = HttpLoggingInterceptor()
        interceptorLog.setLevel(HttpLoggingInterceptor.Level.BODY)
        return OkHttpClient.Builder().addInterceptor(interceptor).addInterceptor(interceptorLog).build()
    }

    @Provides
    @Named("Firebase")
    @Singleton
    fun provideConverter(): Converter.Factory {
        return GsonConverterFactory.create()
    }
    @Provides
    @Named("Firebase")
    @Singleton
    fun provideRetrofit(@Named("Firebase") client: OkHttpClient, @Named("baseUrl") baseUrl: String, @Named("Firebase") converterFactory: Converter.Factory): Retrofit {
        return Retrofit.Builder()
                .client(client)
                .baseUrl(baseUrl)
                .addConverterFactory(converterFactory)
                .addCallAdapterFactory(RxJavaCallAdapterFactory.createWithScheduler(Schedulers.io()))
                .build()
    }

    @Provides
    @Singleton
    fun provideFirebaseService(@Named("Firebase") retrofit: Retrofit): FirebaseService {
        return retrofit.create(FirebaseService::class.java)
    }

    @Provides
    @Singleton
    fun provideFirebaseInteractor(service: FirebaseService): FirebaseInteractor {
        return FirebaseInteractorImpl(service)
    }
}