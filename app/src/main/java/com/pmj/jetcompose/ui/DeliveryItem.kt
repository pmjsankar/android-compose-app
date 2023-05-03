package com.pmj.jetcompose.ui

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Card
import androidx.compose.material.ExperimentalMaterialApi
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.drawWithCache
import androidx.compose.ui.graphics.BlendMode
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.rememberAsyncImagePainter
import com.pmj.jetcompose.model.Restaurant
import com.pmj.jetcompose.theme.DarkGreen
import com.pmj.jetcompose.theme.LightGreen

@OptIn(ExperimentalMaterialApi::class)
@Composable
fun DeliveryItem(restaurant: Restaurant) {
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
                    painter = rememberAsyncImagePainter(restaurant.imageUrl),
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
                    text = "${restaurant.offer}% OFF",
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
                    text = restaurant.title,
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
                                color = if (restaurant.rating > 4) DarkGreen else LightGreen,
                                shape = RoundedCornerShape(6.dp)
                            )
                            .padding(2.dp)
                    ) {
                        Text(
                            text = restaurant.rating.toString(),
                            fontWeight = FontWeight.Bold,
                            color = Color.White,
                            modifier = Modifier.padding(top = 0.dp, bottom = 0.dp),
                        )
                    }
                    Text(
                        text = restaurant.timing,
                        fontSize = 18.sp,
                        modifier = Modifier.padding(start = 10.dp),
                        fontWeight = FontWeight.Bold,
                        color = Color.Black
                    )
                }
                Text(
                    text = restaurant.address,
                    color = Color.Black
                )
                Text(
                    text = restaurant.desc,
                    modifier = Modifier.padding(end = 6.dp),
                    maxLines = 2,
                    overflow = TextOverflow.Ellipsis,
                )
            }
        }
    }
}