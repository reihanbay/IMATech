package com.ima.myhealthydiary.utils.api.service

import com.ima.myhealthydiary.utils.api.response.AnswerResponse
import com.ima.myhealthydiary.utils.api.response.BotResponse
import retrofit2.http.Body
import retrofit2.http.Headers
import retrofit2.http.POST

interface ChatService {

    @Headers("Content-Type: application/json")
    @POST("/")
    suspend fun postChat(@Body chat: AnswerResponse) : BotResponse
}