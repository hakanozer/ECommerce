package com.works

import android.os.Build
import android.content.Context
import androidx.core.content.edit

class AndroidPlatform : Platform {
	override val name: String = "Android ${Build.VERSION.SDK_INT}"
}

actual fun getPlatform(): Platform = AndroidPlatform()

actual class TokenStorage actual constructor() {
	companion object {
		lateinit var context: Context
	}

	private val prefs by lazy { context.getSharedPreferences("auth", Context.MODE_PRIVATE) }

	actual fun save(token: String) {
		prefs.edit { putString("access_token", token) }
	}

	actual fun read(): String? = prefs.getString("access_token", null)

	actual fun clear() {
		prefs.edit { remove("access_token") }
	}
}
