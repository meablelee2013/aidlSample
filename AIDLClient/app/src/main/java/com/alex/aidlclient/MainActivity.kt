package com.alex.aidlclient

import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.os.Bundle
import android.os.IBinder
import android.util.Log
import androidx.appcompat.app.AppCompatActivity
import com.alex.aidlserver.IMyAidlInterface

class MainActivity : AppCompatActivity() {
    private var mBound = false

    private val TAG = "Alex"

    var serviceConnection: ServiceConnection = object : ServiceConnection {
        override fun onServiceConnected(name: ComponentName, service: IBinder) {
            Log.d(TAG, "onServiceConnected");
            val asInterface = IMyAidlInterface.Stub.asInterface(service)
            mBound = true

            try {
                var value = asInterface.getValue();
                Log.d(TAG, "get value: " + value);
            } catch (e: Exception) {
                e.printStackTrace()
                Log.d(TAG, "Exception");
            }

        }

        override fun onServiceDisconnected(name: ComponentName) {}
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        var intent = Intent()
        var componentName = ComponentName("com.alex.aidlserver", "com.alex.aidlserver.MyService");
        intent.component = componentName;
        bindService(intent, serviceConnection, Context.BIND_AUTO_CREATE)
    }

    override fun onDestroy() {
        super.onDestroy()
        if (mBound) {
            unbindService(serviceConnection)
        }
    }
}
