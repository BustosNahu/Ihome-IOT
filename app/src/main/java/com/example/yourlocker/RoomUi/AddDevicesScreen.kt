package com.example.yourlocker.RoomUi

import android.content.res.Resources.Theme
import android.util.Log
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.yourlocker.Model.Device
import com.example.yourlocker.R
import com.example.yourlocker.Utils.Utils
import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.auth.ktx.auth
import com.google.firebase.database.DatabaseReference
import com.google.firebase.database.FirebaseDatabase
import com.google.firebase.ktx.Firebase
import java.util.UUID

private lateinit var firebaseDatabase: FirebaseDatabase
private lateinit var databaseReference: DatabaseReference
private lateinit var auth: FirebaseAuth

var name_device: String = "Nahuel's Room"
var type_device: String = "Locker"
var id_device: String? = null

@Composable
fun AddDevicesScreen(navController: NavController, roomId: String) {

    addNewRoom(roomId)
    Box(modifier = Modifier.fillMaxSize())
    {
        Row(
            Modifier
                .fillMaxWidth()
                .height(100.dp)
                .padding(top = 25.dp)
        ) {
            IconButton(
                onClick = { /*TODO*/ },
                Modifier
                    .height(60.dp)
                    .width(60.dp)
                    .align(Alignment.CenterVertically)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.ic_arrow_back_ios_24),
                    contentDescription = "Arrow back button",
                    Modifier.padding(start = 20.dp),
                    tint = Color(0xFF949494)
                )
            }

            Spacer(modifier = Modifier.width(260.dp))

            IconButton(
                onClick = { /*TODO*/ },
                Modifier
                    .padding(5.dp)
                    .height(70.dp)
                    .width(70.dp)
            ) {
                Icon(
                    painter = painterResource(id = R.drawable.bt_ok),
                    contentDescription = "Button all right"
                )

            }
        }

        Column(
            Modifier
                .padding(top = 148.dp)
                .fillMaxWidth(),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Text(
                text = "New Device",
                style = TextStyle(
                    fontSize = 20.sp,
                    color = Color.Black,
                    fontWeight = FontWeight.Bold
                )
            )


        }

    }
}

fun addNewRoom(roomId: String) {
    firebaseDatabase = FirebaseDatabase.getInstance()
    databaseReference = firebaseDatabase.getReference(Utils.USER_PATH)
    auth = Firebase.auth

    val user = FirebaseAuth.getInstance().currentUser!!
    var uId = user.uid
    var id_device = UUID.randomUUID().toString()

    var device = Device(id_device, type_device,name_device, false)

    databaseReference.child(uId).child("rooms")
        .child(roomId).child("devices").child(id_device).setValue(device)

}


@Preview(
    showBackground = true,
    showSystemUi = true
)
@Composable
fun AddDevicesScreenPreview() {
    AddDevicesScreen(navController = rememberNavController(), "")
}