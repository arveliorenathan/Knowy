package com.capstone.knowy.data.api

import com.capstone.knowy.data.response.PredictCareerResponse
import retrofit2.http.Field
import retrofit2.http.FormUrlEncoded
import retrofit2.http.POST

interface MachineService {
    @FormUrlEncoded
    @POST ("predict_career")
    suspend fun predictCareer(
        @Field("data") dataString: String
    ): PredictCareerResponse
}