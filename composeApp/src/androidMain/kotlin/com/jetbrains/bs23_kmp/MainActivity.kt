package com.jetbrains.bs23_kmp

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.initialize

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        Firebase.initialize(applicationContext)


        setContent {
            App()
        }
    }
}

//@Preview
//@Composable
//fun AppAndroidPreview() {
//    App()
//}