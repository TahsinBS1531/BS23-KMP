package com.jetbrains.bs23_kmp.service

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.Service
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.location.Location
import android.os.Binder
import android.os.Build
import android.os.IBinder
import androidx.compose.runtime.Composable
import androidx.compose.runtime.DisposableEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.core.app.NotificationCompat
import com.google.android.gms.location.FusedLocationProviderClient
import com.google.android.gms.location.LocationCallback
import com.google.android.gms.location.LocationRequest
import com.google.android.gms.location.LocationResult
import com.google.android.gms.location.LocationServices
import com.google.android.gms.location.Priority
import com.jetbrains.bs23_kmp.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.DelicateCoroutinesApi
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.launch

class LocationTrackingService : Service() {

    private val locationFlow = MutableSharedFlow<Location>() // SharedFlow to emit location data
    private lateinit var fusedLocationClient: FusedLocationProviderClient
    private lateinit var locationRequest: LocationRequest

    // Binder class to allow clients to bind to this service
    inner class LocationBinder : Binder() {
        fun getService(): LocationTrackingService = this@LocationTrackingService
    }

    private val binder = LocationBinder() // Create an instance of the binder

    override fun onCreate() {
        super.onCreate()
        createNotificationChannel()
        startForeground(1, createNotification())  // Start as foreground service
        fusedLocationClient = LocationServices.getFusedLocationProviderClient(this) // Initialize FusedLocationProviderClient
    }

    @Suppress("MissingPermission")
    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        startLocationUpdates() // Start tracking in the background
        return START_STICKY
    }

    @OptIn(DelicateCoroutinesApi::class)
    @Suppress("MissingPermission")
    private fun startLocationUpdates() {
        locationRequest = LocationRequest.Builder(Priority.PRIORITY_HIGH_ACCURACY, 5000L)
            .setWaitForAccurateLocation(true)
            .build()

        val locationCallback = object : LocationCallback() {
            override fun onLocationResult(locationResult: LocationResult) {
                locationResult.lastLocation?.let { location ->
                    GlobalScope.launch {
                        locationFlow.emit(location) // Emit the location update
                    }
                }
            }
        }

        // Start location updates
        fusedLocationClient.requestLocationUpdates(locationRequest, locationCallback, null)
    }


    private fun createNotificationChannel() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            val notificationChannelId = "Location_Service_Channel"
            val channel = NotificationChannel(
                notificationChannelId,
                "Location Tracking Service",
                NotificationManager.IMPORTANCE_LOW
            )
            val notificationManager = getSystemService(NotificationManager::class.java)
            notificationManager.createNotificationChannel(channel)
        }
    }

    // Expose the location flow to clients
    fun getLocationFlow(): Flow<Location> = locationFlow.asSharedFlow()

    override fun onBind(intent: Intent?): IBinder {
        return binder // Return the binder instance
    }

    override fun onDestroy() {
        super.onDestroy()
        fusedLocationClient.removeLocationUpdates { } // Stop location updates
    }

    // Create a notification for the foreground service
    private fun createNotification(): Notification {
        val notificationChannelId = "Location_Service_Channel"

        val notificationBuilder = NotificationCompat.Builder(this, notificationChannelId)
            .setContentTitle("Location Tracking")
            .setContentText("Location tracking is active.")
            .setSmallIcon(R.drawable.ic_marker_start)
            .setPriority(NotificationCompat.PRIORITY_LOW)

        return notificationBuilder.build()
    }
}


@Composable
fun rememberLocationServiceFlow(): Flow<Location> {
    val context = LocalContext.current
    val locationFlow = remember { MutableSharedFlow<Location>() }

    // Bind to the location tracking service
    DisposableEffect(Unit) {
        val intent = Intent(context, LocationTrackingService::class.java)
        context.startService(intent) // Ensure the service starts

        val connection = object : ServiceConnection {
            override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
                println("Connection connected")
                val binder = service as? LocationTrackingService.LocationBinder
                binder?.getService()?.getLocationFlow()?.let { flow ->
                    // Use a CoroutineScope to collect the flow
                    CoroutineScope(Dispatchers.Main).launch {
                        flow.collect { location ->
                            locationFlow.emit(location) // Emit locations to the UI
                        }
                    }
                }
            }

            override fun onServiceDisconnected(name: ComponentName?) {
                // Handle service disconnection if necessary
            }
        }

        context.bindService(intent, connection, Context.BIND_AUTO_CREATE)

        onDispose {
            context.unbindService(connection)
        }
    }

    return locationFlow
}



