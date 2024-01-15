package com.example.formapp.navigation

sealed class AppScreens(val route :String){
    object InicioScreen: AppScreens("inicio_screen")
    object FormScreen: AppScreens("formulario_screen")
    object MenuPrincipalScreen: AppScreens("menu_screen")
    object NavegarScreen: AppScreens("navegar_screen")
    object HistorialScreen: AppScreens("historial_screen")
    object RecambiosScreen: AppScreens("recambios_screen")
    object MenuNeumaticosScreen : AppScreens("menu_neumaticos_screen")
    object MenuFiltrosScreen : AppScreens("menu_filtros_screen")
    object MenuFrenosScreen : AppScreens("menu_frenos_screen")
    object MenuLiquidosScreen : AppScreens("menu_liquidos_screen")
    object MenuOtrosComponentesScreen : AppScreens("menu_otroscomponentes_screen")
    object MenuAceitesScreen : AppScreens("menu_aceites_screen")
    object PruebaScreen : AppScreens("prueba")
}
