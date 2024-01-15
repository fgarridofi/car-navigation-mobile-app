package com.example.formapp.ui.screens

import androidx.activity.OnBackPressedCallback
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.navigation.NavController
import androidx.compose.material.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Build
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.colorResource
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.formapp.R


import com.example.formapp.navigation.AppScreens

@Composable
fun RecambiosScrenn(navController: NavController){

    val context = LocalContext.current
    val activity = context.findActivity()
    val onBackPressedDispatcher = activity?.onBackPressedDispatcher

    val backCallback = remember {
        object : OnBackPressedCallback(true) {
            override fun handleOnBackPressed() {
                if (navController.currentBackStackEntry != null) {
                    navController.popBackStack()
                } else {
                    isEnabled = false
                    onBackPressedDispatcher?.onBackPressed()
                }
            }
        }
    }

    DisposableEffect(key1 = backCallback) {
        onBackPressedDispatcher?.addCallback(backCallback)
        onDispose {
            backCallback.remove()
        }
    }

    recambios(navController = navController)
    
}
@Composable
fun recambios(navController: NavController) {
    LazyColumn(
        modifier = Modifier.fillMaxSize(),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center,
    ) {
        item {
            Row(
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.SpaceAround
            ) {
                Text(
                    text = "Recambio de componentes",
                    style = MaterialTheme.typography.h5,
                    modifier = Modifier.align(Alignment.CenterVertically),
                )

                Icon(
                    imageVector = Icons.Outlined.Build,
                    contentDescription = "Weather icon",
                    modifier = Modifier.size(60.dp),
                    tint = Color.Gray,
                )
            }
        }

        item { CardWheel(navController) }
        item { CardFilters(navController) }
        item { CardBrakes(navController) }
        item { CardOil(navController) }
        item { CardLiquids(navController) }
        item { CardOther(navController) }
    }
}




@Composable
fun CardWheel(navController: NavController) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color(0xFFDAE1E7),
        modifier = Modifier
            .height(170.dp)
            .padding(10.dp),

        ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(2f),
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .wrapContentSize()
                        .background(Color.Red, shape = CircleShape)
                ) {
                    Text(
                        text = "1",
                        fontSize = 14.sp,
                        color = Color.White,
                        style = MaterialTheme.typography.h3,
                        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Neumáticos",
                    fontSize = 24.sp,
                    style = MaterialTheme.typography.h3,
                    fontWeight = FontWeight.SemiBold
                )

                Spacer(modifier = Modifier.height(4.dp))

                OutlinedButton(
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.Black,
                        backgroundColor = Color.White,
                    ),
                    onClick = { navController.navigate(route = AppScreens.MenuNeumaticosScreen.route){
                        launchSingleTop = true
                        restoreState = true
                    } }

                ) {
                    Text(
                        text = "Ver estado",
                        fontSize = 11.sp,
                        fontWeight = FontWeight.SemiBold,
                        style = MaterialTheme.typography.h3
                    )
                }
            }

            Surface(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.size(width = 100.dp, height = 140.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.wheel),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(60.dp),
                    contentDescription = null
                )

            }
        }
    }
}

@Composable
fun CardBrakes(navController: NavController) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color(0xFFDAE1E7),
        modifier = Modifier
            .height(170.dp)
            .padding(10.dp),

        ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(2f),
                verticalArrangement = Arrangement.Center
            ) {
               /* Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .wrapContentSize()
                        .background(Color.Red, shape = CircleShape)
                ) {
                    Text(
                        text = "1",
                        fontSize = 14.sp,
                        color = Color.White,
                        style = MaterialTheme.typography.h3,
                        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
                    )
                }*/

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Frenos",
                    fontSize =  24.sp,
                    style = MaterialTheme.typography.h3,
                    fontWeight = FontWeight.SemiBold
                )



                Spacer(modifier = Modifier.height(4.dp))

                OutlinedButton(
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.Black,
                        backgroundColor = Color.White,
                    ),
                    onClick = { navController.navigate(route = AppScreens.MenuFrenosScreen.route){
                        launchSingleTop = true
                        restoreState = true
                    } }                ) {
                    Text(
                        text = "Ver estado",
                        fontSize =  11.sp,
                        fontWeight = FontWeight.SemiBold,
                        style = MaterialTheme.typography.h3
                    )
                }
            }

            Surface(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.size(width = 100.dp, height = 140.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.frenos),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(60.dp),
                    contentDescription = null
                )

            }
        }
    }
}
@Composable
fun CardFilters (navController: NavController) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color(0xFFDAE1E7),
        modifier = Modifier
            .height(170.dp)
            .padding(10.dp),

        ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(2f),
                verticalArrangement = Arrangement.Center
            ) {
               /* Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .wrapContentSize()
                        .background(Color.Red, shape = CircleShape)
                ) {
                    Text(
                        text = "1",
                        fontSize = 14.sp,
                        color = Color.White,
                        style = MaterialTheme.typography.h3,
                        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
                    )
                }*/

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Filtros",
                    fontSize =  24.sp,
                    style = MaterialTheme.typography.h3,
                    fontWeight = FontWeight.SemiBold
                )



                Spacer(modifier = Modifier.height(4.dp))

                OutlinedButton(
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.Black,
                        backgroundColor = Color.White,
                    ),
                    onClick = { navController.navigate(route = AppScreens.MenuFiltrosScreen.route){
                        launchSingleTop = true
                        restoreState = true
                    } }
                ) {
                    Text(
                        text = "Ver estado",
                        fontSize =  11.sp,
                        fontWeight = FontWeight.SemiBold,
                        style = MaterialTheme.typography.h3
                    )
                }
            }

            Surface(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.size(width = 100.dp, height = 140.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.filters),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(60.dp),
                    contentDescription = null
                )

            }
        }
    }
}
@Composable
fun CardOil(navController: NavController) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color(0xFFDAE1E7),
        modifier = Modifier
            .height(170.dp)
            .padding(10.dp),

        ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(2f),
                verticalArrangement = Arrangement.Center
            ) {
               /* Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .wrapContentSize()
                        .background(Color.Red, shape = CircleShape)
                ) {
                    Text(
                        text = "1",
                        fontSize = 14.sp,
                        color = Color.White,
                        style = MaterialTheme.typography.h3,
                        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
                    )
                }*/

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Aceites",
                    fontSize =  24.sp,
                    style = MaterialTheme.typography.h3,
                    fontWeight = FontWeight.SemiBold
                )



                Spacer(modifier = Modifier.height(4.dp))

                OutlinedButton(
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.Black,
                        backgroundColor = Color.White,
                    ),
                    onClick = { navController.navigate(route = AppScreens.MenuAceitesScreen.route){
                        launchSingleTop = true
                        restoreState = true
                    } }
                ) {
                    Text(
                        text = "Ver estado",
                        fontSize =  11.sp,
                        fontWeight = FontWeight.SemiBold,
                        style = MaterialTheme.typography.h3
                    )
                }
            }

            Surface(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.size(width = 100.dp, height = 140.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.oil),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(60.dp),
                    contentDescription = null
                )

            }
        }
    }
}

