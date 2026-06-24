package com.works

import java.util.prefs.Preferences

class JVMPlatform: Platform {
    override val name: String = "Java ${System.getProperty("java.version")}"
}

actual fun getPlatform(): Platform = JVMPlatform()

actual class TokenStorage actual constructor() {
    private val prefs = Preferences.userNodeForPackage(TokenStorage::class.java)

    actual fun save(token: String) {
        prefs.put("access_token", token)
    }

    actual fun read(): String? = prefs.get("access_token", null)

    actual fun clear() {
        prefs.remove("access_token")
    }
}