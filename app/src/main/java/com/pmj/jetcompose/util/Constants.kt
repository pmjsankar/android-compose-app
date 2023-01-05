package com.pmj.jetcompose.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeliveryDining
import androidx.compose.material.icons.filled.Dining
import androidx.compose.material.icons.filled.Person
import com.pmj.jetcompose.model.BottomNavItem
import com.pmj.jetcompose.util.Route.DELIVERY
import com.pmj.jetcompose.util.Route.DINING
import com.pmj.jetcompose.util.Route.PROFILE

object Constants {
    val BottomNavItems = listOf(
        BottomNavItem(
            label = "Delivery",
            icon = Icons.Filled.DeliveryDining,
            route = DELIVERY
        ),
        BottomNavItem(
            label = "Dining",
            icon = Icons.Filled.Dining,
            route = DINING
        ),
        BottomNavItem(
            label = "Profile",
            icon = Icons.Filled.Person,
            route = PROFILE
        )
    )
}

object Route {
    const val LOGIN = "login"
    const val OTP = "otpScreen"
    const val DELIVERY = "delivery"
    const val DINING = "dining"
    const val PROFILE = "profile"
}