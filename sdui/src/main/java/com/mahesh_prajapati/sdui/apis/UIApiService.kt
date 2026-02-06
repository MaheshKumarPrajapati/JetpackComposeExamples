package com.mahesh_prajapati.sdui.apis

import com.mahesh_prajapati.sdui.model.JsonBinResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Url

interface UIApiService {
    
    @GET("v3/b/{binId}")
    suspend fun getScreenData(@Path("binId") binId: String): JsonBinResponse
    
    @GET
    suspend fun getScreenDataByUrl(@Url url: String): JsonBinResponse
    
    @POST
    suspend fun performAction(
        @Url url: String,
        @Body body: Map<String, Any>
    ): Response<ApiResponse>
}

data class ApiResponse(
    val success: Boolean,
    val message: String? = null,
    val data: Any? = null
)
