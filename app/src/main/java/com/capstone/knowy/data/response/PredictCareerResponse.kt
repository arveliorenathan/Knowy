package com.capstone.knowy.data.response

import com.google.gson.annotations.SerializedName

data class PredictCareerResponse(

	@field:SerializedName("predicted_career")
	val predictedCareer: String
)
