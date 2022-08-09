package com.pmj.jetcompose

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.GridCells
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyVerticalGrid
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.*
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import coil.compose.rememberAsyncImagePainter
import com.pmj.jetcompose.ui.theme.JetComposeTheme
import com.pmj.jetcompose.ui.theme.Purple500
import com.pmj.jetcompose.ui.theme.WindowBgColor

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            JetComposeTheme {
                // remember navController so it does not
                // get recreated on recomposition
                val navController = rememberNavController()
                var showBottomBar by rememberSaveable { mutableStateOf(true) }
                val navBackStackEntry by navController.currentBackStackEntryAsState()

                showBottomBar = when (navBackStackEntry?.destination?.route) {
                    "login" -> false // on this screen bottom bar should be hidden
                    "otpScreen" -> false // here too
                    else -> true // in all other cases show bottom bar
                }

                Surface(color = Color.White) {
                    // Scaffold Component
                    Scaffold(
                        // Bottom navigation
                        bottomBar = { if (showBottomBar) BottomNavigationBar(navController = navController) },
                        content = {
                            // Navhost: where screens are placed
                            NavigationComponent(navController = navController)
                        }
                    )
                }
            }
        }
    }
}

@Composable
fun NavigationComponent(navController: NavHostController) {
    NavHost(navController = navController, startDestination = "delivery") {
        composable("login") { LoginScreen(navController) }
        composable("otpScreen") { OtpScreen(navController) }
        // route : Delivery
        composable("delivery") {
            DeliveryScreen()
        }

        // route : Dining
        composable("dining") {
            SearchScreen()
        }

        // route : Profile
        composable("profile") {
            ProfileScreen()
        }
    }
}

@Composable
fun LoginScreen(navController: NavController) {
    val phone = remember { mutableStateOf(TextFieldValue()) }
    val phoneErrorState = remember { mutableStateOf(false) }
    val maxChar = 10
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Spacer(Modifier.size(36.dp))
        Text(text = buildAnnotatedString {
            withStyle(style = SpanStyle(color = Color.Red)) {
                append("J")
            }
            withStyle(style = SpanStyle(color = Color.Black)) {
                append("et")
            }

            withStyle(style = SpanStyle(color = Color.Red)) {
                append(" D")
            }
            withStyle(style = SpanStyle(color = Color.Black)) {
                append("elivery")
            }
        }, fontSize = 30.sp)
        Spacer(Modifier.size(6.dp))
        Text(text = "Order your food online", color = Color.DarkGray)
        Spacer(Modifier.size(36.dp))
        Image(
            painter = rememberAsyncImagePainter("https://images.unsplash.com/photo-1560624052-449f5ddf0c31?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxwaG90by1wYWdlfHx8fGVufDB8fHx8&auto=format&fit=crop&w=435&q=80"),
            contentDescription = "avatar",
            contentScale = ContentScale.Crop,            // crop the image if it's not a square
            modifier = Modifier
                .size(160.dp)
                .clip(CircleShape)                       // clip to the circle shape
            // add a border (optional)
        )
        Spacer(Modifier.size(36.dp))
        OutlinedTextField(
            value = phone.value,
            onValueChange = {
                if (phoneErrorState.value) {
                    phoneErrorState.value = false
                }
                if (it.text.length <= maxChar) phone.value = it
            },
            isError = phoneErrorState.value,
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            label = {
                Text(
                    text = "Enter your mobile number*",
                    style = MaterialTheme.typography.body1
                )
            },
        )
        if (phoneErrorState.value) {
            Text(
                text = "Required", color = Color.Red,
                textAlign = TextAlign.Start,
                modifier = Modifier.align(alignment = Alignment.Start)
            )
        }

        Spacer(Modifier.size(36.dp))
        Button(
            onClick = {
                when {
                    phone.value.text.isEmpty() -> {
                        phoneErrorState.value = true
                    }
                    else -> {
                        phoneErrorState.value = false
                        navController.navigate("otpScreen")
                    }
                }

            },
            content = {
                Text(
                    text = "NEXT", color = Color.White, fontSize = 14.sp,
                    modifier = Modifier.padding(
                        start = 20.dp,
                        top = 3.dp, end = 20.dp, bottom = 3.dp
                    ),
                    style = MaterialTheme.typography.button
                )
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
        )
    }
}

