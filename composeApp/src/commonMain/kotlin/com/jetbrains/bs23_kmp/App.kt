package com.jetbrains.bs23_kmp

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Surface
import androidx.compose.material.darkColors
import androidx.compose.material.lightColors
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.jetbrains.bs23_kmp.screens.detail.DetailScreen
import com.jetbrains.bs23_kmp.screens.list.ListScreen

@Composable
fun App() {
    MaterialTheme(
        colors = if (isSystemInDarkTheme()) darkColors() else lightColors()
    ) {
        Surface {
            val navController: NavHostController = rememberNavController()
            NavHost(
                navController,
                startDestination = "list"
            ) {
                composable("list") {
                    ListScreen(navController)
                }
                composable("detail/{objectId}") { backStackEntry ->
                    val objectId = backStackEntry.arguments?.getString("objectId")?.toInt()
                    DetailScreen(navController, objectId!!)
                }
            }
        }
    }
}
