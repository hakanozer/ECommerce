package com.works

import platform.Foundation.NSUserDefaults

actual class TokenStorage {
    private val defaults = NSUserDefaults.standardUserDefaults

    actual fun save(token: String) {
        defaults.setObject(token, forKey = "access_token")
    }

    actual fun read(): String? = defaults.stringForKey("access_token")

    actual fun clear() {
        defaults.removeObjectForKey("access_token")
    }
}