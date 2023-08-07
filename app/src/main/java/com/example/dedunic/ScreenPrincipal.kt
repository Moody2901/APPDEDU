package com.example.dedunic

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
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
        modifier = Modifier
            .fillMaxSize()
            .padding(top = 16.dp, start = 16.dp, end = 16.dp), // Ajustamos los márgenes superior, izquierdo y derecho
        verticalArrangement = Arrangement.Top, // Alineamos los elementos en la parte superior
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.padding(bottom = 16.dp) // Agregamos un margen inferior al logo
        ) {
            Image(
                painter = painterResource(id = R.drawable.login_logo),
                contentDescription = "Logo",
                modifier = Modifier
                    .size(200.dp)
                    .padding(top = 8.dp), // Ajustamos el margen superior del logo
                alignment = Alignment.Center
            )
        }
        Text(
            text = "Iniciar Sesión",
            style = TextStyle(
                fontSize = 24.sp,
                fontWeight = FontWeight.Bold
            ),
            textAlign = TextAlign.Center,
            modifier = Modifier.padding(top = 8.dp)
        )

        OutlinedTextField(
            value = username,
            onValueChange = { username = it },
            label = { Text("Username") },
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )
        OutlinedTextField(
            value = password,
            onValueChange = { password = it },
            label = { Text("Password") },
            visualTransformation = PasswordVisualTransformation(),
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        Button(
            onClick = {
                if (username == "admin" && password == "admin") {
                    onLoginSuccess(true)
                } else {
                    onLoginSuccess(false)
                }
            },
            modifier = Modifier.padding(16.dp)
        ) {
            Text("Login", color = Color.White)
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
                // Perform liquidacion calculation using the provided inputs
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
                // Perform quincena calculation using the provided inputs
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
    }
}

@Preview(showBackground = true)
@Composable
fun LoginScreenPreview() {
    DeduNicTheme {
        Surface(modifier = Modifier.fillMaxSize(), color = MaterialTheme.colorScheme.background) {
            LoginScreen(onLoginSuccess = {})
        }
    }
}