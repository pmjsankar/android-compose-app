package com.pmj.jetcompose

data class RestaurantModal(
    val name: String,
    val cuisine: String,
    val location: String,
    val distance: String,
    val rating: Double,
    val offer: String,
    val deliveryTime: String,
    val restaurantImg: Int
)