package com.capstone.knowy.data.response

import com.google.gson.annotations.SerializedName

data class SaveScoreResponse(

	@field:SerializedName("message")
	val message: String,

	@field:SerializedName("status")
	val status: String
)

data class ShowScoreResponse(

	@field:SerializedName("scores")
	val scores: List<ScoresItem>,

	@field:SerializedName("status")
	val status: String
)

data class ScoresItem(

	@field:SerializedName("score")
	val score: String,

	@field:SerializedName("userId")
	val userId: String,

	@field:SerializedName("timestamp")
	val timestamp: String
)

