package com.pemmob.ridhotajrian

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.pemmob.ridhotajrian.ui.theme.DigimonBlaterTheme
import com.pemmob.ridhotajrian.view.DigimonDetailScreen
import com.pemmob.ridhotajrian.view.HomeScreen
import com.pemmob.ridhotajrian.viewmodel.DigimonViewModel

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            DigimonBlaterTheme { // Ganti sesuai nama Theme aplikasimu
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    DigimonApp()
                }
            }
        }
    }
}

@Composable
fun DigimonApp() {
    val navController = rememberNavController()
    val viewModel: DigimonViewModel = viewModel()

    NavHost(navController = navController, startDestination = "home") {
        composable("home") {
            HomeScreen(
                viewModel = viewModel,
                onDigimonClick = { digimonName ->
                    navController.navigate("detail/$digimonName")
                }
            )
        }

        // Layar 2: Detail Screen
        composable("detail/{digimonName}") { backStackEntry ->
            val digimonName = backStackEntry.arguments?.getString("digimonName") ?: ""
            DigimonDetailScreen(
                digimonName = digimonName,
                viewModel = viewModel,
                onBackClick = { navController.popBackStack() } // Tombol kembali
            )
        }
    }
}