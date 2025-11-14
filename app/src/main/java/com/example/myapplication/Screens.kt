package com.example.myapplication

import androidx.compose.runtime.*
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight

@Composable
fun HomeScreen() {

    var labelText by remember { mutableStateOf("Це головний екран") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {

        Text(
            text = labelText,
            fontSize = 26.sp,
            fontWeight = FontWeight.Bold
        )

        Spacer(modifier = Modifier.height(20.dp))

        Button(onClick = {
            labelText = "Текст на Home змінено!"
        }) {
            Text("Натисни")
        }
    }
}
@Composable
fun AboutScreen() {

    var labelText by remember { mutableStateOf("Екран About") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {

        Text(
            text = labelText,
            fontSize = 20.sp
        )

        Spacer(modifier = Modifier.height(10.dp))

        Button(onClick = {
            labelText = "Текст на About змінено!"
        }) {
            Text("Змінити текст")
        }
    }
}
@Composable
fun SettingsScreen() {

    var labelText by remember { mutableStateOf("Налаштування") }

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.End
    ) {

        Text(
            text = labelText,
            fontSize = 22.sp,
            color = Color.DarkGray
        )

        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = {
            labelText = "Текст у Settings змінено!"
        }) {
            Text("Оновити текст")
        }
    }
}