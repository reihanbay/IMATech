package com.ima.myhealthydiary.smartdiagnosis

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.ima.myhealthydiary.utils.api.response.AnswerResponse
import com.ima.myhealthydiary.utils.api.response.BotResponse
import com.ima.myhealthydiary.utils.api.service.ChatService
import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

class SmartChatBotViewModel : ViewModel(), CoroutineScope{
    override val coroutineContext: CoroutineContext
    get() = Job() + Dispatchers.Main

    private lateinit var serviceChat : ChatService
    val responseLiveData = MutableLiveData<BotResponse>()
    val isSuccessLiveData = MutableLiveData<Boolean>()

    fun setService(service: ChatService) {
        this.serviceChat = service
    }

    fun postChat(ans: String) {
        launch {
            val response = withContext(Dispatchers.IO) {
                try {
                    serviceChat.postChat(AnswerResponse("kandidatReihan", ans))
                } catch (e: Throwable) {
                    e.printStackTrace()
                }
            }
            if (response is BotResponse) {
                isSuccessLiveData.value = true
                responseLiveData.value = response
            } else {
                isSuccessLiveData.value = false
            }
        }
    }
}