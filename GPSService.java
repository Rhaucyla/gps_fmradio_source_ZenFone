package com.broadcom.gps;

import android.app.Service;
import android.bluetooth.BluetoothAdapter;
import android.bluetooth.IGPSService.Stub;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.os.SystemProperties;
import android.util.Log;

public class GPSService extends Service {
    private static final boolean f79D = true;
    private static final int MSG_INTERNAL_ENABLE_RADIO = 1;
    public static final String TAG = "GPSService";
    private static final boolean f80V = true;
    private static final int WAIT_FOR_BOOT_BT_CONNECTED_DELAY = 300;
    private static final int WAIT_FOR_BT_DELAY = 6000;
    private static int retrycounter;
    private BluetoothAdapter mAdapter;
    private GPSServiceStub mBinder;
    private boolean mBootCompleted;
    private final BroadcastReceiver mBroadcastReciever;
    private Handler mHandler;
    private int mRadioCount;
    private int mSocketCount;

    /* renamed from: com.broadcom.gps.GPSService.1 */
    class C00721 extends Handler {
        C00721() {
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case GPSService.MSG_INTERNAL_ENABLE_RADIO /*1*/:
                    GPSService.this.mHandler.removeMessages(GPSService.MSG_INTERNAL_ENABLE_RADIO);
                    Log.d(GPSService.TAG, "received MSG_INTERNAL_ENABLE_RADIO");
                    GPSService.this.internal_enable_radio();
                    Log.d(GPSService.TAG, "exited MSG_INTERNAL_ENABLE_RADIO");
                default:
            }
        }
    }

    /* renamed from: com.broadcom.gps.GPSService.2 */
    class C00732 extends BroadcastReceiver {
        C00732() {
        }

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            if (action.equals("android.bluetooth.adapter.action.RADIO_STATE_CHANGED")) {
                int state = intent.getIntExtra("android.bluetooth.adapter.extra.STATE", Integer.MIN_VALUE);
                Log.d(GPSService.TAG, "ACTION_RADIO_STATE_CHANGED: state = " + state);
                if (state == 14) {
                    GPSService.this.internal_gps_on();
                }
            } else if (action.equals("android.intent.action.BOOT_COMPLETED")) {
                Log.d(GPSService.TAG, "received ACTION_BOOT_COMPLETED");
                GPSService.this.mBootCompleted = GPSService.f80V;
                GPSService.this.mHandler.sendEmptyMessageDelayed(GPSService.MSG_INTERNAL_ENABLE_RADIO, 300);
            } else if (action.equals("android.intent.action.USER_SWITCHED")) {
                Log.d(GPSService.TAG, "received ACTION_USER_SWITCHED");
                GPSService.this.internal_enable_radio();
            }
        }
    }

    private static final class GPSServiceStub extends Stub {
        private static final String TAG = "GPSService";
        private GPSService mSvc;

        private GPSService getService() {
            if (this.mSvc != null) {
                return this.mSvc;
            }
            Log.e(TAG, "getService() - Service requested, but not available!");
            return null;
        }

        public GPSServiceStub(GPSService service) {
            this.mSvc = service;
            Log.d(TAG, "GPSServiceStub created" + this + "service" + service);
        }

        public void cleanUp() {
        }

        public boolean gps_on() {
            GPSService service = getService();
            if (service == null) {
                return false;
            }
            return service.gps_on();
        }

        public void start() {
            GPSService service = getService();
            if (service != null) {
                service.start();
            }
        }

        public void init() {
            GPSService service = getService();
            if (service != null) {
                service.init();
            }
        }

        public void stop() {
            GPSService service = getService();
            if (service != null) {
                service.stop();
            }
        }

        public boolean gps_off() {
            GPSService service = getService();
            if (service == null) {
                return false;
            }
            return service.gps_off();
        }
    }

    private static native void classInitNative();

    private native void cleanupNative();

    private native boolean gpsOffNative();

    private native boolean gpsOnNative();

    private native void initializeNative();

    static {
        retrycounter = 0;
        classInitNative();
    }

    public GPSService() {
        this.mRadioCount = 0;
        this.mSocketCount = 0;
        this.mBootCompleted = false;
        this.mHandler = new C00721();
        this.mBroadcastReciever = new C00732();
    }

    public void onCreate() {
        super.onCreate();
        this.mBinder = new GPSServiceStub(this);
        this.mAdapter = BluetoothAdapter.getDefaultAdapter();
        if (this.mAdapter == null) {
            Log.d(TAG, "GPS Service  onCreate - failed");
            return;
        }
        Log.d(TAG, "GPS Service  onCreate");
        registerFilters();
        if ("1".equals(SystemProperties.get("gps.bt.socket_restart", "0"))) {
            Log.d(TAG, "GPS Service restart and brcm.gps.socket.restart is 1 !! create socket again");
            this.mBootCompleted = f80V;
            this.mHandler.sendEmptyMessageDelayed(MSG_INTERNAL_ENABLE_RADIO, 6000);
        }
    }

    private void registerFilters() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.bluetooth.adapter.action.RADIO_STATE_CHANGED");
        intentFilter.addAction("android.intent.action.USER_SWITCHED");
        intentFilter.addAction("android.intent.action.BOOT_COMPLETED");
        registerReceiver(this.mBroadcastReciever, intentFilter);
    }

    private void internal_enable_radio() {
        if (this.mRadioCount <= 0) {
            this.mRadioCount += MSG_INTERNAL_ENABLE_RADIO;
            if (!this.mBootCompleted) {
                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                }
            }
            Log.d(TAG, "internal_enable_radio");
            if (!this.mAdapter.enableRadio(2)) {
                retrycounter += MSG_INTERNAL_ENABLE_RADIO;
                if (retrycounter <= 20) {
                    Log.d(TAG, "enable radio failed, retry " + retrycounter + " times.");
                    this.mHandler.sendEmptyMessageDelayed(MSG_INTERNAL_ENABLE_RADIO, 6000);
                }
            }
            retrycounter = 0;
        }
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        return MSG_INTERNAL_ENABLE_RADIO;
    }

    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        if (this.mBroadcastReciever != null) {
            unregisterReceiver(this.mBroadcastReciever);
        }
        this.mBinder.cleanUp();
        this.mBinder = null;
        Log.d(TAG, "onDestroy done");
    }

    public IBinder onBind(Intent intent) {
        Log.d(TAG, "Binding service...");
        return this.mBinder;
    }

    void init() {
        Log.d(TAG, "init - dummmy");
    }

    void start() {
        Log.d(TAG, "start - initializeNative");
        initializeNative();
    }

    void stop() {
        Log.d(TAG, "stop - never called !");
        cleanupNative();
    }

    private boolean internal_gps_on() {
        Log.d(TAG, "internal_gps_on mSocketCount=" + this.mSocketCount);
        if (this.mSocketCount <= 0) {
            this.mSocketCount += MSG_INTERNAL_ENABLE_RADIO;
            SystemProperties.set("gps.bt.socket_restart", "1");
            start();
            return gpsOnNative();
        }
        Log.d(TAG, "gps socket is already created");
        return false;
    }

    boolean gps_on() {
        Log.d(TAG, "gps_on - dummy");
        return false;
    }

    boolean gps_off() {
        Log.d(TAG, "gps_off - dummy");
        return false;
    }
}