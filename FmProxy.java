package com.broadcom.fm.fmreceiver;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.ServiceConnection;
import android.os.Handler;
import android.os.IBinder;
import android.os.Looper;
import android.os.RemoteException;
import android.os.SystemProperties;
import android.util.Log;
import com.broadcom.fm.fmreceiver.IFmReceiverCallback.Stub;

public final class FmProxy {
    public static final String ACTION_ON_AUDIO_MODE = "com.broadcom.bt.app.fm.action.ON_AUDIO_MODE";
    public static final String ACTION_ON_AUDIO_PATH = "com.broadcom.bt.app.fm.action.ON_AUDIO_PATH";
    public static final String ACTION_ON_AUDIO_QUAL = "com.broadcom.bt.app.fm.action.ON_AUDIO_QUAL";
    public static final String ACTION_ON_EST_NFL = "com.broadcom.bt.app.fm.action.ON_EST_NFL";
    public static final String ACTION_ON_RDS_DATA = "com.broadcom.bt.app.fm.action.ON_RDS_DATA";
    public static final String ACTION_ON_RDS_MODE = "com.broadcom.bt.app.fm.action.ON_RDS_MODE";
    public static final String ACTION_ON_SEEK_CMPL = "com.broadcom.bt.app.fm.action.ON_SEEK_CMPL";
    public static final String ACTION_ON_STATUS = "com.broadcom.bt.app.fm.action.ON_STATUS";
    public static final String ACTION_ON_VOL = "ON_VOL";
    public static final String ACTION_ON_WRLD_RGN = "com.broadcom.bt.app.fm.action.ON_WRLD_RGN";
    private static final String ACTION_PREFIX = "com.broadcom.bt.app.fm.action.";
    private static final int ACTION_PREFIX_LENGTH;
    public static final int AF_MODE_DEFAULT = 0;
    public static final int AF_MODE_OFF = 0;
    public static final int AF_MODE_ON = 1;
    public static final int AUDIO_MODE_AUTO = 0;
    public static final int AUDIO_MODE_BLEND = 3;
    public static final int AUDIO_MODE_MONO = 2;
    public static final int AUDIO_MODE_STEREO = 1;
    public static final int AUDIO_MODE_SWITCH = 3;
    public static final int AUDIO_PATH_DIGITAL = 3;
    public static final int AUDIO_PATH_NONE = 0;
    public static final int AUDIO_PATH_SPEAKER = 1;
    public static final int AUDIO_PATH_WIRE_HEADSET = 2;
    public static final int AUDIO_QUALITY_BLEND = 4;
    public static final int AUDIO_QUALITY_MONO = 2;
    public static final int AUDIO_QUALITY_STEREO = 1;
    public static final String BLUETOOTH_PERM = "android.permission.BLUETOOTH";
    private static final boolean f0D = true;
    public static final int DEEMPHASIS_50U = 0;
    public static final int DEEMPHASIS_75U = 64;
    public static final int DEEMPHASIS_TIME_DEFAULT = 64;
    public static final int DEFAULT_BROADCAST_RECEIVER_PRIORITY = 200;
    public static final String EXTRA_ALT_FREQ_MODE = "ALT_FREQ_MODE";
    public static final String EXTRA_AUDIO_MODE = "AUDIO_MODE";
    public static final String EXTRA_AUDIO_PATH = "AUDIO_PATH";
    public static final String EXTRA_FREQ = "FREQ";
    public static final String EXTRA_MUTED = "MUTED";
    public static final String EXTRA_NFL = "NFL";
    public static final String EXTRA_RADIO_ON = "RADIO_ON";
    public static final String EXTRA_RDS_DATA_TYPE = "RDS_DATA_TYPE";
    public static final String EXTRA_RDS_IDX = "RDS_IDX";
    public static final String EXTRA_RDS_MODE = "RDS_MODE";
    public static final String EXTRA_RDS_PRGM_SVC = "RDS_PRGM_SVC";
    public static final String EXTRA_RDS_PRGM_TYPE = "RDS_PRGM_TYPE";
    public static final String EXTRA_RDS_PRGM_TYPE_NAME = "RDS_PRGM_TYPE_NAME";
    public static final String EXTRA_RDS_TXT = "RDS_TXT";
    public static final String EXTRA_RSSI = "RSSI";
    public static final String EXTRA_SNR = "SNR";
    public static final String EXTRA_STATUS = "STATUS";
    public static final String EXTRA_SUCCESS = "RDS_SUCCESS";
    public static final String EXTRA_VOL = "VOL";
    public static final String EXTRA_WRLD_RGN = "WRLD_RGN";
    public static final int FM_MAX_SNR_THRESHOLD = 31;
    public static final int FM_MIN_SNR_THRESHOLD = 0;
    public static final String FM_RECEIVER_PERM = "android.permission.ACCESS_FM_RECEIVER";
    public static final int FM_VOLUME_MAX = 255;
    public static final int FREQ_STEP_100KHZ = 0;
    public static final int FREQ_STEP_50KHZ = 16;
    public static final int FREQ_STEP_DEFAULT = 0;
    public static final int FUNCTIONALITY_DEFAULT = 0;
    public static final int FUNC_AF = 64;
    public static final int FUNC_RBDS = 32;
    public static final int FUNC_RDS = 16;
    public static final int FUNC_REGION_DEFAULT = 0;
    public static final int FUNC_REGION_EUR = 1;
    public static final int FUNC_REGION_JP = 2;
    public static final int FUNC_REGION_JP_II = 3;
    public static final int FUNC_REGION_NA = 0;
    public static final int FUNC_SOFTMUTE = 256;
    public static final boolean LIVE_AUDIO_QUALITY_DEFAULT = false;
    public static final int MIN_SIGNAL_STRENGTH_DEFAULT = 105;
    public static final int NFL_DEFAULT = 1;
    public static final int NFL_FINE = 2;
    public static final int NFL_LOW = 0;
    public static final int NFL_MED = 1;
    public static final int RDS_COND_NONE = 0;
    public static final int RDS_COND_PTY = 1;
    public static final int RDS_COND_PTY_VAL = 0;
    public static final int RDS_COND_TP = 2;
    public static final int RDS_FEATURE_PS = 4;
    public static final int RDS_FEATURE_PTY = 8;
    public static final int RDS_FEATURE_PTYN = 32;
    public static final int RDS_FEATURE_RT = 64;
    public static final int RDS_FEATURE_TP = 16;
    public static final int RDS_MODE_DEFAULT_ON = 1;
    public static final int RDS_MODE_OFF = 0;
    public static final int RDS_MODE_RBDS_ON = 3;
    public static final int RDS_MODE_RDS_ON = 2;
    public static final int SCAN_MODE_DOWN = 0;
    public static final int SCAN_MODE_FAST = 1;
    public static final int SCAN_MODE_FULL = 130;
    public static final int SCAN_MODE_NORMAL = 0;
    public static final int SCAN_MODE_UP = 128;
    public static final int SIGNAL_POLL_INTERVAL_DEFAULT = 100;
    public static final int STATUS_FAIL = 1;
    public static final int STATUS_ILLEGAL_COMMAND = 3;
    public static final int STATUS_ILLEGAL_PARAMETERS = 4;
    public static final int STATUS_OK = 0;
    public static final int STATUS_SERVER_FAIL = 2;
    private static final String TAG = "FmProxy";
    protected BroadcastReceiver mBroadcastReceiver;
    private IFmReceiverCallback mCallback;
    private ServiceConnection mConnection;
    protected Context mContext;
    protected EventCallbackHandler mEventCallbackHandler;
    private IFmReceiverEventHandler mEventHandler;
    protected boolean mIsAvailable;
    protected IFmProxyCallback mProxyAvailCb;
    protected int mReceiverPriority;
    private IFmReceiverService mService;

