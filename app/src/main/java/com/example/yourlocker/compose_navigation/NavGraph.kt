package com.example.yourlocker.compose_navigation

import android.util.Log
import androidx.compose.runtime.Composable
import androidx.navigation.NavHostController
import androidx.navigation.NavType
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.navArgument
import com.example.yourlocker.RoomUi.DeviceScreen
import com.example.yourlocker.RoomUi.RoomData
import com.example.yourlocker.RoomUi.RoomScreen
import com.example.yourlocker.Screen


@Composable
fun SetupNavGraph(
    navController: NavHostController
) {
    NavHost(
        navController = navController,
        startDestination = Screen.Rooms.route
    ) {
        composable(
            route = Screen.Rooms.route,
            arguments = listOf(navArgument("id"){
                type = NavType.IntType
            })
        ){
            RoomScreen(RoomData("",""), navController)
        }
        composable(
            route = Screen.Devices.route,
            arguments = listOf(navArgument("id"){
                type = NavType.IntType
            })
        ){
            Log.d("ArgsSSS", it.arguments?.getInt("id").toString())
            DeviceScreen(navController = navController)
        }


    }

}