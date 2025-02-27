package com.chef.foody.screens

import android.graphics.Bitmap
import android.graphics.Canvas
import android.graphics.Paint
import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.IntrinsicSize
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.BasicTextField
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.ColorFilter
import androidx.compose.ui.graphics.ImageBitmap
import androidx.compose.ui.graphics.ImageBitmapConfig
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.SpanStyle
import androidx.compose.ui.text.buildAnnotatedString
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.withStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.graphics.drawable.toBitmap
import androidx.hilt.navigation.compose.hiltViewModel
import com.chef.foody.R
import com.chef.foody.data.remote.CustomerInfo
import com.chef.foody.domain.model.DeliveryType
import com.chef.foody.events.ChefQuotationEvent
import com.chef.foody.util.Colors
import com.chef.foody.util.OrderBetaType
import com.chef.foody.util.OrderTypeCheckBeta
import com.chef.foody.viewmodels.UserViewModel
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.collectLatest


private val fontSmall = 14.sp

@Composable
fun FancyButton(text: String, modifier: Modifier = Modifier, onClick: ()->Unit = {}) {
    val buttonshape = RoundedCornerShape(25.dp)
    Button(
        shape = buttonshape,
        colors = ButtonDefaults.buttonColors(containerColor = Colors.primaryOrange),
        modifier = modifier
            .shadow(10.dp, buttonshape),
        onClick = onClick
    ) {
        Text(text, color = Color.White)
    }
}
@Composable
private fun FoodyTextField(
    text: String,
    modifier: Modifier = Modifier,
    singleLine: Boolean = true,
    enabled: Boolean = true,
    keyboardOptions: KeyboardOptions = KeyboardOptions.Default,
    readOnly: Boolean = false,
    onValueChange: (String)->Unit = {},
    placeholder: @Composable ()->Unit = {}
) {
    val interactionSource = remember { MutableInteractionSource() }
    BasicTextField(
        value = text,
        keyboardOptions = keyboardOptions,
        readOnly = readOnly,
        onValueChange = onValueChange,
        singleLine = singleLine,
        interactionSource = interactionSource,
        modifier = modifier,
        enabled = enabled,
        decorationBox = @Composable { innerTextField ->
            Box(modifier = modifier) {
                innerTextField()
                if (text.isEmpty()) placeholder()
            }
        }
    )
}

@Composable
fun OfferField(
    symbolId: Int, // img
    hint: String, // placeholder
    kbdopt: KeyboardOptions,
    value: String,
    onInput: (String)->Unit,
    enabled: Boolean = true,
    modifier: Modifier = Modifier
) {
    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = modifier
//            .height(IntrinsicSize.Max)
//            .border(1.dp, Color.Blue)
    ) {
        Image(
            painterResource(id = symbolId),
            null,
            colorFilter = ColorFilter.tint(Colors.primaryOrange),
            modifier = Modifier
                .size(30.dp)
                .aspectRatio(1f)
                .padding(horizontal = 5.dp)
//                .fillMaxHeight()
//                .border(1.dp, Color.Green)
        )
        FoodyTextField(
            text = value,
            placeholder = {
                Text(hint, fontSize = fontSmall, color=Colors.grey)
            },
            enabled = enabled,
            modifier = Modifier
//                .border(1.dp, Color.Red)
                .height(IntrinsicSize.Max)
//                .fillMaxWidth()
                .background(Color.Transparent),
            keyboardOptions = kbdopt,
            onValueChange = {onInput(it)}
        )
    }
}

@Composable
fun ClientText(clientContent: List<Pair<String, String>>) {
    clientContent.forEach { c ->
        val title = c.first
        val content = c.second
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(
                buildAnnotatedString {
                    withStyle(style = SpanStyle(color = Colors.primaryOrange)) {
                        append("$title: ")
                    }
                    append(content)
                },
                fontSize = fontSmall
            )
        }
    }
}



