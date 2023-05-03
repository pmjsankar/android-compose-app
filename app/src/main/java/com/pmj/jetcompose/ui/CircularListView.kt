package com.pmj.jetcompose.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.grid.GridCells
import androidx.compose.foundation.lazy.grid.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp
import com.pmj.jetcompose.R
import com.pmj.jetcompose.model.CuisineModal
import com.pmj.jetcompose.theme.WindowBgColor

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun CircularListView() {
    // on below line we are creating and initializing our array list
    lateinit var cuisineList: List<CuisineModal>
    cuisineList = ArrayList()

    // on below line we are adding data to our list.
    cuisineList = cuisineList + CuisineModal("Healthy", R.drawable.healthy)
    cuisineList = cuisineList + CuisineModal("Burger", R.drawable.burger)
    cuisineList = cuisineList + CuisineModal("Dosa", R.drawable.masala)
    cuisineList = cuisineList + CuisineModal("Chaat", R.drawable.chaat)
    cuisineList = cuisineList + CuisineModal("Pizza", R.drawable.pizza)
    cuisineList = cuisineList + CuisineModal("Juice", R.drawable.juice)
    cuisineList = cuisineList + CuisineModal("Shakes", R.drawable.shakes)
    cuisineList = cuisineList + CuisineModal("Wraps", R.drawable.wraps)

    LazyVerticalGrid(
        columns = GridCells.Fixed(4),
        modifier = Modifier
            .padding(6.dp)
            .background(color = WindowBgColor)
    ) {
        items(cuisineList.size) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier = Modifier.padding(start = 4.dp, top = 10.dp),
            ) {
                Card(
                    onClick = {
                        cuisineList[it].cuisineName
                    },

                    modifier = Modifier.padding(2.dp),
                    shape = CircleShape,
                    elevation = 2.dp
                ) {
                    Column(
                        Modifier
                            .fillMaxSize()
                            .padding(4.dp),
                        horizontalAlignment = Alignment.CenterHorizontally,
                        verticalArrangement = Arrangement.Center
                    ) {
                        Image(
                            painter = painterResource(id = cuisineList[it].cuisineImg),
                            contentDescription = "food",
                            modifier = Modifier
                                .height(68.dp)
                                .width(68.dp)
                        )
                        Spacer(modifier = Modifier.height(2.dp))
                    }
                }

                Text(
                    text = cuisineList[it].cuisineName,
                    modifier = Modifier.padding(4.dp),
                    color = Color.Black
                )
            }
        }
    }
}