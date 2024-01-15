package com.example.formapp.ui.screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.*
import androidx.compose.material.icons.outlined.DirectionsCar
import androidx.compose.material.icons.outlined.Moving
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.testTag
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.example.formapp.domain.vm.HistorialViewModel
import com.example.formapp.ui.theme.RedVisne
import com.example.formapp.data.Result
import com.example.formapp.domain.model.Historial


fun formatTime(timeInHours: Double): String {
    val totalSeconds = (timeInHours * 3600).toInt()
    val hours = totalSeconds / 3600
    val minutes = (totalSeconds % 3600) / 60
    val seconds = totalSeconds % 60

    return String.format("%02d:%02d:%02d", hours, minutes, seconds)
}

@Composable
fun HistorialScrenn(navController: NavController){

    Scaffold() {
        historial(navController)
        
    }
    
}
@Composable
fun historial(navController: NavController){

    val historialViewModel: HistorialViewModel = hiltViewModel()
    val historialList by historialViewModel.historialList.collectAsState()

    val isLoading by historialViewModel.loading.collectAsState()


    // Llamar a getHistorial() para obtener los datos
    LaunchedEffect(Unit) {
        historialViewModel.getHistorial()
    }

    Column(modifier = Modifier.verticalScroll(rememberScrollState())) {
        Row(modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceAround){
                Text(
                    text = "Historial de navegación",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.align(Alignment.CenterVertically),
                )
                Icon(
                    imageVector = Icons.Outlined.DirectionsCar,
                    contentDescription = "Weather icon",
                    modifier = Modifier.size(60.dp),
                    tint = Color.Gray,
                )
        }

        Divider( thickness = 1.dp, color = Color.Black)
        Spacer(modifier = Modifier.height(8.dp))

        //CardGenerator()


        if (isLoading) {
            CircularProgressIndicator(modifier = Modifier.align(Alignment.CenterHorizontally))
        } else {
            when (historialList) {
                is Result.Error -> Text(text = "Error al cargar el historial.")
                is Result.Success -> {
                    val list = (historialList as Result.Success<List<Historial>>).data
                    if (list != null) {
                        list.forEach { historial ->
                            CardGenerator2(historial)
                        }
                    }
                }
            }
        }

    }



}

@Composable
private fun CardGenerator2(historial: Historial) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        elevation = 4.dp,
        shape = RoundedCornerShape(12.dp),
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            HistorialView2(historial)
        }
    }
}

@Composable
private fun CardGenerator(){
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(12.dp),
        elevation = 4.dp,
        shape = RoundedCornerShape(12.dp),
    ) {
        Column(modifier = Modifier.padding(12.dp)) {
            HistorialView()
        }
    }
}

@Composable
private fun HistorialView() {
    Text(text = "Mon,15 Feb 2023", style = MaterialTheme.typography.h5)
    Spacer(modifier = Modifier.height(8.dp))

    Spacer(modifier = Modifier.height(1.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
       Column {
            Text(
                modifier = Modifier.testTag("TEMPERATURE_TAG"),
                text = "Zamora",//origen
                style = MaterialTheme.typography.h5
            )
            Text(
                modifier = Modifier.testTag("TEMPERATURE_TAG"),
                text = "12:00 PM",//hora salida origen
                style = MaterialTheme.typography.body2.copy(color = Color.Gray)
            )
       }

        Icon(
            imageVector = Icons.Outlined.Moving,
            contentDescription = "travel",
            modifier = Modifier.size(70.dp),
            tint = RedVisne,
        )

        Column {
            Text(
                modifier = Modifier.testTag("TEMPERATURE_TAG"),
                text = "Salamanca",//destino
                style = MaterialTheme.typography.h5
            )
            Text(
                modifier = Modifier.testTag("TEMPERATURE_TAG"),
                text = "12:50 PM",//hora llegada
                style = MaterialTheme.typography.body2.copy(color = Color.Gray)
            )
        }


    }
    Spacer(modifier = Modifier.height(8.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.Timer, contentDescription = "Time of travel")
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "00:40:55",//tiempo de viaje
                style = MaterialTheme.typography.body2.copy(color = Color.Gray)
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.PinDrop, contentDescription = "Distance of travel")
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "62 Km",//distancia de viaje
                style = MaterialTheme.typography.body2.copy(color = Color.Gray)
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.Speed, contentDescription = "Speed")
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "108 km/h",//velocidad media
                style = MaterialTheme.typography.body2.copy(color = Color.Gray)
            )
        }
    }
}

@Composable
private fun HistorialView2(historial: Historial) {
    Text(text = historial.fecha, style = MaterialTheme.typography.h5)
    Spacer(modifier = Modifier.height(8.dp))

    Spacer(modifier = Modifier.height(1.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly,
        verticalAlignment = Alignment.CenterVertically
    ) {
        Column(
            modifier = Modifier
                .weight(1f) // Añade peso a la columna
                .padding(end = 8.dp) // Agrega un poco de espacio entre la columna y el ícono
        ) {
            Text(
                modifier = Modifier.testTag("TEMPERATURE_TAG"),
                text = historial.origen, // origen
                style = MaterialTheme.typography.body1,
                maxLines = 3
            )
            /* Text(
                modifier = Modifier.testTag("TEMPERATURE_TAG"),
                text = "12:00 PM", // hora salida origen (no implementado aún)
                style = MaterialTheme.typography.body2.copy(color = Color.Gray)
            )*/
        }

        Icon(
            imageVector = Icons.Outlined.Moving,
            contentDescription = "travel",
            modifier = Modifier.size(80.dp),
            tint = RedVisne,
        )

        Column(
            modifier = Modifier
                .weight(1f) // Añade peso a la columna
                .padding(start = 8.dp) // Agrega un poco de espacio entre la columna y el ícono
        ) {
            Text(
                modifier = Modifier.testTag("TEMPERATURE_TAG"),
                text = historial.destino, // destino
                style = MaterialTheme.typography.body1,
                maxLines = 3
            )
            /* Text(
                modifier = Modifier.testTag("TEMPERATURE_TAG"),
                text = "12:50 PM", // hora llegada (no implementado aún)
                style = MaterialTheme.typography.body2.copy(color = Color.Gray)
            )*/
        }
    }
    Spacer(modifier = Modifier.height(8.dp))
    Row(
        modifier = Modifier.fillMaxWidth(),
        horizontalArrangement = Arrangement.SpaceEvenly
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.Timer, contentDescription = "Time of travel")
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = formatTime(historial.tiempo), // tiempo de viaje
                style = MaterialTheme.typography.body2.copy(color = Color.Gray)
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.PinDrop, contentDescription = "Distance of travel")
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "${historial.kilometers} Km", // distancia de viaje
                style = MaterialTheme.typography.body2.copy(color = Color.Gray)
            )
        }
        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(imageVector = Icons.Default.Speed, contentDescription = "Speed")
            Spacer(modifier = Modifier.width(4.dp))
            Text(
                text = "108 km/h", // velocidad media (no implementado aún)
                style = MaterialTheme.typography.body2.copy(color = Color.Gray)
            )
        }
    }
}

