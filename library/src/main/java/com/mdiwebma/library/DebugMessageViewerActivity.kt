package com.mdiwebma.library

import android.app.Activity
import android.app.AlertDialog
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Toast


class DebugMessageViewerActivity : Activity() {

    companion object {
        private const val EXTRA_MESSAGE = "message"
        private const val EXTRA_TITLE = "title"

        @JvmStatic
        fun createIntent(conext: Context, title: String, message: String?) =
            Intent(conext, DebugMessageViewerActivity::class.java)
                .putExtra(EXTRA_MESSAGE, message)
                .putExtra(EXTRA_TITLE, title)
                .addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        AlertDialog.Builder(this)
            .setTitle(intent.getStringExtra(EXTRA_TITLE))
            .setMessage(intent.getStringExtra(EXTRA_MESSAGE))
            .setCancelable(false)
            .setOnDismissListener { finish() }
            .setPositiveButton("OK", null)
            .setNegativeButton("Copy") { _, _ -> Utils.copyText(intent.getStringExtra(EXTRA_MESSAGE)) }
            .setOnDismissListener { finish() }
            .show()
    }
}
