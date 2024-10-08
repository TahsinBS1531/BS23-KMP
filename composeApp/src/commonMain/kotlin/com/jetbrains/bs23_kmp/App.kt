package com.jetbrains.bs23_kmp

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jetbrains.bs23_kmp.dashboard.DashboardScreen
import com.jetbrains.bs23_kmp.screens.components.SearchScreen
import com.jetbrains.bs23_kmp.screens.detail.DetailScreen
import com.jetbrains.bs23_kmp.screens.list.ListScreen

@Composable
fun App() {
    MaterialTheme{
        Surface {
            val navController: NavHostController = rememberNavController()
            NavHost(
                navController,
                startDestination = "dashboard"
            ) {
                composable("list") {
                    ListScreen(navController)
                }
                composable("dashboard"){
                    DashboardScreen(navController)
                }
                composable("form"){
                    SearchScreen(modifier = Modifier.fillMaxSize())
                }
                composable("detail/{objectId}") { backStackEntry ->
                    val objectId = backStackEntry.arguments?.getString("objectId")?.toInt()
                    DetailScreen(navController, objectId!!)
                }
            }
        }
    }
}
