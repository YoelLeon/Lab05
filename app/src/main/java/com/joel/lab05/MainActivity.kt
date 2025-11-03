package com.joel.lab05

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.viewModels
import androidx.compose.animation.core.animateDpAsState
import androidx.compose.animation.core.tween
import androidx.compose.foundation.Canvas
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
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
import androidx.compose.material3.MaterialTheme
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import androidx.lifecycle.viewModelScope
import kotlinx.coroutines.launch

// ESTRUCTURA DE DATOS PARA LAB 07 - LISTA DINÁMICA
data class AirQualityRecord(
    val id: Int,
    val location: String,
    val pm25: Double,
    val pm10: Double,
    val date: String,
    val qualityLevel: String
)

// FUNCIÓN PARA GENERAR DATOS DE EJEMPLO
fun generateSampleData(): List<AirQualityRecord> {
    return listOf(
        AirQualityRecord(1, "Cercado AQP", 8.5, 32.0, "2024-01-15", "Buena"),
        AirQualityRecord(2, "Yanahuara", 15.2, 48.0, "2024-01-15", "Regular"),
        AirQualityRecord(3, "Cayma", 25.7, 68.0, "2024-01-14", "Mala"),
        AirQualityRecord(4, "Sachaca", 6.8, 28.0, "2024-01-14", "Buena"),
        AirQualityRecord(5, "Hunter", 18.9, 55.0, "2024-01-13", "Regular"),
        AirQualityRecord(6, "Mariano Melgar", 12.3, 42.0, "2024-01-13", "Buena"),
        AirQualityRecord(7, "Miraflores", 22.1, 62.0, "2024-01-12", "Mala"),
        AirQualityRecord(8, "Paucarpata", 9.7, 35.0, "2024-01-12", "Buena"),
        AirQualityRecord(9, "Cerro Colorado", 16.8, 51.0, "2024-01-11", "Regular"),
        AirQualityRecord(10, "Alto Selva Alegre", 28.4, 75.0, "2024-01-11", "Mala"),
        AirQualityRecord(11, "Jacobo Hunter", 11.5, 38.0, "2024-01-10", "Buena"),
        AirQualityRecord(12, "José Luis B. y R.", 19.3, 57.0, "2024-01-10", "Regular"),
        AirQualityRecord(13, "Socabaya", 7.2, 30.0, "2024-01-09", "Buena"),
        AirQualityRecord(14, "Umacollo", 14.6, 45.0, "2024-01-09", "Regular"),
        AirQualityRecord(15, "Tingo", 26.8, 70.0, "2024-01-08", "Mala"),
        AirQualityRecord(16, "Yura", 5.9, 25.0, "2024-01-08", "Buena"),
        AirQualityRecord(17, "La Tomilla", 17.5, 53.0, "2024-01-07", "Regular"),
        AirQualityRecord(18, "San Lázaro", 23.2, 65.0, "2024-01-07", "Mala"),
        AirQualityRecord(19, "Vallecito", 10.1, 36.0, "2024-01-06", "Buena"),
        AirQualityRecord(20, "Barrio Obrero", 20.7, 59.0, "2024-01-06", "Regular"),
        AirQualityRecord(21, "Cono Norte", 29.5, 78.0, "2024-01-05", "Mala"),
        AirQualityRecord(22, "Centro Histórico", 13.4, 44.0, "2024-01-05", "Regular"),
        AirQualityRecord(23, "Zona Industrial", 31.2, 82.0, "2024-01-04", "Mala"),
        AirQualityRecord(24, "Residencial", 8.9, 33.0, "2024-01-04", "Buena")
    )
}

class MainActivity : ComponentActivity() {
    private val themeViewModel: ThemeViewModel by viewModels {
        ThemeViewModelFactory(ThemeManager(this))
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            AppNavigation(themeViewModel = themeViewModel)
        }
    }
}

