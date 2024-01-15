package com.example.formapp.ui.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.remember
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalConfiguration
import com.google.accompanist.systemuicontroller.rememberSystemUiController

private val LightColorPalette = lightColors(
   /* primary = Brown500,
    primaryVariant = Brown900,
    onPrimary = Color.White,
    secondary = Brown500,
    secondaryVariant = Brown900,
    onSecondary = Color.White,
    onError = Red800,
    onBackground = Color.Black*/
    primary = RedVisne,
    primaryVariant = Brown700,
    onPrimary = Color.Black,
    secondary = Brown200,
    onSecondary = Color.Black,
    onError = Red300,
    onBackground = Color.Black
)




@Composable
fun FormAppTheme( content: @Composable () -> Unit) {
    MaterialTheme(
        colors =  LightColorPalette,
        typography = Typography,
        shapes = Shapes,
        content = content
    )
}
@Composable
fun ProvideDimens(
    dimensions: Dimensions,
    content: @Composable () -> Unit
) {
    val dimensionSet = remember { dimensions }
    CompositionLocalProvider(LocalAppDimens provides dimensionSet, content = content)
}
private val LocalAppDimens = staticCompositionLocalOf {
    smallDimensions
}

@Composable
fun LoginScreenTheme( content: @Composable() () -> Unit) {

    val systemUiController = rememberSystemUiController()
    val configuration = LocalConfiguration.current

    val dimensions = if (configuration.screenWidthDp <= 360) smallDimensions else sw360Dimensions


        systemUiController.setSystemBarsColor(
            color = RedVisne
        )



    ProvideDimens(dimensions = dimensions) {

        MaterialTheme(
            colors = LightColorPalette,
            typography = Typography,
            shapes = Shapes,
            content = content
        )
    }
}

object AppTheme {

    val dimens: Dimensions
        @Composable
        get() = LocalAppDimens.current
}

val Dimens: Dimensions
    @Composable
    get() = AppTheme.dimens