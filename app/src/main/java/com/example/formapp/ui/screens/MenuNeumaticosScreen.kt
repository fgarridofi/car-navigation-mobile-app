package com.example.formapp.ui.screens

import android.util.Log

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.BorderStroke
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.StrokeCap

import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.hilt.navigation.compose.hiltViewModel

import androidx.navigation.NavController
import com.example.formapp.R
import com.example.formapp.data.Result

import com.example.formapp.domain.model.Component
import com.example.formapp.util.toCardInfo

import kotlinx.coroutines.delay


private fun List<Component>.toCardInfoList(): List<CardInfo> {
    return map { component ->
        component.toCardInfo()
    }
}



@Composable
fun ShowLoading(loading: Boolean) {
    if (loading) {
        Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
            CircularProgressIndicator()
        }
    }
}





@Composable
fun MenuNeumaticosScreen(navController: NavController) {
    val updateKey = remember { mutableStateOf(0) }

    val updateCounter = remember { mutableStateOf(0) }
    val viewModel: ComponentViewModelB = hiltViewModel()




    val coroutineScope = rememberCoroutineScope()
    val cards = remember { mutableStateListOf<CardInfo>() }
    val showForm = remember { mutableStateOf(false) }

    val loading = remember { mutableStateOf(true) }

    LaunchedEffect(updateKey.value) {
        viewModel.getComponents()
    }

    val componentsResult by viewModel.components.collectAsState()

    when (componentsResult) {
        is Result.Success -> {
            loading.value = false
            val components = componentsResult.data
            cards.clear()
            val filteredComponents = components?.filter { it.type == "neumaticos" }
            cards.addAll(filteredComponents?.toCardInfoList() ?: emptyList())
        }
        is Result.Error -> {
            loading.value = false
            // Muestra un mensaje de error
        }
    }

    if (cards.isEmpty() && !loading.value) {
        LaunchedEffect(Unit) {
            loading.value = true
            delay(500) // Puedes ajustar este valor según sea necesario
            loading.value = false
        }
    }

    Box {
        Scaffold {
            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(top = 8.dp)
            ) {
                item {
                    Row(
                        modifier = Modifier.fillMaxWidth(),
                        horizontalArrangement = Arrangement.Center,
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        IconButton(onClick = { navController.popBackStack() }) {
                            Icon(Icons.Default.ArrowBack, contentDescription = "Atrás")
                        }
                        Text(
                            text = "Estado de neumáticos",
                            style = MaterialTheme.typography.h4,
                        )
                    }
                }
                item { Divider( thickness = 1.dp, color = Color.Black) }
                item { Spacer(modifier = androidx.compose.ui.Modifier.height(8.dp)) }
                // Generar una tarjeta para cada elemento en 'cards'
                itemsIndexed(cards) { index, card ->
                    CustomCard(
                        cardInfo = card,
                        onUpdate = { component ->
                            component.id?.let { it1 -> viewModel.updateComponent(it1,component) }
                            updateCounter.value += 1
                        },
                        onDelete = { component ->
                            Log.d("MenuNeumaticosScreen", "onDelete called") // Añadir este log

                            component.id?.let { it1 -> viewModel.deleteComponent(it1) }
                            updateCounter.value += 1
                        }
                    )
                }
                item {
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .padding(16.dp),
                        horizontalArrangement = Arrangement.Center
                    ) {
                        FloatingActionButton(
                            onClick = { showForm.value = true }
                        ) {
                            Icon(Icons.Default.Add, contentDescription = "Agregar")
                        }
                    }
                }
            }
        }

        if (loading.value) {
            ShowLoading(loading = true)
        }
        if (showForm.value) {
            NewCardForm(
                onCreate = { name, brand, kilometers, lifespan ->
                    val newComponent = Component(
                        name = name,
                        brand = brand,
                        type = "neumaticos",
                        kilometers = kilometers,
                        lifespan = lifespan
                    )
                    viewModel.createComponent(newComponent)
                    showForm.value = false
                    updateKey.value += 1
                },
                onDismiss = { showForm.value = false }
            )
        }
    }

}



