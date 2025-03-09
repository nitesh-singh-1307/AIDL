package com.example.aidlproject

import android.app.Service
import android.content.Intent
import android.os.IBinder
import com.example.aidlproject.aidl.IMyAidlInterface

class MyAidlService : Service() {
    private val binder = object : IMyAidlInterface.Stub() {
        override fun sendData(data: String) {
            val viewModel = AidlViewModel(application)
            viewModel.handleIncomingData(data)
        }

        override fun getData(): String? {
           return "AIDL from service response"
        }

    }

    override fun onBind(intent: Intent): IBinder {
       return binder
    }
}