package com.ima.myhealthydiary.utils.api.response

import com.google.gson.annotations.SerializedName

data class BotResponse(
    @SerializedName("ans") var answer: String,
    @SerializedName("question") var question : String,
    @SerializedName("type") var type: String
)