@Composable
fun NewCardForm(onCreate: (String, String, Int, Int) -> Unit, onDismiss: () -> Unit) {
    val name = remember { mutableStateOf("") }
    val brand = remember { mutableStateOf("") }
    val km = remember { mutableStateOf("") }
    val lifespan = remember { mutableStateOf("") }



    AlertDialog(
        onDismissRequest = onDismiss,
        title = {
            Text(
                "Agregar nuevo componente",
                modifier = Modifier.fillMaxWidth().padding(bottom = 10.dp),
                textAlign = TextAlign.Center,
                style = MaterialTheme.typography.h6 // Ajusta el tamaño del texto
            )
            Spacer(modifier = Modifier.height(8.dp))
        },
        text = {
            Column(modifier = Modifier.padding(top = 8.dp)) {
                TextField(
                    value = name.value,
                    onValueChange = { name.value = it },
                    label = { Text("Nombre") },
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = brand.value,
                    onValueChange = { brand.value = it },
                    label = { Text("Marca") },
                    modifier = Modifier.fillMaxWidth()
                )
                TextField(
                    value = km.value,
                    onValueChange = { km.value = it },
                    label = { Text("Kilómetros hechos") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
                TextField(
                    value = lifespan.value,
                    onValueChange = { lifespan.value = it },
                    label = { Text("Vida útil") },
                    modifier = Modifier.fillMaxWidth(),
                    keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number)
                )
            }
        },
        buttons = {
            Row(
                Modifier
                    .fillMaxWidth()
                    .padding(horizontal = 8.dp),
                horizontalArrangement = Arrangement.SpaceBetween
            ) {
                Button(
                    onClick = onDismiss,
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Red)
                ) {
                    Text("Cancelar")
                }
                Button(
                    onClick = {
                        onCreate(
                            name.value,
                            brand.value,
                            km.value.toIntOrNull() ?: 0,
                            lifespan.value.toIntOrNull() ?: 0
                        )
                        onDismiss()
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color.Green)
                ) {
                    Text("Agregar")
                }
            }
        }
    )
}



@Composable
fun CustomCard(cardInfo: CardInfo, onUpdate: (Component) -> Unit, onDelete: (Component) -> Unit) {

    val viewModel: ComponentViewModelB = hiltViewModel()


    val showDialogRenovar = remember { mutableStateOf(false) }
    val showDialogEliminar = remember { mutableStateOf(false) }

    val updateKey = remember { mutableStateOf(0) }

    LaunchedEffect(updateKey.value) {
        viewModel.getComponents()
    }

    if (showDialogRenovar.value) {
        AlertDialog(
            onDismissRequest = { showDialogRenovar.value = false },
            title = {
                Column {
                    Text("Renovar componente", fontSize = 20.sp)
                    Spacer(Modifier.height(8.dp))
                    Divider(color = Color.Black, thickness = 1.dp)
                }
            },
            text = {
                Column {
                    Spacer(Modifier.height(8.dp))
                    Text("Al renovar este componente, sus kilómetros restantes se restablecerán al máximo de nuevo.")
                    Spacer(Modifier.height(8.dp))
                    Divider(color = Color.Black, thickness = 1.dp)
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        onUpdate(cardInfo.component.apply {
                            kilometers = 0
                        })
                        showDialogRenovar.value = false
                        updateKey.value += 1
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF4CAF50)),
                    shape = RoundedCornerShape(24.dp),
                ) {
                    Text("Continuar", color = Color.White)
                }
            },
            dismissButton = {
                Button(
                    onClick = { showDialogRenovar.value = false

                              },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFF44336)),
                    shape = RoundedCornerShape(24.dp),
                ) {
                    Text("Cancelar", color = Color.White)
                }
            }
        )
    }

    if (showDialogEliminar.value) {
        AlertDialog(
            onDismissRequest = { showDialogEliminar.value = false },
            title = {
                Column {
                    Text("Eliminar componente", fontSize = 20.sp)
                    Spacer(Modifier.height(8.dp))
                    Divider(color = Color.Black, thickness = 1.dp)
                }
            },
            text = {
                Column {
                    Spacer(Modifier.height(8.dp))
                    Text("¿Está seguro de eliminar este componente?")
                    Spacer(Modifier.height(8.dp))
                    Divider(color = Color.Black, thickness = 1.dp)
                }
            },
            confirmButton = {
                Button(
                    onClick = {
                        onDelete(cardInfo.component)
                        showDialogEliminar.value = false
                        updateKey.value += 1
                    },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF4CAF50)),
                    shape = RoundedCornerShape(24.dp),
                ) {
                    Text("Continuar", color = Color.White)
                }
            },
            dismissButton = {
                Button(
                    onClick = { showDialogEliminar.value = false },
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFF44336)),
                    shape = RoundedCornerShape(24.dp),
                ) {
                    Text("Cancelar", color = Color.White)
                }
            }
        )
    }

    Surface(
        border = BorderStroke(1.dp, Color.Black),
        shape = RoundedCornerShape(16.dp),
        color = Color(0xFF3F51B5),
        modifier = Modifier
            .height(220.dp)
            .padding(10.dp),
    ) {
        Column(modifier = Modifier.fillMaxSize()) {
            Text(
                modifier = Modifier
                    .weight(0.7f)
                    .align(Alignment.CenterHorizontally)
                    .padding(top = 6.dp),
                text = cardInfo.name,
                fontFamily = FontFamily(Font(R.font.nunito, FontWeight.Bold)),
                fontSize = 24.sp,
            )
            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.Black)
            )

            Column(
                modifier = Modifier
                    .padding(start = 7.dp)
                    .weight(1f),
            ) {
                Text(
                    text = "Marca: " +  cardInfo.brand ,
                    fontFamily = FontFamily(Font(R.font.nunito, FontWeight.Bold)),
                    fontSize = 18.sp,
                )
                Text(
                    text = "Km restantes: "+ cardInfo.km_restantes + "km",
                    fontFamily = FontFamily(Font(R.font.nunito, FontWeight.Bold)),
                    fontSize = 18.sp,
                )
            }

            Spacer(Modifier.height(8.dp))

            Row(
                modifier = Modifier
                    .padding(4.dp)
                    .weight(0.8f),

            ) {
                GradientProgressbar1(percentage = cardInfo.percentage)
            }

            Spacer(
                modifier = Modifier
                    .fillMaxWidth()
                    .height(1.dp)
                    .background(Color.Black)
            )

            Row(
                modifier = Modifier
                    .background(Color.Gray)

                    .fillMaxWidth()
                    .padding(8.dp), // Aumenta el padding para separar los botones
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Button(
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier.wrapContentSize(),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFF4CAF50)),
                    onClick = {
                        showDialogRenovar.value = true
                    },
                ) {
                    Text(
                        text = "Renovar!",
                        fontSize = 18.sp,
                        color = Color.White,
                        style = MaterialTheme.typography.h3,
                        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
                    )
                }
                Spacer(modifier = Modifier.width(78.dp)) // Añade un espacio entre los botones
                Button(
                    shape = RoundedCornerShape(24.dp),
                    modifier = Modifier.wrapContentSize(),
                    colors = ButtonDefaults.buttonColors(backgroundColor = Color(0xFFF44336)),
                    onClick = {
                        showDialogEliminar.value = true

                    },
                ) {
                    Text(
                        text = "Eliminar!",
                        fontSize = 18.sp,
                        color = Color.White,
                        style = MaterialTheme.typography.h3,
                        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
                    )
                }
            }
        }
    }
}

