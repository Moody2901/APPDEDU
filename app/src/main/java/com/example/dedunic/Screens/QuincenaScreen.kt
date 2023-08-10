package com.example.dedunic.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuincenaScreen(navController: NavHostController) {
    var salarioMensual by remember { mutableStateOf("") }
    var horaExtra by remember { mutableStateOf("") }
    var vacaciones by remember { mutableStateOf("") }
    var viatico by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Quincenas",
            style = TextStyle(fontSize = 24.sp, fontWeight = FontWeight.Bold),
            modifier = Modifier.padding(vertical = 16.dp)
        )

        OutlinedTextField(
            value = salarioMensual,
            onValueChange = { salarioMensual = it },
            label = { Text("Salario Mensual") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )
        OutlinedTextField(
            value = horaExtra,
            onValueChange = { horaExtra = it },
            label = { Text("Hora Extra") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )
        OutlinedTextField(
            value = vacaciones,
            onValueChange = { vacaciones = it },
            label = { Text("Vacaciones") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )
        OutlinedTextField(
            value = viatico,
            onValueChange = { viatico = it },
            label = { Text("Viatico") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        Button(
            onClick = {
                // Realizar cálculo de quincena utilizando los datos proporcionados
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .height(48.dp),
            colors = ButtonDefaults.run { val buttonColors =
                buttonColors(Color.Blue)
                buttonColors
            },
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Calcular", color = Color.White)
        }
        FloatingActionButton(
            onClick = {
                navController.popBackStack() // Retroceder en la navegación
            },
            modifier = Modifier
                .align(Alignment.End) // Alinear en la esquina inferior derecha
                .padding(16.dp)
        ) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
        }
    }
}
