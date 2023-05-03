package com.pmj.jetcompose.ui

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.Icon
import androidx.compose.material.MaterialTheme
import androidx.compose.material.OutlinedTextField
import androidx.compose.material.Text
import androidx.compose.material.TextFieldDefaults
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ExpandMore
import androidx.compose.material.icons.filled.LocationOn
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.pmj.jetcompose.theme.WindowBgColor
import com.pmj.jetcompose.util.getActivity
import com.pmj.jetcompose.util.showAsBottomSheet

@OptIn(ExperimentalFoundationApi::class)
@Composable
fun DeliveryScreen(viewModel: RestaurantViewModel) {
    val context = LocalContext.current
    val searchItem = remember { mutableStateOf(TextFieldValue()) }
    val focusManager = LocalFocusManager.current

    LaunchedEffect(Unit, block = {
        viewModel.getRestaurants()
    })

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

        items(viewModel.restaurants.size) {
            DeliveryItem(viewModel.restaurants[it])
        }
    }
}

