package com.mahesh_prajapati.hiltmvvmnetworking.di

import com.mahesh_prajapati.hiltmvvmnetworking.api.TweetsyApi
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import okhttp3.OkHttpClient
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import javax.inject.Named
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class NetworkModule {

    @Singleton
    @Provides
    @Named("TweetsyRetrofit")
    fun provideTweetsyRetrofit(okHttpClient: OkHttpClient): Retrofit {
        return Retrofit.Builder()
            .baseUrl("https://api.jsonbin.io/")
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create())
            .build()
    }
    @Singleton
    @Provides
    fun provideTweetsyApi(@Named("TweetsyRetrofit") retrofit: Retrofit): TweetsyApi {
        return retrofit.create(TweetsyApi::class.java)
    }
}