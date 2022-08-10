package com.pmj.jetcompose.util

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.DeliveryDining
import androidx.compose.material.icons.filled.Dining
import androidx.compose.material.icons.filled.Person
import com.pmj.jetcompose.model.BottomNavItem

object Constants {
    val BottomNavItems = listOf(
        BottomNavItem(
            label = "Delivery",
            icon = Icons.Filled.DeliveryDining,
            route = "delivery"
        ),
        BottomNavItem(
            label = "Dining",
            icon = Icons.Filled.Dining,
            route = "dining"
        ),
        BottomNavItem(
            label = "Profile",
            icon = Icons.Filled.Person,
            route = "profile"
        )
    )
}