    /* renamed from: com.broadcom.fm.fmreceiver.FmProxy.1 */
    class C00001 implements ServiceConnection {
        C00001() {
        }

        public void onServiceConnected(ComponentName className, IBinder service) {
            Log.d(FmProxy.TAG, "Fm proxy onServiceConnected() name = " + className + ", service = " + service);
            if (service == null || !(FmProxy.this.init(service) || FmProxy.this.mProxyAvailCb == null)) {
                Log.e(FmProxy.TAG, "Unable to create proxy");
            }
            if (FmProxy.this.mProxyAvailCb != null) {
                FmProxy.this.mProxyAvailCb.onProxyAvailable(FmProxy.this);
                FmProxy.this.mProxyAvailCb = null;
            }
        }

        public void onServiceDisconnected(ComponentName className) {
            Log.d(FmProxy.TAG, "Fm Proxy object disconnected");
            FmProxy.this.mService = null;
        }
    }

    private class EventCallbackHandler extends Thread {
        public Handler mHandler;

        public EventCallbackHandler() {
            setPriority(10);
        }

        public void run() {
            Looper.prepare();
            this.mHandler = new Handler();
            Looper.loop();
        }

        public void finish() {
            if (this.mHandler != null) {
                Looper l = this.mHandler.getLooper();
                if (l != null) {
                    l.quit();
                }
            }
        }
    }

