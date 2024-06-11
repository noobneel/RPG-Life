package com.neel.rpglifefinal.presentation.mainapp

import androidx.compose.foundation.Image
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize

import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.CircleShape

import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button

import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.IconButton
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.runtime.MutableState
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableDoubleStateOf
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.mutableStateListOf
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.shadow

import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign

import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.aay.compose.radarChart.RadarChart
import com.aay.compose.radarChart.model.NetLinesStyle
import com.aay.compose.radarChart.model.Polygon
import com.aay.compose.radarChart.model.PolygonStyle
import com.google.firebase.firestore.FieldValue
import com.google.firebase.firestore.SetOptions
import com.google.firebase.firestore.ktx.firestore
import com.google.firebase.firestore.toObject
import com.google.firebase.ktx.Firebase
import com.neel.rpglifefinal.R
import com.neel.rpglifefinal.presentation.EventList
import com.neel.rpglifefinal.presentation.sign_in.UserData
import com.neel.rpglifefinal.ui.theme.BackgroundProject
import io.github.boguszpawlowski.composecalendar.SelectableWeekCalendar
import io.github.boguszpawlowski.composecalendar.rememberSelectableWeekCalendarState
import java.time.LocalDate

@Composable
fun HomeScreen(
    userData: UserData?,
    onSignOut: () -> Unit
) {
    val addEvent = remember { mutableStateOf(false)}
    val radarShow = remember {
        mutableStateOf(false)
    }
    val homeShow = remember {
        mutableStateOf(true)
    }
    val profileShow = remember {
        mutableStateOf(false)
    }
    val calendarShow = remember {
        mutableStateOf(false)
    }
    var event = remember {
        mutableStateListOf(EventList("Yes", "Yes", "Yes", "Yes"))
    }
    val db = Firebase.firestore
    val userId = userData?.userId
    if(userId != null)
        db.collection("user").document(userId).set(userData, SetOptions.merge())
    var int = remember {mutableDoubleStateOf(0.0)}
    var chr = remember {mutableDoubleStateOf(0.0)}
    var wis = remember {mutableDoubleStateOf(0.0)}
    var con = remember {mutableDoubleStateOf(0.0)}
    var str = remember {mutableDoubleStateOf(0.0)}
    var dex = remember {mutableDoubleStateOf(0.0)}
    if(userId!=null) {
        val ref = db.collection("user").document(userId)
        ref.get().addOnSuccessListener {
            if (it != null) {
                int.doubleValue = it.data?.get("INT").toString().toDouble()
                chr.doubleValue = it.data?.get("CHR").toString().toDouble()
                wis.doubleValue = it.data?.get("WIS").toString().toDouble()
                con.doubleValue = it.data?.get("CON").toString().toDouble()
                str.doubleValue = it.data?.get("STR").toString().toDouble()
                dex.doubleValue = it.data?.get("DEX").toString().toDouble()
            }
        }
    }
    Surface(
        modifier = Modifier
            .fillMaxSize()
            //.weight(0.9f),
            .padding(0.dp, 60.dp),
        color = Color.White
    )
    {

        if(radarShow.value) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(40.dp)
            ) {

                Radar(int.doubleValue ,chr.doubleValue ,wis.doubleValue ,con.doubleValue ,str.doubleValue ,dex.doubleValue)
            }
        }

    }
    if(homeShow.value) {

        if (userId != null) {
            val ref = db.collection("events").document(userId)

            ref.get().addOnSuccessListener {
                if (it != null) {
                    it.data?.values?.forEach {
                        if (it is HashMap<*,*>) {
                            val newData = EventList(it.get("date").toString(),
                                it.get("title").toString(), it.get("type") as String, it.get("point") as String
                            )
                            if(newData !in event)
                                event.add(newData)
                        } else {
                            println(it::class.simpleName)
                        }
                    }
                }
            }
        }
        Box(
            Modifier
                .fillMaxSize()
                .padding(0.dp, 65.dp),
            contentAlignment = Alignment.Center
        )        {
            Column(
                modifier = Modifier
                    .verticalScroll(rememberScrollState())
                    .padding(0.dp, 65.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                for (i in 0 until event.count()) {
                    CardForEvent3(event[i].date, event[i].title, event[i].type, event[i].point)
                    Spacer(modifier = Modifier.size(0.dp, 5.dp))
                }
            }
        }
    }
    if(calendarShow.value){
        Surface(
            modifier = Modifier
                .fillMaxSize(),
            color = Color.Transparent
        ) {
            Box(modifier = Modifier
                .fillMaxSize()
                .padding(0.dp, 65.dp)
            ){
                Column(horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Column(modifier = Modifier.padding(0.dp,20.dp),
                        horizontalAlignment = Alignment.CenterHorizontally) {
                        val calendarState = rememberSelectableWeekCalendarState()

                        SelectableWeekCalendar(calendarState = calendarState)
                        var selectionState = remember{mutableStateOf(calendarState.selectionState)}
                        //selectionState.value.onDateSelected(LocalDate.now())
                        if(selectionState.value.selection.isNotEmpty()) {
                            Column(modifier = Modifier
                                .verticalScroll(rememberScrollState())
                                ,horizontalAlignment = Alignment.CenterHorizontally){
                                for (eventList in event) {
                                    if(eventList.date == selectionState.value.selection[0].toString())
                                    {
                                        CardForEvent2(selectionState.value.selection[0].toString())
                                    }
                                }
                                /*CardForEvent2(selectionState.value.selection[0].toString())
                                CardForEvent2(selectionState.value.selection[0].toString())
                                CardForEvent2(selectionState.value.selection[0].toString())
                                CardForEvent()
                                CardForEvent()
                                CardForEvent()
                                CardForEvent()*/
                            }

                        }

                    }

                }

            }
        }
    }

    //Event Adder Card
    Box(
        modifier = Modifier
            .fillMaxSize()
            //.weight(0.9f),
            .padding(0.dp, 60.dp),
        //color = Color.White
        contentAlignment = Alignment.Center
    )
    {
        if(addEvent.value) {
            Box(modifier = Modifier
                .fillMaxSize()
                .clickable { addEvent.value = false },
                contentAlignment = Alignment.Center
            ) {

                Column(
                    verticalArrangement = Arrangement.Center,
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    //Card for Adding event
                    val paddingModifier = Modifier
                        .size(360.dp, 360.dp)
                        .fillMaxSize()
                    Card(
                        elevation = CardDefaults.cardElevation(
                            defaultElevation = 10.dp
                        ),
                        modifier = paddingModifier
                    ) {
                        Column(
                            modifier = paddingModifier,
                            verticalArrangement = Arrangement.SpaceEvenly
                        ) {
                            Text(text = "Add Event:")
                            var date by remember { mutableStateOf("") }
                            TextField(
                                value = date,
                                placeholder = { Text(text = "YYYY-MM-DD") },
                                onValueChange = { date = it },
                                label = { Text("Date") }
                            )
                            var title by remember { mutableStateOf("") }
                            TextField(
                                value = title,
                                placeholder = { Text(text = "Title") },
                                onValueChange = { title = it },
                                label = { Text("Title") }
                            )
                            var type by remember { mutableStateOf("") }
                            TextField(
                                value = type,
                                placeholder = { Text(text = "Type") },
                                onValueChange = { type = it },
                                label = { Text("Type") }
                            )
                            var point by remember { mutableStateOf("") }
                            TextField(
                                value = point,
                                placeholder = { Text(text = "Point") },
                                onValueChange = { point = it },
                                label = { Text("Point") }
                            )
                            val item = EventList(date, title, type, point)
                            var countofItems = remember {
                                mutableIntStateOf(0)
                            }
                            Button(onClick = {
                                val todoCheck = db.collection("events").document(userId!!)
                                todoCheck.get().addOnSuccessListener {

                                }
                                todoCheck.get().addOnSuccessListener {
                                    if(it.get("count")==null){
                                        val count = hashMapOf("count" to 0)
                                        todoCheck.set(count)
                                    }
                                    else{
                                        todoCheck.update("count",FieldValue.increment(1))
                                        countofItems.intValue = it.get("count").toString().toInt()

                                    }
                                }
                                val event = hashMapOf(countofItems.toString() to item)
                                todoCheck.set(event, SetOptions.merge())
                                addEvent.value = false
                            }) {
                                Text(text = "Submit")
                            }
                        }
                    }
                }
            }
        }
    }

    //Event Adder Activator
    Row(horizontalArrangement = Arrangement.Absolute.Right,
        verticalAlignment = Alignment.Bottom,
        modifier = Modifier.padding(15.dp,75.dp)) {
        IconButton(
            onClick = { addEvent.value = !addEvent.value },
            modifier = Modifier.size(100.dp)
        ) {
            Image(
                painter = painterResource(id = R.drawable.mdi_addadd),
                contentDescription = "",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(80.dp)

            )
        }
    }
    //Row for traversal
    Row(horizontalArrangement = Arrangement.Absolute.SpaceEvenly,
        verticalAlignment = Alignment.Bottom) {
        //Home
        IconButton(
            onClick = { homeShow.value = true
                calendarShow.value = false
                profileShow.value = false
                radarShow.value = false},
            modifier = Modifier.size(60.dp)
        ) {
            Image(
                painter =   if(homeShow.value)
                    painterResource(id = R.drawable.home_active)
                else
                    painterResource(id = R.drawable.home),
                contentDescription = "",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(40.dp)

            )
        }
        //Calendar
        IconButton(
            onClick = { homeShow.value = false
                calendarShow.value = true
                profileShow.value = false
                radarShow.value = false },
            modifier = Modifier.size(60.dp)
        ) {
            Image(
                painter = if(calendarShow.value)
                    painterResource(id = R.drawable.cldr_active)
                else
                    painterResource(id = R.drawable.calendar),
                contentDescription = "",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(40.dp)

            )
        }
        //Profile
        IconButton(
            onClick = { homeShow.value = false
                calendarShow.value = false
                profileShow.value = true
                radarShow.value = false },
            modifier = Modifier.size(60.dp)
        ) {
            Image(
                painter = if(profileShow.value)
                    painterResource(id = R.drawable.profile_active)
                else
                    painterResource(id = R.drawable.profile),
                contentDescription = "",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(40.dp)

            )
        }
        //Radar
        IconButton(
            onClick = { homeShow.value = false
                calendarShow.value = false
                profileShow.value = false
                radarShow.value = true },
            modifier = Modifier.size(60.dp)
        ) {
            Image(
                painter = if(radarShow.value)
                    painterResource(id = R.drawable.radar_active)
                else
                    painterResource(id = R.drawable.radar),
                contentDescription = "",
                contentScale = ContentScale.Fit,
                modifier = Modifier
                    .size(40.dp)

            )
        }
    }
    if(profileShow.value) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            modifier = Modifier
                //.verticalScroll(rememberScrollState())
                .padding(0.dp,65.dp)){
            IconButton(onClick = { },
                modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
                    .shadow(10.dp)
            ) {
                Image(painter = painterResource(id = R.drawable.circle), contentDescription = "", contentScale = ContentScale.Fit,modifier = Modifier
                    .size(150.dp)
                    .clip(CircleShape)
                    .shadow(10.dp))
                if(userData?.profilePictureUrl != null) {
                    AsyncImage(
                        model = userData.profilePictureUrl,
                        contentDescription = "Profile picture",
                        modifier = Modifier
                            .size(130.dp)
                            .clip(CircleShape),
                        contentScale = ContentScale.Crop
                    )
                }
            }
            if(userData?.username != null) {
                Text(
                    text = userData.username,
                    textAlign = TextAlign.Center,
                    fontSize = 36.sp,
                    fontWeight = FontWeight.SemiBold
                )
            }

            Button(
                onClick = {
                    onSignOut()
                },
                modifier = Modifier.size(120.dp,60.dp),

                ) {
                Text(text = "Sign Out")
            }
        }
    }
}





