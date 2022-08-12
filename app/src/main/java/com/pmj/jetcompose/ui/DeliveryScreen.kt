package com.pmj.jetcompose.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.pmj.jetcompose.R
import com.pmj.jetcompose.model.CuisineModal
import com.pmj.jetcompose.model.RestaurantModal
import com.pmj.jetcompose.theme.DarkGreen
import com.pmj.jetcompose.theme.LightGreen
import com.pmj.jetcompose.theme.WindowBgColor
import com.pmj.jetcompose.util.getActivity
import com.pmj.jetcompose.util.showAsBottomSheet

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun DeliveryScreen() {
    val context = LocalContext.current
    val searchItem = remember { mutableStateOf(TextFieldValue()) }
    val focusManager = LocalFocusManager.current

    // on below line we are creating and initializing our array list
    lateinit var deliveryList: List<RestaurantModal>
    deliveryList = ArrayList()

    // on below line we are adding data to our list.
    deliveryList = deliveryList + RestaurantModal(
        name = "Burger Factory",
        cuisine = "Burger, Wraps, Sandwiches",
        location = "Kaloor",
        distance = "2.2 km",
        rating = 4.6,
        offer = "20% OFF\nUPTO ₹80",
        deliveryTime = "33 mins",
        restaurantImg = "https://pmjsankar.github.io/api/img/burger.jpg"
    )

    deliveryList = deliveryList + RestaurantModal(
        name = "Pizza Hut",
        cuisine = "Pizza, Beverages",
        location = "Infopark",
        distance = "1.2 km",
        rating = 4.5,
        offer = "EVERY ITEM\n @₹119",
        deliveryTime = "26 mins",
        restaurantImg = "https://pmjsankar.github.io/api/img/pizza.png"
    )

    deliveryList = deliveryList + RestaurantModal(
        name = "Madras Cafe",
        cuisine = "South Indian, North Indian, Chaat",
        location = "Edapally",
        distance = "5.2 km",
        rating = 3.8,
        offer = "FLAT ₹40 OFF",
        deliveryTime = "45 mins",
        restaurantImg = "https://pmjsankar.github.io/api/img/madras.png"
    )

    deliveryList = deliveryList + RestaurantModal(
        name = "Ice berg",
        cuisine = "Ice cream, shakes, desserts",
        location = "Vytila",
        distance = "5.8 km",
        rating = 3.6,
        offer = "25% OFF",
        deliveryTime = "50 mins",
        restaurantImg = "https://pmjsankar.github.io/api/img/shakes.jpg"
    )

    LazyColumn(
        modifier = Modifier
            .background(color = WindowBgColor)
            .fillMaxSize()
            .padding(top = 2.dp, start = 15.dp, end = 15.dp, bottom = 60.dp)
    ) {

        item {
            Column(
                modifier = Modifier
                    .clickable(
                        onClick = {
                            context.getActivity()?.showAsBottomSheet {
                                ChooseLocationScreen()
                            }
                        },
                    )
            ) {
                Row {
                    Icon(
                        imageVector = Icons.Default.LocationOn,
                        contentDescription = "Location",
                        tint = Color.Red,
                        modifier = Modifier
                            .padding(top = 14.dp)
                            .size(28.dp)
                    )
                    Text(
                        text = "Home", fontSize = 22.sp, fontWeight = FontWeight.Bold,
                        color = Color.Black,
                        modifier = Modifier.padding(
                            start = 8.dp, top = 10.dp
                        )
                    )
                    Icon(
                        imageVector = Icons.Default.ExpandMore,
                        contentDescription = "Choose location",
                        tint = Color.Black,
                        modifier = Modifier.padding(start = 8.dp, top = 15.dp)
                    )
                }
                Text(
                    text = "5B, MerryLand, T Nagar", fontSize = 14.sp,
                    modifier = Modifier.padding(start = 36.dp)
                )
            }
        }

        stickyHeader {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = WindowBgColor)
                    .padding(top = 10.dp)
            ) {
                OutlinedTextField(
                    value = searchItem.value,
                    onValueChange = {
                        searchItem.value = it
                    },
                    singleLine = true,
                    modifier = Modifier
                        .fillMaxWidth(),
                    keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
                    keyboardOptions = KeyboardOptions(
                        keyboardType = KeyboardType.Text,
                        imeAction = ImeAction.Done
                    ),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = DarkGray,
                        unfocusedBorderColor = Gray,
                        focusedLabelColor = DarkGray,
                        cursorColor = DarkGray
                    ),
                    label = {
                        Text(
                            text = "Search for restaurant, cuisine or food",
                            style = MaterialTheme.typography.body1
                        )
                    },
                )

                Spacer(Modifier.size(12.dp))
            }
        }

        item {
            Box(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(270.dp)
                    .background(color = WindowBgColor)
            ) {
                CircularListView()
            }
        }

        item {
            Spacer(modifier = Modifier.height(20.dp))
            Text(
                text = "Recommended for you", fontSize = 17.sp, fontWeight = FontWeight.Bold,
                modifier = Modifier.padding(start = 3.dp)
            )
        }

        items(deliveryList.size) {
            Card(
                modifier = Modifier.padding(top = 12.dp),
                shape = RoundedCornerShape(12.dp),
                elevation = 2.dp,
                onClick = {

                },
            ) {
                Row(
                    Modifier
                        .fillMaxSize()
                        .padding(end = 5.dp),
                ) {
                    Box {
                        Image(
                            painter = rememberAsyncImagePainter(deliveryList[it].restaurantImg),
                            contentDescription = "food",
                            contentScale = ContentScale.FillBounds,
                            modifier = Modifier
                                .height(160.dp)
                                .width(125.dp)
                                .drawWithCache {
                                    val gradient = Brush.verticalGradient(
                                        colors = listOf(Color.Transparent, Color.Black),
                                        startY = size.height / 3,
                                        endY = size.height
                                    )
                                    onDrawWithContent {
                                        drawContent()
                                        drawRect(gradient, blendMode = BlendMode.Multiply)
                                    }
                                }
                        )
                        Text(
                            text = deliveryList[it].offer,
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier
                                .padding(start = 8.dp, bottom = 6.dp)
                                .align(Alignment.BottomStart),
                        )
                    }
                    Column(
                        modifier = Modifier.padding(start = 10.dp, top = 4.dp),
                    ) {
                        Text(
                            text = deliveryList[it].name,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            fontWeight = FontWeight.ExtraBold,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(top = 5.dp, end = 5.dp),
                            color = Color.Black
                        )
                        Row(
                            modifier = Modifier.padding(top = 5.dp),
                            verticalAlignment = Alignment.CenterVertically
                        ) {
                            Box(
                                modifier = Modifier
                                    .background(
                                        color = if (deliveryList[it].rating > 4) DarkGreen else LightGreen,
                                        shape = RoundedCornerShape(6.dp)
                                    )
                                    .padding(2.dp)
                            ) {
                                Text(
                                    text = deliveryList[it].rating.toString(),
                                    fontWeight = FontWeight.Bold,
                                    color = Color.White,
                                    modifier = Modifier.padding(top = 0.dp, bottom = 0.dp),
                                )
                            }
                            Text(
                                text = deliveryList[it].deliveryTime,
                                fontSize = 18.sp,
                                modifier = Modifier.padding(start = 10.dp),
                                fontWeight = FontWeight.Bold,
                                color = Color.Black
                            )
                        }
                        Row(modifier = Modifier.padding(top = 5.dp)) {
                            Text(
                                text = deliveryList[it].location,
                                color = Color.Black
                            )
                            Text(
                                text = context.getString(R.string.bullet),
                                fontWeight = FontWeight.Bold,
                                fontSize = 16.sp,
                                modifier = Modifier.padding(start = 8.dp, end = 8.dp),
                                color = Color.Black
                            )
                            Text(
                                text = deliveryList[it].distance,
                                color = Color.Black
                            )
                        }
                        Text(
                            text = deliveryList[it].cuisine,
                            modifier = Modifier.padding(end = 6.dp),
                            maxLines = 1,
                            overflow = TextOverflow.Ellipsis,
                            color = Color.Black
                        )
                    }
                }
            }
        }
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
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
        cells = GridCells.Fixed(4),
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
