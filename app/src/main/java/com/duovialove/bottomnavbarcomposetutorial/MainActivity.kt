package com.duovialove.bottomnavbarcomposetutorial

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import androidx.savedstate.savedState
import com.duovialove.bottomnavbarcomposetutorial.ui.theme.BottomNavBarComposeTutorialTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            BottomNavBarComposeTutorialTheme {

                MainScreen()
            }
        }
    }
}

@Composable
fun MainScreen(){
    val navController = rememberNavController()

    Scaffold(
        bottomBar = { CustomBottomNavigationBar(navController) },
        modifier = Modifier.fillMaxSize()) { innerPadding ->

        NavHost(
            navController = navController,
            startDestination = Screen.Home.route,
            modifier = Modifier.padding(innerPadding)
        ){
            composable (Screen.Home.route){ HomeScreen() }
            composable (Screen.Profile.route){ ProfileScreen() }
            composable (Screen.Settings.route){ SettingsScreen() }
        }

    }
}

@Composable
fun HomeScreen(){
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Text("Ana Sayfa")
    }
}

@Composable
fun ProfileScreen(){
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Text("Profilim")
    }
}

@Composable
fun SettingsScreen(){
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center){
        Text("Ayarlar")
    }
}

@Composable
fun CustomBottomNavigationBar(navController: NavController){

    val currentBackStackState = navController.currentBackStackEntryAsState()
    val currentBackStack = currentBackStackState.value
    val currentRoute = currentBackStack?.destination?.route

    println("currentBackStack:")
    println(currentBackStack)

    NavigationBar {
        bottomNavItems.forEach { screen->


            NavigationBarItem(
                icon={ Icon(screen.icon, contentDescription = screen.title) },
                label = { Text(screen.title) },
                selected = currentRoute==screen.route,
                onClick = {
                    if (currentRoute!=screen.route){
                        navController.navigate(screen.route){
                            popUpTo(navController.graph.startDestinationId){
                                saveState = true
                            }
                            launchSingleTop = true
                            restoreState = true
                        }
                    }
                }
            )
        }
    }
}

