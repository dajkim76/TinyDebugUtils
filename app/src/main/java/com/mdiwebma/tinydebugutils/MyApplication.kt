package com.mdiwebma.tinydebugutils

import android.app.Application
import android.util.Log
import com.mdiwebma.library.ServerLog
import com.mdiwebma.library.ServerLogHandler
import com.mdiwebma.library.TinyDebugUtils

class MyApplication : Application() {

    override fun onCreate() {
        super.onCreate()
        if (BuildConfig.DEBUG) {
            val config = TinyDebugUtils.Config()
                .buildGitBranchName(BuildConfig.GIT_BRANCH_NAME)
                .buildTimeStamp(BuildConfig.BUILD_TIMESTAMP)
                .debugLogWriteToFile(true)
            TinyDebugUtils.init(this, config)
            //DebugLog.level = DebugLog.LEVEL_DISABLE
            //DebugLog.writeToFile = true
            //FileLog.canWrite = true
        } else {
            ServerLog.canSend = true
            ServerLog.setHandler(object : ServerLogHandler {
                override fun send(tag: String, message: String, throwable: Throwable?) {
                    Log.e("ServerLogHandler", "$tag -> $message", throwable)
                }
            })
        }
    }
}
