package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.*
import androidx.navigation.compose.rememberNavController
import com.example.myapplication.ui.theme.MyApplicationTheme

object Routes {
    const val HOME = "home"

    const val HOME_SCREEN0 = "home_screen0"
    const val HOME_SCREEN1 = "home_screen1"
    const val HOME_SCREEN2 = "home_screen2"

    const val ABOUT = "about"
    const val SETTINGS = "settings"
}

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

    val navController = rememberNavController()
    val viewModel: MainViewModel = viewModel()

    NavigationSuiteScaffold(
        navigationSuiteItems = {
            item(
                icon = { Icon(Icons.Default.Home, "Home") },
                label = { Text("Home") },
                selected = false,
                onClick = { navController.navigate(Routes.HOME) }
            )
            item(
                icon = { Icon(Icons.Default.Info, "About") },
                label = { Text("About") },
                selected = false,
                onClick = { navController.navigate(Routes.ABOUT) }
            )
            item(
                icon = { Icon(Icons.Default.Settings, "Settings") },
                label = { Text("Settings") },
                selected = false,
                onClick = { navController.navigate(Routes.SETTINGS) }
            )
        }
    ) {

        Scaffold { innerPadding ->

            NavHost(
                navController = navController,
                startDestination = Routes.HOME,
                modifier = Modifier.padding(innerPadding)
            ) {

                navigation(route = Routes.HOME, startDestination = Routes.HOME_SCREEN0){

                    composable(Routes.HOME_SCREEN0) {
                        HomeScreen(navController, viewModel)
                    }

                    composable(Routes.HOME_SCREEN1) {
                        HomeScreen1(navController, viewModel)
                    }

                    composable(Routes.HOME_SCREEN2) {
                        HomeScreen2(navController)
                    }
                }
//                composable(Routes.HOME) {
//                    HomeScreen(navController, viewModel)
//                }
//
//                composable(Routes.HOME_SCREEN1) {
//                    HomeScreen1(navController, viewModel)
//                }

//                composable(Routes.HOME_SCREEN2) {
//                    HomeScreen2(navController)
//                }

                composable(Routes.ABOUT) {
                    AboutScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(24.dp),
                        viewModel = viewModel
                    )
                }

                composable(Routes.SETTINGS) {
                    SettingsScreen(
                        modifier = Modifier
                            .fillMaxSize()
                            .padding(32.dp),
                        viewModel = viewModel
                    )
                }
            }
        }
    }
}

@Composable
fun UserItem(user: User) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFE3F2FD))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "${user.name}", fontSize = 18.sp, color = Color.Black)
        }
    }
}

@Composable
fun ProductItem(product: Product) {
    Card(
        modifier = Modifier
            .fillMaxWidth()
            .padding(vertical = 4.dp),
        colors = CardDefaults.cardColors(containerColor = Color(0xFFFFF9C4))
    ) {
        Row(
            modifier = Modifier.padding(16.dp),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(text = "${product.title} - ${product.price} ₴", fontSize = 18.sp, color = Color.Black)
        }
    }
}
@Composable
fun HomeScreen(navController: NavController, viewModel: MainViewModel) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(16.dp)
    ) {
        Text(text = viewModel.homeText, fontSize = 24.sp)

        Spacer(Modifier.height(20.dp))

        Button(onClick = { viewModel.updateHomeText() }) {
            Text("Натисни")
        }

        Spacer(Modifier.height(30.dp))

        Button(onClick = { navController.navigate(Routes.HOME_SCREEN1) }) {
            Text("Перейти до підекрану 1")
        }

        Spacer(Modifier.height(30.dp))

        LazyColumn(modifier = Modifier.fillMaxSize()) {
            items(viewModel.users) { user ->
                UserItem(user)
            }

            item {
                Text(
                    "Продукти:",
                    fontSize = 20.sp,
                    modifier = Modifier.padding(top = 16.dp, bottom = 8.dp)
                )

                LazyRow(
                    modifier = Modifier
                        .fillMaxWidth(),
                    horizontalArrangement = Arrangement.spacedBy(16.dp)
                ) {
                    items(viewModel.products) { product ->
                        ProductItem(product)
                    }
                }
            }
        }
    }
}

@Composable
fun HomeScreen1(navController: NavController, viewModel: MainViewModel) {

    Column(
        modifier = Modifier
            .fillMaxSize()
            .padding(24.dp),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text(viewModel.homeText, fontSize = 24.sp)

        Spacer(Modifier.height(20.dp))

        Button(onClick = { viewModel.updateHomeText() }) {
            Text("Натисни")
        }

        Spacer(Modifier.height(30.dp))

        Button(onClick = { navController.navigate(Routes.HOME_SCREEN2) }) {
            Text("Перейти до підекрану 2")
        }

        Spacer(Modifier.height(10.dp))

        Button(onClick = { navController.popBackStack() }) {
            Text("Назад")
        }
    }
}

@Composable
fun HomeScreen2(navController: NavController) {

    Column(
        modifier = Modifier.fillMaxSize(),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        Text("My")

        Spacer(Modifier.height(20.dp))

        Button(onClick = { navController.popBackStack(Routes.HOME_SCREEN0, false) }) {
            Text("На головний підекран")
        }
    }
}

@Composable
fun AboutScreen(modifier: Modifier = Modifier, viewModel: MainViewModel) {

    Column(
        modifier = modifier,
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
        modifier = modifier,
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
        Text("Preview")
    }
}