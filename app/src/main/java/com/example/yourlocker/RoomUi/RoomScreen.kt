package com.example.yourlocker.RoomUi

import android.util.Log
import android.widget.Toast
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.Image
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
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Add
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.Divider
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.layout.ContentScale
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.example.yourlocker.R
import com.example.yourlocker.Screen
import com.example.yourlocker.Utils.Utils.BED_ROOM
import com.example.yourlocker.Utils.Utils.GARAGE
import com.example.yourlocker.Utils.Utils.HOME_OUTSIDE
import com.example.yourlocker.Utils.Utils.KITCHEN
import com.example.yourlocker.Utils.Utils.LIVING_ROOM
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

private var room_name: String? = null
private var type: String? = null
private var background: String? = null

data class RoomData(val roomType: String, val roomId: String)


@Composable
fun RoomScreen(
    data: RoomData,
    navController: NavController
) {
    val mContext = LocalContext.current
    var roomId = data.roomId.toString()

    dataRequest(mContext, roomId)
//    backgroundImageReturn(room_name)

    Box(
        Modifier
            .fillMaxSize()
            .fillMaxHeight()
            .fillMaxWidth()
    ) {
            Image(
                painter = painterResource(id = R.drawable.outside_pic),
                contentDescription = "Background",
                contentScale = ContentScale.FillHeight,
                modifier = Modifier.fillMaxSize()
            )

        Card(

            modifier = Modifier

                .fillMaxWidth()
                .height(400.dp)
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
                        Text(text = "to your $room_name",
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
            }

        }

    }

}

//fun backgroundImageReturn(room_name: String?) {
//    when(room_name){
//        BED_ROOM ->
//        LIVING_ROOM ->
//        KITCHEN ->
//        GARAGE ->
//        HOME_OUTSIDE ->
//    }


//}

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



fun dataRequest(context: android.content.Context, roomId: String): String? {

    firebaseDatabase = FirebaseDatabase.getInstance()
    databaseReference = firebaseDatabase.getReference(USER_PATH)
    auth = Firebase.auth

    val user = FirebaseAuth.getInstance().currentUser!!
    var uId = user.uid


    databaseReference.addValueEventListener(object : ValueEventListener {
        override fun onDataChange(snapshot: DataSnapshot) {
            try {
                Log.d("DATABASE", "uID: " + uId.toString())
                room_name = snapshot.child(uId).child("rooms").child(roomId)
                    .child("type").getValue().toString()


            } catch (e: Exception) {
                Log.d("DATABASE", "ERROR")
                Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()
            }
        }

        override fun onCancelled(error: DatabaseError) {
            Toast.makeText(context, "Error", Toast.LENGTH_SHORT).show()

        }

    })

    return room_name
}
    

