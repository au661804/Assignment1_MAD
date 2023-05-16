package com.example.assignment1_mad.components.screens

import android.graphics.Bitmap
import android.graphics.ImageDecoder
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.util.Log
import android.widget.ImageView
import androidx.activity.compose.rememberLauncherForActivityResult
import androidx.activity.result.contract.ActivityResultContracts
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.material.MaterialTheme.colors
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.asImageBitmap
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.assignment1_mad.services.FireStoreService
import com.google.firebase.auth.ktx.auth
import com.google.firebase.ktx.Firebase
import com.maxkeppeker.sheets.core.models.base.rememberSheetState
import com.maxkeppeler.sheets.calendar.CalendarDialog
import com.maxkeppeler.sheets.calendar.models.CalendarConfig
import com.maxkeppeler.sheets.calendar.models.CalendarSelection
import com.maxkeppeler.sheets.calendar.models.CalendarStyle
import com.maxkeppeler.sheets.clock.ClockDialog
import com.maxkeppeler.sheets.clock.models.ClockConfig
import com.maxkeppeler.sheets.clock.models.ClockSelection
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import java.time.LocalDate
import java.time.LocalTime


const val TAG_OPRETBEGIVENHEDER = "OPRETBEGIVENHEDER"

@Composable
fun OpretBegivenhed(service: FireStoreService, nav: NavController) {

    val user = Firebase.auth.currentUser
    var isButtonClicked by remember { mutableStateOf(false) }
    var expanded by remember { mutableStateOf(false) }

    //Image



    //Caldendarstate trigger selection
    val calendarState = rememberSheetState()
    val selectedDate = remember { mutableStateOf("") }

    CalendarDialog(
        state = calendarState,
        config = CalendarConfig(
            monthSelection = true,
            yearSelection = true,
            style = CalendarStyle.MONTH
        ),
        selection = CalendarSelection.Date { newDate ->
            selectedDate.value =
                LocalDate.of(newDate.year, newDate.month, newDate.dayOfMonth).toString()
            Log.d("Selected Date", "$newDate")


        }
    )


    //Clockstate trigger selection
    val clockStateStart = rememberSheetState()
    val selectedTimeStart = remember { mutableStateOf("") }
    ClockDialog(
        state = clockStateStart,
        config = ClockConfig(
            is24HourFormat = true
        ),
        selection = ClockSelection.HoursMinutes { hours, minutes ->

            selectedTimeStart.value = LocalTime.of(hours, minutes).toString()
            Log.d("Selected starttime", "$hours $minutes")


        })

    //Clockstate trigger selection
    val clockStateEnd = rememberSheetState()
    val selectedTimeEnd = remember { mutableStateOf("null") }
    ClockDialog(
        state = clockStateEnd,
        config = ClockConfig(
            is24HourFormat = true
        ),
        selection = ClockSelection.HoursMinutes { hours, minutes ->

            selectedTimeEnd.value = LocalTime.of(hours, minutes).toString()
            Log.d("Selected Endtime", "$hours $minutes")


        })


    val scope = rememberCoroutineScope()
    val focusRequester = remember { FocusRequester() }


    val name = remember { mutableStateOf("") }
    val lokation = remember { mutableStateOf("") }
    val beskrivelse = remember { mutableStateOf("") }



    Column(
        modifier = Modifier

            .fillMaxSize()
            .background(Color.White)
            .verticalScroll(rememberScrollState()),
        horizontalAlignment = Alignment.CenterHorizontally,
    ) {

        Text(
            "Opret begivenhed",
            color = Color.Black,
            fontWeight = FontWeight.Light,
            fontSize = 30.sp,
            textAlign = TextAlign.Center
        )

        Spacer(modifier = Modifier.padding(bottom = 20.dp))


        PickImageFromGallery()


        Spacer(modifier = Modifier.padding(bottom = 25.dp))

        Row() {

            TextField(
                value = name.value,
                singleLine = true,
                modifier = Modifier
                    .width(300.dp)
                    .height(55.dp)
                    .focusRequester(focusRequester)

                    .background(Color(0xE1D8B99E)),
                textStyle = TextStyle(Color.Black,fontSize = 10.sp),


                onValueChange = {
                    // name.value = it
                        newText ->
                    name.value = newText
                },
                label = {
                    Text(
                        text = "Navn på begivenhed",
                        color = Color(0xFF000000),
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp,
                        textAlign = TextAlign.Left,
                        modifier = Modifier.padding(2.dp)


                    )
                },
                placeholder = {
                    Text(
                        text = "e.g. Den bedste Fredagsbar ",
                        color = Color(0xFF000000),
                        fontWeight = FontWeight.Light,
                        fontSize = 10.sp,
                        textAlign = TextAlign.Left,


                        )
                },
            )
        }

        Spacer(modifier = Modifier.padding(bottom = 10.dp))
        Row() {
            //TEKSTFELT lokation
            TextField(
                value = lokation.value,
                singleLine = true,
                modifier = Modifier
                    .width(300.dp)
                    .height(55.dp)
                    .focusRequester(focusRequester)


                    .background(Color(0xE1D8B99E)),
                textStyle = TextStyle(Color.Black),


                onValueChange = {
                    lokation.value = it
                },
                label = {
                    Text(
                        text = "Lokation for begivenhed",
                        color = Color(0xFF000000),
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp,
                        textAlign = TextAlign.Left,
                        modifier = Modifier.padding(2.dp)

                    )
                },
                placeholder = {
                    Text(
                        text = "e.g. Katrines Kælder",
                        color = Color(0xFF000000),
                        fontWeight = FontWeight.Light,
                        fontSize = 10.sp,
                        textAlign = TextAlign.Left
                    )
                },
            )
        }
        Spacer(modifier = Modifier.padding(bottom = 10.dp))

        Row() {
            //TEKSTFELT Beskrivelse
            TextField(
                value = beskrivelse.value,
                singleLine = true,
                modifier = Modifier
                    .width(300.dp)
                    .height(55.dp)
                    .focusRequester(focusRequester)

                    .background(Color(0xE1D8B99E)),
                textStyle = TextStyle(Color.Black),


                onValueChange = {
                    beskrivelse.value = it
                },
                label = {
                    Text(
                        text = "Beskrivelse af begivenhed",
                        color = Color(0xFF000000),
                        fontWeight = FontWeight.Bold,
                        fontSize = 10.sp,
                        textAlign = TextAlign.Left,
                        modifier = Modifier.padding(2.dp)

                    )
                },
                placeholder = {
                    Text(
                        text = "e.g. Vigtige detaljer",
                        color = Color(0xFF000000),
                        fontWeight = FontWeight.Light,
                        fontSize = 10.sp,
                        textAlign = TextAlign.Left
                    )
                },
            )
        }

        Spacer(modifier = Modifier.padding(bottom = 10.dp))
        Row() {
            //DATO

            Button(modifier = Modifier.size(width = 180.dp, height = 50.dp),
                shape = RoundedCornerShape(0.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFFB1A0A0),

                    contentColor = Color.Black
                ),
                onClick = { calendarState.show() }) {
                Text(text = "Vælg dato", fontSize = 10.sp)
            }

            TextField(
                value = selectedDate.value,
                readOnly = true,
                onValueChange = { selectedDate.value = it },
                textStyle = TextStyle(Color.Black,fontSize = 10.sp),
                modifier = Modifier.size(width = 120.dp, height = 50.dp)
            )
        }
        Spacer(modifier = Modifier.padding(bottom = 10.dp))

        //TIMEstart
        Row() {
            Button(modifier = Modifier.size(width = 180.dp, height = 50.dp),
                shape = RoundedCornerShape(0.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFFB1A0A0),

                    contentColor = Color.Black
                ),
                onClick = { clockStateStart.show() }) {
                Text(text = "Vælg starttidspunkt",fontSize = 10.sp)
            }
            TextField(
                value = selectedTimeStart.value,
                readOnly = true,
                singleLine = true,
                onValueChange = { selectedTimeStart.value = it },
                textStyle = TextStyle(Color.Black, fontSize = 10.sp),
                modifier = Modifier.size(width = 120.dp, height = 60.dp)
            )

        }

        Spacer(modifier = Modifier.padding(bottom = 10.dp))

        //TimeEnd
        Row() {
            Button(modifier = Modifier.size(width = 180.dp, height = 50.dp),
                shape = RoundedCornerShape(0.dp),
                colors = ButtonDefaults.buttonColors(
                    backgroundColor = Color(0xFFB1A0A0),

                    contentColor = Color.Black
                ),
                onClick = { clockStateEnd.show() }
            ) {
                Text(text = "Vælg Sluttidspunkt",fontSize = 10.sp)
            }

            TextField(
                value = selectedTimeEnd.value,
                readOnly = true,
                singleLine = true,
                onValueChange = { selectedTimeEnd.value = it },
                textStyle = TextStyle(Color.Black,fontSize = 10.sp),
                modifier = Modifier.size(width = 120.dp, height = 50.dp)
            )
        }



        Spacer(modifier = Modifier.padding(bottom = 10.dp))


        // KNAP
        Button(
            onClick = {


                scope.launch {
                    service.createBegivenhed(
                        user.toString(),
                        name.value,
                        selectedDate.value,
                        selectedTimeStart.value,
                        selectedTimeEnd.value,
                        beskrivelse.value,
                        lokation.value,


                    )


                    isButtonClicked = true
                    GlobalScope.launch {
                        delay(3000) // Wait for 3 seconds
                        isButtonClicked = false // Hide the message box
                    }

                }


            },
            colors = ButtonDefaults.buttonColors(
                backgroundColor = Color(android.graphics.Color.parseColor("#ffa34f")),

                contentColor = Color.Gray
            ),
            shape = RoundedCornerShape(percent = 20),
            modifier = Modifier.size(width = 300.dp, height = 40.dp),
            enabled = selectedTimeEnd.value.isNotBlank()

        )
        {
            Text(
                "Gem begivenhed",
                color = Color.White.copy(0.8f),
                fontWeight = FontWeight.Bold,
                fontSize = 18.sp
            )
        }
    }

    if (isButtonClicked) {
        // Show the message box
        Card(
            modifier = Modifier
                .fillMaxWidth(0.8f)
                .wrapContentHeight(),

            elevation = 8.dp
        ) {
            Column(
                modifier = Modifier.padding(16.dp),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Center
            ) {
                Text("$name er gemt")
                Spacer(modifier = Modifier.height(16.dp))

            }
        }
    }

}


