package com.example.assignment1_mad.components.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import com.example.assignment1_mad.services.Service
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.model.*
import com.google.maps.android.compose.*

const val TAG_FINDVEJ = "FINDVEJ"

private lateinit var mMap: GoogleMap

val haddokks = LatLng(56.15883144363669, 10.21561737093552)
val approks = LatLng(56.16761166627294, 10.193029958070378)
val umbi = LatLng(56.168340288576836, 10.201952581203939)
val fysisk = LatLng(56.16798188711438, 10.198519353618819)
val alkymia = LatLng(56.16872257978334, 10.199034337756585)
val biologisk = LatLng(56.16764737606492, 10.203454618272426)
val BM = LatLng(56.143323722847924, 10.19903868998674)


@Composable
fun FindVej(locationService: Service) {

    val polyLineList = remember { mutableStateOf(listOf<LatLng>()) }
    val scope = rememberCoroutineScope()
    val locationName = remember { mutableStateOf("Unknown Location") }
    val currentPos = remember { mutableStateOf(LatLng(1.35, 103.87)) }
    val cameraPositionState = rememberCameraPositionState {
        position = CameraPosition.fromLatLngZoom(currentPos.value, 15f)
    }
    LaunchedEffect(locationService.locationOn.value) {
        if (!locationService.locationOn.value) {
            locationService.requestPermission()
        } else {
            val location = locationService.getCurrentLocation()
            currentPos.value = LatLng(location.latitude, location.longitude)
            cameraPositionState.position = CameraPosition.fromLatLngZoom(currentPos.value, 15f)
            locationName.value = locationService.getLocationName(location)
        }
    }

    Column() {


        GoogleMap(
            modifier = Modifier.fillMaxSize(),
            cameraPositionState = cameraPositionState,
        ) {

            Polyline(
                color = Color.Magenta,
                pattern = listOf(Dash(2f)),
                points = polyLineList.value
            )
            Marker(
                state = MarkerState(position = currentPos.value),
                title = "Location: ${locationName.value}",
                snippet = "Marker in ${locationName.value}",
                //icon = BitmapDescriptorFactory.fromResource(R.drawable.maps_person)

            )
            Marker(
                state = MarkerState(haddokks),
                title = "Location: Haddokks",
                snippet = "Marker in Aarhus."
            )
            Marker(
                state = MarkerState(approks),
                title = "Location: Aproksimerbar",
                snippet = "Marker in Aarhus."
            )
            Marker(
                state = MarkerState(umbi),
                title = "Location: Medicinsk fredagsbar",
                snippet = "Marker in Aarhus."
            )
            Marker(
                state = MarkerState(fysisk),
                title = "Location: Fysisk fredagsbar",
                snippet = "Marker in Aarhus."
            )
            Marker(
                state = MarkerState(biologisk),
                title = "Location: Biologisk fredagsbar",
                snippet = "Marker in Aarhus."
            )

        }

    }

}
