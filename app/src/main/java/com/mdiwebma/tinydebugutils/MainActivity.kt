package com.mdiwebma.tinydebugutils

import android.os.AsyncTask
import android.os.Bundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import com.mdiwebma.library.DebugLog
import com.mdiwebma.library.DebugUtils

class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)
    }

    fun onClickCrash(v: View) {
        Integer.parseInt("abc")
    }

    fun onClickDebugUtilsException(v: View) {
        try {
            Integer.parseInt("abc")
        } catch (ex: Exception) {
            DebugUtils.exception(ex)
        }
    }

    fun onClickFinishDebugUtilsException(v: View) {
        finish()
        Task1().execute()
    }

    class Task1 : AsyncTask<Unit, Void, Unit>() {
        override fun doInBackground(vararg params: Unit?) {
            try {
                Integer.parseInt("def")
            } catch (ex: Exception) {
                DebugUtils.exception(ex)
            }
        }
    }

    fun onClickDebugLog(v: View) {
        DebugLog.e("TAG", "DebugLov.v")
        object : Thread() {
            override fun run() {
                DebugLog.e("TAG", "DebugLov.v at thread")
            }
        }.start()
    }
}
