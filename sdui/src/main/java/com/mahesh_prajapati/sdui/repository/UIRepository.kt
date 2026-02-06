package com.mahesh_prajapati.sdui.repository

import com.mahesh_prajapati.sdui.apis.UIApiService
import com.mahesh_prajapati.sdui.model.UIScreenData
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.delay
import kotlinx.coroutines.withContext
import javax.inject.Inject

class UIRepository @Inject constructor(
    private val apiService: UIApiService
) {
    
    suspend fun getScreenData(binId: String): Result<UIScreenData> {
        return withContext(Dispatchers.IO) {
            try {
                val response = apiService.getScreenData(binId)
                Result.success(response.record)
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
    
    suspend fun performApiAction(
        endpoint: String,
        method: String,
        body: Map<String, Any>
    ): Result<String> {
        return withContext(Dispatchers.IO) {
            try {
                // For demo purposes, we'll simulate API call
                // In production, you'd make actual API call
                delay(1500)
                
                // Simulate success
                Result.success("Login successful")
            } catch (e: Exception) {
                Result.failure(e)
            }
        }
    }
}

private suspend fun delay(timeMillis: Long) {
    delay(timeMillis)
}