@Composable
fun OtpScreen(navController: NavController) {
    val context = LocalContext.current
    val otpErrorState = remember { mutableStateOf(false) }
    val otp = remember { mutableStateOf(TextFieldValue()) }
    val maxChar = 4
    val focusManager = LocalFocusManager.current

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Row(
            modifier = Modifier
                .padding(top = 6.dp, end = 10.dp, bottom = 6.dp)
                .fillMaxWidth(),
            horizontalArrangement = Arrangement.Start,
        ) {
            IconButton(modifier = Modifier.then(Modifier.size(24.dp)),
                onClick = {
                    navController.navigateUp()
                }) {
                Icon(
                    Icons.Filled.ArrowBack,
                    "Back",
                    tint = Color.Black
                )
            }
            Text(
                text = "OTP Verification", fontSize = 18.sp,
                modifier = Modifier.padding(start = 20.dp)
            )
        }
        Spacer(Modifier.size(36.dp))
        val passwordVisibility = remember { mutableStateOf(true) }
        OutlinedTextField(
            value = otp.value,
            onValueChange = {
                if (otpErrorState.value) {
                    otpErrorState.value = false
                }
                if (it.text.length <= maxChar) otp.value = it
            },
            isError = otpErrorState.value,
            singleLine = true,
            modifier = Modifier.fillMaxWidth(),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Number,
                imeAction = ImeAction.Done
            ),
            label = {
                Text(
                    text = "Enter the OTP*",
                    style = MaterialTheme.typography.body1
                )
            },
            trailingIcon = {
                IconButton(onClick = {
                    passwordVisibility.value = !passwordVisibility.value
                }) {
                    Icon(
                        imageVector = if (passwordVisibility.value) Icons.Default.VisibilityOff else Icons.Default.Visibility,
                        contentDescription = "visibility",
                        tint = Color.Red
                    )
                }
            },
            visualTransformation = if (passwordVisibility.value) PasswordVisualTransformation() else VisualTransformation.None
        )
        if (otpErrorState.value) {
            Text(
                text = "Required", color = Color.Red,
                textAlign = TextAlign.Start,
                modifier = Modifier.align(alignment = Alignment.Start)
            )
        }
        Spacer(Modifier.size(46.dp))
        Button(
            onClick = {
                when {
                    otp.value.text.isEmpty() -> {
                        otpErrorState.value = true
                    }
                    else -> {
                        otpErrorState.value = false
                        navController.navigate("delivery") {
                            popUpTo("otpScreen") { inclusive = true }
                        }
                        Toast.makeText(
                            context,
                            "Logged in successfully",
                            Toast.LENGTH_SHORT
                        ).show()
                    }
                }

            },
            content = {
                Text(
                    text = "VERIFY", color = Color.White, fontSize = 14.sp,
                    modifier = Modifier.padding(
                        start = 20.dp,
                        top = 3.dp, end = 20.dp, bottom = 3.dp
                    ),
                    style = MaterialTheme.typography.button
                )
            },
            colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
        )
    }
}

@Composable
fun BottomNavigationBar(navController: NavHostController) {
    BottomNavigation(
        backgroundColor = Purple500
    ) {

        // observe the backstack
        val navBackStackEntry by navController.currentBackStackEntryAsState()
        val currentRoute = navBackStackEntry?.destination?.route
        Constants.BottomNavItems.forEach { navItem ->

            // Place the bottom nav items
            BottomNavigationItem(

                // it currentRoute is equal then its selected route
                selected = currentRoute == navItem.route,

                // navigate on click
                onClick = {
                    navController.navigate(navItem.route)
                },

                // Icon of navItem
                icon = {
                    Icon(imageVector = navItem.icon, contentDescription = navItem.label)
                },

                // label
                label = {
                    Text(text = navItem.label)
                },
                alwaysShowLabel = false
            )
        }
    }
}

@Composable
fun SearchScreen() {
    // Column Composable,
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        // parameters set to place the items in center
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Icon Composable
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = "Profile",
            tint = Color(0xFF0F9D58)
        )
        // Text to Display the current Screen
        Text(text = "Search", color = Color.Black)
    }
}

@Composable
fun ProfileScreen() {
    // Column Composable,
    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.White),
        // parameters set to place the items in center
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Icon Composable
        Icon(
            imageVector = Icons.Default.Person,
            contentDescription = "Profile",
            tint = Color(0xFF0F9D58)
        )
        // Text to Display the current Screen
        Text(text = "Profile", color = Color.Black)
    }
}

