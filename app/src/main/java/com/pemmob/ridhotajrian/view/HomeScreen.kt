package com.pemmob.ridhotajrian.view

import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import coil.compose.AsyncImage
import com.pemmob.ridhotajrian.model.DigimonItem
import com.pemmob.ridhotajrian.viewmodel.DigimonViewModel
import com.pemmob.ridhotajrian.viewmodel.UiState

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(
    viewModel: DigimonViewModel,
    onDigimonClick: (String) -> Unit
) {
    val state by viewModel.homeState.collectAsState()

    // Scaffold digunakan untuk memasang TopAppBar (Baris judul di paling atas)
    Scaffold(
        topBar = {
            TopAppBar(
                title = { Text("Digimon Blater", fontWeight = FontWeight.Bold) },
                colors = TopAppBarDefaults.topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer
                )
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
                    // Tampilan Loading diubah menjadi seperti Splash Screen
                    Column(
                        modifier = Modifier.fillMaxSize(),
                        verticalArrangement = Arrangement.Center,
                        horizontalAlignment = Alignment.CenterHorizontally
                    ) {
                        Text(
                            text = "Digimon Blater",
                            fontSize = 32.sp,
                            fontWeight = FontWeight.Bold,
                            color = MaterialTheme.colorScheme.primary
                        )
                        Spacer(modifier = Modifier.height(16.dp))
                        CircularProgressIndicator() // Ikon loading muter
                    }
                }
                is UiState.Error -> {
                    val errorMessage = (state as UiState.Error).message
                    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
                        Text(text = "Error: $errorMessage", color = MaterialTheme.colorScheme.error)
                    }
                }
                is UiState.Success -> {
                    val digimonList = (state as UiState.Success).data
                    LazyColumn(
                        modifier = Modifier.fillMaxSize(),
                        contentPadding = PaddingValues(16.dp),
                        verticalArrangement = Arrangement.spacedBy(8.dp)
                    ) {
                        items(digimonList) { digimon ->
                            DigimonCard(digimon = digimon, onClick = { onDigimonClick(digimon.name) })
                        }
                    }
                }
            }
        }
    }
}

@Composable
fun DigimonCard(digimon: DigimonItem, onClick: () -> Unit) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .clickable { onClick() },
        elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            if (digimon.image != null) {
                AsyncImage(
                    model = digimon.image,
                    contentDescription = digimon.name,
                    modifier = Modifier.size(64.dp)
                )
                Spacer(modifier = Modifier.width(16.dp))
            }
            Text(
                text = digimon.name,
                style = MaterialTheme.typography.titleLarge
            )
        }
    }
}