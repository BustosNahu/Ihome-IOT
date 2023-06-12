package com.example.yourlocker.RoomUi

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.shadow
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.ViewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.yourlocker.Activities.ui.theme.DarkBlue
import com.example.yourlocker.Activities.ui.theme.DarkGrey
import com.example.yourlocker.Model.Device
import com.example.yourlocker.R
import com.example.yourlocker.Screen
import com.example.yourlocker.Utils.Utils.BED_ROOM
import com.example.yourlocker.Utils.Utils.CAMERA
import com.example.yourlocker.Utils.Utils.GARAGE
import com.example.yourlocker.Utils.Utils.HOME_OUTSIDE
import com.example.yourlocker.Utils.Utils.KITCHEN
import com.example.yourlocker.Utils.Utils.LIVING_ROOM
import com.example.yourlocker.Utils.Utils.LOCKER
import com.example.yourlocker.Utils.Utils.USER_PATH
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DataSnapshot
import com.google.firebase.database.DatabaseError
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.database.ValueEventListener
import com.google.firebase.ktx.Firebase


private lateinit var firebaseDatabase: FirebaseDatabase
private lateinit var databaseReference: DatabaseReference
private lateinit var auth: FirebaseAuth

private var room_type: String? = null
private var type: String? = null
private var lastDeviceState: Boolean? = null
private var deviceList: MutableList<Device> = emptyList<Device>().toMutableList()

//class RoomViewmodel : ViewModel(){
//
//}

//MVP
data class RoomData(val roomType: String, val roomId: String)


@Composable
fun RoomScreen(
    data: RoomData,
//    vm : RoomViewmodel,
    navController: NavController,
) {
    val mContext = LocalContext.current
    var roomId = data.roomId.toString()

    val name = remember {
        mutableStateOf("")
    }

    val list = remember {
        dataRequest(mContext, roomId)
    }

//    backgroundImageReturn(room_name)

    Box(
        Modifier
            .fillMaxSize()
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id =
            when(room_type){
                GARAGE-> R.drawable.garage_pic
                LIVING_ROOM -> R.drawable.living_pic
                HOME_OUTSIDE -> R.drawable.outside_pic
                BED_ROOM -> R.drawable.room_pic
                KITCHEN -> R.drawable.kitchen_pic
                else -> {R.drawable.kitchen_pic}
            }
            ),
            contentDescription = "Background",
            contentScale = ContentScale.FillHeight,
            modifier = Modifier.fillMaxSize()
        )

        Card(

            modifier = Modifier

                .fillMaxWidth()
                .height(450.dp)
                .align(Alignment.BottomCenter),
            colors = CardDefaults.cardColors(
                containerColor = Color.Black.copy(alpha = 0.7f),
            ),
            shape = RoundedCornerShape(topEnd = 45.dp, topStart = 50.dp)
        )
        {
            Column(
                modifier = Modifier
                    .fillMaxWidth()
                    .fillMaxHeight()
                    .align(Alignment.CenterHorizontally)
            ) {
                Row(
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 30.dp)
                ) {
                    Column {
                        Text(
                            text = "Welcome",
                            modifier = Modifier
                                .padding(PaddingValues(27.dp, 0.dp, 0.dp, 0.dp))
                                .width(280.dp),
                            style = TextStyle(
                                color = Color.White,
                                fontSize = 30.sp,
                                fontStyle = FontStyle.Normal
                            )
                        )
                        Text(text = "to your $room_type",
                            modifier = Modifier
                                .padding(top = 0.dp, start = 27.dp),
                            style = TextStyle(
                                color = Color.White,
                                fontSize = 16.sp,
                                fontStyle = FontStyle.Normal
                            ))
                    }
                    Card(
                        modifier = Modifier
                            .padding(3.dp)
                            .fillMaxWidth()
                            .height(45.dp)
                            .align(Alignment.CenterVertically),
                        colors = CardDefaults.cardColors(
                            containerColor = Color.Black
                        ),
                        shape = RoundedCornerShape(topStart = 50.dp, bottomStart = 50.dp)
                    ) {
                        Column(Modifier.fillMaxSize()) {
                            OutlinedButton(
                                onClick = {
                                    //instance to navigate with component and pass values from screens
//                                    navController.navigate(route = Screen.Devices.passIdDevices(
//                                        id = 12
//                                    ))
                                    navController.navigate(route = Screen.AddDevicesScreen.passIdRoomToAddDevice(
                                        id = roomId
                                    ))

                                },
                                modifier = Modifier
                                    .size(40.dp)
                                    .padding(top = 5.dp, start = 5.dp),
                                shape = CircleShape,
                                border = BorderStroke(2.dp, Color.White),
                                contentPadding = PaddingValues(0.dp),
                                colors = ButtonDefaults.outlinedButtonColors(contentColor = Color.White)
                            ) {
                                Icon(
                                    Icons.Default.Add,
                                    contentDescription = "Add devices button"
                                )
                            }
                        }
                    }


                }

                Text(
                    text = "Your ${data.roomType} is conected with 5 devices, you have all access",
                    Modifier
                        .fillMaxWidth()
                        .padding(PaddingValues(27.dp, 37.dp, 27.dp, 0.dp)),
                    style = TextStyle(
                        color = Color.White,
                        fontSize = 16.sp
                    )
                )
                Divider(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(PaddingValues(15.dp, 26.dp, 15.dp, 0.dp)),
                    color = Color.White,
                    thickness = 1.dp
                )


                Text(text = "Devices",
                    Modifier
                        .fillMaxWidth()
                        .padding(top = 14.dp, start = 27.dp),
                    style = TextStyle(
                        fontWeight = FontWeight.Bold,
                        fontSize = 24.sp,
                        color = Color.White

                    ))

                LaunchedEffect(deviceList){
                    Log.d("nahuGay", deviceList.toString())
                }
                LazyRow(
                    Modifier
                        .fillMaxWidth()
                        .height(200.dp)
                        .padding(top = 20.dp, start = 15.dp, bottom = 10.dp),
                    horizontalArrangement = Arrangement.spacedBy(12.dp)
                ){
                    items(list){ item ->
                        Devices(item, roomId)
                    }

                }
            }

        }

    }

}



