package com.mdiwebma.library

import android.app.Activity
import android.app.AlertDialog
import android.content.Context
import android.content.Intent
import android.os.Bundle

class DebugMessageViewerActivity : Activity() {

    companion object {
        private const val EXTRA_MESSAGE = "message"

        @JvmStatic
        fun createIntent(conext: Context, message: String?) =
            Intent(conext, DebugMessageViewerActivity::class.java)
                .putExtra(EXTRA_MESSAGE, message)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AlertDialog.Builder(this)
            .setTitle("Debug!")
            .setMessage(intent.getStringExtra(EXTRA_MESSAGE))
            .setCancelable(false)
            .setOnDismissListener { finish() }
            .setPositiveButton("OK", null)
            .setOnDismissListener { finish() }
            .show()
    }
}
