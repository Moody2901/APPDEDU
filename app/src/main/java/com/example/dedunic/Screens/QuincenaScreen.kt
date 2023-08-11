package com.example.dedunic.Screens

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.dedunic.ui.theme.DeduNicTheme

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuincenaScreen(navController: NavHostController) {
    var salarioMensual by remember { mutableStateOf("") }
    var horaExtra by remember { mutableStateOf("") }
    var vacaciones by remember { mutableStateOf("") }
    var viatico by remember { mutableStateOf("") }
    var calculoRealizado by remember { mutableStateOf(false) }
    var resultadoCalculo by remember { mutableStateOf("") }

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
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )
        OutlinedTextField(
            value = horaExtra,
            onValueChange = { horaExtra = it },
            label = { Text("Hora Extra") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        OutlinedTextField(
            value = vacaciones,
            onValueChange = { vacaciones = it },
            label = { Text("Vacaciones") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )
        OutlinedTextField(
            value = viatico,
            onValueChange = { viatico = it },
            label = { Text("Viatico") },
            keyboardOptions = KeyboardOptions.Default.copy(
                keyboardType = KeyboardType.Number
            ),
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        Button(
            onClick = {
                // Realizar el cálculo de impuestos
                val resultado = calcularImpuestos(
                    salarioMensual.toDoubleOrNull() ?: 0.0,
                    vacaciones.toDoubleOrNull() ?: 0.0,
                    horaExtra.toDoubleOrNull() ?: 0.0,
                    viatico.toDoubleOrNull() ?: 0.0
                )
                resultadoCalculo = resultado
                calculoRealizado = true
            },
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = 16.dp)
                .height(48.dp),
            colors = ButtonDefaults.buttonColors(Color(0xFF9E6DF7)),
            shape = RoundedCornerShape(8.dp)
        ) {
            Text("Calcular", color = Color.White)
        }

        if (calculoRealizado) {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(top = 18.dp),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(
                    text = "Resultado del cálculo:",
                    style = TextStyle(
                        fontSize = 26.sp,
                        fontWeight = FontWeight.Bold,
                        color = MaterialTheme.colorScheme.primary
                    ),
                    textAlign = TextAlign.Center,
                    modifier = Modifier.padding(bottom = 8.dp)
                )
                Text(
                    text = resultadoCalculo,
                    style = TextStyle(fontSize = 20.sp),
                    textAlign = TextAlign.Center
                )
            }
        }

        FloatingActionButton(
            onClick = {
                navController.popBackStack() // Retroceder en la navegación
            },
            modifier = Modifier
                .align(Alignment.End)
                .padding(16.dp)
        ) {
            Icon(imageVector = Icons.Default.ArrowBack, contentDescription = "Back")
        }
    }
}

private fun calcularImpuestos(
    salarioMensual: Double,
    vacaciones: Double,
    extraH: Double,
    viatico: Double
): String {
    val quincena = salarioMensual / 2
    val totalVaca = (salarioMensual / 30) * vacaciones
    val totalExtraH = ((salarioMensual / 30) / 8) * 2 * extraH
    val devengado = quincena + totalVaca + totalExtraH
    val totalAnual = devengado * 24
    val anualDEDU = totalAnual * 0.07
    val inss = anualDEDU / 24
    val irAnual = totalAnual - anualDEDU

    val deduccionIR = when {
        irAnual < 100000 -> 0.0
        irAnual in 100000.0..200000.0 -> (((irAnual - 100000) * 0.15) / 24)
        irAnual in 200000.0..350000.0 -> ((((irAnual - 200000) * 0.2) + 15000) / 24)
        irAnual in 350000.0..500000.0 -> ((((irAnual - 350000) * 0.25) + 45000) / 24)
        else -> 0.0
    }

    val neto = devengado - inss - deduccionIR + viatico

    return buildString {
        append("El total devengado es: $devengado córdobas\n")
        append("La Deducción del INSS es: $inss córdobas\n")
        append("La deducción del IR es: $deduccionIR córdobas\n")
        append("El neto a recibir es: $neto córdobas")
    }
}


@Preview(showBackground = true)
@Composable
fun QuincenaScreenPreview() {
    val navController = rememberNavController()
    DeduNicTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            QuincenaScreen(navController)
        }
    }
}