@Composable
fun Lab05Theme(
    darkTheme: Boolean = false,
    content: @Composable () -> Unit
) {
    val colorScheme = if (darkTheme) {
        darkColorScheme(
            primary = Color(0xFF4CAF50),
            secondary = Color(0xFF2196F3),
            background = Color(0xFF121212),
            surface = Color(0xFF1E1E1E),
            onPrimary = Color.White,
            onSecondary = Color.White,
            onBackground = Color(0xFFE0E0E0),
            onSurface = Color(0xFFE0E0E0)
        )
    } else {
        lightColorScheme(
            primary = Color(0xFF2E7D32),
            secondary = Color(0xFF1976D2),
            background = Color(0xFFE8F5E8),
            surface = Color(0xFFFFFFFF),
            onPrimary = Color.White,
            onSecondary = Color.White,
            onBackground = Color(0xFF1B5E20),
            onSurface = Color(0xFF1B5E20)
        )
    }

    MaterialTheme(
        colorScheme = colorScheme,
        content = content
    )
}

@Composable
fun AppNavigation(themeViewModel: ThemeViewModel) {
    var currentScreen by remember { mutableStateOf("welcome") }
    val isDarkTheme by themeViewModel.isDarkTheme.collectAsState(initial = false)

    Lab05Theme(darkTheme = isDarkTheme) {
        Surface(
            modifier = Modifier.fillMaxSize(),
            color = MaterialTheme.colorScheme.background
        ) {
            when (currentScreen) {
                "welcome" -> WelcomeScreen(
                    onNavigateToForm = { currentScreen = "form" },
                    onNavigateToAnimation = { currentScreen = "animation" },
                    onNavigateToList = { currentScreen = "list" },
                    onNavigateToSettings = { currentScreen = "theme_settings" }
                )
                "form" -> AirQualityForm()
                "animation" -> AnimationLab()
                "list" -> AirQualityList()
                "theme_settings" -> ThemeSettingsScreen(
                    themeViewModel = themeViewModel,
                    onBack = { currentScreen = "welcome" }
                )
            }
        }
    }
}

