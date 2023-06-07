package com.example.yourlocker

const val ROOM_ID = "room_id"

const val DEVICE_ID = "device_id"
sealed class Screen(val route : String){
    object Rooms: Screen(route = "room_screen/{$ROOM_ID}")
    fun passIdRoom(id: Int): String{
        return this.route.replace(oldValue =" {$ROOM_ID}", newValue = id.toString())
    }

    object Devices: Screen(route = "device_screen/{$DEVICE_ID}"){
        fun passIdDevices(id: Int): String{
            return this.route.replace(oldValue =" {$ROOM_ID}", newValue = id.toString())
        }
    }

    object AddDevicesScreen: Screen(route = "add_device_screen/{$ROOM_ID}"){
        fun passIdRoomToAddDevice(id: String): String{
            return this.route.replace(oldValue =" {$ROOM_ID}", newValue = id.toString())
        }
    }
}
