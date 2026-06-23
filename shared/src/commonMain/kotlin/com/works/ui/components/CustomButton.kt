package com.works.ui.components

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.works.shared.Res
import com.works.shared.ic_home
import org.jetbrains.compose.resources.painterResource

@Composable
fun CustomButton(width: Dp = 100.dp, height: Dp = 100.dp, onClick: () -> Unit) {
    Button(
        // genişlik 100 ve yükseklik 48 dp olacak şekilde ayarlayalım
        modifier = Modifier
            .width(width)
            .height(height),
        contentPadding = ButtonDefaults.ContentPadding,
        onClick = onClick,
    )
    {
        Image(
            painter =painterResource(Res.drawable.ic_home),
            contentDescription = "Home",
        )
    }
}