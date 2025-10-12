package com.joel.lab05

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            Lab05Theme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppNavigation()
                }
            }
        }
    }
}

@Composable
fun Lab05Theme(content: @Composable () -> Unit) {
    MaterialTheme(
        colorScheme = lightColorScheme(
            primary = Color(0xFF2E7D32),      // Verde ambiental
            secondary = Color(0xFF1976D2),    // Azul cielo
            background = Color(0xFFE8F5E8),   // Verde muy claro de fondo
            surface = Color(0xFFFFFFFF),      // Blanco para superficies
            onPrimary = Color.White,
            onSecondary = Color.White,
            onBackground = Color(0xFF1B5E20), // Verde oscuro para texto
            onSurface = Color(0xFF1B5E20)
        ),
        content = content
    )
}

@Composable
fun AppNavigation() {
    var currentScreen by remember { mutableStateOf("welcome") }

    when (currentScreen) {
        "welcome" -> WelcomeScreen { currentScreen = "form" }
        "form" -> AirQualityForm()
    }
}

@Composable
fun WelcomeScreen(onNavigateToForm: () -> Unit) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF4CAF50),    // Verde claro
                        Color(0xFF2E7D32),    // Verde medio
                        Color(0xFF1B5E20)     // Verde oscuro
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            // Icono o imagen relacionada al aire (puedes agregar una imagen después)
            Image(
                painter = painterResource(id = android.R.drawable.ic_menu_compass), // Icono temporal
                contentDescription = "Icono de calidad del aire",
                modifier = Modifier.size(120.dp)
            )

            // Título de bienvenida
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Bienvenido a",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White,
                    fontSize = 24.sp,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "AirQuality AQP",
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color.White,
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }

            // Descripción
            Text(
                text = "Monitoreo de la calidad del aire\nen Arequipa",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White,
                fontSize = 18.sp,
                textAlign = TextAlign.Center,
                lineHeight = 24.sp
            )

            // Botón para continuar
            Button(
                onClick = onNavigateToForm,
                modifier = Modifier
                    .width(200.dp)
                    .height(50.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color(0xFF2E7D32)
                )
            ) {
                Text(
                    text = "Comenzar",
                    fontSize = 18.sp,
                    fontWeight = FontWeight.Bold
                )
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AirQualityForm() {
    var location by remember { mutableStateOf("") }
    var pm25 by remember { mutableStateOf("") }
    var pm10 by remember { mutableStateOf("") }
    var temperature by remember { mutableStateOf("") }
    var humidity by remember { mutableStateOf("") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .background(Color(0xFFE8F5E8))
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        // Header con título y botón de volver
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "AirQuality AQP",
                style = MaterialTheme.typography.titleLarge,
                color = Color(0xFF1B5E20),
                fontWeight = FontWeight.Bold
            )
        }

        // Tarjeta principal del formulario
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(containerColor = Color.White)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Text(
                    text = "Registro de Calidad del Aire",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color(0xFF1B5E20),
                    modifier = Modifier.fillMaxWidth()
                )

                // Ubicación
                OutlinedTextField(
                    value = location,
                    onValueChange = { location = it },
                    label = { Text("Ubicación en AQP") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = Color(0xFF2E7D32),
                        focusedLabelColor = Color(0xFF2E7D32)
                    )
                )

                // PM2.5 y PM10
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedTextField(
                        value = pm25,
                        onValueChange = { pm25 = it },
                        label = { Text("PM2.5 (μg/m³)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color(0xFF2E7D32),
                            focusedLabelColor = Color(0xFF2E7D32)
                        )
                    )
                    OutlinedTextField(
                        value = pm10,
                        onValueChange = { pm10 = it },
                        label = { Text("PM10 (μg/m³)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color(0xFF2E7D32),
                            focusedLabelColor = Color(0xFF2E7D32)
                        )
                    )
                }

                // Temperatura y Humedad
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    OutlinedTextField(
                        value = temperature,
                        onValueChange = { temperature = it },
                        label = { Text("Temperatura (°C)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color(0xFF2E7D32),
                            focusedLabelColor = Color(0xFF2E7D32)
                        )
                    )
                    OutlinedTextField(
                        value = humidity,
                        onValueChange = { humidity = it },
                        label = { Text("Humedad (%)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = Color(0xFF2E7D32),
                            focusedLabelColor = Color(0xFF2E7D32)
                        )
                    )
                }

                // Botones
                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = {
                            // Acción para guardar datos
                            saveAirQualityData(location, pm25, pm10, temperature, humidity)
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF2E7D32)
                        )
                    ) {
                        Text("Guardar Datos")
                    }
                    Button(
                        onClick = {
                            location = ""
                            pm25 = ""
                            pm10 = ""
                            temperature = ""
                            humidity = ""
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF1976D2)
                        )
                    ) {
                        Text("Limpiar")
                    }
                }
            }
        }

        // Información adicional
        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD))
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "📊 Indicadores de Calidad del Aire:",
                    style = MaterialTheme.typography.labelLarge,
                    color = Color(0xFF1B5E20),
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text("• PM2.5: < 12 μg/m³ (Óptimo)")
                Text("• PM10: < 54 μg/m³ (Óptimo)")
                Text("• Temperatura: 15-25°C (Confortable)")
                Text("• Humedad: 40-60% (Ideal)")
            }
        }
    }
}

// Función para guardar datos (simulada)
fun saveAirQualityData(
    location: String,
    pm25: String,
    pm10: String,
    temperature: String,
    humidity: String
) {
    // Aquí implementarías la lógica para guardar en base de datos
    println("Datos guardados: $location, PM2.5: $pm25, PM10: $pm10")
}