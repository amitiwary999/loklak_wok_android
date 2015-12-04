package org.loklak.android.wok;

import android.app.IntentService;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

public class HarvestService extends IntentService {

    public HarvestService() {
        // method is required to keep the xml verifier for AndroidManifest silent
        super("harvester");
    }

    public HarvestService(String name) {
        super(name);
    }

    @Override
    protected void onHandleIntent(Intent intent) {
        Log.d("HarvestService", "onHandleIntent intent = " + intent);
        while (true) {
            if (Preferences.getConfig(Preferences.Key.APPGRANTED, false) && MainActivity.isConnectedWifi()) {
                //Log.d("HarvestService", "onHandleIntent " + intent + (MainActivity.sketch.canDraw() ? ", app canDraw" : ""));
                Harvester.harvest();
            }
            SystemClock.sleep(MainActivity.sketch.canDraw() ? 1000 : 10000);
        }
    }

    @Override
    public void onDestroy() {
        Log.d("HarvestService", "destroyed");
    }

    @Override
    public IBinder onBind(Intent intent) {
        Log.d("HarvestService", "onBind intend = " + intent);
        return mBinder;
    }

    private final IBinder mBinder = new LocalBinder();

    public class LocalBinder extends Binder {
        HarvestService getService() {
            return HarvestService.this;
        }
    }

}