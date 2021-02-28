package com.studio.mattiaferigutti.mvvm_pattern_sample

import android.content.Context
import android.os.Handler
import android.widget.Toast

fun postDelayed(delayMillis: Long, task: () -> Unit) {
    Handler().postDelayed(task, delayMillis)
}

fun makeToast(context: Context, message: String) {
    Toast.makeText(context, message, Toast.LENGTH_SHORT).show()
}