    private class FmBroadcastReceiver extends BroadcastReceiver {
        private FmBroadcastReceiver() {
        }

        public void onReceive(Context context, Intent intent) {
            IFmReceiverEventHandler handler = FmProxy.this.mEventHandler;
            if (handler != null) {
                abortBroadcast();
                String action = intent.getAction();
                if (FmProxy.actionsEqual(FmProxy.ACTION_ON_STATUS, action, FmProxy.ACTION_PREFIX_LENGTH)) {
                    handler.onStatusEvent(intent.getIntExtra(FmProxy.EXTRA_FREQ, FmProxy.STATUS_OK), intent.getIntExtra(FmProxy.EXTRA_RSSI, FmProxy.STATUS_OK), intent.getIntExtra(FmProxy.EXTRA_SNR, -126), intent.getBooleanExtra(FmProxy.EXTRA_RADIO_ON, FmProxy.LIVE_AUDIO_QUALITY_DEFAULT), intent.getIntExtra(FmProxy.EXTRA_RDS_PRGM_TYPE, -1), intent.getStringExtra(FmProxy.EXTRA_RDS_PRGM_SVC), intent.getStringExtra(FmProxy.EXTRA_RDS_TXT), intent.getStringExtra(FmProxy.EXTRA_RDS_PRGM_TYPE_NAME), intent.getBooleanExtra(FmProxy.EXTRA_MUTED, FmProxy.LIVE_AUDIO_QUALITY_DEFAULT));
                } else if (FmProxy.actionsEqual(FmProxy.ACTION_ON_AUDIO_MODE, action, FmProxy.ACTION_PREFIX_LENGTH)) {
                    handler.onAudioModeEvent(intent.getIntExtra(FmProxy.EXTRA_AUDIO_MODE, -1));
                } else if (FmProxy.actionsEqual(FmProxy.ACTION_ON_AUDIO_PATH, action, FmProxy.ACTION_PREFIX_LENGTH)) {
                    handler.onAudioPathEvent(intent.getIntExtra(FmProxy.EXTRA_AUDIO_PATH, -1));
                } else if (FmProxy.actionsEqual(FmProxy.ACTION_ON_AUDIO_QUAL, action, FmProxy.ACTION_PREFIX_LENGTH)) {
                    handler.onLiveAudioQualityEvent(intent.getIntExtra(FmProxy.EXTRA_RSSI, -1), intent.getIntExtra(FmProxy.EXTRA_SNR, -126));
                } else if (FmProxy.actionsEqual(FmProxy.ACTION_ON_EST_NFL, action, FmProxy.ACTION_PREFIX_LENGTH)) {
                    handler.onEstimateNoiseFloorLevelEvent(intent.getIntExtra(FmProxy.EXTRA_NFL, -1));
                } else if (FmProxy.actionsEqual(FmProxy.ACTION_ON_RDS_DATA, action, FmProxy.ACTION_PREFIX_LENGTH)) {
                    handler.onRdsDataEvent(intent.getIntExtra(FmProxy.EXTRA_RDS_DATA_TYPE, -1), intent.getIntExtra(FmProxy.EXTRA_RDS_IDX, -1), intent.getStringExtra(FmProxy.EXTRA_RDS_TXT));
                } else if (FmProxy.actionsEqual(FmProxy.ACTION_ON_RDS_MODE, action, FmProxy.ACTION_PREFIX_LENGTH)) {
                    handler.onRdsModeEvent(intent.getIntExtra(FmProxy.EXTRA_RDS_MODE, -1), intent.getIntExtra(FmProxy.EXTRA_ALT_FREQ_MODE, -1));
                } else if (FmProxy.actionsEqual(FmProxy.ACTION_ON_SEEK_CMPL, action, FmProxy.ACTION_PREFIX_LENGTH)) {
                    handler.onSeekCompleteEvent(intent.getIntExtra(FmProxy.EXTRA_FREQ, -1), intent.getIntExtra(FmProxy.EXTRA_RSSI, -1), intent.getIntExtra(FmProxy.EXTRA_SNR, -126), intent.getBooleanExtra(FmProxy.EXTRA_SUCCESS, FmProxy.LIVE_AUDIO_QUALITY_DEFAULT));
                } else if (FmProxy.actionsEqual(FmProxy.ACTION_ON_VOL, action, FmProxy.ACTION_PREFIX_LENGTH)) {
                    handler.onVolumeEvent(intent.getIntExtra(FmProxy.EXTRA_STATUS, -1), intent.getIntExtra(FmProxy.EXTRA_VOL, -1));
                } else if (FmProxy.actionsEqual(FmProxy.ACTION_ON_WRLD_RGN, action, FmProxy.ACTION_PREFIX_LENGTH)) {
                    handler.onWorldRegionEvent(intent.getIntExtra(FmProxy.EXTRA_WRLD_RGN, -1));
                }
            }
        }
    }

