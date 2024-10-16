package com.jetbrains.bs23_kmp

import android.Manifest.permission.ACCESS_BACKGROUND_LOCATION
import android.Manifest.permission.ACCESS_COARSE_LOCATION
import android.Manifest.permission.ACCESS_FINE_LOCATION
import android.Manifest.permission.POST_NOTIFICATIONS
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build.VERSION.SDK_INT
import android.os.Build.VERSION_CODES.Q
import android.os.Build.VERSION_CODES.TIRAMISU
import android.os.Bundle
import android.provider.Settings
import android.util.Log
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.result.contract.ActivityResultContracts.RequestMultiplePermissions
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.core.content.ContextCompat
import dev.gitlive.firebase.Firebase
import dev.gitlive.firebase.initialize

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        Firebase.initialize(applicationContext)
        checkPermissionsAndProceed()
    }

    override fun onResume() {
        super.onResume()
        checkPermissionsAndProceed()
    }

    private fun checkPermissionsAndProceed() {
        val permissionsToRequest = REQUIRED_PERMISSIONS.filter {
            ContextCompat.checkSelfPermission(this, it) != PackageManager.PERMISSION_GRANTED
        }
        Log.d("Permissions", "Permissions to request: $permissionsToRequest")
        if (permissionsToRequest.isNotEmpty()) {
            setContent {
                ShowUserPermissionDialog(permissionsToRequest)
            }
        } else {
            setContent {
                App()
            }
        }
    }

    @Composable
    fun ShowUserPermissionDialog(permissions: List<String>) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(15.dp),
            horizontalAlignment = Alignment.CenterHorizontally,
            verticalArrangement = Arrangement.Center
        ) {
            LazyColumn(
                modifier = Modifier.fillMaxWidth().fillMaxHeight(.9f),
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.spacedBy(20.dp, Alignment.CenterVertically),
                contentPadding = PaddingValues(top = 40.dp, bottom = 10.dp)
            ) {
                item {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "Terms & Conditions",
                        style = MaterialTheme.typography.labelLarge.copy(
                            fontWeight = FontWeight.Bold,
                            fontSize = 32.sp,
                            textAlign = TextAlign.Center
                        )
                    )
                }
                item {
                    Text(
                        text = "⬤ For this app to operate correctly some permissions are required.\n\n" +
                                "⬤ Please grant the permissions to continue.\n\n" +
                                "⬤ Please Follow the steps to allow the permissions.",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontSize = 16.sp,
                            textAlign = TextAlign.Start,
                        ),
                        letterSpacing = 1.sp
                    )
                }
                item {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "⬤ Step 1: Click \"Open Settings\" Button.",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontSize = 14.sp,
                            textAlign = TextAlign.Start
                        )
                    )
                }
                item {
                    Image(
                        painter = androidx.compose.ui.res.painterResource(R.drawable.setting),
                        contentDescription = null
                    )
                }
                item {
                    Text(
                        modifier = Modifier.fillMaxWidth(),
                        text = "⬤ Step 2: Give the location and notification permission.",
                        style = MaterialTheme.typography.labelMedium.copy(
                            fontSize = 14.sp,
                            textAlign = TextAlign.Start
                        )
                    )
                }
                item {
                    Image(
                        painter = androidx.compose.ui.res.painterResource(R.drawable.permission),
                        contentDescription = null
                    )
                }
            }
            Button(
                modifier = Modifier.fillMaxWidth().padding(horizontal = 20.dp, vertical = 20.dp),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
                colors = ButtonDefaults.buttonColors(containerColor = MaterialTheme.colorScheme.primaryContainer),
                onClick = {
                    permissionLauncher.launch(permissions.toTypedArray())
                },
                shape = RoundedCornerShape(25)
            ) {
                Text(text = "Open Settings", color = MaterialTheme.colorScheme.onPrimaryContainer)
            }

        }
    }

    @Preview(showBackground = true)
    @Composable
    private fun Dialog() {
        ShowUserPermissionDialog(listOf("Lol"))
    }

    private val permissionLauncher =
        registerForActivityResult(RequestMultiplePermissions()) { permission ->
            var permissionGranted = true
            permission.entries.forEach {
                if (it.key in REQUIRED_PERMISSIONS && !it.value) permissionGranted = false
            }
            if (!permissionGranted) {
                val intent = Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS).apply {
                    data = Uri.fromParts("package", packageName, null)
                }
                startActivity(intent)
            } else {
                setContent {
                    App()
                }
            }
        }

    companion object {
        private val REQUIRED_PERMISSIONS = if (SDK_INT >= TIRAMISU /*Api level 33*/) {
            mutableListOf(
                ACCESS_FINE_LOCATION,
                ACCESS_COARSE_LOCATION,
                ACCESS_BACKGROUND_LOCATION, // Start from Android 10
                POST_NOTIFICATIONS // Only for Android 13+
            ).toTypedArray()
        } else if (SDK_INT >= Q /* Api level 29 */) {
            mutableListOf(
                ACCESS_FINE_LOCATION,
                ACCESS_COARSE_LOCATION,
                ACCESS_BACKGROUND_LOCATION // Start from Android 10
            ).toTypedArray()
        } else {
            // Below Android 10 (API level 29) we just need the location permission
            mutableListOf(
                ACCESS_FINE_LOCATION,
                ACCESS_COARSE_LOCATION
            ).toTypedArray()
        }
    }
}