@Composable
private fun ClientCard(
    avatar: ImageBitmap,
    customerInfo: CustomerInfo,
    modifier: Modifier = Modifier,
//    chefRental: Boolean = false, // rental
    userViewModel: UserViewModel = hiltViewModel()
    ) {
    val price = remember { mutableStateOf("") }
    val time = remember { mutableStateOf("") }
    val menu = remember { mutableStateOf("") }
    val orderBetaType = OrderTypeCheckBeta.orderTypeCheckCustomerInfoBeta(customerInfo)
//    Log.d("ClientCard", "$orderBetaType -- $customerInfo")
    if (customerInfo.isAlreadyQuoted||(customerInfo.price!=0f||customerInfo.preparationTime!=0)) {
        price.value = customerInfo.price.toString()
        time.value = customerInfo.preparationTime.toString()
        menu.value = customerInfo.menu?: ""
    }
//    val menu = remember { mutableStateOf("") }
//    userViewModel.customerInfoList
    val ctx = LocalContext.current
    Card(
        colors = CardDefaults.cardColors(
            containerColor = Colors.secondaryOrange,
        ),
        shape = RoundedCornerShape(10.dp), // shape & shadow
        modifier = modifier
//            .height(IntrinsicSize.Min)
            .padding(vertical = 5.dp)
            .shadow(5.dp, RoundedCornerShape(10.dp), true) // shape & shadow
//            .border(1.dp, Color.Red)
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
//                .height(IntrinsicSize.Min)
                .padding(5.dp, 5.dp)
//                .border(1.dp, Color.Black)
        ) {
            Box(
                contentAlignment = Alignment.BottomCenter,
                modifier = Modifier
                    .height(120.dp)
                    .width(IntrinsicSize.Max)
                    .padding(2.dp)
                    .clip(RoundedCornerShape(8.dp))
                    .border(1.dp, Color.Black, RoundedCornerShape(8.dp))
            ) {
                Image(
                    bitmap = avatar,
                    contentDescription = "client avatar",
                    modifier = Modifier
                        .aspectRatio(1f, true)
//                        .border(1.dp,Color.Red)
                )
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(25.dp)
                        .background(Color.Black)
                ) {
                    Row(
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .border(1.dp, Colors.primaryOrange, RoundedCornerShape(10.dp))
                            .background(Color.Transparent)
                            .padding(4.dp, 2.dp)
                    ) {
                        Image(
                            painterResource(id = R.drawable.homeshape),
                            contentDescription = "homeshape",
                            modifier = Modifier
                                .size(16.dp)
                                .padding(1.dp)
                        )

                        Text(
                            when (orderBetaType) {
                                is OrderBetaType.ChefCook -> "Your Home"
                                else -> "Client Home" // Rental
                            },
                            color = Colors.primaryOrange, fontSize = 10.sp
                        )
                    }
                }
            }
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                modifier=Modifier
                    .padding(vertical = 5.dp, horizontal = 10.dp)
//                    .border(1.dp,Color.Red)
            ) {
                Text("${customerInfo.customerFirstName} ${customerInfo.customerLastName}")
                if (customerInfo.cartInfo!=null)
                    ClientText(listOf(Pair<String,String>("Food Items",customerInfo.cartInfo.map{"${it.name}(${it.quantity})"}.joinToString(", "))))
                if (customerInfo.userNote!=null)
                    ClientText(listOf(Pair<String, String>("Note", customerInfo.userNote)))
                Row(
                    horizontalArrangement = Arrangement.Start,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier
                        .fillMaxWidth()
                        .height(IntrinsicSize.Max)
                        .padding(2.dp)
                ) {
                    OfferField(
                        symbolId = R.drawable.dollarshape,
                        hint = "In Dollar",
                        kbdopt = KeyboardOptions(keyboardType = KeyboardType.Number),
                        value = price.value,
                        enabled = !customerInfo.isAlreadyQuoted,
                        onInput = {
                            price.value = it.trim()
                        },
                        modifier = Modifier
                            .weight(1f, fill = true)
                    )
                    OfferField(
                        symbolId = R.drawable.clockshape,
                        hint = "In Min",
                        kbdopt = KeyboardOptions(keyboardType = KeyboardType.Decimal),
                        value = time.value,
                        enabled = !customerInfo.isAlreadyQuoted,
                        onInput = {
                            time.value = it.trim()
                        },
                        modifier = Modifier
                            .weight(1f, fill = true)
                    )
                }
                // rental
                if (orderBetaType is OrderBetaType.ChefRental) {
                    Row(
                        horizontalArrangement = Arrangement.Start,
                        verticalAlignment = Alignment.CenterVertically,
                        modifier = Modifier
                            .fillMaxWidth()
                            .height(IntrinsicSize.Max)
                            .padding(2.dp)
                    ) {
                        OfferField(
                            symbolId = R.drawable.cutleryshape,
                            hint = "Your Menu",
                            kbdopt = KeyboardOptions(keyboardType = KeyboardType.Text),
                            value = menu.value,
                            onInput = {
                                menu.value = it
                            },
                            enabled = !customerInfo.isAlreadyQuoted,
                            modifier = Modifier
                                .weight(1f, fill = true)
//                            .border(1.dp,Color.Red)
                        )
                    }
                }
                FancyButton(
                    if (customerInfo.isAlreadyQuoted) "Quotation Sent!" else "Send Quote",
                    modifier = Modifier
                        .padding(4.dp)
                        .height(40.dp),
                    onClick = {
                        if (customerInfo.isAlreadyQuoted) {
                            Toast.makeText(ctx, "quotation already sent!", Toast.LENGTH_SHORT).show()
                            return@FancyButton
                        }
                        val pricevalue = price.value.toFloatOrNull()
                        val timevalue = time.value.toIntOrNull()
                        if (pricevalue == null || pricevalue <= 0f) {
                            Toast.makeText(ctx, "price error", Toast.LENGTH_SHORT).show()
                            return@FancyButton
                        }
                        if (timevalue == null || timevalue <= 0) {
                            Toast.makeText(ctx, "time value should be integer", Toast.LENGTH_SHORT).show()
                            return@FancyButton
                        }
                        if (orderBetaType is OrderBetaType.ChefRental && menu.value.isBlank()) {
                            Toast.makeText(ctx, "empty menu not allowed", Toast.LENGTH_SHORT).show()
                            return@FancyButton
                        }
//                        Toast.makeText(ctx, "quotation!", Toast.LENGTH_SHORT).show()
                        if (orderBetaType is OrderBetaType.ChefCook) {
                            userViewModel.onSendQuotationEventBeta(
                                ChefQuotationEvent.QuotationBeta(
                                    orderId = customerInfo.orderId,
                                    price = pricevalue,
                                    time = timevalue,
                                    type = customerInfo.type?:"your home",
                                    menu = ""
                                )
                            )
                        } else {
                            userViewModel.onSendQuotationEventBeta(
                                ChefQuotationEvent.QuotationBeta(
                                    orderId = customerInfo.orderId,
                                    price = pricevalue,
                                    time = timevalue,
                                    type = customerInfo.type?:"client home",
                                    menu = menu.value
                                )
                            )
                        }
                    }
                )
            }
        }
    }
}