    private class FmReceiverCallback extends Stub {
        private FmReceiverCallback() {
        }

        public synchronized void onStatusEvent(int freq, int rssi, int snr, boolean radioIsOn, int rdsProgramType, String rdsProgramService, String rdsRadioText, String rdsProgramTypeName, boolean isMute) throws RemoteException {
            if (FmProxy.this.mEventHandler != null) {
                FmProxy.this.mEventHandler.onStatusEvent(freq, rssi, snr, radioIsOn, rdsProgramType, rdsProgramService, rdsRadioText, rdsProgramTypeName, isMute);
            }
        }

        public synchronized void onSeekCompleteEvent(int freq, int rssi, int snr, boolean seeksuccess) throws RemoteException {
            if (FmProxy.this.mEventHandler != null) {
                FmProxy.this.mEventHandler.onSeekCompleteEvent(freq, rssi, snr, seeksuccess);
            }
        }

        public synchronized void onRdsModeEvent(int rdsMode, int alternateFreqHopEnabled) throws RemoteException {
            if (FmProxy.this.mEventHandler != null) {
                FmProxy.this.mEventHandler.onRdsModeEvent(rdsMode, alternateFreqHopEnabled);
            }
        }

        public synchronized void onRdsDataEvent(int rdsDataType, int rdsIndex, String rdsText) throws RemoteException {
            if (FmProxy.this.mEventHandler != null) {
                FmProxy.this.mEventHandler.onRdsDataEvent(rdsDataType, rdsIndex, rdsText);
            }
        }

        public synchronized void onAudioModeEvent(int audioMode) throws RemoteException {
            if (FmProxy.this.mEventHandler != null) {
                FmProxy.this.mEventHandler.onAudioModeEvent(audioMode);
            }
        }

        public synchronized void onAudioPathEvent(int audioPath) throws RemoteException {
            if (FmProxy.this.mEventHandler != null) {
                FmProxy.this.mEventHandler.onAudioPathEvent(audioPath);
            }
        }

        public synchronized void onEstimateNflEvent(int nfl) throws RemoteException {
            if (FmProxy.this.mEventHandler != null) {
                FmProxy.this.mEventHandler.onEstimateNoiseFloorLevelEvent(nfl);
            }
        }

        public synchronized void onLiveAudioQualityEvent(int rssi, int snr) throws RemoteException {
            if (FmProxy.this.mEventHandler != null) {
                FmProxy.this.mEventHandler.onLiveAudioQualityEvent(rssi, snr);
            }
        }

        public synchronized void onWorldRegionEvent(int worldRegion) throws RemoteException {
            if (FmProxy.this.mEventHandler != null) {
                FmProxy.this.mEventHandler.onWorldRegionEvent(worldRegion);
            }
        }

