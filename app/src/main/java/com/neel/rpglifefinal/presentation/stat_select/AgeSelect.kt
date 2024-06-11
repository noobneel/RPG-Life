package com.neel.rpglifefinal.presentation.stat_select

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ElevatedButton
import androidx.compose.material3.IconButton
import androidx.compose.material3.IconButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.unit.dp
import com.google.firebase.ktx.Firebase
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.neel.rpglifefinal.R

import com.neel.rpglifefinal.ui.theme.BackgroundProject
import com.neel.rpglifefinal.ui.theme.ButtonColor
import com.neel.rpglifefinal.ui.theme.Lobster_Regular

@Composable
fun AgeSelect(navController: NavController) {
    var ageText by remember{ mutableIntStateOf(21) }
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = BackgroundProject
    ) {
        Column(
            modifier = Modifier.padding(40.dp, 156.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            Image(
                modifier = Modifier
                    .height(142.dp)
                    .width(122.dp),
                contentScale = ContentScale.Inside,
                painter = painterResource(id = R.drawable.shape),
                contentDescription = "RPG Life"
            )
            Spacer(Modifier.height(20.dp))
            Text(
                text = "Your Age?",
                fontFamily = Lobster_Regular,
                color = Color.White,
                style = TextStyle(
                    shadow = Shadow(
                        color = Color.Blue, offset = Offset(5.0f, 5.0f), blurRadius = 3f
                    )
                )
            )
            Spacer(Modifier.height(20.dp))
            ElevatedButton(modifier = Modifier
                .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ButtonColor
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 5.dp
                ),
                onClick = {
                },
                enabled = false) {

                IconButton(onClick = { ageText -= 1},colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color.Transparent
                ) ){
                    Image(painter = painterResource(id = R.drawable.larrow), contentDescription = "")
                }
                Text(
                    text = ageText.toString(),
                    fontFamily = Lobster_Regular,
                    color = Color.White
                )
                IconButton(onClick = { ageText += 1},colors = IconButtonDefaults.iconButtonColors(
                    containerColor = Color.Transparent
                ) ){
                    Image(painter = painterResource(id = R.drawable.rightrarrow), contentDescription = "")
                }
            }
            Spacer(Modifier.height(20.dp))
            ElevatedButton(modifier = Modifier
                .fillMaxWidth(),
                colors = ButtonDefaults.buttonColors(
                    containerColor = ButtonColor
                ),
                elevation = ButtonDefaults.buttonElevation(
                    defaultElevation = 5.dp
                ),
                onClick = {
                    val db = Firebase.firestore
                    val userId = FirebaseAuth.getInstance().currentUser!!.uid
                    val ageHash = hashMapOf("age" to ageText)
                    db.collection("user").document(userId).set(ageHash, SetOptions.merge())
                    navController.navigate("gender")
                }) {
                Text(
                    "Continue",
                    fontFamily = Lobster_Regular,
                    color = Color.White
                )
            }

        }
    }
}