@Composable
fun PickImageFromGallery() {

    var imageUri by remember { mutableStateOf<Uri?>(null) }
    val context = LocalContext.current
    val bitmap = remember { mutableStateOf<Bitmap?>(null) }

    val colors = listOf(Color(0xFFffe53b), Color(0xFFff2525))

    val launcher =
        rememberLauncherForActivityResult(contract = ActivityResultContracts.GetContent()) { uri: Uri? ->
            imageUri = uri
        }

    Row(
        modifier = Modifier.fillMaxSize(),
        //verticalArrangement = Arrangement.Center,
       // horizontalAlignment = Alignment.CenterHorizontally
    ) {

        imageUri?.let {
            if (Build.VERSION.SDK_INT < 28) {
                bitmap.value = MediaStore.Images
                    .Media.getBitmap(context.contentResolver, it)
            } else {
                val source = ImageDecoder.createSource(context.contentResolver, it)
                bitmap.value = ImageDecoder.decodeBitmap(source)
            }

            bitmap.value?.let { btm ->
                Image(
                    bitmap = btm.asImageBitmap(),
                    contentDescription = null,
                    modifier = Modifier
                        .size(200.dp)
                        .padding(20.dp)
                )
            }
        }

        Spacer(modifier = Modifier.height(12.dp))

        OutlinedButton(
            onClick = {

                launcher.launch("image/*")


                Log.v(this::class.simpleName, "HALLI HALLO clicked")

            },
            modifier = Modifier
                .size(55.dp)
                .background(color = Color.White),

            shape = CircleShape,
            border = BorderStroke(
                2.dp,
                brush = Brush.horizontalGradient(colors = colors, 0.1f)
            ),
            contentPadding = PaddingValues(0.dp),
            colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.Blue),


            ) {
            Icon(Icons.Default.Add, contentDescription = "contentdescription")

            //Sæt value fra det valgtet til parametren picture
        }

    }

}


