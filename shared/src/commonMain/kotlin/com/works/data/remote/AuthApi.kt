package com.works.data.remote

import com.works.data.dto.UserLoginRequestDto
import com.works.data.dto.UserLoginResponseDto
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class AuthApi(private val client: HttpClient) {

    suspend fun login( req: UserLoginRequestDto ) : UserLoginResponseDto =
         client.post("auth/login") {
            contentType(ContentType.Application.Json)
            setBody(req)
        }.body<UserLoginResponseDto>()

}