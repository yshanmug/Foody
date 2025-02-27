package com.chef.foody.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.chef.foody.util.Colors
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.PreviewDynamicColors
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewLightDark
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel
import com.chef.foody.R
import com.chef.foody.domain.model.ChefStatus
import com.chef.foody.util.TEST_TAG
import com.chef.foody.viewmodels.UserViewModel


@Composable
fun ChefCertificateUploadScreen( modifier: Modifier, onNavigationChefToChefHomeScreenAfterVerification: ()->Unit = {}, userViewModel: UserViewModel = hiltViewModel()) {



    LaunchedEffect(key1 = userViewModel.userState.value.chefStatus)
    {
        if(userViewModel.userState.value.chefStatus == ChefStatus.VERIFIED)
        {
            onNavigationChefToChefHomeScreenAfterVerification.invoke()
        }
    }

        Column(
            modifier = modifier
                .fillMaxSize()
            , horizontalAlignment = Alignment.Start,
            verticalArrangement = Arrangement.SpaceEvenly
        ) {

            Column(
                modifier = Modifier
                    .fillMaxHeight(0.5f)
                    .fillMaxWidth()
                    .padding(23.dp)
                , horizontalAlignment = Alignment.Start,
                verticalArrangement = Arrangement.SpaceBetween
            )
            {
                Column( horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.SpaceBetween)
                {
                    Text(
                        text = "Food Safety Certificate ",
                        color = Colors.black,
                        textAlign = TextAlign.Left,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "To ensure food safety standards are maintained, please upload a copy of your food safety certificate. ",
                        color = Colors.black,
                        textAlign = TextAlign.Justify
                    )

                }
                Column( horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.SpaceBetween)
                {
                    Text(
                        text = "Privacy Notice ",
                        color = Colors.black,
                        textAlign = TextAlign.Left,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Your uploaded certificate will be kept confidential and used solely for verification purposes. ",
                        color = Colors.black,
                        textAlign = TextAlign.Justify
                    )


                }
                Column( horizontalAlignment = Alignment.Start, verticalArrangement = Arrangement.SpaceBetween)
                {
                    Text(
                        text = "File Format Information ",
                        color = Colors.black,
                        textAlign = TextAlign.Left,
                        fontWeight = FontWeight.Bold
                    )
                    Text(
                        text = "Accepted file formats: PDF, JPG, PNG",
                        color = Colors.black,
                        textAlign = TextAlign.Justify
                    )

                }
            }

            Box(
                modifier = Modifier.testTag(TEST_TAG.CHEF_FILE_UPLOAD_BUTTON)
                    .fillMaxWidth()
                    .fillMaxHeight(0.7f)
                    .padding(30.dp)
                    .clip(RoundedCornerShape(40.dp))
                    .background(Colors.secondaryOrange)
                    .clickable {
                        userViewModel.registerChef()
                    },
                Alignment.Center
            )
            {

                Column(horizontalAlignment = Alignment.CenterHorizontally, verticalArrangement = Arrangement.Center) {
                    Image(
                        painter = painterResource(id = R.drawable.upload_icon),
                        contentDescription = "Upload Icon",
                        modifier = Modifier
                            .size(80.dp)
                    )

                    Text(
                        text = "Upload Your File",
                        color = Colors.black,
                        textAlign = TextAlign.Center,
                        fontWeight = FontWeight.Bold
                    )
                }

            }

        }

    }


////@PreviewScreenSizes
////@PreviewFontScale
////@PreviewLightDark
////@PreviewDynamicColors
//@Preview(showBackground = true)
//@Composable
//fun ChefCertificateUploadScreenPreview() {
//    ChefCertificateUploadScreen(modifier = Modifier)
//}