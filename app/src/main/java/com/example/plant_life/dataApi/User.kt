package com.example.plant_life.dataApi

data class User(val userId : String,
                val firstName : String = "",
                val lastName :String = "",
                val bio :String ="",
                val my_plant : Map<String, ResponseItem>)
