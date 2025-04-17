package org.example.requestpermission

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.Button
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.compose.viewModel
import dev.icerock.moko.permissions.PermissionState
import dev.icerock.moko.permissions.compose.BindEffect
import dev.icerock.moko.permissions.compose.rememberPermissionsControllerFactory
import org.jetbrains.compose.ui.tooling.preview.Preview

@Composable
@Preview
fun App() {
    MaterialTheme {
        val factory = rememberPermissionsControllerFactory()
        val controller = remember(factory) {
            factory.createPermissionsController()
        }
        BindEffect(controller)

        val viewModel = viewModel {
            PermissionsViewModel(controller)
        }

        Column(
            modifier = Modifier
                .fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            when(viewModel.locationPermission) {
                PermissionState.Granted -> {
                    Text("location permission granted!")
                }
                PermissionState.DeniedAlways -> {
                    Text("location Permission was permanently declined.")
                    Button(onClick = {
                        controller.openAppSettings()
                    }) {
                        Text("Open app settings")
                    }
                }
                else -> {
                    Button(
                        onClick = {
                            viewModel.provideOrRequestRecordLocationPermission()
                        }
                    ) {
                        Text("Request location permission")
                    }
                }
            }

            when(viewModel.notificationPermision) {
                PermissionState.Granted -> {
                    Text("notification permission granted!")
                }
                PermissionState.DeniedAlways -> {
                    Text("notification Permission was permanently declined.")
                    Button(onClick = {
                        controller.openAppSettings()
                    }) {
                        Text("Open app settings")
                    }
                }
                else -> {
                    Button(
                        onClick = {
                            viewModel.provideOrRequestRecordNotificationPermission()
                        }
                    ) {
                        Text("Request notification permission")
                    }
                }
            }
        }
    }
}