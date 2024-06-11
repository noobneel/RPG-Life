package com.neel.rpglifefinal.presentation.sign_in

import android.widget.Toast
import com.neel.rpglifefinal.R
import com.neel.rpglifefinal.ui.theme.BackgroundProject


import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.RectangleShape
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.unit.dp

@Composable
fun SignInScreen(state: SignInState,
          onSignInClick: () -> Unit){
    val context = LocalContext.current
    LaunchedEffect(key1 = state.signInError) {
        state.signInError?.let { error ->
            Toast.makeText(
                context,
                error,
                Toast.LENGTH_LONG
            ).show()
        }
    }
    Surface(
        modifier = Modifier
            .fillMaxSize(),
        color = BackgroundProject
    ) {
        Column(
            modifier = Modifier.padding(40.dp, 156.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ){
            Image(
                modifier = Modifier
                    .height(142.dp)
                    .width(122.dp),
                contentScale = ContentScale.Inside,
                painter = painterResource(id = R.drawable.shape),
                contentDescription = "RPG Life"
            )
            Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = onSignInClick,
                modifier = Modifier.fillMaxWidth(),
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                )
            ) {
                Row(verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center){
                    Image(
                        contentScale = ContentScale.Fit,
                        painter = painterResource(id = R.drawable.logo_googleg_48dp),
                        contentDescription = "Google"
                    )
                    Spacer(Modifier.width(5.dp))
                    Text(text = "Login With Google",color = Color.Black)
                }
            }
            /*Spacer(modifier = Modifier.height(20.dp))
            Button(onClick = { /*TODO*/ },
                modifier = Modifier.fillMaxWidth(),
                shape = RectangleShape,
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.Black,
                )
            ) {
                Row(verticalAlignment = Alignment.CenterVertically,
                    horizontalArrangement = Arrangement.Center) {
                    Image(
                        contentScale = ContentScale.Fit,
                        painter = painterResource(id = R.drawable.apple),
                        contentDescription = "Apple"
                    )
                    Spacer(Modifier.width(5.dp))
                    Text(text = "Login With Apple")
                }
            }*/

        }
    }

}
