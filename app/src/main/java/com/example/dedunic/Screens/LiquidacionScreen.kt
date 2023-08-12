package com.example.dedunic.Screens

import android.annotation.SuppressLint
import android.icu.text.SimpleDateFormat
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.example.dedunic.ui.theme.DeduNicTheme
import java.time.LocalDate
import java.time.format.DateTimeFormatter
import java.time.temporal.ChronoUnit
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.concurrent.TimeUnit


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LiquidacionScreen(navController: NavHostController) {
    var salarioMensual by remember { mutableStateOf("") }
    var vacaciones by remember { mutableStateOf("") }
    var fechaIngreso by remember { mutableStateOf("") }
    var fechaFinalizacion by remember { mutableStateOf("") }
    var resultadoLiquidacion by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(
            text = "Liquidación",
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
            value = vacaciones,
            onValueChange = { vacaciones = it },
            label = { Text("Vacaciones") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )
        OutlinedTextField(
            value = fechaIngreso,
            onValueChange = { fechaIngreso = it },
            label = { Text("Fecha de Ingreso") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )
        OutlinedTextField(
            value = fechaFinalizacion,
            onValueChange = { fechaFinalizacion = it },
            label = { Text("Fecha de Finalización") },
            modifier = Modifier
                .fillMaxWidth()
                .padding(vertical = 8.dp)
        )

        Button(
            onClick = {
                // Realizar cálculos de liquidación utilizando los datos proporcionados
                val diasDiferencia = calcularDiferenciaEnDias(fechaIngreso, fechaFinalizacion)
                val costo = calcularCosto(diasDiferencia)
                val antiguedad = calcularAntiguedad(costo)
                val salarioDiario = salarioMensual.toDoubleOrNull() ?: (0.0 / 30)
                val proporcional = calcularProporcional(salarioDiario, costo)
                val aguinaldoPro = calcularAguinaldoPro(fechaFinalizacion)
                val aguinaldoProporcional = calcularAguinaldoProporcional(salarioDiario, aguinaldoPro)
                val diasEnQuincena = calcularDiasEnQuincena(
                    SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(fechaFinalizacion) ?: Date()
                )
                val primeraQuincena = esPrimeraQuincena(
                    SimpleDateFormat("dd/MM/yyyy", Locale.getDefault()).parse(fechaFinalizacion) ?: Date()
                )

                val totalVacaciones =
                    (salarioMensual.toDoubleOrNull() ?: (0.0 / 30)) * (vacaciones.toDoubleOrNull()
                        ?: 0.0)
                val deduccionesInssVacaciones = totalVacaciones * 0.07
                val deduccionesIrVacaciones = totalVacaciones * 0.14

                val devengadoTotal = antiguedad + proporcional + aguinaldoProporcional
                val deduccionesInssProporcional = proporcional * 0.07
                val deduccionesIrProporcional = proporcional * 0.14

                val totalDeducciones =
                    deduccionesInssVacaciones + deduccionesIrVacaciones + deduccionesInssProporcional + deduccionesIrProporcional
                val netoRecibir = devengadoTotal - totalDeducciones

                resultadoLiquidacion = """
                    Antigüedad Laboral: $antiguedad
                    Salario Proporcional: $proporcional
                    Aguinaldo Proporcional: $aguinaldoProporcional
                    Días en Quincena: $diasEnQuincena
                    Primera Quincena: $primeraQuincena
                    Total Vacaciones: $totalVacaciones
                    Deducciones INSS Vacaciones: $deduccionesInssVacaciones
                    Deducciones IR Vacaciones: $deduccionesIrVacaciones
                    Devengado Total: $devengadoTotal
                    Deducciones INSS Proporcional: $deduccionesInssProporcional
                    Deducciones IR Proporcional: $deduccionesIrProporcional
                    Total Deducciones: $totalDeducciones
                    Neto a Recibir: $netoRecibir
                """.trimIndent()
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

        Spacer(modifier = Modifier.height(16.dp))

        Text(
            text = resultadoLiquidacion,
            modifier = Modifier.fillMaxWidth()
        )

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

@Preview(showBackground = true)
@Composable
fun LiquidacionScreenPreview() {
    val navController = rememberNavController()
    DeduNicTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            LiquidacionScreen(navController)
        }
    }
}


@SuppressLint("SimpleDateFormat")
fun calcularDiferenciaEnDias(fechaInicio: String, fechaFin: String): Long {
    val formato = SimpleDateFormat("dd/MM/yyyy")
    val fechaInicial: Date = formato.parse(fechaInicio)
    val fechaFinal: Date = formato.parse(fechaFin)

    val diferenciaMillis: Long = fechaFinal.time - fechaInicial.time

    return TimeUnit.DAYS.convert(diferenciaMillis, TimeUnit.MILLISECONDS)
}

fun calcularCosto(diasDiferencia: Long): Double {
    val diasPorAno = 365L
    val costoPrimerosTresAnos = 30.0
    val costoDespuesTresAnos = 20.0

    val anosCompletos = diasDiferencia / diasPorAno
    val diasRestantes = diasDiferencia % diasPorAno

    var costoTotal: Double

    if (anosCompletos < 3) {
        costoTotal = anosCompletos * costoPrimerosTresAnos
    } else {
        costoTotal = 3 * costoPrimerosTresAnos

        val anosDespuesTres = anosCompletos - 3
        costoTotal += anosDespuesTres * costoDespuesTresAnos

        // Calcular costo proporcional para el último año incompleto
        if (diasRestantes > 0) {
            val proporcionDias = diasRestantes.toDouble() / diasPorAno
            val costoProporcional = proporcionDias * costoDespuesTresAnos
            costoTotal += costoProporcional
        }
    }

    return costoTotal
}

fun calcularAntiguedad(costo: Double): Double {
    val limiteCosto = 90.0
    val costoPrimerosTresAnos = 30.0
    val costoDespuesTresAnos = 20.0

    return when {
        costo <= limiteCosto -> costo / 30 * costoPrimerosTresAnos
        else -> ((costo - limiteCosto) / 20 * costoDespuesTresAnos) + (costoPrimerosTresAnos * 3)
    }
}

fun calcularProporcional(salarioDiario: Double, costo: Double): Double {
    return salarioDiario * costo
}

fun calcularAguinaldoPro(fechaFinStr: String): Double {
    val formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy")
    val fechaFin = LocalDate.parse(fechaFinStr, formatter)
    val fechaInicio = LocalDate.of(fechaFin.year - 1, 11, 30)
    val mesesTotales = ChronoUnit.MONTHS.between(fechaInicio, fechaFin)
    val diasEnMesFin = fechaFin.dayOfMonth
    val fraccionDias = diasEnMesFin.toDouble() / fechaFin.lengthOfMonth()
    return mesesTotales + fraccionDias
}

fun calcularAguinaldoProporcional(salarioDiario: Double, aguinaldoPro: Double): Double {
    return salarioDiario * aguinaldoPro
}

fun calcularDiasEnQuincena(fecha: Date): Int {
    val calendar = Calendar.getInstance()
    calendar.time = fecha
    val diaDelMes = calendar.get(Calendar.DAY_OF_MONTH)
    return if (diaDelMes in 1..15) {
        diaDelMes
    } else {
        diaDelMes - 15
    }
}

fun esPrimeraQuincena(fecha: Date): Boolean {
    val calendar = Calendar.getInstance()
    calendar.time = fecha
    val diaDelMes = calendar.get(Calendar.DAY_OF_MONTH)
    return diaDelMes in 1..15
}