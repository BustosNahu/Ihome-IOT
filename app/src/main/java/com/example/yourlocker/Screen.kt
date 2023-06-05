package com.example.yourlocker

sealed class Screen(val route : String){
    object Rooms: Screen(route = "room_screen/{id}")
    object Devices: Screen(route = "device_screen/{id}")
}
