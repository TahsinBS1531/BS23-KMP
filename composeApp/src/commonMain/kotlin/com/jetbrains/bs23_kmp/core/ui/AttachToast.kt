package com.jetbrains.bs23_kmp.core.ui

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.navigation.NavController
import com.jetbrains.bs23_kmp.core.base.viewmodel.MvvmViewModel

class AttachToast {
}

@Composable
fun AttachToast(viewModel: MvvmViewModel, navController: NavController) {
    LaunchedEffect(viewModel) {
        viewModel
            .showToast
//            .collect { message ->
//                Toast.makeText(
//                    navController.context,
//                    message,
//                    Toast.LENGTH_SHORT,
//                ).show()
//            }

    }
}