@Composable
fun Radar(int : Double, chr : Double, wis: Double, con: Double, str: Double, dex: Double){
    val radarLabels =
        listOf(
            "INT","CHR","WIS","CON","STR","DEX"
        )
    val values = listOf(int ,chr ,wis ,con ,str ,dex)
    val labelsStyle = TextStyle(
        color = BackgroundProject,
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 15.sp
    )

    val scalarValuesStyle = TextStyle(
        color = Color.Transparent,
        fontFamily = FontFamily.Serif,
        fontWeight = FontWeight.ExtraBold,
        fontSize = 10.sp
    )

    RadarChart(
        modifier = Modifier.fillMaxSize(),
        radarLabels = radarLabels,
        labelsStyle = labelsStyle,
        netLinesStyle = NetLinesStyle(
            netLineColor = Color(0xffD3CFD3),
            netLinesStrokeWidth = 2f,
            netLinesStrokeCap = StrokeCap.Butt
        ),
        scalarSteps = 5,
        scalarValue = 50.0,
        scalarValuesStyle = scalarValuesStyle,
        polygons = listOf(
            Polygon(
                values = values,
                unit = "",
                style = PolygonStyle(
                    fillColor = Color(0xffffb703),
                    fillColorAlpha = 0.5f,
                    borderColor = Color(0xffe6ffd6),
                    borderColorAlpha = 0.5f,
                    borderStrokeWidth = 2f,
                    borderStrokeCap = StrokeCap.Butt,
                )
            )
        )
    )
}

@Composable
fun CardForEvent() {
    val paddingModifier = Modifier
        .size(360.dp, 120.dp)
        .fillMaxSize()
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        modifier = paddingModifier
    ) {
        Column(modifier = paddingModifier) {
            Text(text = "First Text")
            Text(text = "Second Text")
        }
    }
}

@Composable
fun CardForEvent2(text: String) {
    val paddingModifier = Modifier
        .size(360.dp, 120.dp)
        .fillMaxSize()
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        modifier = paddingModifier
    ) {
        Column(modifier = paddingModifier) {
            Text(text = text)
            Text(text = "Second Text")
        }
    }
}
@Composable
fun CardForEvent3(date: String,title: String,type: String,int: String) {
    val paddingModifier = Modifier
        .size(360.dp, 120.dp)
        .fillMaxSize()
    Card(
        elevation = CardDefaults.cardElevation(
            defaultElevation = 10.dp
        ),
        modifier = paddingModifier
    ) {
        Column(modifier = paddingModifier) {
            Text(text = if(date!=null)
                        date
                        else
                        "")
            Text(text = title)
        }
    }
}



