package com.pmj.jetcompose.ui

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Divider
import androidx.compose.material.Icon
import androidx.compose.material.Text
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AddLocation
import androidx.compose.material.icons.filled.Home
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Color.Companion.DarkGray
import androidx.compose.ui.graphics.Color.Companion.Gray
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun ChooseLocationScreen() {
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .background(Color.White)
            .padding(20.dp)

    ) {
        Text(
            text = "Select location", fontSize = 18.sp, fontWeight = FontWeight.Bold,
            color = Color.Black,
        )
        Row(
            verticalAlignment = Alignment.CenterVertically,
        ) {
            Icon(
                imageVector = Icons.Default.Home,
                contentDescription = "Home",
                tint = DarkGray,
                modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
            )
            Column {
                Text(
                    text = "Home", fontSize = 16.sp, fontWeight = FontWeight.Bold,
                    color = Color.Black,
                    modifier = Modifier.padding(
                        start = 10.dp, top = 10.dp
                    )
                )
                Text(
                    text = "5B, MerryLand, T Nagar", fontSize = 14.sp,
                    modifier = Modifier.padding(start = 10.dp, bottom = 10.dp)
                )
            }
        }
        Divider(color = Gray, thickness = 1.dp)
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .padding(top = 10.dp, bottom = 10.dp)
                .clickable(
                    onClick = {

                    },
                )
        ) {
            Icon(
                imageVector = Icons.Default.AddLocation,
                contentDescription = "AddLocation",
                tint = DarkGray,
                modifier = Modifier.padding(top = 10.dp, bottom = 10.dp)
            )
            Text(
                text = "Add address", fontSize = 14.sp,
                modifier = Modifier.padding(start = 10.dp, end = 10.dp)
            )
        }
    }
}