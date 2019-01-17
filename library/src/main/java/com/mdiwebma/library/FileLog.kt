package com.mdiwebma.library

import android.content.pm.PackageManager
import android.os.Build
import android.util.Log
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

object FileLog {

    @JvmStatic
    var canWrite: Boolean = BuildConfig.DEBUG

    private var logFile: File? = null
    private var fileOutputStream: FileOutputStream? = null
    private val formatter = SimpleDateFormat("[yyyy.MM.dd HH:mm:ss]", Locale.getDefault())

    @JvmStatic
    @Synchronized
    fun write(tag: String, msg: String?) {
        if (!canWrite || msg == null) return
        try {
            val sb = StringBuilder()
            sb.append(formatter.format(Date()))
                .append("[").append(tag).append("] ")
                .append(msg)
                .append("\n")
            getFileStream()?.write(sb.toString().toByteArray())
        } catch (ex: IOException) {
            Log.e("Exception", ex.message, ex)
        }
    }

    private fun getFileStream(): FileOutputStream? {
        if (fileOutputStream != null) {
            return fileOutputStream
        }

        val context = ApplicationHolder.application!!
        val sb = StringBuilder()
        sb.append("log_")
        sb.append(SimpleDateFormat("yyyy.MM.dd", Locale.getDefault()).format(Date()))
        sb.append(".txt")

        val directoryName =
            "log-" + context.packageName.substring(context.packageName.lastIndexOf('.') + 1)

        logFile = if (FileUtils.isExternalStorageWritable()) {
            FileUtils.getExternalStorageFile(directoryName, sb.toString())
        } else {
            FileUtils.getAppExternalCacheFile(directoryName, sb.toString())
        }

        if (fileOutputStream == null) {
            try {
                fileOutputStream = FileOutputStream(logFile, true)
            } catch (ex: FileNotFoundException) {
                Log.e("Exception", ex.message, ex)
            }

        }

        sb.setLength(0)
        sb.append("\n-----------------------------------------\n")
        try {
            val info = context.packageManager.getPackageInfo(context.packageName, 0)
            sb.append(Build.MANUFACTURER).append(" ")
                .append(Build.MODEL).append(" ")
                .append(Build.DISPLAY).append("\n")
                .append(context.packageName)
                .append(" Version:")
                .append(info.versionName)
                .append("(")
                .append(info.versionCode)
                .append(")")
        } catch (ex: PackageManager.NameNotFoundException) {
            Log.e("Exception", ex.message, ex)
        } catch (ex: Exception) {
            Log.e("Exception", ex.message, ex)
            return null
        }

        sb.append("\n-----------------------------------------\n")

        // initial start up
        try {
            fileOutputStream?.write(sb.toString().toByteArray())
        } catch (ex: IOException) {
            Log.e("Exception", ex.toString(), ex)
        }

        return fileOutputStream
    }


    @JvmStatic
    @Synchronized
    fun closeFileStream() {
        if (fileOutputStream != null) {
            try {
                fileOutputStream?.close()
            } catch (ex: IOException) {
                Log.e("Exception", ex.toString(), ex)
            }
            fileOutputStream = null
        }
    }
}
