package com.works.platform

import android.content.Intent
import com.works.SampleActivity
import com.works.TokenStorage

actual fun openSampleActivity() {
    val context = TokenStorage.context
    val intent = Intent(context, SampleActivity::class.java).apply {
        addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }
    context.startActivity(intent)
}
