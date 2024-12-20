package com.example.kotlinproject.view

import android.os.Bundle
import android.widget.Toast
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.padding
import androidx.compose.material.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.datastore.core.DataStore

import androidx.datastore.preferences.core.Preferences
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import coil3.network.HttpException
import com.example.kotlinproject.BottomNav
import com.example.kotlinproject.NavigationGraph


import com.example.kotlinproject.ui.theme.MyAppTheme
import org.koin.android.ext.koin.androidContext
import org.koin.core.context.startKoin
import org.koin.dsl.module


class MainActivity : ComponentActivity() {



    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            MyAppTheme {
                BottomNavExample()
            }
        }
    }

}

@Composable
fun BottomNavExample() {

    val navController = rememberNavController()
    Scaffold(
        bottomBar = {
            BottomNav(navController)
        }
    ) { innerPadding ->
        NavigationGraph(navController, Modifier.padding(innerPadding))
    }
}
