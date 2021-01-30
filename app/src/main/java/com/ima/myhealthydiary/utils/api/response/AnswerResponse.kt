package com.ima.myhealthydiary.utils.api.response

import com.google.gson.annotations.SerializedName

data class AnswerResponse(
    @SerializedName("id_peserta") var name : String,
    @SerializedName("content") var content : String
)