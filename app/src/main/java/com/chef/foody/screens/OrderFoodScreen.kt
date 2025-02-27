    package com.chef.foody.screens

    import androidx.compose.foundation.Image
    import androidx.compose.foundation.background
    import androidx.compose.foundation.clickable
    import androidx.compose.foundation.layout.Arrangement
    import androidx.compose.foundation.layout.Box
    import androidx.compose.foundation.layout.Column
    import androidx.compose.foundation.layout.PaddingValues
    import androidx.compose.foundation.layout.Row
    import androidx.compose.foundation.layout.Spacer
    import androidx.compose.foundation.layout.fillMaxHeight
    import androidx.compose.foundation.layout.fillMaxSize
    import androidx.compose.foundation.layout.fillMaxWidth
    import androidx.compose.foundation.layout.height
    import androidx.compose.foundation.layout.padding
    import androidx.compose.foundation.layout.size
    import androidx.compose.foundation.layout.width
    import androidx.compose.foundation.lazy.LazyColumn
    import androidx.compose.foundation.lazy.LazyRow
    import androidx.compose.foundation.lazy.items
    import androidx.compose.foundation.lazy.rememberLazyListState
    import androidx.compose.foundation.shape.CircleShape
    import androidx.compose.foundation.shape.RoundedCornerShape
    import androidx.compose.material.icons.Icons
    import androidx.compose.material.icons.rounded.ShoppingCart
    import androidx.compose.material3.Button
    import androidx.compose.material3.ButtonDefaults
    import androidx.compose.material3.Icon
    import androidx.compose.material3.LargeFloatingActionButton
    import androidx.compose.material3.Text
    import androidx.compose.runtime.Composable
    import androidx.compose.runtime.getValue
    import androidx.compose.runtime.mutableStateOf
    import androidx.compose.runtime.remember
    import androidx.compose.runtime.rememberCoroutineScope
    import androidx.compose.runtime.setValue
    import androidx.compose.ui.Alignment
    import androidx.compose.ui.Modifier
    import androidx.compose.ui.draw.clip
    import androidx.compose.ui.graphics.Color
    import androidx.compose.ui.platform.testTag
    import androidx.compose.ui.res.painterResource
    import androidx.compose.ui.text.font.FontWeight.Companion.W500
    import androidx.compose.ui.text.font.FontWeight.Companion.W800
    import androidx.compose.ui.text.style.TextAlign
    import androidx.compose.ui.unit.dp
    import androidx.compose.ui.unit.sp
    import androidx.hilt.navigation.compose.hiltViewModel
    import com.chef.foody.R
    import com.chef.foody.events.CartEvent
    import com.chef.foody.events.CartItem
    import com.chef.foody.util.TEST_TAG
    import com.chef.foody.viewmodels.UserViewModel
    import kotlinx.coroutines.launch

    @Composable
    fun OrderFoodScreen(
        modifier: Modifier = Modifier,
        onNavigateCart:() -> Unit = {},
        userViewModel: UserViewModel = hiltViewModel()
    ) {
        var vegetarian by remember { mutableStateOf(false) }
        val foodListState = rememberLazyListState()
        val coroutineScope = rememberCoroutineScope()
        val cartState by userViewModel.cartState
    Box(modifier = modifier.fillMaxSize()){
    Column(
        modifier = Modifier
            .fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Top
    ) {
        // search bar
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(0.08f)
                .padding(horizontal = 10.dp)
                .clip(RoundedCornerShape(20.dp))
                .background(Color(0xFFFFE6C7))
                .align(Alignment.CenterHorizontally)
        ) {
            Row(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(10.dp),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                //TextField(value = "Find Your Dish", onValueChange = )
                Text(
                    "Find Your Dish",
                    color = Color(0xFFFAA73D),
                    fontWeight = W500,
                    modifier = Modifier
                )
                Image(
                    painter = painterResource(id = R.drawable.searchicon),
                    contentDescription = null
                )
            }
        }
        // Vegetarian/Non-vegetarian option buttons
        Row(
            modifier = Modifier
                .padding(5.dp)
        ) {
            // Non-vegetarian button
            Button(
                onClick = {
                    vegetarian = false
                    coroutineScope.launch {
                        foodListState.scrollToItem(0)
                    }
                },
                modifier = Modifier
                    .padding(10.dp)
                    .width(180.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (vegetarian) Color(0xFFFFE6C7) else Color(0xFFFAA73D)
                ),
                elevation = ButtonDefaults.buttonElevation(if (vegetarian) 0.dp else 10.dp)
                //if (vegetarian) Color.Gray else Color.White // Set color based on vegetarian state
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.non_veg_icon),
                        contentDescription = null,
                        modifier = if (vegetarian)
                            Modifier
                                .size(30.dp)
                                .clip(CircleShape)
                                .padding(5.dp)
                        else
                            Modifier
                                .size(30.dp)
                                .clip(CircleShape)
                                .background(Color.White)
                                .padding(5.dp)
                    )
                    Text(
                        "Non-Vegetarian",
                        color = if (vegetarian) Color.Black else Color.White
                    ) // Change text color based on vegetarian state
                }
            }
            // Vegetarian button
            Button(
                onClick = {
                    vegetarian = true
                    coroutineScope.launch {
                        foodListState.scrollToItem(0)
                    }
                },
                modifier = Modifier
                    .padding(10.dp)
                    .width(180.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = if (vegetarian) Color(0xFFFAA73D) else Color(0xFFFFE6C7)
                ),
                elevation = ButtonDefaults.buttonElevation(if (vegetarian) 10.dp else 0.dp)
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth(),
                    verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.SpaceBetween
                    //horizontalArrangement = Arrangement.spacedBy(10.dp)
                ) {
                    Image(
                        painter = painterResource(id = R.drawable.veg_icon),
                        contentDescription = null,
                        modifier = if (vegetarian)
                            Modifier
                                .size(30.dp)
                                .clip(CircleShape)
                                .background(Color.White)
                                .padding(5.dp)
                        else
                            Modifier
                                .size(30.dp)
                                .clip(CircleShape)
                                .padding(5.dp)
                    )
                    Text(
                        "Vegetarian",
                        color = if (vegetarian) Color.White else Color.Black
                    ) // Change text color based on vegetarian state
                }

            }
        }
        LazyColumn(modifier = Modifier.testTag(TEST_TAG.FOOD_ORDER_LIST_LAZY_COLUMN), state = foodListState) {
            items(userViewModel.foodState.value.foodList.filter { if (vegetarian) it.foodType == "VEG" else it.foodType == "NON_VEG" })
            { item ->
                Column(
                    modifier = Modifier
                        .padding(vertical = 5.dp, horizontal = 10.dp),
                    verticalArrangement = Arrangement.Center
                ) {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(20.dp))
                            .background(color = Color(0xFFFFE6C7))
                            .padding(5.dp),
                        verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.spacedBy(10.dp)
                    ) {
                        Image(
                            painter = painterResource(item.fileLocation),
                            contentDescription = null,
                            modifier = Modifier
                                .size(100.dp)
                                .padding(5.dp)

                        )
                        Column {
                            //food name
                            Text(
                                text = item.name,
                                modifier = Modifier.fillMaxWidth(),
                                fontWeight = W800,
                                textAlign = TextAlign.Center
                            )
                            //food description
                            Text(
                                text = item.description
                            )
                            //two buttons
                            Row(
                                modifier = Modifier
                                    .padding(vertical = 5.dp)
                                    .height(30.dp),
                                horizontalArrangement = Arrangement.spacedBy(15.dp),
                                verticalAlignment = Alignment.CenterVertically

                            ) {
                                //Quantity Count Button
                                Box(
                                    modifier = Modifier
                                        .weight(1f)
                                        .fillMaxHeight()
                                        .clip(RoundedCornerShape(30.dp))
                                        .background(color = Color(0xFFFAA73D))
                                        .width(150.dp),
                                    contentAlignment = Alignment.Center
                                ) {
                                    Row(
                                        modifier = Modifier.fillMaxSize(),
                                        horizontalArrangement = Arrangement.SpaceEvenly,
                                        verticalAlignment = Alignment.CenterVertically
                                    ) {
                                        // Minus button
                                        Button(
                                            onClick = {
                                                userViewModel.onCartEvent(
                                                    CartEvent.ChangeQuantity(
                                                        item,
                                                        "-"
                                                    )
                                                )
                                            },
                                            modifier = Modifier.testTag(TEST_TAG.QUANTITY_DECREASE_BUTTON_CLICK)
                                                .weight(0.3f),
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = Color(0xFFFAA73D)
                                            ),
                                            contentPadding = PaddingValues(0.dp)
                                            //Color(0xFFFFE6C7)
                                        ) {
                                            Text(
                                                text = "-",
                                                fontSize = 25.sp,
                                                color = Color(0XFFFFE6C7),
                                                textAlign = TextAlign.Center,
                                                modifier = Modifier
                                                    .align(Alignment.CenterVertically)
                                            )
                                        }

                                        // Counter text
                                        Text(
                                            //text = count.toString(), // Update this value with your actual counter value
                                            text = item.quantity.toString(),
                                            fontSize = 16.sp,
                                            fontWeight = W500,
                                            textAlign = TextAlign.Center,
                                            color = Color(0XFFFFE6C7),
                                            modifier = Modifier
                                                .align(Alignment.CenterVertically)
                                                .weight(0.2f)
                                        )

                                        // Plus button
                                        Button(
                                            onClick = {
                                                userViewModel.onCartEvent(
                                                    CartEvent.ChangeQuantity(
                                                        item,
                                                        "+"
                                                    )
                                                )
                                            },
                                            modifier = Modifier.testTag(TEST_TAG.QUANTITY_INCREASE_BUTTON_CLICK)
                                                .weight(0.3f),
                                            colors = ButtonDefaults.buttonColors(
                                                containerColor = Color(0xFFFAA73D)
                                            ),
                                            contentPadding = PaddingValues(0.dp)
                                            //Color(0xFFFFE6C7)
                                        ) {
                                            Text(
                                                text = "+",
                                                fontSize = 20.sp,
                                                color = Color(0XFFFFE6C7),
                                                textAlign = TextAlign.Center,
                                                modifier = Modifier
                                                    .align(Alignment.CenterVertically)
                                            )
                                        }
                                    }
                                }

                                // button 2 - Add to Cart
                                Button(
                                    onClick = {
                                        userViewModel.onCartEvent(CartEvent.AddToCart(item))
                                    },
                                    modifier = Modifier.testTag(TEST_TAG.ADD_TO_CART_BUTTON_CLICK)
                                        .weight(1f)
                                        .fillMaxHeight()
                                        .clip(RoundedCornerShape(30.dp))
                                        .width(150.dp)
                                        .align(Alignment.CenterVertically),
                                    contentPadding = PaddingValues(0.dp),
                                    colors = ButtonDefaults.buttonColors(
                                        containerColor = Color(0xFFFAA73D)
                                    ),
                                    elevation = ButtonDefaults.buttonElevation(10.dp)
                                    //contentAlignment = Alignment.Center
                                ) {
                                    Text(
                                        text = "Add to Cart",
                                        color = Color(0XFFFFE6C7)
                                    )
                                }


                            }
                        }
                    }
                }
            }


        }

    }
        LargeFloatingActionButton(
            onClick = {
                onNavigateCart()
            },
            shape = CircleShape,
            containerColor = Color(0xFFFAA73D),
            modifier = Modifier
                .padding(horizontal = 50.dp)
                .align(Alignment.BottomEnd)
                .size(75.dp).testTag(TEST_TAG.VIEW_CART_BUTTON_CLICKED).clickable {
                    onNavigateCart()
                }
        ) {
            Icon(
                Icons.Rounded.ShoppingCart,
                contentDescription = null,
                modifier = Modifier
                    .size(30.dp)
            )
        }
}

    }
    @Composable
    fun CartItemRow(cartItem: CartItem) {
        Row(
            modifier = Modifier.padding(horizontal = 8.dp, vertical = 4.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            // Display cart item details
            Text(text = cartItem.title)
            Spacer(modifier = Modifier.width(8.dp))
            Text(text = "Quantity: ${cartItem.quantity}")
        }
    }

//    @Composable
//    private fun OrderFoodNavigationConfigurations(
//        modifier: Modifier,
//        navController: NavHostController
//    ) {
//        NavHost(navController, startDestination = Screen.OrderFoodScreen.route) {
//            composable(Screen.OrderFoodScreen.route) {
//                OrderFoodScreen(modifier = Modifier, onNavigate = {
//                    navController.navigate(it)
//                }) // Pass the navController here
//            }
//            composable(Screen.CartScreen.route) {
//                CartScreen()
//            }
//        }
//    }


    //
    //@Preview(showBackground = true)
    //@Composable
    //fun OrderFoodScreenPreview() {
    //    OrderFoodScreen(Modifier)
    //}