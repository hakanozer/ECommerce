package com.works

import web.navigator.navigator

class JsPlatform : Platform {
	override val name: String = navigator.userAgent
}

actual fun getPlatform(): Platform = JsPlatform()
