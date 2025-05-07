package com.deepakjetpackcompose.ainotes.model.repository

import com.deepakjetpackcompose.ainotes.model.client.ApiClient
import com.deepakjetpackcompose.ainotes.model.client.GeminiResponse

class ApiRepository {

    suspend fun getSummary(content:String): GeminiResponse{
         return ApiClient.getSummary(content)
    }

    suspend fun getTranslation(content:String,lang:String): GeminiResponse{
        return ApiClient.getTranslation(content = content, language = lang)
    }

    suspend fun getMeaning(word:String): GeminiResponse{
        return ApiClient.getMeaning(content = word)
    }
}