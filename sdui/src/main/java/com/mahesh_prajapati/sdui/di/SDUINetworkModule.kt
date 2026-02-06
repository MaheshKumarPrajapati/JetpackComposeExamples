package com.mahesh_prajapati.sdui.di

import com.google.gson.Gson
import com.google.gson.GsonBuilder
import com.mahesh_prajapati.sdui.apis.UIApiService
import com.mahesh_prajapati.sdui.repository.UIRepository
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
object  SDUINetworkModule {

    private const val BASE_URL = "https://api.jsonbin.io/"

    @Provides
    @Singleton
    fun provideGson(): Gson {
        return GsonBuilder()
            .setLenient()
            .create()
    }

    @Provides
    @Singleton
    @Named("SDUINetworkModule")
    fun provideRetrofit(
        okHttpClient: OkHttpClient,
        gson: Gson
    ): Retrofit {
        return Retrofit.Builder()
            .baseUrl(BASE_URL)
            .client(okHttpClient)
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build()
    }

    @Provides
    @Singleton
    fun provideUIApiService(@Named("SDUINetworkModule") retrofit: Retrofit): UIApiService {
        return retrofit.create(UIApiService::class.java)
    }

    @Provides
    @Singleton
    fun provideUIRepository(apiService: UIApiService): UIRepository {
        return UIRepository(apiService)
    }
}