@Composable
fun WelcomeScreen(
    onNavigateToForm: () -> Unit,
    onNavigateToAnimation: () -> Unit,
    onNavigateToList: () -> Unit,
    onNavigateToSettings: () -> Unit
) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(
                brush = Brush.verticalGradient(
                    colors = listOf(
                        Color(0xFF4CAF50),
                        Color(0xFF2E7D32),
                        Color(0xFF1B5E20)
                    )
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(16.dp)
        ) {
            Image(
                painter = painterResource(id = android.R.drawable.ic_menu_compass),
                contentDescription = "Icono de calidad del aire",
                modifier = Modifier.size(100.dp)
            )

            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                Text(
                    text = "Bienvenido a",
                    style = MaterialTheme.typography.headlineMedium,
                    color = Color.White,
                    fontSize = 22.sp,
                    textAlign = TextAlign.Center
                )
                Text(
                    text = "AirQuality AQP",
                    style = MaterialTheme.typography.headlineLarge,
                    color = Color.White,
                    fontSize = 28.sp,
                    fontWeight = FontWeight.Bold,
                    textAlign = TextAlign.Center
                )
            }

            Text(
                text = "Monitoreo de la calidad del aire\nen Arequipa",
                style = MaterialTheme.typography.bodyLarge,
                color = Color.White,
                fontSize = 16.sp,
                textAlign = TextAlign.Center,
                lineHeight = 20.sp
            )

            Button(
                onClick = onNavigateToForm,
                modifier = Modifier
                    .width(200.dp)
                    .height(45.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color.White,
                    contentColor = Color(0xFF2E7D32)
                )
            ) {
                Text(
                    text = "Nueva Medición",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Button(
                onClick = onNavigateToAnimation,
                modifier = Modifier
                    .width(200.dp)
                    .height(45.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFFFF9800),
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Indicador Animado",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Button(
                onClick = onNavigateToList,
                modifier = Modifier
                    .width(200.dp)
                    .height(45.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF2196F3),
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Ver Historial",
                    fontSize = 16.sp,
                    fontWeight = FontWeight.Bold
                )
            }

            Button(
                onClick = onNavigateToSettings,
                modifier = Modifier
                    .width(200.dp)
                    .height(45.dp),
                colors = ButtonDefaults.buttonColors(
                    containerColor = Color(0xFF9C27B0),
                    contentColor = Color.White
                )
            ) {
                Text(
                    text = "Configuración",
                    fontSize = 16.sp,
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
            .background(MaterialTheme.colorScheme.background)
            .padding(16.dp),
        verticalArrangement = Arrangement.spacedBy(20.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Text(
                text = "AirQuality AQP",
                style = MaterialTheme.typography.titleLarge,
                color = MaterialTheme.colorScheme.onBackground,
                fontWeight = FontWeight.Bold
            )
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 8.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surface)
        ) {
            Column(
                modifier = Modifier.padding(20.dp),
                verticalArrangement = Arrangement.spacedBy(20.dp)
            ) {
                Text(
                    text = "Registro de Calidad del Aire",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onSurface,
                    modifier = Modifier.fillMaxWidth()
                )

                OutlinedTextField(
                    value = location,
                    onValueChange = { location = it },
                    label = { Text("Ubicación en AQP") },
                    modifier = Modifier.fillMaxWidth(),
                    colors = TextFieldDefaults.outlinedTextFieldColors(
                        focusedBorderColor = MaterialTheme.colorScheme.primary,
                        focusedLabelColor = MaterialTheme.colorScheme.primary
                    )
                )

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
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            focusedLabelColor = MaterialTheme.colorScheme.primary
                        )
                    )
                    OutlinedTextField(
                        value = pm10,
                        onValueChange = { pm10 = it },
                        label = { Text("PM10 (μg/m³)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            focusedLabelColor = MaterialTheme.colorScheme.primary
                        )
                    )
                }

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
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            focusedLabelColor = MaterialTheme.colorScheme.primary
                        )
                    )
                    OutlinedTextField(
                        value = humidity,
                        onValueChange = { humidity = it },
                        label = { Text("Humedad (%)") },
                        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Number),
                        modifier = Modifier.weight(1f),
                        colors = TextFieldDefaults.outlinedTextFieldColors(
                            focusedBorderColor = MaterialTheme.colorScheme.primary,
                            focusedLabelColor = MaterialTheme.colorScheme.primary
                        )
                    )
                }

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = {
                            saveAirQualityData(location, pm25, pm10, temperature, humidity)
                        },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MaterialTheme.colorScheme.primary
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
                            containerColor = MaterialTheme.colorScheme.secondary
                        )
                    ) {
                        Text("Limpiar")
                    }
                }
            }
        }

        Card(
            modifier = Modifier.fillMaxWidth(),
            elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
            colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.surfaceVariant)
        ) {
            Column(
                modifier = Modifier.padding(16.dp)
            ) {
                Text(
                    text = "📊 Indicadores de Calidad del Aire:",
                    style = MaterialTheme.typography.labelLarge,
                    color = MaterialTheme.colorScheme.onSurfaceVariant,
                    fontWeight = FontWeight.Bold
                )
                Spacer(modifier = Modifier.height(8.dp))
                Text("• PM2.5: < 12 μg/m³ (Óptimo)", color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("• PM10: < 54 μg/m³ (Óptimo)", color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("• Temperatura: 15-25°C (Confortable)", color = MaterialTheme.colorScheme.onSurfaceVariant)
                Text("• Humedad: 40-60% (Ideal)", color = MaterialTheme.colorScheme.onSurfaceVariant)
            }
        }
    }
}
@Composable
fun AnimationLab() {
    var circleSize by remember { mutableStateOf(100.dp) }
    val animatedSize by animateDpAsState(
        targetValue = circleSize,
        animationSpec = tween(durationMillis = 1000)
    )

    // Usar colores fijos para evitar el problema con MaterialTheme
    val backgroundColor = Color(0xFFE8F5E8)
    val textColor = Color(0xFF1B5E20)
    val circleColor = Color(0xFF2E7D32)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = backgroundColor
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.spacedBy(32.dp)
        ) {
            Text(
                text = "Laboratorio 06",
                style = MaterialTheme.typography.headlineMedium,
                color = textColor
            )

            Text(
                text = "Animación de Indicador de Calidad",
                style = MaterialTheme.typography.bodyLarge,
                color = circleColor,
                textAlign = TextAlign.Center
            )

            Box(
                modifier = Modifier
                    .size(250.dp),
                contentAlignment = Alignment.Center
            ) {
                Canvas(
                    modifier = Modifier
                        .size(animatedSize)
                ) {
                    drawCircle(
                        color = circleColor,
                        radius = size.minDimension / 2
                    )
                }
            }

            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(16.dp)
            ) {
                Text(
                    text = "Controlar tamaño del indicador:",
                    style = MaterialTheme.typography.labelLarge,
                    color = textColor
                )

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ) {
                    Button(
                        onClick = { circleSize = 50.dp },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFF44336)
                        )
                    ) {
                        Text("Pequeño\n(Mala)")
                    }

                    Button(
                        onClick = { circleSize = 100.dp },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFFFF9800)
                        )
                    ) {
                        Text("Normal\n(Regular)")
                    }

                    Button(
                        onClick = { circleSize = 200.dp },
                        modifier = Modifier.weight(1f),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = Color(0xFF2E7D32)
                        )
                    ) {
                        Text("Grande\n(Buena)")
                    }
                }
            }

            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = Color(0xFFE0E0E0))
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "💡 Simulación de Calidad del Aire:",
                        style = MaterialTheme.typography.labelLarge,
                        color = textColor,
                        fontWeight = FontWeight.Bold
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text("• Pequeño = Mala calidad", color = textColor)
                    Text("• Normal = Calidad regular", color = textColor)
                    Text("• Grande = Buena calidad", color = textColor)
                    Text("• Animación suave de 1 segundo", color = textColor)
                }
            }
        }
    }
}

