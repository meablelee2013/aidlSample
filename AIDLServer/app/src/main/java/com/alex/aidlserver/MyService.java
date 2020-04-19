package com.alex.aidlserver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;

import androidx.annotation.Nullable;

public class MyService extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return stub;
    }

    IMyAidlInterface.Stub stub = new IMyAidlInterface.Stub() {
        @Override
        public int getValue() throws RemoteException {
            return 99;
        }
    };

    public MyService() {
    }

}