@Composable
fun GradientProgressbar1(
    percentage: Float,
    indicatorHeight: Dp = 24.dp,
    backgroundIndicatorColor: Color = Color.DarkGray.copy(alpha = 0.5f),
    indicatorPadding: Dp = 10.dp,
    gradientColors: List<Color> = listOf(
        Color(0xFFF44336),
        Color(0xFFCDDC39),
        Color(0xFFB7E06C),
        Color(0xFF33EE3A)
    ),
    numberStyle: TextStyle = TextStyle(
        fontFamily = FontFamily(Font(R.font.nunito, FontWeight.Bold)),
        fontSize = 28.sp
    ),
    animationDuration: Int = 1000,
    animationDelay: Int = 0
) {
    val animateNumber = animateFloatAsState(
        targetValue = percentage,
        animationSpec = tween(
            durationMillis = animationDuration,
            delayMillis = animationDelay
        )
    )

    Row(
        verticalAlignment = Alignment.CenterVertically,
        modifier = Modifier.padding(horizontal = 16.dp)
    ) {
        Text(
            text = percentage.toInt().toString() + "%",
            style = numberStyle,
            modifier = Modifier.padding(end = 8.dp)
                               .offset(y = (-15).dp)
        )

        Canvas(
            modifier = Modifier
                .fillMaxWidth()
                .height(indicatorHeight)
                .padding(start = indicatorPadding, end = indicatorPadding)
        ) {
            // Background indicator
            drawLine(
                color = backgroundIndicatorColor,
                cap = StrokeCap.Round,
                strokeWidth = size.height,
                start = Offset(x = 0f, y = 0f),
                end = Offset(x = size.width, y = 0f)
            )

            // Convert the downloaded percentage into progress (width of foreground indicator)
            val progress =
                (animateNumber.value / 100) * size.width // size.width returns the width of the canvas

            // Foreground indicator
            drawLine(
                brush = Brush.linearGradient(
                    colors = gradientColors
                ),
                cap = StrokeCap.Round,
                strokeWidth = size.height,
                start = Offset(x = 0f, y = 0f),
                end = Offset(x = progress, y = 0f)
            )
        }
    }
}