        public synchronized void onVolumeEvent(int status, int volume) throws RemoteException {
            if (FmProxy.this.mEventHandler != null) {
                FmProxy.this.mEventHandler.onVolumeEvent(status, volume);
            }
        }
    }

    static {
        ACTION_PREFIX_LENGTH = ACTION_PREFIX.length();
    }

    public static boolean isFmSupport() {
        String versatility = SystemProperties.get("ro.config.versatility", "UNKNOWN");
        if (versatility.equalsIgnoreCase("TR") || versatility.equalsIgnoreCase("TUR") || versatility.equalsIgnoreCase("UNKNOWN")) {
            return LIVE_AUDIO_QUALITY_DEFAULT;
        }
        Log.e(TAG, "isFmSupport()");
        return f0D;
    }

    public static boolean getProxy(Context ctx, IFmProxyCallback cb) {
        if (!isFmSupport()) {
            return LIVE_AUDIO_QUALITY_DEFAULT;
        }
        try {
            FmProxy fmProxy = new FmProxy(ctx, cb);
            return f0D;
        } catch (Throwable t) {
            Log.e(TAG, "Unable to get FM Proxy", t);
            return LIVE_AUDIO_QUALITY_DEFAULT;
        }
    }

    public FmProxy(Context ctx, IFmProxyCallback cb) {
        this.mEventHandler = null;
        this.mReceiverPriority = DEFAULT_BROADCAST_RECEIVER_PRIORITY;
        this.mConnection = new C00001();
        Log.d(TAG, "FmProxy object created obj =" + this);
        this.mContext = ctx;
        this.mProxyAvailCb = cb;
        if (BluetoothAdapter.getDefaultAdapter() == null) {
            Log.w(TAG, "BluetoothAdapter is null.");
            return;
        }
        Intent intent = new Intent(IFmReceiverService.class.getName());
        intent.setComponent(intent.resolveSystemService(this.mContext.getPackageManager(), STATUS_OK));
        if (!this.mContext.bindService(intent, this.mConnection, STATUS_FAIL)) {
            Log.e(TAG, "Could not bind to IFmReceiverService Service");
        }
    }

    protected boolean init(IBinder service) {
        try {
            this.mService = IFmReceiverService.Stub.asInterface(service);
            return f0D;
        } catch (Throwable t) {
            Log.e(TAG, "Unable to initialize BluetoothFM proxy with service", t);
            return LIVE_AUDIO_QUALITY_DEFAULT;
        }
    }

    public synchronized void registerEventHandler(IFmReceiverEventHandler handler) {
        Log.v(TAG, "registerEventHandler()");
        registerEventHandler(handler, null, (boolean) f0D, (int) DEFAULT_BROADCAST_RECEIVER_PRIORITY);
    }

    public synchronized void registerEventHandler(IFmReceiverEventHandler eventHandler, IntentFilter filter, boolean createCallbackThread, int receiverPriority) {
        registerEventHandler(eventHandler, null, null, receiverPriority);
    }

    public synchronized void registerEventHandler(IFmReceiverEventHandler eventHandler, IntentFilter filter, Handler threadHandler, int receiverPriority) {
        this.mEventHandler = eventHandler;
        if (this.mCallback == null) {
            this.mCallback = new FmReceiverCallback();
            try {
                this.mService.registerCallback(this.mCallback);
            } catch (RemoteException e) {
                Log.e(TAG, "Error registering callback handler", e);
            }
        }
    }

    public static IntentFilter createFilter(IntentFilter filter) {
        if (filter == null) {
            filter = new IntentFilter();
        }
        filter.addAction(ACTION_ON_AUDIO_MODE);
        filter.addAction(ACTION_ON_AUDIO_PATH);
        filter.addAction(ACTION_ON_AUDIO_QUAL);
        filter.addAction(ACTION_ON_EST_NFL);
        filter.addAction(ACTION_ON_RDS_DATA);
        filter.addAction(ACTION_ON_RDS_MODE);
        filter.addAction(ACTION_ON_SEEK_CMPL);
        filter.addAction(ACTION_ON_STATUS);
        filter.addAction(ACTION_ON_VOL);
        filter.addAction(ACTION_ON_WRLD_RGN);
        return filter;
    }

