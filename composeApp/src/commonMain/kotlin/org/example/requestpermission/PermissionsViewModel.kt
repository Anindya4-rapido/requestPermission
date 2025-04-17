package org.example.requestpermission

import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.setValue
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import dev.icerock.moko.permissions.DeniedAlwaysException
import dev.icerock.moko.permissions.DeniedException
import dev.icerock.moko.permissions.Permission
import dev.icerock.moko.permissions.PermissionState
import dev.icerock.moko.permissions.PermissionsController
import dev.icerock.moko.permissions.RequestCanceledException
import dev.icerock.moko.permissions.location.BACKGROUND_LOCATION
import dev.icerock.moko.permissions.location.LOCATION
import dev.icerock.moko.permissions.notifications.REMOTE_NOTIFICATION
import kotlinx.coroutines.launch

class PermissionsViewModel(
    private val controller: PermissionsController
):ViewModel() {

    var locationPermission by mutableStateOf(PermissionState.NotDetermined)
        private set

    var notificationPermision by mutableStateOf(PermissionState.NotDetermined)
        private set

    var locationPermissionBackground by mutableStateOf(PermissionState.NotDetermined)
        private set
    

    init {
        viewModelScope.launch {
            locationPermission = controller.getPermissionState(Permission.LOCATION)
            locationPermissionBackground = controller.getPermissionState(Permission.BACKGROUND_LOCATION)
            println(locationPermissionBackground)
//            when(locationPermissionBackground){
//                PermissionState.NotDetermined -> TODO()
//                PermissionState.NotGranted -> TODO()
//                PermissionState.Granted -> TODO()
//                PermissionState.Denied -> TODO()
//                PermissionState.DeniedAlways -> TODO()
//            }
            notificationPermision = controller.getPermissionState(Permission.REMOTE_NOTIFICATION)
        }
    }

    fun provideOrRequestRecordLocationPermission() {
        viewModelScope.launch {
            try {
                controller.providePermission(Permission.LOCATION)
                locationPermission = PermissionState.Granted
            } catch(e: DeniedAlwaysException) {
                locationPermission = PermissionState.DeniedAlways
            } catch(e: DeniedException) {
                locationPermission = PermissionState.Denied
            } catch(e: RequestCanceledException) {
                e.printStackTrace()
            }
        }
    }

    fun provideOrRequestRecordNotificationPermission() {
        viewModelScope.launch {
            try {
                controller.providePermission(Permission.REMOTE_NOTIFICATION)
                notificationPermision = PermissionState.Granted
            } catch(e: DeniedAlwaysException) {
                 notificationPermision = PermissionState.DeniedAlways
            } catch(e: DeniedException) {
                 notificationPermision = PermissionState.Denied
            } catch(e: RequestCanceledException) {
                e.printStackTrace()
            }
        }
    }

}