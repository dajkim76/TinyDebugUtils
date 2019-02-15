package com.mdiwebma.library

import android.content.pm.PackageManager
import android.os.Build
import android.os.Looper
import android.util.Log
import java.io.File
import java.io.FileNotFoundException
import java.io.FileOutputStream
import java.io.IOException
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import java.util.concurrent.Executors

object FileLog {

    @JvmStatic
    var canWrite: Boolean = false

    private var logFile: File? = null
    private var fileOutputStream: FileOutputStream? = null
    private val dateFormatter = SimpleDateFormat("[yyyy.MM.dd]", Locale.getDefault())
    private val timeFormatter = SimpleDateFormat("HH:mm:ss", Locale.getDefault())
    private val executor = Executors.newSingleThreadExecutor()
    private var lastDayOfYear = -1

    @JvmStatic
    @Synchronized
    fun write(tag: String, msg: String?) {
        if (!canWrite || msg == null) return
        val threadType = if (Looper.myLooper() == Looper.getMainLooper()) "M" else "W"
        executor.execute {
            try {
                val sb = StringBuilder()
                val calendar = Calendar.getInstance()
                val dayOfYear = calendar.get(Calendar.DAY_OF_YEAR)
                val date = calendar.time

                if (lastDayOfYear != dayOfYear) {
                    sb.append(dateFormatter.format(date)).append("\n")
                    lastDayOfYear = dayOfYear
                }
                sb.append(timeFormatter.format(Date())).append(" ")
                    .append(threadType).append("/")
                    .append(tag).append(": ")
                    .append(msg)
                    .append("\n")
                getFileStream()?.write(sb.toString().toByteArray())
            } catch (ex: IOException) {
                Log.e("Exception", ex.message, ex)
            }
        }
    }

    private fun getFileStream(): FileOutputStream? {
        if (fileOutputStream != null) {
            return fileOutputStream
        }

        val context = ApplicationHolder.application
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
            sb.append("App: ").append(context.packageName).append("\n")
                .append(Utils.getVersionBuildInfo().replace('\t', '\n')).append("\n")
                .append("OS Version: ").append(Build.VERSION.SDK_INT).append(", ")
                .append("OS Locale: ").append(Locale.getDefault().toString()).append("\n")
                .append("Device: ").append(Build.MANUFACTURER).append("/")
                .append(Build.MODEL).append("/")
                .append(Build.DISPLAY)
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
