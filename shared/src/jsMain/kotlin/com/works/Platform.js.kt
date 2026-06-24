package com.works

import kotlinx.browser.window
import web.navigator.navigator

class JsPlatform : Platform {
	override val name: String = navigator.userAgent
}

actual fun getPlatform(): Platform = JsPlatform()

actual class TokenStorage actual constructor() {
	actual fun save(token: String) {
		window.localStorage.setItem("access_token", token)
	}

	actual fun read(): String? = window.localStorage.getItem("access_token")

	actual fun clear() {
		window.localStorage.removeItem("access_token")
	}
}
