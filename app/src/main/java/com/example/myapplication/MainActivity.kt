package com.example.myapplication

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Home
import androidx.compose.material.icons.filled.Info
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.*
import androidx.compose.material3.adaptive.navigationsuite.NavigationSuiteScaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewScreenSizes
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.navigation
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

                navigation(route = Routes.HOME, startDestination = Routes.HOME_SCREEN0) {

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

        Spacer(Modifier.height(24.dp))

        Text(
            text = "Tech catalog",
            fontSize = 20.sp,
            fontWeight = FontWeight.SemiBold,
            modifier = Modifier.padding(bottom = 8.dp)
        )

        FeaturedProductsRow(viewModel)
        Spacer(modifier = Modifier.height(16.dp))

        ProductListScreen(viewModel)
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

@Composable
fun ProductListScreen(viewModel: MainViewModel) {
    LazyColumn(
        modifier = Modifier.fillMaxWidth()
    ) {
        items(
            items = viewModel.products,
            key = { item ->
                when (item) {
                    is ProductItem -> item.id
                    is HeaderItem -> item.id
                    is NewsItem -> item.id
                    else -> item.hashCode()
                }
            }
        ) { item ->
            when (item) {
                is HeaderItem -> HeaderItemView(item)
                is ProductItem -> ProductItemView(item)
                is NewsItem -> NewsItemView(item)
                else -> {}
            }
        }
    }
}

@Composable
fun FeaturedProductsRow(viewModel: MainViewModel) {

    Column {
        Text(
            text = "🔥 Featured Products",
            fontSize = 20.sp,
            fontWeight = FontWeight.Bold,
            modifier = Modifier.padding(horizontal = 16.dp, vertical = 8.dp)
        )

        LazyRow(
            modifier = Modifier.fillMaxWidth(),
            contentPadding = PaddingValues(horizontal = 16.dp),
            horizontalArrangement = Arrangement.spacedBy(12.dp)
        ) {
            items(viewModel.featuredProducts, key = { it.id }) { item ->
                FeaturedProductCard(item)
            }
        }
    }
}
@Composable
fun FeaturedProductCard(item: ProductItem) {
    Surface(
        modifier = Modifier
            .width(220.dp)
            .height(140.dp),
        shape = RoundedCornerShape(16.dp),
        color = Color(0xFF1E1E2E),
        shadowElevation = 6.dp
    ) {
        Column(
            modifier = Modifier.padding(12.dp),
            verticalArrangement = Arrangement.SpaceBetween
        ) {

            Text(
                text = item.title,
                color = Color(0xFFBB86FC),
                fontWeight = FontWeight.Bold,
                fontSize = 16.sp,
                maxLines = 2
            )

            Text(
                text = item.price,
                color = Color(0xFF03DAC5),
                fontSize = 15.sp,
                fontWeight = FontWeight.SemiBold
            )

            Text(
                text = item.values.first(),
                color = Color.LightGray,
                fontSize = 12.sp
            )
        }
    }
}

@Composable
fun ProductItemView(item: ProductItem) {
    Surface(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 6.dp)
            .fillMaxWidth(),
        color = Color(0xFF1E1E2E),
        shape = RoundedCornerShape(10.dp),
        tonalElevation = 4.dp
    ) {
        Column(modifier = Modifier.padding(12.dp)) {

            Text(
                text = item.title,
                fontSize = 16.sp,
                fontWeight = FontWeight.Bold,
                color = Color(0xFFBB86FC)
            )

            Spacer(modifier = Modifier.height(4.dp))

            Text(
                text = "Price from: ${item.price}",
                fontSize = 14.sp,
                color = Color(0xFF03DAC5)
            )

            Spacer(modifier = Modifier.height(10.dp))

            LazyRow(
                horizontalArrangement = Arrangement.spacedBy(8.dp)
            ) {
                items(item.values) { value ->
                    Text(
                        text = value,
                        modifier = Modifier
                            .background(
                                Color(0xFF3700B3),
                                RoundedCornerShape(8.dp)
                            )
                            .padding(horizontal = 12.dp, vertical = 6.dp),
                        color = Color.White,
                        fontSize = 13.sp,
                        fontWeight = FontWeight.Medium
                    )
                }
            }
        }
    }
}
@Composable
fun NewsItemView(item: NewsItem) {
    Surface(
        modifier = Modifier
            .padding(12.dp)
            .fillMaxWidth(),
        color = Color(0xFF263238),
        shape = RoundedCornerShape(10.dp)
    ) {
        Column(modifier = Modifier.padding(14.dp)) {
            Text(item.title, color = Color.White, fontWeight = FontWeight.Bold)
            Spacer(Modifier.height(4.dp))
            Text(item.date, color = Color.LightGray, fontSize = 12.sp)
        }
    }
}

@Composable
fun HeaderItemView(item: HeaderItem) {
    Surface(
        modifier = Modifier
            .padding(horizontal = 12.dp, vertical = 10.dp)
            .fillMaxWidth(),
        color = Color(0xFF0D47A1),
        shape = RoundedCornerShape(16.dp),
        shadowElevation = 6.dp
    ) {
        Text(
            text = item.title,
            modifier = Modifier.padding(14.dp),
            color = Color.White,
            fontSize = 17.sp,
            fontWeight = FontWeight.Bold
        )
    }
}

@Preview(showBackground = true)
@Composable
fun GreetingPreview() {
    MyApplicationTheme {
        Text("Preview")
    }
}