    public synchronized void unregisterEventHandler() {
        Log.v(TAG, "unregisterEventHandler()");
        this.mEventHandler = null;
        try {
            this.mService.unregisterCallback(this.mCallback);
        } catch (Throwable t) {
            Log.e(TAG, "Unable to unregister callback", t);
        }
    }

    public synchronized void finish() {
        if (this.mEventHandler != null) {
            this.mEventHandler = null;
        }
        if (!(this.mCallback == null || this.mService == null)) {
            try {
                this.mService.unregisterCallback(this.mCallback);
            } catch (Throwable t) {
                Log.e(TAG, "Unable to unregister callback", t);
            }
            this.mCallback = null;
        }
        baseFinish();
        this.mContext = null;
        this.mService = null;
    }

    public synchronized int turnOnRadio(int functionalityMask, String clientPackagename) {
        int returnCode;
        returnCode = STATUS_SERVER_FAIL;
        Log.d(TAG, "Fmproxy" + this + "mService" + this.mService);
        try {
            returnCode = this.mService.turnOnRadio(functionalityMask, clientPackagename.toCharArray());
        } catch (RemoteException e) {
            Log.e(TAG, "turnOnRadio() failed", e);
        }
        return returnCode;
    }

    public int turnOnRadio(String clientPackagename) {
        return turnOnRadio(STATUS_OK, clientPackagename);
    }

    public synchronized int turnOffRadio() {
        int returnCode;
        int returnCode2 = STATUS_SERVER_FAIL;
        try {
            returnCode = this.mService.turnOffRadio();
        } catch (RemoteException e) {
            Log.e(TAG, "turnOffRadio() failed", e);
            returnCode = returnCode2;
        }
        return returnCode;
    }

    public synchronized int cleanupFmService() {
        try {
            this.mService.cleanupFmService();
        } catch (RemoteException e) {
            Log.e(TAG, "cleanupFmService() failed", e);
        }
        Log.i(TAG, "cleanup triggered");
        return STATUS_SERVER_FAIL;
    }

    public synchronized int tuneRadio(int freq) {
        int returnCode;
        returnCode = STATUS_SERVER_FAIL;
        try {
            returnCode = this.mService.tuneRadio(freq);
        } catch (RemoteException e) {
            Log.e(TAG, "tuneRadio() failed", e);
        }
        return returnCode;
    }

    public synchronized int getStatus() {
        int returnCode;
        returnCode = STATUS_SERVER_FAIL;
        try {
            returnCode = this.mService.getStatus();
        } catch (RemoteException e) {
            Log.e(TAG, "getStatus() failed", e);
        }
        return returnCode;
    }

    public boolean getRadioIsOn() {
        boolean returnStatus = LIVE_AUDIO_QUALITY_DEFAULT;
        try {
            returnStatus = this.mService.getRadioIsOn();
        } catch (RemoteException e) {
            Log.e(TAG, "getRadioIsOn() failed", e);
        }
        return returnStatus;
    }

    public int getMonoStereoMode() {
        int returnStatus = STATUS_OK;
        try {
            returnStatus = this.mService.getMonoStereoMode();
        } catch (RemoteException e) {
            Log.e(TAG, "getMonoStereoMode() failed", e);
        }
        return returnStatus;
    }

    public int getTunedFrequency() {
        int returnStatus = STATUS_OK;
        try {
            returnStatus = this.mService.getTunedFrequency();
        } catch (RemoteException e) {
            Log.e(TAG, "getTunedFrequency() failed", e);
        }
        return returnStatus;
    }

    public boolean getIsMute() {
        boolean returnStatus = LIVE_AUDIO_QUALITY_DEFAULT;
        try {
            returnStatus = this.mService.getIsMute();
        } catch (RemoteException e) {
            Log.e(TAG, "getIsMute() failed", e);
        }
        return returnStatus;
    }

    public synchronized int muteAudio(boolean mute) {
        int returnCode;
        returnCode = STATUS_SERVER_FAIL;
        try {
            returnCode = this.mService.muteAudio(mute);
        } catch (RemoteException e) {
            Log.e(TAG, "muteAudio() failed", e);
        }
        return returnCode;
    }

