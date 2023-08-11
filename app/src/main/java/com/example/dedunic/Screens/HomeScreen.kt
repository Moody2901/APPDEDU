package com.example.dedunic.Screens



import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.dedunic.R
import com.example.dedunic.ui.theme.DeduNicTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HomeScreen(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Top, // Cambiado a Arrangement.Top
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TopAppBar(
            title = { Text(text = "DeduNic App") },
            modifier = Modifier
                .fillMaxWidth() // Ocupar todo el ancho disponible
                .background(Color.Blue) // Agregar color de fondo al TopAppBar
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

        // Estilo personalizado para los botones
        val buttonModifier = Modifier
            .padding(16.dp)
            .fillMaxWidth()

        Button(
            onClick = { navController.navigate("liquidacion") },
            modifier = buttonModifier,
            colors = ButtonDefaults.buttonColors(Color(0xFF5F82E8))
        ) {
            Text("Calcular Liquidación", color = Color.White)
        }

        Button(
            onClick = { navController.navigate("quincenas") },
            modifier = buttonModifier,
            colors = ButtonDefaults.buttonColors(Color(0xFFE85F5F))
        ) {
            Text("Calcular Quincenas", color = Color.White)
        }
    }
}


@Preview(showBackground = true)
@Composable
fun HomeScreenPreview() {
    val navController = rememberNavController()
    DeduNicTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            HomeScreen(navController)
        }
    }
}

