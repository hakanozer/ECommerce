package com.works

import kotlinx.browser.window

class WasmPlatform: Platform {
    override val name: String = "Web with Kotlin/Wasm"
}

actual fun getPlatform(): Platform = WasmPlatform()

actual class TokenStorage {
    actual fun save(token: String) {
        window.localStorage.setItem("access_token", token)
    }

    actual fun read(): String? = window.localStorage.getItem("access_token")

    actual fun clear() {
        window.localStorage.removeItem("access_token")
    }
}