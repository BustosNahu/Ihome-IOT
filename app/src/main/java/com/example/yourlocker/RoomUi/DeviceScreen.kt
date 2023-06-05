package com.example.yourlocker.RoomUi

import android.text.Layout.Alignment
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.core.view.TintableBackgroundView
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.android.material.bottomappbar.BottomAppBar.MenuAlignmentMode

@Composable
fun DeviceScreen(navController: NavController) {
    Text(text = "Hola",
        Modifier.fillMaxWidth()
    )

}

@Preview(showBackground = true,
    showSystemUi = true)
@Composable
fun DeviceScreenPreview() {
    DeviceScreen(navController = rememberNavController())
}