@OptIn(ExperimentalFoundationApi::class, ExperimentalMaterialApi::class)
@Composable
fun DeliveryScreen() {

    val searchItem = remember { mutableStateOf(TextFieldValue()) }
    val focusManager = LocalFocusManager.current

    // on below line we are creating and initializing our array list
    lateinit var deliveryList: List<RestaurantModal>
    deliveryList = ArrayList()

    // on below line we are adding data to our list.
    deliveryList = deliveryList + RestaurantModal(
        name = "Burger factory",
        cuisine = "Indian, Biryani, Chinese",
        location = "Kaloor",
        distance = "2.2 km",
        rating = 4.5,
        offer = "20% OFF",
        deliveryTime = "33 mins",
        restaurantImg = R.drawable.burger
    )

    deliveryList = deliveryList + RestaurantModal(
        name = "Pizza Hut",
        cuisine = "Pizza, Beverages",
        location = "Infopark",
        distance = "1.2 km",
        rating = 4.5,
        offer = "10% OFF",
        deliveryTime = "26 mins",
        restaurantImg = R.drawable.pizza
    )

    deliveryList = deliveryList + RestaurantModal(
        name = "Madras Cafe",
        cuisine = "South Indian, North Indian, Chaat",
        location = "Edapally",
        distance = "5.2 km",
        rating = 4.3,
        offer = "30% OFF",
        deliveryTime = "45 mins",
        restaurantImg = R.drawable.masala
    )

    LazyColumn(
        modifier = Modifier
            .background(color = WindowBgColor)
            .fillMaxSize()
            .padding(top = 20.dp, start = 20.dp, end = 20.dp, bottom = 70.dp)
    ) {

        item {
            Row(
                modifier = Modifier
                    .padding(top = 10.dp, bottom = 14.dp)
                    .fillMaxWidth()
            ) {
                Icon(
                    imageVector = Icons.Default.LocalDining,
                    contentDescription = "Jet Food",
                    tint = Color(0xFF0F9D58)
                )
                Text(
                    text = "Jet Food", fontSize = 20.sp, fontWeight = FontWeight.Bold,
                    modifier = Modifier.padding(start = 10.dp)
                )
            }
        }

        stickyHeader {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .background(color = WindowBgColor)
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
                modifier = Modifier.padding(start = 10.dp)
            )
        }

        items(deliveryList.size) {
            Card(
                modifier = Modifier.padding(top = 12.dp),
                elevation = 2.dp,
                onClick = {

                },
            ) {
                Row(
                    Modifier
                        .fillMaxSize()
                        .padding(5.dp),
                ) {
                    Image(
                        painter = painterResource(id = deliveryList[it].restaurantImg),
                        contentDescription = "food",
                        modifier = Modifier
                            .height(125.dp)
                            .width(120.dp)
                    )
                    Column(
                        modifier = Modifier.padding(start = 6.dp),
                    ) {
                        Text(
                            text = deliveryList[it].name,
                            maxLines = 2,
                            overflow = TextOverflow.Ellipsis,
                            fontWeight = FontWeight.Bold,
                            fontSize = 20.sp,
                            modifier = Modifier.padding(top = 5.dp),
                            color = Color.Black
                        )
                        Text(
                            text = deliveryList[it].cuisine,
                            modifier = Modifier.padding(top = 5.dp),
                            maxLines = 3,
                            overflow = TextOverflow.Ellipsis,
                            color = Color.Black
                        )
                        Row {
                            Text(
                                text = deliveryList[it].rating.toString(),
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(top = 5.dp),
                                color = Color.Black
                            )
                            Text(
                                text = " . " + deliveryList[it].deliveryTime,
                                fontWeight = FontWeight.Bold,
                                modifier = Modifier.padding(top = 5.dp),
                                color = Color.Black
                            )
                        }
                        Row {
                            Text(
                                text = deliveryList[it].location,
                                modifier = Modifier.padding(top = 5.dp),
                                color = Color.Black
                            )
                            Text(
                                text = " . " + deliveryList[it].distance,
                                modifier = Modifier.padding(top = 5.dp),
                                color = Color.Black
                            )
                        }
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

@Preview
@Composable
fun ComposablePreview() {
    DeliveryScreen()
}