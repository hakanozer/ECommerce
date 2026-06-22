package com.works

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.safeContentPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.works.ui.components.CustomButton
import com.works.ui.theme.ECommerceTheme
import org.jetbrains.compose.resources.painterResource

import ecommerce.shared.generated.resources.Res
import ecommerce.shared.generated.resources.compose_multiplatform

@Composable
@Preview
fun App() {
    ECommerceTheme {
        // bir button oluşturup tıklayınca bir text gösterelim
        Column(
            modifier = Modifier
                .verticalScroll(rememberScrollState())
                .fillMaxSize()
                .background(MaterialTheme.colorScheme.background)
                .safeContentPadding(),
        ) {
            Text(text = "Başlık", style = MaterialTheme.typography.titleLarge)
            Text(text = "Açıklama", style = MaterialTheme.typography.bodyLarge)
            Button(
                modifier = Modifier.fillMaxWidth(),
                onClick = {
                    println("Button Click")
                },
            ) {
                Text(text = "Register")
            }
            Row (
                modifier = Modifier.fillMaxWidth(),
                horizontalArrangement = Arrangement.Center,
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier.weight(1f),
                    painter = painterResource(Res.drawable.compose_multiplatform),
                    contentDescription = "Logo",
                )
                Image(
                    modifier = Modifier.weight(1f),
                    painter = painterResource(Res.drawable.compose_multiplatform),
                    contentDescription = "Logo",
                )
            }
            Box(
                modifier = Modifier.fillMaxSize(),
                contentAlignment = Alignment.Center
            ) {
                Image(
                    modifier = Modifier.rotate(90f),
                    painter = painterResource(Res.drawable.compose_multiplatform),
                    contentDescription = "Logo",
                )
                Image(
                    painter = painterResource(Res.drawable.compose_multiplatform),
                    contentDescription = "Logo",
                )
            }

            Text(
                text = "Lorem Ipsum is simply dummy text of the printing and typesetting industry. Lorem Ipsum has been the industry's standard dummy text ever since 1966, when designers at Letraset and James Mosley, the librarian at St Bride Printing Library in London, took a 1914 Cicero translation and scrambled it to make dummy text for Letraset's Body Type sheets. It has survived not only many decades, but also the leap into electronic typesetting, remaining essentially unchanged. It was popularised thanks to these sheets and more recently with desktop publishing software including versions of Lorem Ipsum.", 
                style = MaterialTheme.typography.titleLarge
                )

            CustomButton(onClick = { btnClick() })
        }
    }
}


fun btnClick() {
    println("Custom Button Click")
}