package com.gonzalez.tupmundial2026

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.rememberNavController
import com.gonzalez.tupmundial2026.ui.navigation.AppNavigation
//import com.gonzalez.tupmundial2026.navigation.AppNavigation
import com.gonzalez.tupmundial2026.network.RetrofitClient
import com.gonzalez.tupmundial2026.repository.MundialRepository
import com.gonzalez.tupmundial2026.ui.theme.TUPMundial2026Theme
import com.gonzalez.tupmundial2026.viewModel.MundialViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            TUPMundial2026Theme {
                val repository = remember { MundialRepository(RetrofitClient.api) }
                val viewModel: MundialViewModel = viewModel(
                    factory = object : androidx.lifecycle.ViewModelProvider.Factory {
                        @Suppress("UNCHECKED_CAST")
                        override fun <T : androidx.lifecycle.ViewModel> create(modelClass: Class<T>): T {
                            return MundialViewModel(repository) as T
                        }
                    }
                )
                val navController = rememberNavController()

                AppNavigation(
                    modifier = Modifier.fillMaxSize(),
                    viewModel = viewModel,
                    navController = navController
                )
            }
        }
    }
}

