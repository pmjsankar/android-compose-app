package com.pmj.jetcompose.ui

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.pmj.jetcompose.model.Restaurant
import com.pmj.jetcompose.network.APIService
import kotlinx.coroutines.launch

class RestaurantViewModel : ViewModel() {
    private val _restaurants = mutableStateListOf<Restaurant>()
    var errorMessage: String by mutableStateOf("")
    val restaurants: List<Restaurant>
        get() = _restaurants

    fun getRestaurants() {
        viewModelScope.launch {
            val apiService = APIService.getInstance()
            try {
                _restaurants.clear()
                _restaurants.addAll(apiService.getRestaurants())

            } catch (e: Exception) {
                errorMessage = e.message.toString()
            }
        }
    }
}