fun ProfileImageChar(c: Char): ImageBitmap {
    val textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    textPaint.color = 0xff000000.toInt()
    textPaint.textSize = 96f
    val w = 128
    val h = 128
    val defaultProfilePhoto = Bitmap.createBitmap(w, h, Bitmap.Config.ARGB_8888)
    val canvas = Canvas(defaultProfilePhoto)
//    canvas.drawCircle(w / 2f, h /2f, w / 2.4f, circlePaint)
//    canvas.drawColor(Colors.primaryOrange.value.toInt())
    canvas.drawText(c.toString(), 24f, 96f, textPaint)
    return defaultProfilePhoto.asImageBitmap()
}

@Composable
fun profileImage(): ImageBitmap {
    val img = LocalContext.current.getDrawable(R.drawable.profile)?.toBitmap()?.asImageBitmap()
    if (img==null) return ImageBitmap(128, 128, ImageBitmapConfig.Argb8888)
    else return img
}

@Composable
fun ChefHomeScreen(
    modifier: Modifier = Modifier,
    onNavigationChefOrderConfirmedPickup: ()->Unit = {},
    onNavigationChefOrderConfirmedDoor: ()->Unit = {},
    onNavigationChefRentChefConfirmed: (Int)->Unit,
    userViewModel: UserViewModel = hiltViewModel(),
) {

    LaunchedEffect(Unit) {
        while(true) {
            userViewModel.onChefFindCustomerBeta()
            delay(5000)
        }
    }

    LaunchedEffect(Unit) {

        userViewModel.isChefRentalAcceptedByUser.collectLatest {
            onNavigationChefRentChefConfirmed(it)
        }
        userViewModel.chefNewOrderAccepted.collect {
            when (it) {
                is OrderBetaType.ChefCook -> {
                    val chefActiveOrderStatus = userViewModel.chefActiveOrderStatus.value
                    if (chefActiveOrderStatus!=null) {
                        when (chefActiveOrderStatus.deliveryType) {
                            DeliveryType.DOOR_DELIVERY -> onNavigationChefOrderConfirmedDoor()
                            DeliveryType.TAKE_AWAY -> onNavigationChefOrderConfirmedPickup()
                        }
                    }
                }
               else->{}
            }
        }
    }


    val customerInfoList by remember { userViewModel.customerInfoList }
//    val chefActiveOrderStatus by remember {userViewModel.chefActiveOrderStatus}

//    LaunchedEffect(key1 = Unit)
//    {
//        userViewModel.isChefRentalAcceptedByUser.collectLatest {
//            onNavigationChefRentChefConfirmed(it)
//        }
//    }
    if (customerInfoList.data.isNotEmpty()) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = modifier
                .fillMaxSize()
                .verticalScroll(rememberScrollState())
            //            .background(Color.Yellow)
        ) {
            customerInfoList.data.forEach {
                //            val ch = if (it.customerFirstName.isEmpty()) ' ' else it.customerFirstName[0]
                //            val defaultProfilePhoto = ProfileImageChar(ch.uppercaseChar())
                val defaultProfilePhoto = profileImage()
                ClientCard(
                    avatar = defaultProfilePhoto,
                    it,
                    modifier = Modifier
                        .fillMaxWidth(.95f)
                )
            }
        }
    } else {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center,
            modifier = modifier.fillMaxSize()
        ) {
//            LoopAnimation(modifier)
            Text(text = "Updating Client Quotations...", fontSize = 16.sp, fontStyle = FontStyle.Italic)
        }
    }
    LaunchedEffect(Unit) {
        while(true) {
            userViewModel.onChefFindCustomerBeta()
            delay(10000)
        }
    }
    when (val chefActiveOrderStatus = userViewModel.chefActiveOrderStatus.value) {
        null -> {}
        else -> {
            LaunchedEffect(Unit){
                when (chefActiveOrderStatus.deliveryType) {
                    DeliveryType.DOOR_DELIVERY -> {onNavigationChefOrderConfirmedDoor()}
                    DeliveryType.TAKE_AWAY -> {onNavigationChefOrderConfirmedPickup()}
                }
            }
        }
    }



    // [TODO] rent screen
}



/*
        val customerInfoList = CustomerInfoList(
            listOf(
                CustomerInfo(
                    10,233,"John","Watson",
                    listOf(
                        CartInfo("A",1),
                        CartInfo("B",2),
                        CartInfo("CCC",3),
                        CartInfo("D",0),
                    ),
                    false,
                    0f,
                    0,
                    -1,
                    "DEFAULT"
                )
            )
        )
*/



