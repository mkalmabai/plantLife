package com.example.plant_life.dataApi

import com.squareup.moshi.Json

data class Response(
	val response: List<ResponseItem>
)

data class ResponseItem(

	val image: String,

	val Watering: String,

	val Temperature: String,

	val watarAlarm: String,

	val Lighting: String,

	val backgroundImage: String,

	val sparingAlarm: String,

	val id: String,

	val PlantName: String=""

)