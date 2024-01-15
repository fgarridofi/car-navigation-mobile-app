package com.example.formapp.navigation

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.formapp.data.repository.ComponentRepositoryImpl
import com.example.formapp.domain.repository.ComponentRepository
import com.example.formapp.ui.form.*
import com.example.formapp.ui.screens.*

@Composable
fun AppNavigation () {
    val navController = rememberNavController()
    NavHost(navController =navController , startDestination = AppScreens.InicioScreen.route) {
        composable(route = AppScreens.InicioScreen.route) {
            InicioScrenn(navController)
        }
        composable(route = AppScreens.FormScreen.route) {
            val context = LocalContext.current
            FormScreen(navController,context = context)
        }
        composable(route = AppScreens.MenuPrincipalScreen.route){
            MenuPrincipalScreen(navController)
        }
        composable(route = AppScreens.NavegarScreen.route){
            NavegarScrenn(navController)
        }
        composable(route = AppScreens.HistorialScreen.route){
            HistorialScrenn(navController)
        }
        composable(route = AppScreens.RecambiosScreen.route){
            RecambiosScrenn(navController)
        }


        composable(route = AppScreens.MenuNeumaticosScreen.route) {
            MenuNeumaticosScreen(navController = rememberNavController(),)
        }

        composable(route = AppScreens.MenuFiltrosScreen.route){
            MenuFiltrosScrenn(navController)
        }
        composable(route = AppScreens.MenuFrenosScreen.route){
            MenuFrenosScrenn(navController)
        }

        composable(route = AppScreens.MenuLiquidosScreen.route){
            MenuLiquidosScrenn(navController)
        }

        composable(route = AppScreens.MenuOtrosComponentesScreen.route){
            MenuOtrosComponentesScrenn(navController)
        }
        composable(route = AppScreens.MenuAceitesScreen.route){
            MenuAceitesScrenn(navController)
        }

        composable(route = AppScreens.PruebaScreen.route) {
            PruebaScreen()
        }

    }
    }



