package com.pemmob.ridhotajrian.view

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import coil.compose.AsyncImage
import com.pemmob.ridhotajrian.viewmodel.DigimonViewModel
import com.pemmob.ridhotajrian.viewmodel.UiState
import androidx.compose.material3.Icon
@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun DigimonDetailScreen(
    digimonName: String,
    viewModel: DigimonViewModel,
    onBackClick: () -> Unit
) {
    // Memanggil API otomatis saat layar ini dibuka
    LaunchedEffect(digimonName) {
        viewModel.fetchDigimonDetail(digimonName)
    }

    val state by viewModel.detailState.collectAsState()

    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Detail Digimon") },
                navigationIcon = {
                    IconButton(onClick = onBackClick) {
                        Icon(Icons.Default.ArrowBack, contentDescription = "Kembali")
                    }
                }
            )
        }
    ) { paddingValues ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues)
        ) {
            when (state) {
                is UiState.Loading -> {
                    CircularProgressIndicator(modifier = Modifier.align(Alignment.Center))
                }
                is UiState.Error -> {
                    val error = (state as UiState.Error).message
                    Text(text = "Error: $error", color = MaterialTheme.colorScheme.error, modifier = Modifier.align(Alignment.Center))
                }
                is UiState.Success -> {
                    val digimon = (state as UiState.Success).data

                    Column(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(16.dp),
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        if (!digimon.images.isNullOrEmpty()) {
                            AsyncImage(
                                model = digimon.images[0].href,
                                contentDescription = digimon.name,
                                modifier = Modifier.size(150.dp)
                            )
                            Spacer(modifier = Modifier.height(16.dp))
                        }

                        Text(text = "Nama: ${digimon.name}", style = MaterialTheme.typography.headlineMedium)
                        Spacer(modifier = Modifier.height(8.dp))

                        val level = digimon.levels?.firstOrNull()?.level ?: "Unknown"
                        Text(text = "Level: $level", style = MaterialTheme.typography.bodyLarge)

                        val attribute = digimon.attributes?.firstOrNull()?.attribute ?: "Unknown"
                        Text(text = "Attribute: $attribute", style = MaterialTheme.typography.bodyLarge)

                        val type = digimon.types?.firstOrNull()?.type ?: "Unknown"
                        Text(text = "Type: $type", style = MaterialTheme.typography.bodyLarge)
                    }
                }
            }
        }
    }
}