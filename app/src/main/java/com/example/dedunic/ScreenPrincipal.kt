package com.example.dedunic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.dedunic.ui.theme.DeduNicTheme

class ScreenPrincipal : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            DeduNicTheme {
                Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
                    MainScreen()
                }
            }
        }
    }
}

@Composable
fun MainScreen() {
    val navController = rememberNavController()
    NavHost(navController, startDestination = "home") {
        composable("home") { HomeScreen(navController) }
        composable("liquidacion") { LiquidacionScreen() }
        composable("quincenas") { QuincenaScreen() }
    }
}

@Composable
fun HomeScreen(navController: NavHostController) {
    var isLoggedIn by remember { mutableStateOf(false) }
    if (!isLoggedIn) {
        LoginScreen(onLoginSuccess = { isLoggedIn = it })
    } else {
        ScreenWithButtons(navController)
    }
}

@Composable
fun ScreenWithButtons(navController: NavHostController) {
    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Button(
            onClick = { navController.navigate("liquidacion") },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Calculate Liquidacion")
        }
        Button(
            onClick = { navController.navigate("quincenas") },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Calculate Quincenas")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LoginScreen(onLoginSuccess: (Boolean) -> Unit) {
    var username by remember { mutableStateOf("") }
    var password by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.padding(16.dp)
        )
        TextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            modifier = Modifier.padding(16.dp)
        )

        Button(
            onClick = {
                if (username == "admin" && password == "admin") {
                    onLoginSuccess(true)
                }
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Login")
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun LiquidacionScreen() {
    var salarioMensual by remember { mutableStateOf("") }
    var vacaciones by remember { mutableStateOf("") }
    var fechaIngreso by remember { mutableStateOf("") }
    var fechaFinalizacion by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = salarioMensual,
            onValueChange = { salarioMensual = it },
            label = { Text("Salario Mensual") },
            modifier = Modifier.padding(16.dp)
        )
        TextField(
            value = vacaciones,
            onValueChange = { vacaciones = it },
            label = { Text("Vacaciones") },
            modifier = Modifier.padding(16.dp)
        )
        TextField(
            value = fechaIngreso,
            onValueChange = { fechaIngreso = it },
            label = { Text("Fecha de Ingreso") },
            modifier = Modifier.padding(16.dp)
        )
        TextField(
            value = fechaFinalizacion,
            onValueChange = { fechaFinalizacion = it },
            label = { Text("Fecha de Finalización") },
            modifier = Modifier.padding(16.dp)
        )

        Button(
            onClick = {
                // Perform liquidacion calculation using the provided inputs
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Calcular")
        }
    }
}


@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun QuincenaScreen() {
    var salarioMensual by remember { mutableStateOf("") }
    var horaExtra by remember { mutableStateOf("") }
    var vacaciones by remember { mutableStateOf("") }
    var viatico by remember { mutableStateOf("") }

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        TextField(
            value = salarioMensual,
            onValueChange = { salarioMensual = it },
            label = { Text("Salario Mensual") },
            modifier = Modifier.padding(16.dp)
        )
        TextField(
            value = horaExtra,
            onValueChange = { horaExtra = it },
            label = { Text("Hora Extra") },
            modifier = Modifier.padding(16.dp)
        )
        TextField(
            value = vacaciones,
            onValueChange = { vacaciones = it },
            label = { Text("Vacaciones") },
            modifier = Modifier.padding(16.dp)
        )
        TextField(
            value = viatico,
            onValueChange = { viatico = it },
            label = { Text("Viatico") },
            modifier = Modifier.padding(16.dp)
        )

        Button(
            onClick = {
                // Perform quincena calculation using the provided inputs
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Calcular")
        }
    }
}