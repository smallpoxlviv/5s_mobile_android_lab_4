package com.example.mobile_android.data.entity

data class Location(
    val city: String,
    val coordinates: Coordinates,
    val postcode: String,
    val state: String,
    //  val street: String,
    val timezone: Timezone
)
