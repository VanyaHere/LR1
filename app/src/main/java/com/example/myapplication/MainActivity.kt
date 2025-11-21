package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.AccountBox
import androidx.compose.material.icons.filled.Favorite
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.myapplication.ui.theme.MyApplicationTheme
import androidx.lifecycle.viewmodel.compose.viewModel



class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            MyApplicationTheme {
                MyApplicationApp()
            }
        }
    }
}

@PreviewScreenSizes
@Composable
fun MyApplicationApp() {
    var currentDestination by rememberSaveable { mutableStateOf(AppDestinations.HOME) }

    val viewModel: MainViewModel = viewModel()

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            AppDestinations.entries.forEach {
                item(
                    icon = {
                        Icon(
                            it.icon,
                            contentDescription = it.label
                        )
                    },
                    label = { Text(it.label) },
                    selected = it == currentDestination,
                    onClick = { currentDestination = it }
                )
            }
        }
    ) {
        Scaffold(modifier = Modifier.fillMaxSize()) { innerPadding ->

            when (currentDestination) {
                AppDestinations.HOME -> HomeScreen(
                    modifier = Modifier.padding(innerPadding),
                    viewModel = viewModel
                )

                AppDestinations.ABOUT_SCREEN -> AboutScreen(
                    modifier = Modifier.padding(innerPadding),
                    viewModel = viewModel
                )

                AppDestinations.SETTINGS_SCREEN -> SettingsScreen(
                    modifier = Modifier.padding(innerPadding),
                    viewModel = viewModel
                )
            }
        }
    }
}

enum class AppDestinations(
    val label: String,
    val icon: ImageVector,
) {
    HOME("Home", Icons.Default.Home),
    ABOUT_SCREEN("About", Icons.Default.Info),
    SETTINGS_SCREEN("Settings", Icons.Default.Settings)
}

enum class SubScreens {
    MAIN,
    SCREEN1,
    SCREEN2
}

@Composable
fun Greeting(name: String, modifier: Modifier = Modifier) {
    Text(
        text = "Hello $name!",
        modifier = modifier
    )
}

@Composable
fun HomeScreen(modifier: Modifier = Modifier, viewModel: MainViewModel) {

    var currentSubScreen by rememberSaveable { mutableStateOf(SubScreens.MAIN) }

    when (currentSubScreen) {
        SubScreens.MAIN -> {
            Column(
                modifier = modifier.fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = viewModel.homeText, fontSize = 24.sp)

                Spacer(modifier = Modifier.height(20.dp))

                Button(onClick = { viewModel.updateHomeText() }) {
                    Text("Натисни")
                }

                Spacer(modifier = Modifier.height(30.dp))

                Button(onClick = { currentSubScreen = SubScreens.SCREEN1 }) {
                    Text("Перейти до підекрану 1")
                }
            }
        }

        SubScreens.SCREEN1 -> {
            Column(
                modifier = modifier
                    .fillMaxSize()
                    .padding(24.dp),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text(text = viewModel.homeText, fontSize = 24.sp)

                Spacer(modifier = Modifier.height(20.dp))

                Button(onClick = { viewModel.updateHomeText() }) {
                    Text("Натисни")
                }

                Spacer(modifier = Modifier.height(30.dp))

                Button(onClick = { currentSubScreen = SubScreens.SCREEN2 }) {
                    Text("Перейти до підекрану 2")
                }

                Spacer(modifier = Modifier.height(10.dp))

                Button(onClick = { currentSubScreen = SubScreens.MAIN }) {
                    Text("Назад")
                }
            }
        }

        SubScreens.SCREEN2 -> {
            Column(
                modifier = modifier
                    .fillMaxSize(),
                verticalArrangement = Arrangement.Center,
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Text("My")

                Button(onClick = { currentSubScreen = SubScreens.MAIN }) {
                    Text("На головний підекран")
                }
            }
        }
    }
}

@Composable
fun AboutScreen(modifier: Modifier = Modifier, viewModel: MainViewModel) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Top,
        horizontalAlignment = Alignment.Start
    ) {
        Text(text = viewModel.aboutText, fontSize = 20.sp)

        Spacer(modifier = Modifier.height(10.dp))

        Button(onClick = { viewModel.updateAboutText() }) {
            Text("Змінити текст")
        }
    }
}


@Composable
fun SettingsScreen(modifier: Modifier = Modifier, viewModel: MainViewModel) {

    Column(
        modifier = modifier
            .fillMaxSize()
            .padding(32.dp),
        verticalArrangement = Arrangement.Bottom,
        horizontalAlignment = Alignment.End
    ) {
        Text(text = viewModel.settingsText, fontSize = 22.sp, color = Color.DarkGray)

        Spacer(modifier = Modifier.height(12.dp))

        Button(onClick = { viewModel.updateSettingsText() }) {
            Text("Оновити")
        }
    }
}



@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Greeting("Android")
    }
}