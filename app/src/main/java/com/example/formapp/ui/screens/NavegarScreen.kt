package com.example.formapp.ui.form

import android.view.LayoutInflater
import android.view.Surface
import android.widget.TextView
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.*
import androidx.compose.runtime.Composable
import com.example.formapp.R
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.viewinterop.AndroidView
import androidx.navigation.NavController


import com.example.formapp.navigation.AppScreens
import com.google.maps.android.compose.GoogleMap

@Composable
fun NavegarScrenn(navController: NavController){

    navegacion( )

    
}
@Composable
fun navegacion(){

    Surface(modifier = Modifier.fillMaxSize())
    {
        AndroidView(
            factory = { context ->
                val view = LayoutInflater.from(context).inflate(R.layout.navigationview, null, false)
                //val textView = view.findViewById<TextView>(R.id.text)

                // do whatever you want...
                view // return the view
            },


           // update = { view -> /*TODO*/}
        )
    }


}


