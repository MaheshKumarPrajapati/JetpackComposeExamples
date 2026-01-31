package com.mahesh_prajapati.hiltmvvmnetworking.api

import com.mahesh_prajapati.hiltmvvmnetworking.model.Tweet
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Header
import retrofit2.http.Headers

interface TweetsyApi {

    @GET("/v3/b/697cd33843b1c97be957fbe3?meta=false")
    suspend fun getTweets(@Header("X-JSON-Path") category: String): Response<List<Tweet>>

    @GET("/v3/b/697cd33843b1c97be957fbe3?meta=false")
    @Headers("X-JSON-Path: tweets..category")
    suspend fun getCategories(): Response<List<String>>
}