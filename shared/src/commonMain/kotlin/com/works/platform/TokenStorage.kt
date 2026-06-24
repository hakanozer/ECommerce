package com.works

expect class TokenStorage() {
    fun save(token: String)
    fun read(): String?
    fun clear()
}



class GetAccessTokenUseCase(
    private val tokenStorage: TokenStorage
) {
    operator fun invoke(): String? = tokenStorage.read()
}