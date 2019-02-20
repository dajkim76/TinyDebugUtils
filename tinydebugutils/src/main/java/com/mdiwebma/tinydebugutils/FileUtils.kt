package com.mdiwebma.tinydebugutils

import android.Manifest
import android.content.pm.PackageManager
import android.os.Environment
import android.os.Process
import java.io.File


internal object FileUtils {

    @JvmStatic
    fun isExternalStorageWritable(): Boolean {
        val context = ApplicationHolder.application
        return context.checkPermission(
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Process.myPid(),
            Process.myUid()
        ) == PackageManager.PERMISSION_GRANTED
    }

    /**
     * Return File("/sdcard/{directoryName}/{fileName}")
     */
    @JvmStatic
    fun getExternalStorageFile(directoryName: String, fileName: String): File {
        val dir = File(Environment.getExternalStorageDirectory(), directoryName)
        dir.mkdirs()
        return File(dir, fileName)
    }

    /**
     * Return File("/sdcard/Android/data/{PACKAGE_ID}/cache/{directoryName}/{fileName}")
     */
    @JvmStatic
    fun getAppExternalCacheFile(directoryName: String, fileName: String): File {
        val context = ApplicationHolder.application
        val dir = File(context.externalCacheDir, directoryName)
        dir.mkdirs()
        return File(dir, fileName)
    }

}
