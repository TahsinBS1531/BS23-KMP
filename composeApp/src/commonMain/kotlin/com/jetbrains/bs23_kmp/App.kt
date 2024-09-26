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
import com.jetbrains.bs23_kmp.screens.auth.AuthServiceImpl
import com.jetbrains.bs23_kmp.screens.auth.LoginScreen1
import com.jetbrains.bs23_kmp.screens.auth.LoginViewModel
import com.jetbrains.bs23_kmp.screens.auth.SignUp
import com.jetbrains.bs23_kmp.screens.auth.SignUpViewModel
import com.jetbrains.bs23_kmp.screens.components.SearchScreen
import com.jetbrains.bs23_kmp.screens.detail.DetailScreen
import com.jetbrains.bs23_kmp.screens.home.HomeScreen
import com.jetbrains.bs23_kmp.screens.list.ListScreen
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.auth.auth

@Composable
fun App() {

    MaterialTheme{
        Surface {
            val loginViewModel = LoginViewModel(AuthServiceImpl(auth = Firebase.auth))
            val signUpViewModel = SignUpViewModel(AuthServiceImpl(auth = Firebase.auth))

            val navController: NavHostController = rememberNavController()
            NavHost(
                navController,
                startDestination = "login"
            ) {
                composable("list") {
                    ListScreen(navController)
                }
                composable("login") {
//                    LoginScreen(modifier = Modifier.fillMaxSize().padding(16.dp))
                    LoginScreen1(onLoginSuccess = {}, viewModel = loginViewModel,navController)
                }

                composable("signup"){
                    SignUp(onLoginSuccess = {}, viewModel = signUpViewModel, navController)
                }

                composable("home") {
//                    LoginScreen(modifier = Modifier.fillMaxSize().padding(16.dp))
                    HomeScreen(navController,loginViewModel)
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
