package com.example.formapp.ui

import android.content.Intent
import android.os.Bundle
import android.view.View
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.formapp.data.repository.ComponentRepositoryImpl
import com.example.formapp.navigation.AppNavigation
import com.example.formapp.ui.theme.FormAppTheme
import dagger.hilt.android.AndroidEntryPoint

@AndroidEntryPoint
class MainActivity : ComponentActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {

            FormAppTheme {
                AppNavigation()
            }
        }
    }
}