@Composable
fun CardLiquids(navController: NavController) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color(0xFFDAE1E7),
        modifier = Modifier
            .height(170.dp)
            .padding(10.dp),

        ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(2f),
                verticalArrangement = Arrangement.Center
            ) {
              /*  Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .wrapContentSize()
                        .background(Color.Red, shape = CircleShape)
                ) {
                    Text(
                        text = "1",
                        fontSize = 14.sp,
                        color = Color.White,
                        style = MaterialTheme.typography.h3,
                        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
                    )
                }*/

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Liquidos",
                    fontSize =  24.sp,
                    style = MaterialTheme.typography.h3,
                    fontWeight = FontWeight.SemiBold
                )



                Spacer(modifier = Modifier.height(4.dp))

                OutlinedButton(
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.Black,
                        backgroundColor = Color.White,
                    ),
                    onClick = { navController.navigate(route = AppScreens.MenuLiquidosScreen.route){
                        launchSingleTop = true
                        restoreState = true
                    } }
                ) {
                    Text(
                        text = "Ver estado",
                        fontSize =  11.sp,
                        fontWeight = FontWeight.SemiBold,
                        style = MaterialTheme.typography.h3
                    )
                }
            }

            Surface(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.size(width = 100.dp, height = 140.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.liquidos),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(60.dp),
                    contentDescription = null
                )

            }
        }
    }
}

@Composable
fun CardOther(navController: NavController) {
    Surface(
        shape = RoundedCornerShape(16.dp),
        color = Color(0xFFDAE1E7),
        modifier = Modifier
            .height(180.dp)
            .padding(10.dp),

        ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Column(
                modifier = Modifier
                    .fillMaxSize()
                    .weight(2f),
                verticalArrangement = Arrangement.Center
            ) {
                Box(
                    contentAlignment = Alignment.Center,
                    modifier = Modifier
                        .wrapContentSize()
                        .background(Color.Red, shape = CircleShape)
                ) {
                    Text(
                        text = "1",
                        fontSize = 14.sp,
                        color = Color.White,
                        style = MaterialTheme.typography.h3,
                        modifier = Modifier.padding(vertical = 4.dp, horizontal = 8.dp)
                    )
                }

                Spacer(modifier = Modifier.height(4.dp))

                Text(
                    text = "Otros Componentes",
                    fontSize =  24.sp,
                    style = MaterialTheme.typography.h3,
                    fontWeight = FontWeight.SemiBold
                )



                Spacer(modifier = Modifier.height(4.dp))

                OutlinedButton(
                    shape = RoundedCornerShape(8.dp),
                    colors = ButtonDefaults.buttonColors(
                        contentColor = Color.Black,
                        backgroundColor = Color.White,
                    ),
                    onClick = { navController.navigate(route = AppScreens.MenuOtrosComponentesScreen.route){
                        launchSingleTop = true
                        restoreState = true
                    } }
                ) {
                    Text(
                        text = "Ver estado",
                        fontSize =  11.sp,
                        fontWeight = FontWeight.SemiBold,
                        style = MaterialTheme.typography.h3
                    )
                }
            }

            Surface(
                shape = RoundedCornerShape(16.dp),
                modifier = Modifier.size(width = 100.dp, height = 140.dp)
            ) {
                Image(
                    painter = painterResource(id = R.drawable.otroscomp),
                    contentScale = ContentScale.Crop,
                    modifier = Modifier.size(60.dp),
                    contentDescription = null
                )

            }
        }
    }
}

