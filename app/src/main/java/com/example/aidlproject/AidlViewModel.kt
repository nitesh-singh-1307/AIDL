package com.example.aidlproject

import android.app.Application
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.IBinder
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.example.aidlproject.aidl.IMyAidlInterface
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class AidlViewModel(application: Application): AndroidViewModel(application) {
    private val _mutableResult = MutableStateFlow("Initial Value")
    val sharedResult: StateFlow<String> = _mutableResult.asStateFlow()
    private var aidlService: IMyAidlInterface? = null
    private val serviceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            aidlService = IMyAidlInterface.Stub.asInterface(service)
            _mutableResult.value = aidlService?.getData() ?: "No Data"
        }

        override fun onServiceDisconnected(name: ComponentName?) {
            aidlService = null
            _mutableResult.value = "Service Disconnected"
        }
    }
    fun bindService(context: Context) {
        val intent = Intent(context, MyAidlService::class.java).apply {
            action = "com.example.aidlproject.aidl.IMyAidlInterface"
        }
        context.bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    fun unbindService(context: Context){
        context.unbindService(serviceConnection)
    }

    fun sendData(data: String) {
        Log.d("AidlViewModel", "sendData::::: $data")
        aidlService?.sendData(data)
    }

    fun handleIncomingData(data: String) {
        _mutableResult.value = data
    }

    override fun onCleared() {
        unbindService(getApplication())
        super.onCleared()
    }
}