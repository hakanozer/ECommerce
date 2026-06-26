package com.works.data.remote

import com.works.data.dto.UserLoginRequestDto
import com.works.data.dto.UserLoginResponseDto
import com.works.data.dto.UserProfileResponseDto
import com.works.domain.ApiResult
import io.ktor.client.HttpClient
import io.ktor.client.call.body
import io.ktor.client.plugins.ClientRequestException
import io.ktor.client.request.get
import io.ktor.client.request.header
import io.ktor.client.request.headers
import io.ktor.client.request.post
import io.ktor.client.request.setBody
import io.ktor.http.ContentType
import io.ktor.http.contentType

class AuthApi(private val client: HttpClient) {

    suspend fun login(req: UserLoginRequestDto): ApiResult<UserLoginResponseDto>{
        return try {
            val response = client.post("auth/login") {
                contentType(ContentType.Application.Json)
                setBody(req)
            }
            ApiResult.Success(response.body())
        } catch (e: ClientRequestException) {
            ApiResult.Error(e.response.status.value, "Unauthorized")
        } catch (e: Exception) {
            ApiResult.NetworkError(e.message ?: "Unknown error")
        }
    }



    suspend fun profile(
        jwt: String
    ): ApiResult<UserProfileResponseDto> {
        return try {
            val response = client.get("profile/me") {
                header("Authorization", "Bearer $jwt")
            }
            ApiResult.Success(response.body())
        } catch (e: ClientRequestException) {
            ApiResult.Error(e.response.status.value, "Unauthorized")
        } catch (e: Exception) {
            ApiResult.NetworkError(e.message ?: "Unknown error")
        }
    }


    suspend fun logout(
        jwt: String
    ): ApiResult<Unit> {
        return try {
            client.post("auth/logout") {
                header("Authorization", "Bearer $jwt")
            }
            ApiResult.Success(Unit)
        } catch (e: ClientRequestException) {
            ApiResult.Error(e.response.status.value, "Unauthorized")
        } catch (e: Exception) {
            ApiResult.NetworkError(e.message ?: "Unknown error")
        }
    }

}