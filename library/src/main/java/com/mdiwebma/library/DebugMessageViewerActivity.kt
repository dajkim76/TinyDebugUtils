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
            .setNegativeButton("Copy") { _, _ ->
                val clipboard = getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
                val clip = ClipData.newPlainText("label", intent.getStringExtra(EXTRA_MESSAGE))
                clipboard.primaryClip = clip
                Toast.makeText(this@DebugMessageViewerActivity, "Done", Toast.LENGTH_SHORT).show()
            }
            .setOnDismissListener { finish() }
            .show()
    }
}
