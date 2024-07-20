package com.capstone.knowy.data.response

import com.google.gson.annotations.SerializedName

data class OceanResponse(

    @field:SerializedName("Questions")
    val questions: List<String>,

    @field:SerializedName("status")
    val status: String
)

data class AptitudeQuestionResponse(

    @field:SerializedName("image_urls")
    val imageUrls: List<String>,

    @field:SerializedName("status")
    val status: String
)

data class AptitudeAnswerResponse(

    @field:SerializedName("answers")
    val answers: List<String>,

    @field:SerializedName("status")
    val status: String
)