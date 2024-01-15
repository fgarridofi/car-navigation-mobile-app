package com.example.formapp.ui.form

import android.content.Intent
import androidx.compose.foundation.layout.*
import androidx.compose.material.Button
import androidx.compose.material.ButtonDefaults
import androidx.compose.material.Scaffold
import androidx.compose.material.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.NavController
import com.example.formapp.navigation.AppScreens
import com.example.formapp.ui.MainActivity2
import com.example.formapp.ui.MainActivity3
import com.example.formapp.ui.MainActivity4
import com.example.formapp.ui.theme.RedVisne

@Composable
fun MenuPrincipalScreen(navController: NavController){

    Scaffold() {
        menu(navController)
        
    }
    
}
@Composable
fun menu(navController: NavController){
    val context = LocalContext.current
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally

    ) {

        Button(onClick = {context.startActivity(Intent(context, MainActivity4::class.java))/* navController.navigate(route = AppScreens.NavegarScreen.route)*/},
                colors = ButtonDefaults.buttonColors(
                                                    backgroundColor = RedVisne,
                                                    contentColor = Color.White
        )) {
            Text("Navegar")

        }

        Button(onClick = { navController.navigate(route = AppScreens.HistorialScreen.route)},
            colors = ButtonDefaults.buttonColors(
                backgroundColor = RedVisne,
                contentColor = Color.White
            )


        ) {
            Text("Historial")
        }
        Button(onClick = { navController.navigate(route = AppScreens.RecambiosScreen.route)},
            colors = ButtonDefaults.buttonColors(
                backgroundColor = RedVisne,
                contentColor = Color.White
            )


        ) {
            Text("Recambios")
        }



    }
}


