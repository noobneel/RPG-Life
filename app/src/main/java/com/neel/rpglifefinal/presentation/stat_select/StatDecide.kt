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
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.Shadow
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavController
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase

import com.neel.rpglifefinal.presentation.staticdata.questionList
import com.neel.rpglifefinal.R
import com.neel.rpglifefinal.presentation.staticdata.Stats

import com.neel.rpglifefinal.ui.theme.BackgroundProject
import com.neel.rpglifefinal.ui.theme.ButtonColor2
import com.neel.rpglifefinal.ui.theme.Lobster_Regular


@Composable
fun StatDecide(navController: NavController) {
    var strength = 0f
    var charisma = 0f
    var constitution = 0f
    var intelligence = 0f
    var wisdom = 0f
    var dexterity = 0f
    val goHome = remember{ mutableStateOf(false)}
    val statDone = true
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = BackgroundProject
    ) {
        val num = remember {
            mutableIntStateOf(1)
        }
        val listNow = questionList
        listNow.forEach { _ ->
            val typeNow = listNow.map { it.type }
            val questionNow = listNow.map { it.question }
            val option1 = listNow.map { it.option1 }
            val option2 = listNow.map { it.option2 }
            val option3 = listNow.map { it.option3 }
            val sc1 = listNow.map { it.score1 }
            val sc2 = listNow.map { it.score2 }
            val sc3 = listNow.map { it.score3 }

            Column(
                modifier = Modifier.padding(40.dp, 20.dp),
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
                    text = num.intValue.toString(),
                    fontFamily = Lobster_Regular,
                    color = Color.White,
                    style = TextStyle(
                        shadow = Shadow(
                            color = Color.Blue, offset = Offset(5.0f, 5.0f), blurRadius = 3f
                        )
                    )
                )
                Spacer(Modifier.height(10.dp))
                Text(
                    text = questionNow[num.intValue - 1],
                    fontFamily = Lobster_Regular,
                    color = Color.White,
                    style = TextStyle(
                        shadow = Shadow(
                            color = Color.Black, offset = Offset(5.0f, 5.0f), blurRadius = 3f
                        )
                    )
                )
                ElevatedButton(modifier = Modifier
                    .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ButtonColor2
                    ),
//                    elevation = ButtonDefaults.buttonElevation(
//                        defaultElevation = 5.dp
//                    ),
                    enabled = //num.intValue < 32,
                    !goHome.value,
                    onClick = {
                        if(typeNow[num.intValue-1] == Stats.STR){
                            strength += sc1[num.intValue-1]
                        }
                        else if(typeNow[num.intValue-1] == Stats.CON){
                            constitution += sc1[num.intValue-1]
                        }
                        else if(typeNow[num.intValue-1] == Stats.DEX){
                            dexterity += sc1[num.intValue-1]
                        }
                        else if(typeNow[num.intValue-1] == Stats.INT){
                        intelligence += sc1[num.intValue-1]
                        }
                        else if(typeNow[num.intValue-1] == Stats.WIS){
                            wisdom += sc1[num.intValue-1]
                        }
                        else {
                            charisma += sc1[num.intValue-1]
                        }
                        if (num.intValue < 30)
                            num.intValue += 1
                        else
                            goHome.value = true
                    }) {
                    Text(
                        text = option1[num.intValue - 1],
                        fontFamily = Lobster_Regular,
                        color = Color.White
                    )
                }
                ElevatedButton(modifier = Modifier
                    .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ButtonColor2
                    ),
//                    elevation = ButtonDefaults.buttonElevation(
//                        defaultElevation = 5.dp
//                    ),enabled = if(num.intValue<=30)
//                    {false}
//                    else{true},
                    enabled = //num.intValue < 32,
                    !goHome.value,
                    onClick = {
                        if(typeNow[num.intValue-1] == Stats.STR){
                            strength += sc2[num.intValue-1]
                        }
                        else if(typeNow[num.intValue-1] == Stats.CON){
                            constitution += sc2[num.intValue-1]
                        }
                        else if(typeNow[num.intValue-1] == Stats.DEX){
                            dexterity += sc2[num.intValue-1]
                        }
                        else if(typeNow[num.intValue-1] == Stats.INT){
                            intelligence += sc2[num.intValue-1]
                        }
                        else if(typeNow[num.intValue-1] == Stats.WIS){
                            wisdom += sc2[num.intValue-1]
                        }
                        else {
                            charisma += sc2[num.intValue-1]
                        }
                        if (num.intValue < 30)
                            num.intValue += 1
                        else
                            goHome.value = true
                    }) {
                    Text(
                        text = option2[num.intValue - 1],
                        fontFamily = Lobster_Regular,
                        color = Color.White
                    )
                }
                ElevatedButton(modifier = Modifier
                    .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ButtonColor2
                    ),
//                    elevation = ButtonDefaults.buttonElevation(
//                        defaultElevation = 5.dp
//                    ),
                    enabled = //num.intValue < 32,
                    !goHome.value,
                    onClick = {
                        if(typeNow[num.intValue-1] == Stats.STR){
                            strength += sc3[num.intValue-1]
                        }
                        else if(typeNow[num.intValue-1] == Stats.CON){
                            constitution += sc3[num.intValue-1]
                        }
                        else if(typeNow[num.intValue-1] == Stats.DEX){
                            dexterity += sc3[num.intValue-1]
                        }
                        else if(typeNow[num.intValue-1] == Stats.INT){
                            intelligence += sc3[num.intValue-1]
                        }
                        else if(typeNow[num.intValue-1] == Stats.WIS){
                            wisdom += sc3[num.intValue-1]
                        }
                        else {
                            charisma += sc3[num.intValue-1]
                        }
                        if (num.intValue < 30)
                            num.intValue += 1
                        else{
                            goHome.value = true
                        }
                    }) {
                    Text(
                        text = option3[num.intValue - 1],
                        fontFamily = Lobster_Regular,
                        color = Color.White
                    )
                }
                ElevatedButton(
                    modifier = Modifier
                        .fillMaxWidth(),
                    colors = ButtonDefaults.buttonColors(
                        containerColor = ButtonColor2,
                        disabledContainerColor = Color.Transparent
                    ),
//                    elevation = ButtonDefaults.buttonElevation(
//                        defaultElevation = 5.dp
//                    ),
                    enabled = goHome.value,
                    onClick = {
                        val db = Firebase.firestore
                        val userId = FirebaseAuth.getInstance().currentUser!!.uid
                        val weightHash = hashMapOf("INT" to intelligence,
                            "CON" to constitution,"CHR" to charisma,
                            "WIS" to wisdom,"DEX" to dexterity,
                            "STR" to strength,"stats" to statDone
                            )
                        db.collection("user").document(userId).set(weightHash, SetOptions.merge())
                        navController.navigate("mainapp")
                    },

                ) {
                    Text(
                        "Continue",
                        fontFamily = Lobster_Regular,
                        color = Color.White
                    )
                }
            }
        }
    }
}



@Preview
@Composable
fun StatPrev(){
    //Navigation()
}