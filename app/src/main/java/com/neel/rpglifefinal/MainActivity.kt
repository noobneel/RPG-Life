package com.neel.rpglifefinal

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.compose.setContent
import androidx.activity.result.IntentSenderRequest
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.core.splashscreen.SplashScreen.Companion.installSplashScreen
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.lifecycle.lifecycleScope
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
//import androidx.navigation.compose.NavHost
//import androidx.navigation.compose.composable
//import androidx.navigation.compose.rememberNavController
import com.google.android.gms.auth.api.identity.Identity
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.firestore
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.ktx.Firebase
import com.neel.rpglifefinal.presentation.mainapp.HomeScreen
import com.neel.rpglifefinal.presentation.profile.ProfileScreen
import com.neel.rpglifefinal.presentation.sign_in.GoogleAuthUiClient
import com.neel.rpglifefinal.presentation.sign_in.SignInScreen
import com.neel.rpglifefinal.presentation.sign_in.SignInViewModel
import com.neel.rpglifefinal.presentation.stat_select.AgeSelect
import com.neel.rpglifefinal.presentation.stat_select.GenderSelect
import com.neel.rpglifefinal.presentation.stat_select.HeightSelect
import com.neel.rpglifefinal.presentation.stat_select.ProfSelect
import com.neel.rpglifefinal.presentation.stat_select.StatDecide
import com.neel.rpglifefinal.presentation.stat_select.WeightSelect
import com.neel.rpglifefinal.ui.theme.RPGLifeFinalTheme
import kotlinx.coroutines.launch

class MainActivity : ComponentActivity() {



    private val googleAuthUiClient by lazy {
        GoogleAuthUiClient(
            context = applicationContext,
            oneTapClient = Identity.getSignInClient(applicationContext)
        )
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        installSplashScreen()

        setContent {
            RPGLifeFinalTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colors.background
                ) {
                    val navController = rememberNavController()
                    NavHost(navController = navController, startDestination = "sign_in") {
                        composable("sign_in") {
                            val viewModel = viewModel<SignInViewModel>()
                            val state by viewModel.state.collectAsStateWithLifecycle()

                            LaunchedEffect(key1 = Unit) {
                                if(googleAuthUiClient.getSignedInUser() != null) {
                                    var db = Firebase.firestore
                                    val userId = FirebaseAuth.getInstance().currentUser!!.uid
                                    val ref = db.collection("user").document(userId)
                                    ref.get().addOnSuccessListener {
                                        if(it!=null){
                                            if(it.data?.get("stats")==null){
                                                if(it.data?.get("age") == null){
                                                    navController.navigate("age")
                                                }
                                                else if(it.data?.get("gender") == null){
                                                    navController.navigate("gender")
                                                }
                                                else if(it.data?.get("height") == null){
                                                    navController.navigate("height")
                                                }
                                                else if(it.data?.get("prof") == null){
                                                    navController.navigate("prof")
                                                }
                                                else if(it.data?.get("weight") == null){
                                                    navController.navigate("weight")
                                                }
                                                else {
                                                    navController.navigate("stats")
                                                }
                                            }
                                            else{
                                                navController.navigate("mainapp")
                                            }

                                        }
}
                                }
                            }

                            val launcher = rememberLauncherForActivityResult(
                                contract = ActivityResultContracts.StartIntentSenderForResult(),
                                onResult = { result ->
                                    if(result.resultCode == RESULT_OK) {
                                        lifecycleScope.launch {
                                            val signInResult = googleAuthUiClient.signInWithIntent(
                                                intent = result.data ?: return@launch
                                            )
                                            viewModel.onSignInResult(signInResult)
                                        }
                                    }
                                }
                            )

                            LaunchedEffect(key1 = state.isSignInSuccessful) {
                                if(state.isSignInSuccessful) {
                                    Toast.makeText(
                                        applicationContext,
                                        "Sign in successful",
                                        Toast.LENGTH_LONG
                                    ).show()
                                    var db = Firebase.firestore
                                    val userId = FirebaseAuth.getInstance().currentUser!!.uid
                                    val ref = db.collection("user").document(userId)
                                    ref.get().addOnSuccessListener {
                                        if(it!=null){

                                            if(it.data?.get("age") == null){
                                                navController.navigate("age")
                                            }
                                            else if(it.data?.get("gender") == null){
                                                navController.navigate("gender")
                                            }
                                            else if(it.data?.get("height") == null){
                                                navController.navigate("height")
                                            }
                                            else if(it.data?.get("prof") == null){
                                                navController.navigate("prof")
                                            }
                                            else if(it.data?.get("weight") == null){
                                                navController.navigate("weight")
                                            }
                                            else if(it.data?.get("stats") == null){
                                                navController.navigate("stats")
                                            }
                                            else{
                                                navController.navigate("mainapp")
                                                viewModel.resetState()
                                            }

                                        }
//
                                    }
//                                    navController.navigate("mainapp")
//                                    viewModel.resetState()
                                }
                            }

                            SignInScreen(
                                state = state,
                                onSignInClick = {
                                    lifecycleScope.launch {
                                        val signInIntentSender = googleAuthUiClient.signIn()
                                        launcher.launch(
                                            IntentSenderRequest.Builder(
                                                signInIntentSender ?: return@launch
                                            ).build()
                                        )
                                    }
                                }
                            )
                        }
                        composable("mainapp") {
                            HomeScreen(
                                userData = googleAuthUiClient.getSignedInUser(),
                                onSignOut = {
                                    lifecycleScope.launch {
                                        googleAuthUiClient.signOut()
                                        Toast.makeText(
                                            applicationContext,
                                            "Signed out",
                                            Toast.LENGTH_LONG
                                        ).show()

                                        navController.navigate("sign_in")
                                    }

                                }
                            )
                        }
                        composable("age"){
                            AgeSelect(navController)

                        }
                        composable("gender"){
                            GenderSelect(navController)
                        }
                        composable("height"){
                            HeightSelect(navController)
                        }
                        composable("prof"){
                            ProfSelect(navController)
                        }
                        composable("weight"){
                            WeightSelect(navController)
                        }
                        composable("stats"){
                            StatDecide(navController)
                        }
                    }
                }
            }
        }
    }
    override fun onBackPressed() {

        // To execute back press
        // super.onBackPressed()

        // To do something else
        Toast.makeText(applicationContext, "Back Button disabled for this app.", Toast.LENGTH_SHORT).show()
    }
}