package com.example.dedunic.Screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import com.example.dedunic.R

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopAppBar(
            title = { Text(text = "DeduNic App") },
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Logo de la aplicación
        Image(
            painter = painterResource(id = R.drawable.login_logo),
            contentDescription = "Logo de la aplicación",
            modifier = Modifier.size(200.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Descripción de la aplicación
        Text(
            text = "Bienvenido a la aplicación DeduNic. Esta aplicación te permite realizar cálculos de liquidación y quincenas de forma sencilla.",
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(horizontal = 16.dp)
        )

        Spacer(modifier = Modifier.height(16.dp))

        // Botones para acceder a las pantallas de Liquidación y Quincenas
        Button(
            onClick = { navController.navigate("liquidacion") },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Calcular Liquidación")
        }
        Button(
            onClick = { navController.navigate("quincenas") },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Calcular Quincenas")
        }
    }
}
