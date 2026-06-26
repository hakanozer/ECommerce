package com.works.ui.screens.auth

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Email
import androidx.compose.material.icons.outlined.Lock
import androidx.compose.material.icons.outlined.Visibility
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.material3.Surface
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.input.PasswordVisualTransformation
import androidx.compose.ui.text.input.VisualTransformation
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.works.TokenStorage
import com.works.data.dto.UserLoginRequestDto
import com.works.data.remote.AuthApi
import com.works.domain.ApiResult
import com.works.domain.AppStore
import com.works.platform.Platform
import com.works.platform.getPlatform
import kotlinx.coroutines.launch
import org.koin.compose.koinInject

import com.works.platform.openSampleActivity

private val MainColor = Color(0xFFFF3B1F)

@Composable
fun LoginScreen(
    onLoginSuccess: () -> Unit,
    onNavigateToSignup: () -> Unit
) {

    val authApi: AuthApi = koinInject()
    val tokenStorage: TokenStorage = koinInject()
    val scope = rememberCoroutineScope()

    var email by remember {
        mutableStateOf("hakanozer02@gmail.com")
    }

    var password by remember {
        mutableStateOf("123456")
    }

    var passwordVisible by remember {
        mutableStateOf(false)
    }

    var error by remember { mutableStateOf<String?>(null) }

    var width  by remember {
        mutableStateOf(Modifier.fillMaxSize())
    }
    if (getPlatform() == Platform.Desktop) {
        width = Modifier.width(400.dp)
    }

    Box(
        modifier = width,
        contentAlignment = Alignment.Center
    ) {


        Surface(
            modifier = Modifier
                .fillMaxWidth(0.85f)
                .padding(vertical = 32.dp),
            shape = RoundedCornerShape(24.dp),
            border = BorderStroke(2.dp, MainColor),
            color = Color.White
        ) {

            Column(
                modifier = Modifier
                    .padding(24.dp)
                    .fillMaxWidth(),
                horizontalAlignment = Alignment.CenterHorizontally
            ) {

                Spacer(modifier = Modifier.height(20.dp))

                Text(
                    text = "User Login",
                    fontSize = 32.sp,
                    fontWeight = FontWeight.Bold,
                    color = MainColor
                )

                error?.let {
                    Text(
                        text = "Hata oldu, Tekrar deneyiniz!",
                        color = MaterialTheme.colorScheme.error,
                        modifier = Modifier.padding(16.dp)
                    )
                }


                Spacer(modifier = Modifier.height(40.dp))

                OutlinedTextField(
                    value = email,
                    onValueChange = { email = it },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Email,
                            contentDescription = null,
                            tint = MainColor
                        )
                    },
                    placeholder = {
                        Text("email")
                    },
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = MainColor,
                        unfocusedIndicatorColor = MainColor
                    )
                )

                Spacer(modifier = Modifier.height(20.dp))

                OutlinedTextField(
                    value = password,
                    onValueChange = { password = it },
                    modifier = Modifier.fillMaxWidth(),
                    leadingIcon = {
                        Icon(
                            imageVector = Icons.Outlined.Lock,
                            contentDescription = null,
                            tint = MainColor
                        )
                    },
                    trailingIcon = {
                        IconButton(
                            onClick = {
                                passwordVisible = !passwordVisible
                            }
                        ) {
                            Icon(
                                imageVector = Icons.Outlined.Visibility,
                                contentDescription = null,
                                tint = MainColor
                            )
                        }
                    },
                    placeholder = {
                        Text("password")
                    },
                    visualTransformation =
                        if (passwordVisible)
                            VisualTransformation.None
                        else
                            PasswordVisualTransformation(),
                    shape = RoundedCornerShape(12.dp),
                    colors = TextFieldDefaults.colors(
                        focusedIndicatorColor = MainColor,
                        unfocusedIndicatorColor = MainColor
                    )
                )

                Spacer(modifier = Modifier.height(32.dp))

                Row(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalArrangement = Arrangement.SpaceBetween
                ) {

                    OutlinedButton(
                        onClick = onNavigateToSignup,
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(2.dp, MainColor)
                    ) {
                        Text(
                            text = "SIGN UP",
                            color = MainColor
                        )
                    }

                    Spacer(modifier = Modifier.width(16.dp))

                    Button(
                        onClick =  {
                            scope.launch {
                                val loginRequestDto = UserLoginRequestDto(email, password)
                                val response = authApi.login(loginRequestDto)

                                when (response) {
                                    is ApiResult.Success -> {
                                        val user = response.data
                                        AppStore.login(user.data.user.name, user.data.access_token)
                                        tokenStorage.save(user.data.access_token)
                                        onLoginSuccess()

                                    }

                                    is ApiResult.Error -> {
                                        error = response.message
                                    }

                                    is ApiResult.NetworkError -> {
                                        error = response.message
                                    }
                                }
                            }
                        },
                        modifier = Modifier.weight(1f),
                        shape = RoundedCornerShape(12.dp),
                        colors = ButtonDefaults.buttonColors(
                            containerColor = MainColor
                        )
                    ) {
                        Text("LOGIN")
                    }
                }

                Spacer(modifier = Modifier.height(16.dp))

                if (getPlatform() == Platform.Android) {
                    OutlinedButton(
                        onClick = { openSampleActivity() },
                        modifier = Modifier.fillMaxWidth(),
                        shape = RoundedCornerShape(12.dp),
                        border = BorderStroke(2.dp, MainColor)
                    ) {
                        Text(
                            text = "OPEN SAMPLE ACTIVITY",
                            color = MainColor
                        )
                    }
                }

                Spacer(modifier = Modifier.height(250.dp))
            }
        }
    }
}