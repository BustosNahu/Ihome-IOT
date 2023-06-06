package com.example.yourlocker.compose_navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.yourlocker.ROOM_ID
import com.example.yourlocker.RoomUi.DeviceScreen
import com.example.yourlocker.RoomUi.RoomData
import com.example.yourlocker.RoomUi.RoomScreen
import com.example.yourlocker.Screen
import kotlin.math.log


@Composable
fun SetupNavGraph(
    navController: NavHostController,
    startDestination: String,
    room_id: String
) {

    NavHost(
        navController = navController,
        startDestination = startDestination
    ) {

        composable(route = Screen.Rooms.route,
        ){
//            Log.d("ROOM_ID_TEST", room_id)
            RoomScreen(RoomData("Garage",room_id), navController)
        }

        composable(route = Screen.Devices.route,
            arguments = listOf(navArgument(ROOM_ID){
                type = NavType.IntType
            })
        ){
            Log.d("ArgsSSS", it.arguments?.getInt(ROOM_ID).toString())
            DeviceScreen(navController = navController)
        }


    }

}