    public synchronized int seekStation(int scanMode, int minSignalStrength) {
        int returnCode;
        returnCode = STATUS_SERVER_FAIL;
        try {
            returnCode = this.mService.seekStation(scanMode, minSignalStrength);
        } catch (RemoteException e) {
            Log.e(TAG, "seekStation() failed", e);
        }
        return returnCode;
    }

    public int seekStation(int scanMode) {
        return seekStation(scanMode, MIN_SIGNAL_STRENGTH_DEFAULT);
    }

    public synchronized int seekStationCombo(int startFrequency, int endFrequency, int minSignalStrength, int scanDirection, int scanMethod, boolean multi_channel, int rdsType, int rdsTypeValue) {
        int returnCode;
        returnCode = STATUS_SERVER_FAIL;
        try {
            returnCode = this.mService.seekStationCombo(startFrequency, endFrequency, minSignalStrength, scanDirection, scanMethod, multi_channel, rdsType, rdsTypeValue);
        } catch (RemoteException e) {
            Log.e(TAG, "seekStation() failed", e);
        }
        return returnCode;
    }

    public synchronized int seekRdsStation(int scanMode, int minSignalStrength, int rdsCondition, int rdsValue) {
        int returnCode;
        returnCode = STATUS_SERVER_FAIL;
        try {
            returnCode = this.mService.seekRdsStation(scanMode, minSignalStrength, rdsCondition, rdsValue);
        } catch (RemoteException e) {
            Log.e(TAG, "seekRdsStation() failed", e);
        }
        return returnCode;
    }

    public int seekRdsStation(int scanMode, int rdsCondition, int rdsValue) {
        return seekRdsStation(scanMode, MIN_SIGNAL_STRENGTH_DEFAULT, rdsCondition, rdsValue);
    }

    public synchronized int seekStationAbort() {
        int returnCode;
        returnCode = STATUS_SERVER_FAIL;
        try {
            returnCode = this.mService.seekStationAbort();
        } catch (RemoteException e) {
            Log.e(TAG, "seekStationAbort() failed", e);
        }
        return returnCode;
    }

    public synchronized int setRdsMode(int rdsMode, int rdsFeatures, int afMode, int afThreshold) {
        int returnCode;
        returnCode = STATUS_SERVER_FAIL;
        try {
            returnCode = this.mService.setRdsMode(rdsMode, rdsFeatures, afMode, afThreshold);
        } catch (RemoteException e) {
            Log.e(TAG, "setRdsMode() failed", e);
        }
        return returnCode;
    }

    public synchronized int setAudioMode(int audioMode) {
        int returnCode;
        returnCode = STATUS_SERVER_FAIL;
        try {
            returnCode = this.mService.setAudioMode(audioMode);
        } catch (RemoteException e) {
            Log.e(TAG, "setAudioMode() failed", e);
        }
        return returnCode;
    }

    public synchronized int setAudioPath(int audioPath) {
        int returnCode;
        returnCode = STATUS_SERVER_FAIL;
        try {
            returnCode = this.mService.setAudioPath(audioPath);
        } catch (RemoteException e) {
            Log.e(TAG, "setAudioPath() failed", e);
        }
        return returnCode;
    }

    public synchronized int setStepSize(int stepSize) {
        int returnCode;
        returnCode = STATUS_SERVER_FAIL;
        try {
            returnCode = this.mService.setStepSize(stepSize);
        } catch (RemoteException e) {
            Log.e(TAG, "setStepSize() failed", e);
        }
        return returnCode;
    }

