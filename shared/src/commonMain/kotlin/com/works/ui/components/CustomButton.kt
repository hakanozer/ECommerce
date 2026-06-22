package com.works.ui.components

import androidx.compose.foundation.Image
import androidx.compose.material3.Button
import androidx.compose.runtime.Composable
import ecommerce.shared.generated.resources.Res
import ecommerce.shared.generated.resources.ico_home
import org.jetbrains.compose.resources.painterResource

@Composable
fun CustomButton() {
    Button(onClick = {
        println("Custom Button Click")
    }) {
        Image(
            painter =painterResource(Res.drawable.ico_home),
            contentDescription = "Home",
        )
    }
}