@Composable
fun Devices(device: Device, roomId: String) {
    var lastState: Boolean? = remember {
        saveDeviceData(device.device_id.toString(), roomId)
    }
    IconButton(
        onClick =
        {
            if (lastState == true) {
                changeLockerState(roomId, device.device_id.toString(), false)
                lastState = false

            } else {
                changeLockerState(roomId, device.device_id.toString(), true)
                lastState = true
            }
        }, Modifier
            .height(174.dp)
            .width(139.dp)
            .background(
                Color.DarkGray, RoundedCornerShape(50.dp)
            )
            .padding(5.dp)
    ) {
        Column(
            Modifier.fillMaxSize(),
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        )
        {
            Column(
                horizontalAlignment = Alignment.CenterHorizontally
            ) {
                Icon(
                    painter = painterResource(
                        id =
                        if (device.device_type == LOCKER) {
                            R.drawable.ic_locker
                        } else {
                            R.drawable.ic_camera
                        }
                    ),
                    contentDescription = "Locker button",
                    tint = Color.White
                )
                device.device_name?.let {
                    Text(
                        text = it,
                        Modifier.padding(top = 14.dp),
                        style = TextStyle(
                            fontSize = 15.sp,
                            fontWeight = FontWeight.Light,
                            color = Color.White
                        )
                    )
                }

            }

        }

    }
}

fun saveDeviceData(deviceId: String, roomId: String): Boolean? {

    firebaseDatabase = FirebaseDatabase.getInstance()
    databaseReference = firebaseDatabase.getReference(USER_PATH)
    auth = Firebase.auth

    val user = FirebaseAuth.getInstance().currentUser!!
    var uId = user.uid

    databaseReference.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            lastDeviceState = snapshot.child(uId).child("rooms")
                .child(roomId).child("devices").child(deviceId.toString())
                .child("device_state").value as Boolean
        }

        override fun onCancelled(error: DatabaseError) {
            TODO("Not yet implemented")
        }

    })
    return lastDeviceState
}

fun changeLockerState(roomId: String, device_id: String?, actualState: Boolean?) {
    firebaseDatabase = FirebaseDatabase.getInstance()
    databaseReference = firebaseDatabase.getReference(USER_PATH)
    auth = Firebase.auth


    val user = FirebaseAuth.getInstance().currentUser!!
    var uId = user.uid

    databaseReference.child(uId).child("rooms")
        .child(roomId).child("devices").child(device_id.toString())
        .child("device_state").setValue(actualState)

}

@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun DefaultPreview() {
    RoomScreen(
        RoomData("Garage", "0012"),
        navController = rememberNavController()
    )
}


fun dataRequest(context: android.content.Context, roomId: String): MutableList<Device> {
    firebaseDatabase = FirebaseDatabase.getInstance()
    databaseReference = firebaseDatabase.getReference(USER_PATH)
    auth = Firebase.auth

    val user = FirebaseAuth.getInstance().currentUser!!
    var uId = user.uid


    databaseReference.addValueEventListener(object : ValueEventListener {

        override fun onDataChange(snapshot: DataSnapshot) {
            deviceList.clear()
            try {
                Log.d("DATABASE", "uID: " + uId)
                room_type = snapshot.child(uId).child("rooms").child(roomId)
                    .child("type").value.toString()

                for (postSnapshot in snapshot.child(uId).child("rooms")
                    .child(roomId).child("devices").children) {
                    val device_id = postSnapshot.child("device_id").value.toString()
                    val device_name = postSnapshot.child("device_name").value.toString()
                    val device_state = postSnapshot.child("device_state").value as Boolean
                    val device_type = postSnapshot.child("device_type").value.toString()

                    val device = Device(device_id, device_type, device_name, device_state)
                    Log.d("DeviceNahuGya", device.device_name.toString())
                    deviceList.add(device)
                    Log.d("DeviceNahuGya", deviceList.toString())
                }


            } catch (e: Exception) {
                Log.d("DATABASE", "ERROR")
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
            }
        }

        override fun onCancelled(error: DatabaseError) {
            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()

        }

    })
    return deviceList
}