    /* JADX WARNING: inconsistent code. */
    /* Code decompiled incorrectly, please refer to instructions dump. */
    public synchronized int setFMVolume(int r6) {
        /*
        r5 = this;
        monitor-enter(r5);
        r1 = 2;
        r3 = 16;
        r2 = new int[r3];	 Catch:{ all -> 0x0023 }
        r2 = {0, 1, 2, 5, 8, 12, 19, 28, 45, 72, 87, 108, 135, 164, 204, 255};
        if (r6 < 0) goto L_0x0018;
    L_0x000b:
        r3 = r2.length;	 Catch:{ RemoteException -> 0x001a }
        if (r6 >= r3) goto L_0x0018;
    L_0x000e:
        r3 = r5.mService;	 Catch:{ RemoteException -> 0x001a }
        r4 = r2[r6];	 Catch:{ RemoteException -> 0x001a }
        r1 = r3.setFMVolume(r4);	 Catch:{ RemoteException -> 0x001a }
    L_0x0016:
        monitor-exit(r5);
        return r1;
    L_0x0018:
        r1 = 4;
        goto L_0x0016;
    L_0x001a:
        r0 = move-exception;
        r3 = "FmProxy";
        r4 = "setFMVolume() failed";
        android.util.Log.e(r3, r4, r0);	 Catch:{ all -> 0x0023 }
        goto L_0x0016;
    L_0x0023:
        r3 = move-exception;
        monitor-exit(r5);
        throw r3;
        */
        throw new UnsupportedOperationException("Method not decompiled: com.broadcom.fm.fmreceiver.FmProxy.setFMVolume(int):int");
    }

    public synchronized int setWorldRegion(int worldRegion, int deemphasisTime) {
        int returnCode;
        returnCode = STATUS_SERVER_FAIL;
        try {
            returnCode = this.mService.setWorldRegion(worldRegion, deemphasisTime);
        } catch (RemoteException e) {
            Log.e(TAG, "setWorldRegion() failed", e);
        }
        return returnCode;
    }

    public synchronized int estimateNoiseFloorLevel(int nflLevel) {
        int returnCode;
        returnCode = STATUS_SERVER_FAIL;
        try {
            returnCode = this.mService.estimateNoiseFloorLevel(nflLevel);
        } catch (RemoteException e) {
            Log.e(TAG, "estimateNoiseFloorLevel() failed", e);
        }
        return returnCode;
    }

    public synchronized int setLiveAudioPolling(boolean liveAudioPolling, int signalPollInterval) {
        int returnCode;
        returnCode = STATUS_SERVER_FAIL;
        try {
            returnCode = this.mService.setLiveAudioPolling(liveAudioPolling, signalPollInterval);
        } catch (RemoteException e) {
            Log.e(TAG, "setLiveAudioPolling() failed", e);
        }
        return returnCode;
    }

    public synchronized int setSnrThreshold(int snrThreshold) {
        int returnCode;
        returnCode = STATUS_SERVER_FAIL;
        try {
            returnCode = this.mService.setSnrThreshold(snrThreshold);
        } catch (RemoteException e) {
            Log.e(TAG, "setSnrThreshold() failed", e);
        }
        return returnCode;
    }

    protected void finalize() {
        baseFinalize();
    }

    protected static boolean actionsEqual(String a1, String a2, int offset) {
        if (a1 == a2) {
            return f0D;
        }
        int a1length = a1.length();
        if (a1length != a2.length()) {
            return LIVE_AUDIO_QUALITY_DEFAULT;
        }
        return a1.regionMatches(offset, a2, offset, a1length - offset);
    }

    protected synchronized Handler initEventCallbackHandler() {
        if (this.mEventCallbackHandler == null) {
            this.mEventCallbackHandler = new EventCallbackHandler();
            this.mEventCallbackHandler.start();
            while (this.mEventCallbackHandler.mHandler == null) {
                try {
                    Thread.sleep(100);
                } catch (Exception e) {
                }
            }
        }
        return this.mEventCallbackHandler.mHandler;
    }

    protected synchronized void finishEventCallbackHandler() {
        if (this.mEventCallbackHandler != null) {
            this.mEventCallbackHandler.finish();
            this.mEventCallbackHandler = null;
        }
    }

    public synchronized void baseFinish() {
        Log.d(TAG, "finish() mContext = " + this.mContext);
        if (this.mEventCallbackHandler != null) {
            this.mEventCallbackHandler.finish();
            this.mEventCallbackHandler = null;
        }
        if (this.mContext != null) {
            this.mContext.unbindService(this.mConnection);
            this.mContext = null;
        }
    }

    public boolean requiresAccessProcessing() {
        return LIVE_AUDIO_QUALITY_DEFAULT;
    }

    protected void baseFinalize() {
        finish();
    }
}