@Composable
fun AirQualityList() {
    val records = remember { generateSampleData() }

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier.fillMaxSize()
        ) {
            Card(
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(16.dp),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp),
                colors = CardDefaults.cardColors(containerColor = MaterialTheme.colorScheme.primary)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Text(
                        text = "Historial de Calidad del Aire",
                        style = MaterialTheme.typography.headlineMedium,
                        color = MaterialTheme.colorScheme.onPrimary
                    )
                    Text(
                        text = "${records.size} registros en Arequipa",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onPrimary.copy(alpha = 0.9f),
                        modifier = Modifier.padding(top = 4.dp)
                    )
                }
            }

            LazyColumn(
                modifier = Modifier
                    .fillMaxSize()
                    .padding(horizontal = 16.dp),
                verticalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(records) { record ->
                    AirQualityItem(record = record)
                }
            }
        }
    }
}

@Composable
fun AirQualityItem(record: AirQualityRecord) {
    // Colores fijos sin MaterialTheme
    val (cardColor, textColor) = when (record.qualityLevel) {
        "Buena" -> Pair(Color(0xFFE8F5E8), Color(0xFF2E7D32))
        "Regular" -> Pair(Color(0xFFFFF8E1), Color(0xFFF57C00))
        "Mala" -> Pair(Color(0xFFFFEBEE), Color(0xFFD32F2F))
        else -> Pair(Color(0xFFF5F5F5), Color(0xFF212121))
    }

    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        elevation = CardDefaults.cardElevation(defaultElevation = 2.dp),
        colors = CardDefaults.cardColors(containerColor = cardColor)
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.SpaceBetween
        ) {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(4.dp)
            ) {
                val iconRes = when (record.qualityLevel) {
                    "Buena" -> android.R.drawable.presence_online
                    "Regular" -> android.R.drawable.presence_away
                    "Mala" -> android.R.drawable.presence_busy
                    else -> android.R.drawable.presence_online
                }

                Image(
                    painter = painterResource(id = iconRes),
                    contentDescription = "Calidad ${record.qualityLevel}",
                    modifier = Modifier.size(32.dp)
                )

                Text(
                    text = record.qualityLevel,
                    style = MaterialTheme.typography.labelSmall,
                    color = textColor,
                    fontWeight = FontWeight.Bold
                )
            }

            Column(
                modifier = Modifier
                    .weight(1f)
                    .padding(horizontal = 16.dp)
            ) {
                Text(
                    text = record.location,
                    style = MaterialTheme.typography.bodyLarge,
                    color = textColor,
                    fontWeight = FontWeight.Bold
                )

                Text(
                    text = "PM2.5: ${record.pm25} μg/m³",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF666666),
                    modifier = Modifier.padding(top = 2.dp)
                )

                Text(
                    text = "PM10: ${record.pm10} μg/m³",
                    style = MaterialTheme.typography.bodyMedium,
                    color = Color(0xFF666666)
                )
            }

            Column(
                horizontalAlignment = Alignment.End
            ) {
                Text(
                    text = record.date,
                    style = MaterialTheme.typography.labelSmall,
                    color = Color(0xFF666666)
                )
                Text(
                    text = "ID: ${record.id}",
                    style = MaterialTheme.typography.labelSmall,
                    color = Color(0xFF666666),
                    modifier = Modifier.padding(top = 2.dp)
                )
            }
        }
    }
}
@Composable
fun ThemeSettingsScreen(
    themeViewModel: ThemeViewModel,
    onBack: () -> Unit
) {
    val isDarkTheme by themeViewModel.isDarkTheme.collectAsState(initial = false)

    Surface(
        modifier = Modifier.fillMaxSize(),
        color = MaterialTheme.colorScheme.background
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(16.dp),
            verticalArrangement = Arrangement.spacedBy(24.dp)
        ) {
            // Header
            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                IconButton(onClick = onBack) {
                    Icon(
                        painter = painterResource(id = android.R.drawable.ic_menu_revert),
                        contentDescription = "Volver",
                        tint = MaterialTheme.colorScheme.onBackground
                    )
                }
                Text(
                    text = "Configuración de Tema",
                    style = MaterialTheme.typography.headlineMedium,
                    color = MaterialTheme.colorScheme.onBackground,
                    modifier = Modifier.padding(start = 8.dp)
                )
            }

            // Theme Selection Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Seleccionar Tema",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.primary
                    )

                    Spacer(modifier = Modifier.height(16.dp))

                    // Light Theme Option
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { themeViewModel.setDarkTheme(false) }
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = android.R.drawable.ic_menu_day),
                            contentDescription = "Tema claro",
                            modifier = Modifier.size(32.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "Tema Claro",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .weight(1f)
                        )
                        if (!isDarkTheme) {
                            Icon(
                                painter = painterResource(id = android.R.drawable.checkbox_on_background),
                                contentDescription = "Seleccionado",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }

                    Divider()

                    // Dark Theme Option
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clickable { themeViewModel.setDarkTheme(true) }
                            .padding(16.dp),
                        verticalAlignment = Alignment.CenterVertically
                    ) {
                        Icon(
                            painter = painterResource(id = android.R.drawable.ic_lock_idle_lock),
                            contentDescription = "Tema oscuro",
                            modifier = Modifier.size(32.dp),
                            tint = MaterialTheme.colorScheme.primary
                        )
                        Text(
                            text = "Tema Oscuro",
                            style = MaterialTheme.typography.bodyLarge,
                            color = MaterialTheme.colorScheme.onSurface,
                            modifier = Modifier
                                .padding(start = 16.dp)
                                .weight(1f)
                        )
                        if (isDarkTheme) {
                            Icon(
                                painter = painterResource(id = android.R.drawable.checkbox_on_background),
                                contentDescription = "Seleccionado",
                                tint = MaterialTheme.colorScheme.primary
                            )
                        }
                    }
                }
            }

            // Preview Card
            Card(
                modifier = Modifier.fillMaxWidth(),
                elevation = CardDefaults.cardElevation(defaultElevation = 4.dp)
            ) {
                Column(
                    modifier = Modifier.padding(16.dp)
                ) {
                    Text(
                        text = "Vista Previa",
                        style = MaterialTheme.typography.titleLarge,
                        color = MaterialTheme.colorScheme.onSurface
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Esta es una vista previa de cómo se verá la aplicación con el tema seleccionado.",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.onSurfaceVariant
                    )
                    Spacer(modifier = Modifier.height(8.dp))
                    Text(
                        text = "Tema actual: ${if (isDarkTheme) "Oscuro" else "Claro"}",
                        style = MaterialTheme.typography.bodyMedium,
                        color = MaterialTheme.colorScheme.primary,
                        fontWeight = FontWeight.Bold
                    )
                }
            }
        }
    }
}

fun saveAirQualityData(
    location: String,
    pm25: String,
    pm10: String,
    temperature: String,
    humidity: String
) {
    println("Datos guardados: $location, PM2.5: $pm25, PM10: $pm10")
}