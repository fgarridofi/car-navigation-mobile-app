package com.example.formapp.ui.screens

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.*
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.StrokeCap
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import com.example.formapp.R
import com.example.formapp.ui.theme.CardsDurabilityBack

@Composable
fun MenuFiltrosScrenn(navController: NavController){

    Scaffold {
        Column(modifier = Modifier
            .fillMaxSize().verticalScroll(rememberScrollState())
            .padding(top = 8.dp)) {
            Text(
                text = "Estado de los filtros",
                style = MaterialTheme.typography.h4,
                textAlign = TextAlign.Center,
            )
            Divider( thickness = 1.dp, color = Color.Black)
            Spacer(modifier = Modifier.height(8.dp))


        }

    }


}

