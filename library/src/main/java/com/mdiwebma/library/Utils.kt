package com.mdiwebma.library

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.widget.Toast

internal object Utils {

    fun copyText(text: String?) {
        val context = ApplicationHolder.application
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("label", text)
        clipboard.primaryClip = clip
        Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show()
    }
}
