package com.mdiwebma.library

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.pm.PackageManager
import android.util.Log
import android.widget.Toast
import java.text.SimpleDateFormat
import java.util.Date


internal object Utils {

    var buildTimeStamp: Long? = null
    var buildGitBranchName: String? = null

    fun copyText(text: String?) {
        val context = ApplicationHolder.application
        val clipboard = context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
        val clip = ClipData.newPlainText("label", text)
        clipboard.primaryClip = clip
        Toast.makeText(context, "Done", Toast.LENGTH_SHORT).show()
    }

    private fun getBuildDatetimeString(): String? {
        if (buildTimeStamp != null) {
            return SimpleDateFormat.getDateTimeInstance().format(buildTimeStamp)
        }

        return try {
            val context = ApplicationHolder.application
            val pm = context.packageManager
            val packageInfo = pm.getPackageInfo(context.packageName, 0)
            SimpleDateFormat.getDateTimeInstance().format(Date(packageInfo.lastUpdateTime))
        } catch (ex: PackageManager.NameNotFoundException) {
            Log.e("Utils", "", ex)
            null
        }
    }

    fun getVersionBuildInfo(): String {
        val context = ApplicationHolder.application
        val pm = context.packageManager
        val packageInfo = pm.getPackageInfo(context.packageName, 0)
        val datetimeString = getBuildDatetimeString()
        val sb = StringBuilder()

        sb.append("Version: ")
            .append(packageInfo.versionName)
            .append("(")
            .append(packageInfo.versionCode)
            .append(")\t")
        buildGitBranchName?.let { sb.append("Branch: ").append(it).append("\t") }
        datetimeString?.let { sb.append("BuildTime: ").append(it) }
        return sb.toString()
    }
}
