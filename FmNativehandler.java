package com.broadcom.fm.fmreceiver;

import android.bluetooth.BluetoothAdapter;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.media.AudioSystem;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.os.RemoteCallbackList;
import android.os.RemoteException;
import android.util.Log;
import com.android.bluetooth.map.BluetoothMapContent;
import java.text.DateFormat;
import java.util.Date;
import java.util.Iterator;
import java.util.LinkedList;

public final class FmNativehandler {
    private static final int FM_CHIP_OFF = 1;
    private static final int FM_CHIP_ON = 0;
    private static final int FM_CMD_ANY = 19;
    private static final int FM_ESTIMATE_NOISE_FLOOR_LEVEL = 16;
    private static final int FM_GET_STATUS = 5;
    private static final int FM_MUTE_AUDIO = 6;
    private static final int FM_OFF = 3;
    private static final int FM_ON = 2;
    private static final int FM_SEEK_RDS_STATION = 9;
    private static final int FM_SEEK_STATION = 7;
    private static final int FM_SEEK_STATION_ABORT = 10;
    private static final int FM_SEEK_STATION_COMBO = 8;
    private static final int FM_SET_AUDIO_MODE = 12;
    private static final int FM_SET_AUDIO_PATH = 13;
    private static final int FM_SET_LIVE_AUDIO_POLLING = 17;
    private static final int FM_SET_RDS_MODE = 11;
    private static final int FM_SET_STEP_SIZE = 14;
    private static final int FM_SET_VOLUME = 18;
    private static final int FM_SET_WORLD_REGION = 15;
    private static final int FM_TUNE_RADIO = 4;
    private static final String TAG = "FmNativehandler";
    private static final boolean f75V = true;
    private final RemoteCallbackList<IFmReceiverCallback> mCallbacks;
    private String mClientName;
    private Context mContext;
    private int mCurrCmd;
    private LinkedList<FMJob> mFmQueue;
    private int mFunctionalityMask;
    private BroadcastReceiver mIntentRadioState;
    private BroadcastReceiver mIntentReceiver;
    private boolean mIsFinish;
    private boolean mIsStarted;
    private boolean mWaitforRadioState;
    protected Handler operationHandler;

    /* renamed from: com.broadcom.fm.fmreceiver.FmNativehandler.1 */
    class C00691 extends Handler {
        C00691() {
        }

        public void handleMessage(Message msg) {
            switch (msg.what) {
                case FmNativehandler.FM_CHIP_OFF /*1*/:
                    Log.w(FmNativehandler.TAG, "handleMessage: OPERATION_TIMEOUT on " + FmNativehandler.fmCmdToString(msg.arg1));
                    switch (msg.arg1) {
                        case FmNativehandler.FM_CHIP_ON /*0*/:
                        case FmNativehandler.FM_ON /*2*/:
                            FmNativehandler.this.initializeStateMachine();
                            FmReceiverServiceState.radio_state = FmNativehandler.FM_CHIP_ON;
                        case FmNativehandler.FM_OFF /*3*/:
                            FmNativehandler.this.initializeStateMachine();
                            try {
                                FmNativehandler.this.disableFmNative(FmNativehandler.f75V);
                            } catch (Exception e) {
                            }
                            FmReceiverServiceState.radio_state = FmNativehandler.FM_CHIP_ON;
                        case FmNativehandler.FM_SEEK_STATION /*7*/:
                        case FmNativehandler.FM_SEEK_STATION_COMBO /*8*/:
                        case FmNativehandler.FM_SEEK_RDS_STATION /*9*/:
                            FmReceiverServiceState.radio_state = FmNativehandler.FM_ON;
                            FmReceiverServiceState.mSeekSuccess = FmNativehandler.f75V;
                            FmNativehandler.this.sendSeekCompleteEventCallback(FmReceiverServiceState.mFreq, FmReceiverServiceState.mRssi, FmReceiverServiceState.mSnr, FmReceiverServiceState.mSeekSuccess, msg.arg1, FmNativehandler.FM_CHIP_OFF);
                        default:
                            Log.w(FmNativehandler.TAG, "handleMessage: Unknown OPERATION_TIMEOUT " + FmNativehandler.fmCmdToString(msg.arg1));
                            FmReceiverServiceState.radio_state = FmNativehandler.FM_ON;
                            FmNativehandler.this.sendStatusEventCallback(FmReceiverServiceState.mFreq, FmReceiverServiceState.mRssi, FmReceiverServiceState.mSnr, FmReceiverServiceState.mRadioIsOn, FmReceiverServiceState.mRdsProgramType, FmReceiverServiceState.mRdsProgramService, FmReceiverServiceState.mRdsRadioText, FmReceiverServiceState.mRdsProgramTypeName, FmReceiverServiceState.mIsMute, msg.arg1, FmNativehandler.FM_CHIP_OFF);
                    }
                case FmNativehandler.FM_ON /*2*/:
                    Log.d(FmNativehandler.TAG, "handleMessage: OPERATION_STATUS_EVENT_CALLBACK");
                    FM_Status_Params st = msg.obj;
                    FmNativehandler.this.sendStatusEventCallback(st.mStFreq, st.mStRssi, st.mStSnr, st.mStRadioIsOn, st.mStRdsProgramType, st.mStRdsProgramService, st.mStRdsRadioText, st.mStRdsProgramTypeName, st.mStIsMute, msg.arg1, msg.arg2);
                case FmNativehandler.FM_OFF /*3*/:
                    Log.d(FmNativehandler.TAG, "handleMessage: OPERATION_SEARCH_EVENT_CALLBACK");
                    FM_Search_Params st2 = msg.obj;
                    FmNativehandler.this.sendSeekCompleteEventCallback(st2.mStFreq, st2.mStRssi, st2.mStSnr, st2.mStSeekSuccess, msg.arg1, msg.arg2);
                case FmNativehandler.FM_TUNE_RADIO /*4*/:
                    Log.d(FmNativehandler.TAG, "handleMessage: OPERATION_RDS_EVENT_CALLBACK");
                    FmNativehandler.this.sendRdsModeEventCallback(msg.arg1, msg.arg2);
                case FmNativehandler.FM_GET_STATUS /*5*/:
                    Log.d(FmNativehandler.TAG, "handleMessage: OPERATION_RDS_DATA_EVENT_CALLBACK");
                    FmNativehandler.this.sendRdsDataEventCallback(msg.arg1, msg.arg2, (String) msg.obj);
                case FmNativehandler.FM_MUTE_AUDIO /*6*/:
                    Log.d(FmNativehandler.TAG, "handleMessage: OPERATION_AUDIO_MODE_EVENT_CALLBACK");
                    FmNativehandler.this.sendAudioModeEventCallback(msg.arg1);
                case FmNativehandler.FM_SEEK_STATION /*7*/:
                    Log.d(FmNativehandler.TAG, "handleMessage: OPERATION_AUDIO_PATH_EVENT_CALLBACK");
                    FmNativehandler.this.sendAudioPathEventCallback(msg.arg1);
                case FmNativehandler.FM_SEEK_STATION_COMBO /*8*/:
                    Log.d(FmNativehandler.TAG, "handleMessage: OPERATION_REGION_EVENT_CALLBACK");
                    FmNativehandler.this.sendWorldRegionEventCallback(msg.arg1);
                case FmNativehandler.FM_SEEK_RDS_STATION /*9*/:
                    Log.d(FmNativehandler.TAG, "handleMessage: OPERATION_NFE_EVENT_CALLBACK");
                    FmNativehandler.this.sendEstimateNflEventCallback(msg.arg1);
                case FmNativehandler.FM_SEEK_STATION_ABORT /*10*/:
                    Log.d(FmNativehandler.TAG, "handleMessage: OPERATION_LIVE_AUDIO_EVENT_CALLBACK");
                    FmNativehandler.this.sendLiveAudioQualityEventCallback(msg.arg1, msg.arg2);
                case FmNativehandler.FM_SET_RDS_MODE /*11*/:
                    Log.d(FmNativehandler.TAG, "handleMessage: OPERATION_VOLUME_EVENT_CALLBACK");
                    FmNativehandler.this.sendVolumeEventCallback(msg.arg1, msg.arg2);
                case FmNativehandler.FM_SET_AUDIO_MODE /*12*/:
                    Log.d(FmNativehandler.TAG, "handleMessage: OPERATION_QUEUE_FM_CMD");
                    FmNativehandler.this.onQueueFMCommand((FMJob) msg.obj);
                default:
                    Log.w(FmNativehandler.TAG, "handleMessage: Unknown message: " + msg.what);
            }
        }
    }

    /* renamed from: com.broadcom.fm.fmreceiver.FmNativehandler.2 */
    class C00702 extends BroadcastReceiver {
        C00702() {
        }

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d(FmNativehandler.TAG, action);
            if (action.equals("android.intent.action.PACKAGE_REMOVED") || action.equals("android.intent.action.PACKAGE_RESTARTED")) {
                Uri uri = intent.getData();
                if (uri != null) {
                    String pkgName = uri.getSchemeSpecificPart();
                    if (pkgName != null) {
                        Log.d(FmNativehandler.TAG, pkgName);
                        if (pkgName.equals(FmNativehandler.this.mClientName)) {
                            FmNativehandler.this.turnOffRadio();
                        }
                    }
                }
            }
        }
    }

    /* renamed from: com.broadcom.fm.fmreceiver.FmNativehandler.3 */
    class C00713 extends BroadcastReceiver {
        C00713() {
        }

        public void onReceive(Context context, Intent intent) {
            String action = intent.getAction();
            Log.d(FmNativehandler.TAG, action);
            if (action.equals("android.bluetooth.adapter.action.RADIO_STATE_CHANGED")) {
                int state = intent.getIntExtra("android.bluetooth.adapter.extra.STATE", Integer.MIN_VALUE);
                Log.v(FmNativehandler.TAG, "ACTION_RADIO_STATE_CHANGED: state = " + state);
                if (state == FmNativehandler.FM_SET_STEP_SIZE && FmNativehandler.this.mWaitforRadioState == FmNativehandler.f75V) {
                    FmNativehandler.this.mWaitforRadioState = false;
                    FmNativehandler.this.queueFMCommand(new FMJob((int) FmNativehandler.FM_ON, FmNativehandler.this.mFunctionalityMask));
                } else if (state == FmNativehandler.FM_SET_WORLD_REGION && FmReceiverServiceState.mRadioIsOn == FmNativehandler.f75V) {
                    FmReceiverServiceState.mRadioIsOn = false;
                    FmNativehandler.this.operationHandler.removeMessages(FmNativehandler.FM_CHIP_OFF);
                    FmReceiverServiceState.radio_state = FmNativehandler.FM_CHIP_ON;
                    FmNativehandler.this.sendStatusEventCallbackFromLocalStore(FmNativehandler.FM_CHIP_OFF, FmNativehandler.f75V);
                }
            }
        }
    }

    static class FMJob {
        int arg1;
        int arg2;
        int arg3;
        int arg4;
        int arg5;
        int arg6;
        int arg7;
        boolean b_arg1;
        final int command;
        long timeSent;

        public FMJob(int command) {
            this.command = command;
            this.timeSent = 0;
        }

        public FMJob(int command, int arg1) {
            this.command = command;
            this.timeSent = 0;
            this.arg1 = arg1;
        }

        public FMJob(int command, int arg1, int arg2) {
            this.command = command;
            this.timeSent = 0;
            this.arg1 = arg1;
            this.arg2 = arg2;
        }

        public FMJob(int command, int arg1, int arg2, int arg3) {
            this.command = command;
            this.timeSent = 0;
            this.arg1 = arg1;
            this.arg2 = arg2;
            this.arg3 = arg3;
        }

        public FMJob(int command, int arg1, int arg2, int arg3, int arg4) {
            this.command = command;
            this.timeSent = 0;
            this.arg1 = arg1;
            this.arg2 = arg2;
            this.arg3 = arg3;
            this.arg4 = arg4;
        }

        public FMJob(int command, boolean b_arg1, int arg1, int arg2, int arg3, int arg4, int arg5, int arg6, int arg7) {
            this.command = command;
            this.timeSent = 0;
            this.b_arg1 = b_arg1;
            this.arg1 = arg1;
            this.arg2 = arg2;
            this.arg3 = arg3;
            this.arg4 = arg4;
            this.arg5 = arg5;
            this.arg6 = arg6;
            this.arg7 = arg7;
        }

        public FMJob(int command, boolean b_arg1) {
            this.command = command;
            this.timeSent = 0;
            this.b_arg1 = b_arg1;
        }

        public FMJob(int command, boolean b_arg1, int arg2) {
            this.command = command;
            this.timeSent = 0;
            this.b_arg1 = b_arg1;
            this.arg2 = arg2;
        }

        public String toString() {
            StringBuilder sb = new StringBuilder();
            sb.append(FmNativehandler.fmCmdToString(this.command));
            sb.append(" TimeSent:");
            if (this.timeSent == 0) {
                sb.append("not yet");
            } else {
                sb.append(DateFormat.getTimeInstance().format(new Date(this.timeSent)));
            }
            return sb.toString();
        }
    }

    private class FM_Search_Params {
        private int mStFreq;
        private int mStRssi;
        private boolean mStSeekSuccess;
        private int mStSnr;

        public FM_Search_Params(int freq, int rssi, int snr, boolean seekSuccess) {
            this.mStFreq = freq;
            this.mStRssi = rssi;
            this.mStSnr = snr;
            this.mStSeekSuccess = seekSuccess;
        }
    }

    private class FM_Status_Params {
        private int mStFreq;
        private boolean mStIsMute;
        private boolean mStRadioIsOn;
        private String mStRdsProgramService;
        private int mStRdsProgramType;
        private String mStRdsProgramTypeName;
        private String mStRdsRadioText;
        private int mStRssi;
        private int mStSnr;

        public FM_Status_Params(int freq, int rssi, int snr, boolean radioIsOn, int rdsProgramType, String rdsProgramService, String rdsRadioText, String rdsProgramTypeName, boolean isMute) {
            this.mStFreq = freq;
            this.mStRssi = rssi;
            this.mStSnr = snr;
            this.mStRadioIsOn = radioIsOn;
            this.mStRdsProgramType = rdsProgramType;
            this.mStRdsProgramService = rdsProgramService;
            this.mStRdsRadioText = rdsRadioText;
            this.mStRdsProgramTypeName = rdsProgramTypeName;
            this.mStIsMute = isMute;
        }
    }

    private static native void classInitNative();

    private native void cleanupNative();

    private native boolean comboSearchNative(int i, int i2, int i3, int i4, int i5, boolean z, int i6, int i7);

    private native boolean configureDeemphasisNative(int i);

    private native boolean configureSignalNotificationNative(int i);

    private native boolean disableFmNative(boolean z);

    private native boolean enableFmNative(int i);

    private native boolean estimateNoiseFloorNative(int i);

    private native boolean getAudioQualityNative(boolean z);

    private native void initializeNative();

    private native boolean muteNative(boolean z);

    private native boolean searchAbortNative();

    private native boolean searchNative(int i, int i2, int i3, int i4);

    private native boolean setAudioModeNative(int i);

    private native boolean setAudioPathNative(int i);

    private native boolean setFMVolumeNative(int i);

    private native boolean setRdsModeNative(boolean z, boolean z2, int i);

    private native boolean setRegionNative(int i);

    private native boolean setScanStepNative(int i);

    private native boolean setSnrThresholdNative(int i);

    private native boolean tuneNative(int i);

    private static String fmCmdToString(int what) {
        switch (what) {
            case FM_CHIP_ON /*0*/:
                return "FM_CHIP_ON";
            case FM_CHIP_OFF /*1*/:
                return "FM_CHIP_OFF";
            case FM_ON /*2*/:
                return "FM_ON";
            case FM_OFF /*3*/:
                return "FM_OFF";
            case FM_TUNE_RADIO /*4*/:
                return "FM_TUNE_RADIO";
            case FM_GET_STATUS /*5*/:
                return "FM_GET_STATUS";
            case FM_MUTE_AUDIO /*6*/:
                return "FM_MUTE_AUDIo";
            case FM_SEEK_STATION /*7*/:
                return "FM_SEEK_STATION";
            case FM_SEEK_STATION_COMBO /*8*/:
                return "FM_SEEK_STATION_COMBO";
            case FM_SEEK_RDS_STATION /*9*/:
                return "FM_SEEK_RDS_STATION";
            case FM_SET_RDS_MODE /*11*/:
                return "FM_SET_RDS_MODE";
            case FM_SET_AUDIO_MODE /*12*/:
                return "FM_SET_AUDIO_MODE";
            case FM_SET_AUDIO_PATH /*13*/:
                return "FM_SET_AUDIO_PATH";
            case FM_SET_STEP_SIZE /*14*/:
                return "FM_SET_STEP_SIZE";
            case FM_SET_WORLD_REGION /*15*/:
                return "FM_SET_WORLD_REGION";
            case FM_ESTIMATE_NOISE_FLOOR_LEVEL /*16*/:
                return "FM_ESTIMATE_NOISE_FLOOR_LEVEL";
            case FM_SET_LIVE_AUDIO_POLLING /*17*/:
                return "FM_SET_LIVE_AUDIO_POLLING";
            case FM_SET_VOLUME /*18*/:
                return "FM_SET_VOLUME";
            default:
                return "UNKNOWN COMMAND: " + what;
        }
    }

    private int queueFMCommand(FMJob job) {
        Message msg = Message.obtain();
        msg.what = FM_SET_AUDIO_MODE;
        msg.obj = job;
        if (job.command == FM_SET_AUDIO_PATH) {
            this.operationHandler.sendMessageDelayed(msg, 1000);
        } else {
            this.operationHandler.sendMessage(msg);
        }
        return FM_CHIP_ON;
    }

    private void onQueueFMCommand(FMJob job) {
        if (job != null) {
            synchronized (this.mFmQueue) {
                Iterator<FMJob> it;
                FMJob existingJob;
                if (job.command == FM_OFF) {
                    FMJob firstJob = (FMJob) this.mFmQueue.peek();
                    if (firstJob != null) {
                        if (firstJob.command == FM_OFF) {
                            Log.d(TAG, "onQueueFMCommand: Ignore duplicated FM_OFF command...");
                            return;
                        }
                        it = this.mFmQueue.iterator();
                        while (it.hasNext()) {
                            existingJob = (FMJob) it.next();
                            if (existingJob.timeSent == 0) {
                                Log.d(TAG, "onQueueFMCommand: " + existingJob + " removed due to FM_OFF");
                                it.remove();
                            }
                        }
                    }
                } else if (job.command == FM_CHIP_OFF) {
                    it = this.mFmQueue.iterator();
                    while (it.hasNext()) {
                        existingJob = (FMJob) it.next();
                        if (existingJob.command == FM_OFF || existingJob.timeSent == 0) {
                            Log.d(TAG, "onQueueFMCommand:" + existingJob + " removed due to FM_CHIP_OFF");
                            it.remove();
                        }
                    }
                }
                this.mFmQueue.add(job);
                if (this.mFmQueue.size() == FM_CHIP_OFF) {
                    processCommands();
                }
            }
        }
    }

    private int processCommand(FMJob job) {
        int successful = FM_CHIP_ON;
        if (job.timeSent == 0) {
            this.mCurrCmd = job.command;
            job.timeSent = System.currentTimeMillis();
            Log.d(TAG, "processCommand: [" + job + "]");
            switch (job.command) {
                case FM_CHIP_ON /*0*/:
                    successful = process_enableChip();
                    break;
                case FM_CHIP_OFF /*1*/:
                    successful = process_disableChip();
                    break;
                case FM_ON /*2*/:
                    successful = process_turnOnRadio(job.arg1);
                    break;
                case FM_OFF /*3*/:
                    successful = process_turnOffRadio();
                    break;
                case FM_TUNE_RADIO /*4*/:
                    successful = process_tuneRadio(job.arg1);
                    break;
                case FM_GET_STATUS /*5*/:
                    successful = process_getStatus();
                    break;
                case FM_MUTE_AUDIO /*6*/:
                    successful = process_muteAudio(job.b_arg1);
                    break;
                case FM_SEEK_STATION /*7*/:
                    successful = process_seekStation(job.arg1, job.arg2);
                    break;
                case FM_SEEK_STATION_COMBO /*8*/:
                    successful = process_seekStationCombo(job.b_arg1, job.arg1, job.arg2, job.arg3, job.arg4, job.arg5, job.arg6, job.arg7);
                    break;
                case FM_SEEK_RDS_STATION /*9*/:
                    successful = process_seekRdsStation(job.arg1, job.arg2, job.arg3, job.arg4);
                    break;
                case FM_SET_RDS_MODE /*11*/:
                    successful = process_setRdsMode(job.arg1, job.arg2, job.arg3, job.arg4);
                    break;
                case FM_SET_AUDIO_MODE /*12*/:
                    successful = process_setAudioMode(job.arg1);
                    break;
                case FM_SET_AUDIO_PATH /*13*/:
                    successful = process_setAudioPath(job.arg1);
                    break;
                case FM_SET_STEP_SIZE /*14*/:
                    successful = process_setStepSize(job.arg1);
                    break;
                case FM_SET_WORLD_REGION /*15*/:
                    successful = process_setWorldRegion(job.arg1, job.arg2);
                    break;
                case FM_ESTIMATE_NOISE_FLOOR_LEVEL /*16*/:
                    try {
                        successful = process_estimateNoiseFloorLevel(job.arg1);
                        break;
                    } catch (RemoteException e) {
                        successful = FM_ON;
                        break;
                    }
                case FM_SET_LIVE_AUDIO_POLLING /*17*/:
                    try {
                        successful = process_setLiveAudioPolling(job.b_arg1, job.arg2);
                        break;
                    } catch (RemoteException e2) {
                        successful = FM_ON;
                        break;
                    }
                case FM_SET_VOLUME /*18*/:
                    successful = process_setFMVolume(job.arg1);
                    break;
            }
            if (successful != 0) {
                this.mCurrCmd = FM_CMD_ANY;
            }
        }
        return successful;
    }

    private int processCommands() {
        Log.d(TAG, "processCommands: " + this.mFmQueue.toString());
        int status = FM_OFF;
        Iterator<FMJob> it = this.mFmQueue.iterator();
        while (it.hasNext()) {
            FMJob job = (FMJob) it.next();
            status = processCommand(job);
            if (status == 0) {
                if (job.command == 0) {
                    it.remove();
                }
                return status;
            }
            it.remove();
        }
        return status;
    }

    private void fetchNextJob(int currJobCmd) {
        Log.d(TAG, "fetchNextJob: currJobCmd = " + fmCmdToString(currJobCmd));
        synchronized (this.mFmQueue) {
            FMJob job = (FMJob) this.mFmQueue.peek();
            if (job == null) {
                return;
            }
            if (currJobCmd == FM_CMD_ANY || currJobCmd == job.command) {
                Log.d(TAG, "fetchNextJob: remove completed job [" + job + "]");
                this.mFmQueue.poll();
            } else {
                Log.w(TAG, "fetchNextJob: currJob = " + currJobCmd + ", but the current job on the queue is [" + job + "]");
            }
            processCommands();
        }
    }

    public void clearAllQueue() {
        synchronized (this.mFmQueue) {
            if (this.mFmQueue != null) {
                Log.d(TAG, "clearAllQueue: mFmQueue = " + this.mFmQueue.toString());
                this.mFmQueue.clear();
                this.mFmQueue = null;
            }
        }
    }

    public boolean getRadioIsOn() {
        return FmReceiverServiceState.mRadioIsOn;
    }

    public synchronized int getMonoStereoMode() {
        return FmReceiverServiceState.mAudioMode;
    }

    public synchronized int getTunedFrequency() {
        return FmReceiverServiceState.mFreq;
    }

    public synchronized boolean getIsMute() {
        return FmReceiverServiceState.mIsMute;
    }

    public void registerCallback(IFmReceiverCallback cb) throws RemoteException {
        if (cb != null) {
            this.mCallbacks.register(cb);
        }
    }

    public synchronized void unregisterCallback(IFmReceiverCallback cb) throws RemoteException {
        if (cb != null) {
            this.mCallbacks.unregister(cb);
        }
    }

    static {
        classInitNative();
    }

    public FmNativehandler(Context context) {
        this.mCallbacks = new RemoteCallbackList();
        this.mIsStarted = false;
        this.mIsFinish = false;
        this.mFmQueue = new LinkedList();
        this.mCurrCmd = FM_CMD_ANY;
        this.mWaitforRadioState = false;
        this.operationHandler = new C00691();
        this.mIntentReceiver = new C00702();
        this.mIntentRadioState = new C00713();
        this.mContext = context;
    }

    public synchronized void start() {
        Log.d(TAG, "start");
        if (this.mIsStarted) {
            Log.w(TAG, "Service already started. Skipping...");
        } else {
            this.mIsStarted = f75V;
        }
    }

    public synchronized void stop() {
        Log.d(TAG, "stop");
        if (this.mIsStarted) {
            unRegisterIntent();
            BluetoothAdapter btAdap = BluetoothAdapter.getDefaultAdapter();
            if (btAdap.isRadioEnabled()) {
                Log.e(TAG, "Disable radio if app failed to disable radio");
                setAudioPathNative(FM_CHIP_ON);
                disableFmNative(false);
                if (FmReceiverServiceState.mRadioIsOn) {
                    btAdap.disableRadio(FM_CHIP_ON);
                }
                initializeStateMachine();
            }
            cleanupNative();
            this.mIsStarted = false;
        } else {
            Log.d(TAG, "Service already stopped. Skipping...");
        }
    }

    public void finish() {
        Log.d(TAG, "finish - cleanup Service here");
        if (this.operationHandler != null) {
            this.operationHandler.removeMessages(FM_CHIP_OFF);
            this.operationHandler.removeMessages(FM_ON);
            this.operationHandler.removeMessages(FM_OFF);
            this.operationHandler.removeMessages(FM_TUNE_RADIO);
            this.operationHandler.removeMessages(FM_GET_STATUS);
            this.operationHandler.removeMessages(FM_MUTE_AUDIO);
            this.operationHandler.removeMessages(FM_SEEK_STATION);
            this.operationHandler.removeMessages(FM_SEEK_STATION_COMBO);
            this.operationHandler.removeMessages(FM_SEEK_RDS_STATION);
            this.operationHandler.removeMessages(FM_SEEK_STATION_ABORT);
            this.operationHandler.removeMessages(FM_SET_RDS_MODE);
            this.operationHandler.removeMessages(FM_SET_AUDIO_MODE);
            this.operationHandler.removeCallbacksAndMessages(null);
            this.operationHandler = null;
        }
        clearAllQueue();
        this.mCallbacks.kill();
    }

    private void initializeStateMachine() {
        FmReceiverServiceState.radio_state = FM_CHIP_ON;
        FmReceiverServiceState.mFreq = FM_CHIP_ON;
        FmReceiverServiceState.mRssi = 127;
        FmReceiverServiceState.mSnr = 127;
        FmReceiverServiceState.mRadioIsOn = false;
        FmReceiverServiceState.mRdsProgramType = FM_CHIP_ON;
        FmReceiverServiceState.mRdsProgramService = "";
        FmReceiverServiceState.mRdsRadioText = "";
        FmReceiverServiceState.mRdsProgramTypeName = "";
        FmReceiverServiceState.mIsMute = false;
        FmReceiverServiceState.mSeekSuccess = false;
        FmReceiverServiceState.mRdsOn = false;
        FmReceiverServiceState.mAfOn = false;
        FmReceiverServiceState.mRdsType = FM_CHIP_ON;
        FmReceiverServiceState.mAlternateFreqHopThreshold = FM_CHIP_ON;
        FmReceiverServiceState.mAudioMode = FM_CHIP_ON;
        FmReceiverServiceState.mAudioPath = FM_CHIP_OFF;
        FmReceiverServiceState.mWorldRegion = FM_CHIP_ON;
        FmReceiverServiceState.mStepSize = FM_CHIP_ON;
        FmReceiverServiceState.mLiveAudioQuality = false;
        FmReceiverServiceState.mEstimatedNoiseFloorLevel = FM_CHIP_OFF;
        FmReceiverServiceState.mSignalPollInterval = 100;
        FmReceiverServiceState.mDeemphasisTime = 64;
        FmReceiverServiceState.radio_op_state = FM_CHIP_ON;
    }

    private void registerIntent() {
        IntentFilter intentFilter = new IntentFilter();
        intentFilter.addAction("android.intent.action.PACKAGE_REMOVED");
        intentFilter.addAction("android.intent.action.PACKAGE_RESTARTED");
        intentFilter.addAction("android.intent.action.QUERY_PACKAGE_RESTART");
        intentFilter.addDataScheme("package");
        this.mContext.registerReceiver(this.mIntentReceiver, intentFilter);
        IntentFilter intentFilter1 = new IntentFilter();
        intentFilter1.addAction("android.bluetooth.adapter.action.RADIO_STATE_CHANGED");
        this.mContext.registerReceiver(this.mIntentRadioState, intentFilter1);
    }

    private void unRegisterIntent() {
        if (this.mClientName != null) {
            this.mContext.unregisterReceiver(this.mIntentReceiver);
            this.mContext.unregisterReceiver(this.mIntentRadioState);
        }
        this.mClientName = null;
    }

    public synchronized int turnOnRadio(int functionalityMask, char[] clientPackagename) {
        int i = FM_TUNE_RADIO;
        synchronized (this) {
            this.mClientName = String.copyValueOf(clientPackagename);
            Log.d(TAG, "turnOnRadio: functionalityMask = " + functionalityMask + ", clientPackagename = " + clientPackagename);
            int requestedRegion = functionalityMask & FM_OFF;
            int requestedRdsFeatures = functionalityMask & 112;
            if (requestedRegion != FM_CHIP_OFF && requestedRegion != FM_ON && requestedRegion != FM_OFF && requestedRegion != 0) {
                Log.e(TAG, "turnOnRadio: Illegal requestedRegion = " + requestedRegion);
            } else if ((requestedRdsFeatures & FM_ESTIMATE_NOISE_FLOOR_LEVEL) == 0 || (requestedRdsFeatures & 32) == 0) {
                registerIntent();
                this.mFunctionalityMask = functionalityMask;
                i = queueFMCommand(new FMJob(FM_CHIP_ON));
            } else {
                Log.e(TAG, "turnOnRadio: Illegal requestedRdsFeatures = " + requestedRdsFeatures);
            }
        }
        return i;
    }

    public synchronized int process_enableChip() {
        int i = FM_CHIP_ON;
        synchronized (this) {
            Log.d(TAG, "process_enableChip:");
            if (FmReceiverServiceState.radio_state != 0) {
                Log.w(TAG, "process_enableChip: STATE = " + FmReceiverServiceState.radio_state);
                i = FM_OFF;
            } else {
                FmReceiverServiceState.radio_state = FM_GET_STATUS;
                Message msg = Message.obtain();
                msg.what = FM_CHIP_OFF;
                msg.arg1 = FM_CHIP_ON;
                this.operationHandler.removeMessages(FM_CHIP_OFF);
                this.operationHandler.sendMessageDelayed(msg, 10000);
                BluetoothAdapter btAdap = BluetoothAdapter.getDefaultAdapter();
                if (btAdap.isRadioEnabled()) {
                    btAdap.enableRadio(FM_CHIP_ON);
                    queueFMCommand(new FMJob((int) FM_ON, this.mFunctionalityMask));
                } else {
                    this.mWaitforRadioState = f75V;
                    btAdap.enableRadio(FM_CHIP_ON);
                }
            }
        }
        return i;
    }

    private int process_turnOnRadio(int functionalityMask) {
        Log.d(TAG, "process_turnOnRadio: functionalityMask = " + functionalityMask);
        if (FmReceiverServiceState.mRadioIsOn) {
            sendStatusEventCallbackFromLocalStore(FM_ON, f75V);
            return FM_CHIP_ON;
        } else if (FM_GET_STATUS != FmReceiverServiceState.radio_state) {
            Log.w(TAG, "process_turnOnRadio: STATE = " + FmReceiverServiceState.radio_state);
            return FM_OFF;
        } else {
            initializeNative();
            Message msg = Message.obtain();
            msg.what = FM_CHIP_OFF;
            msg.arg1 = FM_ON;
            this.operationHandler.removeMessages(FM_CHIP_OFF);
            this.operationHandler.sendMessageDelayed(msg, 10000);
            int returnStatus = FM_CHIP_ON;
            FmReceiverServiceState.radio_state = FM_CHIP_OFF;
            try {
                if (!enableFmNative(functionalityMask & 115)) {
                    returnStatus = FM_ON;
                }
            } catch (Exception e) {
                returnStatus = FM_ON;
                Log.e(TAG, "process_turnOnRadio: enableFmNative failed", e);
            }
            if (returnStatus == 0) {
                return returnStatus;
            }
            this.operationHandler.removeMessages(FM_CHIP_OFF);
            return returnStatus;
        }
    }

    public synchronized int turnOffRadio() {
        Log.d(TAG, "turnOffRadio:");
        AudioSystem.setParameters("fm_route=disabled");
        queueFMCommand(new FMJob(FM_OFF));
        return FM_CHIP_ON;
    }

    public synchronized int process_disableChip() {
        Log.d(TAG, "process_disableChip:");
        BluetoothAdapter.getDefaultAdapter().disableRadio(FM_CHIP_ON);
        FmReceiverServiceState.mRadioIsOn = false;
        this.operationHandler.removeMessages(FM_CHIP_OFF);
        FmReceiverServiceState.radio_state = FM_CHIP_ON;
        sendStatusEventCallbackFromLocalStore(FM_CHIP_OFF, f75V);
        return FM_CHIP_ON;
    }

    private int process_turnOffRadio() {
        Log.d(TAG, "process_turnOffRadio:");
        Message msg = Message.obtain();
        msg.what = FM_CHIP_OFF;
        msg.arg1 = FM_OFF;
        this.operationHandler.removeMessages(FM_CHIP_OFF);
        this.operationHandler.sendMessageDelayed(msg, 10000);
        int returnStatus = FM_CHIP_ON;
        FmReceiverServiceState.radio_state = FM_OFF;
        try {
            if (!disableFmNative(false)) {
                returnStatus = FM_ON;
            }
        } catch (Exception e) {
            returnStatus = FM_ON;
            Log.e(TAG, "process_turnOffRadio: disableFmNative failed", e);
        }
        if (returnStatus != 0) {
            this.operationHandler.removeMessages(FM_CHIP_OFF);
        }
        FmReceiverServiceState.radio_state = FM_CHIP_ON;
        return returnStatus;
    }

    public synchronized int cleanupFmService() {
        onRadioOffEvent(FM_CHIP_ON);
        return FM_CHIP_ON;
    }

    public synchronized int tuneRadio(int freq) {
        int i = FM_TUNE_RADIO;
        synchronized (this) {
            Log.d(TAG, "tuneRadio: freq = " + freq);
            if (freq < FM_CHIP_OFF || freq > 99999) {
                Log.e(TAG, "tuneRadio: Illegal freq = " + freq);
            } else {
                queueFMCommand(new FMJob((int) FM_TUNE_RADIO, freq));
                i = FM_CHIP_ON;
            }
        }
        return i;
    }

    private int process_tuneRadio(int freq) {
        Log.d(TAG, "process_tuneRadio: freq = " + freq);
        if (FM_ON != FmReceiverServiceState.radio_state) {
            Log.w(TAG, "process_tuneRadio: STATE = " + FmReceiverServiceState.radio_state);
            return FM_OFF;
        }
        FmReceiverServiceState.radio_state = FM_TUNE_RADIO;
        Message msg = Message.obtain();
        msg.what = FM_CHIP_OFF;
        msg.arg1 = FM_TUNE_RADIO;
        this.operationHandler.removeMessages(FM_CHIP_OFF);
        this.operationHandler.sendMessageDelayed(msg, 20000);
        int returnStatus = FM_CHIP_ON;
        try {
            if (!tuneNative(freq)) {
                returnStatus = FM_ON;
            }
        } catch (Exception e) {
            returnStatus = FM_ON;
            Log.e(TAG, "process_tuneRadio: tuneNative failed", e);
        }
        if (returnStatus == 0) {
            return returnStatus;
        }
        this.operationHandler.removeMessages(FM_CHIP_OFF);
        return returnStatus;
    }

    public synchronized int getStatus() {
        Log.d(TAG, "getStatus:");
        return queueFMCommand(new FMJob(FM_GET_STATUS));
    }

    private int process_getStatus() {
        Log.d(TAG, "process_getStatus:");
        if (FM_ON != FmReceiverServiceState.radio_state) {
            Log.w(TAG, "process_getStatus: STATE = " + FmReceiverServiceState.radio_state);
            return FM_OFF;
        }
        sendStatusEventCallbackFromLocalStore(FM_GET_STATUS, f75V);
        return FM_CHIP_ON;
    }

    public synchronized int muteAudio(boolean mute) {
        Log.d(TAG, "muteAudio: mute = " + mute);
        queueFMCommand(new FMJob((int) FM_MUTE_AUDIO, mute));
        return FM_CHIP_ON;
    }

    private int process_muteAudio(boolean mute) {
        Log.d(TAG, "process_muteAudio: mute = " + mute);
        if (FM_ON != FmReceiverServiceState.radio_state) {
            Log.w(TAG, "process_muteAudio: STATE = " + FmReceiverServiceState.radio_state);
            return FM_OFF;
        }
        FmReceiverServiceState.radio_state = FM_TUNE_RADIO;
        Message msg = Message.obtain();
        msg.what = FM_CHIP_OFF;
        msg.arg1 = FM_MUTE_AUDIO;
        this.operationHandler.removeMessages(FM_CHIP_OFF);
        this.operationHandler.sendMessageDelayed(msg, 5000);
        int returnStatus = FM_CHIP_ON;
        try {
            if (!muteNative(mute)) {
                returnStatus = FM_ON;
            }
        } catch (Exception e) {
            returnStatus = FM_ON;
            Log.e(TAG, "process_muteAudio: muteNative failed", e);
        }
        if (returnStatus == 0) {
            return returnStatus;
        }
        this.operationHandler.removeMessages(FM_CHIP_OFF);
        return returnStatus;
    }

    public synchronized int seekStation(int scanMode, int minSignalStrength) {
        int i = FM_TUNE_RADIO;
        synchronized (this) {
            Log.d(TAG, "seekStation: scanMode = " + scanMode + ", minSignalStrength = " + minSignalStrength);
            if (minSignalStrength < 0 || minSignalStrength > 255) {
                Log.e(TAG, "seekStation: Illegal minSignalStrength = " + minSignalStrength);
            } else if (scanMode == 0 || scanMode == 128 || scanMode == FM_CHIP_OFF || scanMode == BluetoothMapContent.MMS_CC) {
                queueFMCommand(new FMJob((int) FM_SEEK_STATION, scanMode, minSignalStrength));
                i = FM_CHIP_ON;
            } else {
                Log.e(TAG, "seekStation: Illegal scanMode = " + scanMode);
            }
        }
        return i;
    }

    private int process_seekStation(int scanMode, int minSignalStrength) {
        Log.d(TAG, "process_seekStation: scanMode = " + scanMode + ", minSignalStrength = " + minSignalStrength);
        if (FM_ON != FmReceiverServiceState.radio_state) {
            Log.w(TAG, "process_seekStation: STATE = " + FmReceiverServiceState.radio_state);
            return FM_OFF;
        }
        FmReceiverServiceState.radio_state = FM_TUNE_RADIO;
        Message msg = Message.obtain();
        msg.what = FM_CHIP_OFF;
        msg.arg1 = FM_SEEK_STATION;
        this.operationHandler.removeMessages(FM_CHIP_OFF);
        this.operationHandler.sendMessageDelayed(msg, 20000);
        int returnStatus = FM_CHIP_ON;
        try {
            if (!searchNative(scanMode & 131, minSignalStrength, FM_CHIP_ON, FM_CHIP_ON)) {
                returnStatus = FM_ON;
            }
        } catch (Exception e) {
            returnStatus = FM_ON;
            Log.e(TAG, "process_seekStation: searchNative failed", e);
        }
        if (returnStatus == 0) {
            return returnStatus;
        }
        this.operationHandler.removeMessages(FM_CHIP_OFF);
        return returnStatus;
    }

    public synchronized int seekStationCombo(int startFreq, int endFreq, int minSignalStrength, int direction, int scanMethod, boolean multiChannel, int rdsType, int rdsTypeValue) {
        int i;
        Log.d(TAG, "seekStationCombo: startFreq = " + startFreq + ", endFreq = " + endFreq + ", minSignalStrength = " + minSignalStrength + ", direction = " + direction + ", scanMethod = " + scanMethod + ", multiChannel = " + multiChannel + ", rdsType = " + rdsType + ", rdsTypeValue = " + rdsTypeValue);
        if (minSignalStrength < 0 || minSignalStrength > 255) {
            Log.e(TAG, "seekStationCombo: Illegal minSignalStrength = " + minSignalStrength);
            i = FM_TUNE_RADIO;
        } else {
            queueFMCommand(new FMJob(FM_SEEK_STATION_COMBO, multiChannel, startFreq, endFreq, minSignalStrength, direction, scanMethod, rdsType, rdsTypeValue));
            i = FM_CHIP_ON;
        }
        return i;
    }

    private int process_seekStationCombo(boolean multiChannel, int startFreq, int endFreq, int minSignalStrength, int direction, int scanMethod, int rdsType, int rdsTypeValue) {
        Log.d(TAG, "process_seekStationCombo: startFreq = " + startFreq + ", endFreq = " + endFreq + ", minSignalStrength = " + minSignalStrength + ", direction = " + direction + ", scanMethod = " + scanMethod + ", multiChannel = " + multiChannel + ", rdsType = " + rdsType + ", rdsTypeValue = " + rdsTypeValue);
        if (FM_ON != FmReceiverServiceState.radio_state) {
            Log.w(TAG, "process_seekStationCombo: STATE = " + FmReceiverServiceState.radio_state);
            return FM_OFF;
        }
        FmReceiverServiceState.radio_state = FM_TUNE_RADIO;
        Message msg = Message.obtain();
        msg.what = FM_CHIP_OFF;
        msg.arg1 = FM_SEEK_STATION_COMBO;
        this.operationHandler.removeMessages(FM_CHIP_OFF);
        this.operationHandler.sendMessageDelayed(msg, 20000);
        int returnStatus = FM_CHIP_ON;
        try {
            if (!comboSearchNative(startFreq, endFreq, minSignalStrength, direction, scanMethod, multiChannel, rdsType, rdsTypeValue)) {
                returnStatus = FM_ON;
            }
        } catch (Exception e) {
            returnStatus = FM_ON;
            Log.e(TAG, "process_seekStationCombo: comboSearchNative failed", e);
        }
        if (returnStatus == 0) {
            return returnStatus;
        }
        this.operationHandler.removeMessages(FM_CHIP_OFF);
        return returnStatus;
    }

    public synchronized int seekRdsStation(int scanMode, int minSignalStrength, int rdsCondition, int rdsValue) {
        int i = FM_TUNE_RADIO;
        synchronized (this) {
            Log.d(TAG, "seekRdsStation: scanMode = " + scanMode + ", minSignalStrength = " + minSignalStrength + ", rdsCondition = " + rdsCondition + ", rdsValue = " + rdsValue);
            if (minSignalStrength < 0 || minSignalStrength > 255) {
                Log.e(TAG, "seekRdsStation: Illegal minSignalStrength = " + minSignalStrength);
            } else if (rdsValue < 0 || rdsValue > 255) {
                Log.e(TAG, "seekRdsStation: Illegal rdsValue = " + rdsValue);
            } else if (rdsCondition == 0 || rdsCondition == FM_CHIP_OFF || rdsCondition == FM_ON) {
                queueFMCommand(new FMJob(FM_SEEK_RDS_STATION, scanMode, minSignalStrength, rdsCondition, rdsValue));
                i = FM_CHIP_ON;
            } else {
                Log.e(TAG, "seekRdsStation: Illegal rdsCondition = " + rdsCondition);
            }
        }
        return i;
    }

    private int process_seekRdsStation(int scanMode, int minSignalStrength, int rdsCondition, int rdsValue) {
        Log.d(TAG, "process_seekRdsStation: scanMode = " + scanMode + ", minSignalStrength = " + minSignalStrength + ", rdsCondition = " + rdsCondition + ", rdsValue = " + rdsValue);
        if (FM_ON != FmReceiverServiceState.radio_state) {
            Log.w(TAG, "process_seekRdsStation: STATE = " + FmReceiverServiceState.radio_state);
            return FM_OFF;
        }
        FmReceiverServiceState.radio_state = FM_TUNE_RADIO;
        Message msg = Message.obtain();
        msg.what = FM_CHIP_OFF;
        msg.arg1 = FM_SEEK_RDS_STATION;
        this.operationHandler.removeMessages(FM_CHIP_OFF);
        this.operationHandler.sendMessageDelayed(msg, 20000);
        int returnStatus = FM_CHIP_ON;
        try {
            if (!searchNative(scanMode & 131, minSignalStrength, rdsCondition, rdsValue)) {
                returnStatus = FM_ON;
            }
        } catch (Exception e) {
            returnStatus = FM_ON;
            Log.e(TAG, "process_seekRdsStation: searchNative failed", e);
        }
        if (returnStatus == 0) {
            return returnStatus;
        }
        this.operationHandler.removeMessages(FM_CHIP_OFF);
        return returnStatus;
    }

    public synchronized int seekStationAbort() {
        int returnStatus;
        Log.d(TAG, "seekStationAbort:");
        returnStatus = FM_OFF;
        if (this.mCurrCmd == FM_SEEK_STATION || this.mCurrCmd == FM_SEEK_STATION_COMBO || this.mCurrCmd == FM_SEEK_RDS_STATION) {
            try {
                if (!searchAbortNative()) {
                    returnStatus = FM_ON;
                }
            } catch (Exception e) {
                returnStatus = FM_ON;
                Log.e(TAG, "seekStationAbort: searchAbortNative failed", e);
            }
        }
        return returnStatus;
    }

    public synchronized int setRdsMode(int rdsMode, int rdsFeatures, int afMode, int afThreshold) {
        int i;
        Log.d(TAG, "setRdsMode: rdsMode = " + rdsMode + ", rdsFeatures = " + rdsFeatures + ", afMode = " + afMode + ", afThreshold = " + afThreshold);
        if (afThreshold < 0 || afThreshold > 255) {
            Log.e(TAG, "seekStation: Illegal afThreshold = " + afThreshold);
            i = FM_TUNE_RADIO;
        } else {
            queueFMCommand(new FMJob(FM_SET_RDS_MODE, rdsMode, rdsFeatures, afMode, afThreshold));
            i = FM_CHIP_ON;
        }
        return i;
    }

    private int process_setRdsMode(int rdsMode, int rdsFeatures, int afMode, int afThreshold) {
        Log.d(TAG, "process_setRdsMode: rdsMode = " + rdsMode + ", rdsFeatures = " + rdsFeatures + ", afMode = " + afMode + ", afThreshold = " + afThreshold);
        if (FM_ON != FmReceiverServiceState.radio_state) {
            Log.w(TAG, "process_setRdsMode: STATE = " + FmReceiverServiceState.radio_state);
            return FM_OFF;
        }
        FmReceiverServiceState.radio_state = FM_TUNE_RADIO;
        FmReceiverServiceState.radio_op_state = FM_CHIP_ON;
        Message msg = Message.obtain();
        msg.what = FM_CHIP_OFF;
        msg.arg1 = FM_SET_RDS_MODE;
        this.operationHandler.removeMessages(FM_CHIP_OFF);
        this.operationHandler.sendMessageDelayed(msg, 5000);
        int returnStatus = FM_CHIP_ON;
        rdsMode &= FM_OFF;
        rdsFeatures &= 124;
        try {
            if (!setRdsModeNative(rdsMode != 0 ? f75V : false, (afMode & FM_CHIP_OFF) != 0 ? f75V : false, rdsMode & FM_CHIP_OFF)) {
                returnStatus = FM_ON;
            }
        } catch (Exception e) {
            Log.e(TAG, "process_setRdsMode: setRdsModeNative failed", e);
            returnStatus = FM_ON;
        }
        if (returnStatus == 0) {
            return returnStatus;
        }
        this.operationHandler.removeMessages(FM_CHIP_OFF);
        FmReceiverServiceState.radio_state = FM_ON;
        FmReceiverServiceState.radio_op_state = FM_CHIP_ON;
        return returnStatus;
    }

    public synchronized int setAudioMode(int audioMode) {
        Log.d(TAG, "setAudioMode: audioMode = " + audioMode);
        return queueFMCommand(new FMJob((int) FM_SET_AUDIO_MODE, audioMode));
    }

    private int process_setAudioMode(int audioMode) {
        Log.d(TAG, "process_setAudioMode: audioMode = " + audioMode);
        if (FM_ON != FmReceiverServiceState.radio_state) {
            Log.w(TAG, "process_setAudioMode: STATE = " + FmReceiverServiceState.radio_state);
            return FM_OFF;
        }
        FmReceiverServiceState.radio_state = FM_TUNE_RADIO;
        Message msg = Message.obtain();
        msg.what = FM_CHIP_OFF;
        msg.arg1 = FM_SET_AUDIO_MODE;
        this.operationHandler.removeMessages(FM_CHIP_OFF);
        this.operationHandler.sendMessageDelayed(msg, 5000);
        int returnStatus = FM_CHIP_ON;
        try {
            if (!setAudioModeNative(audioMode & FM_OFF)) {
                returnStatus = FM_ON;
            }
        } catch (Exception e) {
            returnStatus = FM_ON;
            Log.e(TAG, "process_setAudioMode: setAudioModeNative failed", e);
        }
        if (returnStatus == 0) {
            return returnStatus;
        }
        this.operationHandler.removeMessages(FM_CHIP_OFF);
        return returnStatus;
    }

    public synchronized int setAudioPath(int audioPath) {
        Log.d(TAG, "setAudioPath: audioPath = " + audioPath);
        queueFMCommand(new FMJob((int) FM_SET_AUDIO_PATH, audioPath));
        return FM_CHIP_ON;
    }

    private int process_setAudioPath(int audioPath) {
        Log.d(TAG, "process_setAudioPath: audioPath = " + audioPath);
        if (FM_ON != FmReceiverServiceState.radio_state) {
            Log.w(TAG, "process_setAudioPath: STATE = " + FmReceiverServiceState.radio_state);
            return FM_OFF;
        }
        FmReceiverServiceState.radio_state = FM_TUNE_RADIO;
        Message msg = Message.obtain();
        msg.what = FM_CHIP_OFF;
        msg.arg1 = FM_SET_AUDIO_PATH;
        this.operationHandler.removeMessages(FM_CHIP_OFF);
        this.operationHandler.sendMessageDelayed(msg, 5000);
        int returnStatus = FM_CHIP_ON;
        audioPath &= FM_OFF;
        try {
            if (!setAudioPathNative(audioPath)) {
                returnStatus = FM_ON;
            }
        } catch (Exception e) {
            returnStatus = FM_ON;
            Log.e(TAG, "process_setAudioPath: setAudioPathNative failed", e);
        }
        if (returnStatus != 0) {
            this.operationHandler.removeMessages(FM_CHIP_OFF);
        }
        if (audioPath == 0) {
            AudioSystem.setParameters("fm_route=disabled");
            return returnStatus;
        } else if (audioPath == FM_CHIP_OFF) {
            AudioSystem.setParameters("fm_route=fm_speaker");
            return returnStatus;
        } else if (audioPath != FM_ON) {
            return returnStatus;
        } else {
            AudioSystem.setParameters("fm_route=fm_headset");
            return returnStatus;
        }
    }

    public synchronized int setStepSize(int stepSize) {
        int i;
        Log.d(TAG, "setStepSize: stepSize = " + stepSize);
        if (stepSize == FM_ESTIMATE_NOISE_FLOOR_LEVEL || stepSize == 0) {
            queueFMCommand(new FMJob((int) FM_SET_STEP_SIZE, stepSize));
            i = FM_CHIP_ON;
        } else {
            Log.e(TAG, "setStepSize: Illegal stepSize = " + stepSize);
            i = FM_TUNE_RADIO;
        }
        return i;
    }

    private int process_setStepSize(int stepSize) {
        Log.d(TAG, "process_setStepSize: stepSize = " + stepSize);
        if (FM_ON != FmReceiverServiceState.radio_state) {
            Log.w(TAG, "process_setStepSize: STATE = " + FmReceiverServiceState.radio_state);
            return FM_OFF;
        }
        FmReceiverServiceState.radio_state = FM_TUNE_RADIO;
        Message msg = Message.obtain();
        msg.what = FM_CHIP_OFF;
        msg.arg1 = FM_SET_STEP_SIZE;
        this.operationHandler.removeMessages(FM_CHIP_OFF);
        this.operationHandler.sendMessageDelayed(msg, 5000);
        int returnStatus = FM_CHIP_ON;
        try {
            if (!setScanStepNative(stepSize)) {
                returnStatus = FM_ON;
            }
        } catch (Exception e) {
            returnStatus = FM_ON;
            Log.e(TAG, "process_setStepSize: setScanStepNative failed", e);
        }
        if (returnStatus == 0) {
            return returnStatus;
        }
        this.operationHandler.removeMessages(FM_CHIP_OFF);
        return returnStatus;
    }

    public synchronized int setFMVolume(int volume) {
        int i;
        Log.d(TAG, "setFMVolume: volume = " + volume);
        if (volume < 0 || volume > 255) {
            Log.e(TAG, "setFMVolume: Illegal volume = " + volume);
            i = FM_TUNE_RADIO;
        } else {
            queueFMCommand(new FMJob((int) FM_SET_VOLUME, volume));
            i = FM_CHIP_ON;
        }
        return i;
    }

    private int process_setFMVolume(int volume) {
        Log.d(TAG, "process_setFMVolume: volume = " + volume);
        if (FM_ON != FmReceiverServiceState.radio_state) {
            Log.w(TAG, "process_setFMVolume: STATE = " + FmReceiverServiceState.radio_state);
            return FM_OFF;
        }
        FmReceiverServiceState.radio_state = FM_TUNE_RADIO;
        Message msg = Message.obtain();
        msg.what = FM_CHIP_OFF;
        msg.arg1 = FM_SET_VOLUME;
        this.operationHandler.removeMessages(FM_CHIP_OFF);
        this.operationHandler.sendMessageDelayed(msg, 5000);
        int returnStatus = FM_CHIP_ON;
        try {
            if (!setFMVolumeNative(volume)) {
                returnStatus = FM_ON;
            }
        } catch (Exception e) {
            returnStatus = FM_ON;
            Log.e(TAG, "process_setFMVolume: setFMVolumeNative failed", e);
        }
        if (returnStatus == 0) {
            return returnStatus;
        }
        this.operationHandler.removeMessages(FM_CHIP_OFF);
        return returnStatus;
    }

    public synchronized int setWorldRegion(int worldRegion, int deemphasisTime) {
        int i = FM_TUNE_RADIO;
        synchronized (this) {
            Log.d(TAG, "setWorldRegion: worldRegion = " + worldRegion + ", deemphasisTime = " + deemphasisTime);
            if (worldRegion != 0 && worldRegion != FM_CHIP_OFF && worldRegion != FM_ON) {
                Log.e(TAG, "setWorldRegion: Illegal worldRegion = " + worldRegion);
            } else if (deemphasisTime == 0 || deemphasisTime == 64) {
                queueFMCommand(new FMJob((int) FM_SET_WORLD_REGION, worldRegion, deemphasisTime));
                i = FM_CHIP_ON;
            } else {
                Log.e(TAG, "setWorldRegion: Illegal deemphasisTime = " + deemphasisTime);
            }
        }
        return i;
    }

    private int process_setWorldRegion(int worldRegion, int deemphasisTime) {
        Log.d(TAG, "process_setWorldRegion: worldRegion = " + worldRegion + ", deemphasisTime = " + deemphasisTime);
        if (FM_ON != FmReceiverServiceState.radio_state) {
            Log.w(TAG, "process_setWorldRegion: STATE = " + FmReceiverServiceState.radio_state);
            return FM_OFF;
        }
        FmReceiverServiceState.radio_state = FM_TUNE_RADIO;
        Message msg = Message.obtain();
        msg.what = FM_CHIP_OFF;
        msg.arg1 = FM_SET_WORLD_REGION;
        this.operationHandler.removeMessages(FM_CHIP_OFF);
        this.operationHandler.sendMessageDelayed(msg, 5000);
        int returnStatus = FM_CHIP_ON;
        try {
            if (!(setRegionNative(worldRegion) && configureDeemphasisNative(deemphasisTime))) {
                returnStatus = FM_ON;
            }
        } catch (Exception e) {
            returnStatus = FM_ON;
            Log.e(TAG, "process_setWorldRegion: setRdsNative failed", e);
        }
        if (returnStatus == 0) {
            return returnStatus;
        }
        this.operationHandler.removeMessages(FM_CHIP_OFF);
        return returnStatus;
    }

    public synchronized int estimateNoiseFloorLevel(int nflLevel) {
        int i;
        Log.d(TAG, "estimateNoiseFloorLevel: nflLevel = " + nflLevel);
        if (nflLevel == FM_ON || nflLevel == FM_CHIP_OFF || nflLevel == 0) {
            queueFMCommand(new FMJob((int) FM_ESTIMATE_NOISE_FLOOR_LEVEL, nflLevel));
            i = FM_CHIP_ON;
        } else {
            Log.e(TAG, "estimateNoiseFloorLevel: Illegal nflLevel = " + nflLevel);
            i = FM_TUNE_RADIO;
        }
        return i;
    }

    private int process_estimateNoiseFloorLevel(int nflLevel) throws RemoteException {
        Log.d(TAG, "process_estimateNoiseFloorLevel: nflLevel = " + nflLevel);
        if (FM_ON != FmReceiverServiceState.radio_state) {
            Log.w(TAG, "process_estimateNoiseFloorLevel: STATE = " + FmReceiverServiceState.radio_state);
            return FM_OFF;
        }
        FmReceiverServiceState.radio_state = FM_TUNE_RADIO;
        Message msg = Message.obtain();
        msg.what = FM_CHIP_OFF;
        msg.arg1 = FM_ESTIMATE_NOISE_FLOOR_LEVEL;
        this.operationHandler.removeMessages(FM_CHIP_OFF);
        this.operationHandler.sendMessageDelayed(msg, 20000);
        int returnStatus = FM_CHIP_ON;
        try {
            if (!estimateNoiseFloorNative(nflLevel)) {
                returnStatus = FM_ON;
            }
        } catch (Exception e) {
            returnStatus = FM_ON;
            Log.e(TAG, "process_estimateNoiseFloorLevel: estimateNoiseFloorNative failed", e);
        }
        if (returnStatus == 0) {
            return returnStatus;
        }
        this.operationHandler.removeMessages(FM_CHIP_OFF);
        return returnStatus;
    }

    public synchronized int setLiveAudioPolling(boolean liveAudioPolling, int signalPollInterval) {
        int i;
        Log.d(TAG, "setLiveAudioPolling: liveAudioPolling = " + liveAudioPolling + ", signalPollInterval = " + signalPollInterval);
        if (!liveAudioPolling || (signalPollInterval >= FM_SEEK_STATION_ABORT && signalPollInterval <= 100000)) {
            queueFMCommand(new FMJob((int) FM_SET_LIVE_AUDIO_POLLING, liveAudioPolling, signalPollInterval));
            i = FM_CHIP_ON;
        } else {
            Log.e(TAG, "seekStation: Illegal liveAudioPolling = " + liveAudioPolling + ", signalPollInterval = " + signalPollInterval);
            i = FM_TUNE_RADIO;
        }
        return i;
    }

    private int process_setLiveAudioPolling(boolean liveAudioPolling, int signalPollInterval) throws RemoteException {
        Log.d(TAG, "process_setLiveAudioPolling: liveAudioPolling = " + liveAudioPolling + ", signalPollInterval = " + signalPollInterval);
        if (FM_ON != FmReceiverServiceState.radio_state) {
            Log.w(TAG, "process_setLiveAudioPolling: STATE = " + FmReceiverServiceState.radio_state);
            return FM_OFF;
        }
        FmReceiverServiceState.radio_state = FM_TUNE_RADIO;
        int returnStatus = FM_CHIP_ON;
        try {
            if (!(getAudioQualityNative(liveAudioPolling) && configureSignalNotificationNative(signalPollInterval))) {
                returnStatus = FM_ON;
            }
        } catch (Exception e) {
            returnStatus = FM_ON;
            Log.e(TAG, "process_setLiveAudioPolling: setLiveAudioPolling failed", e);
        }
        FmReceiverServiceState.radio_state = FM_ON;
        fetchNextJob(FM_SET_LIVE_AUDIO_POLLING);
        return returnStatus;
    }

    private void sendStatusEventCallbackFromLocalStore(int currCmd, boolean sendNextJob) {
        FM_Status_Params status = new FM_Status_Params(FmReceiverServiceState.mFreq, FmReceiverServiceState.mRssi, FmReceiverServiceState.mSnr, FmReceiverServiceState.mRadioIsOn, FmReceiverServiceState.mRdsProgramType, FmReceiverServiceState.mRdsProgramService, FmReceiverServiceState.mRdsRadioText, FmReceiverServiceState.mRdsProgramTypeName, FmReceiverServiceState.mIsMute);
        Message msg = Message.obtain();
        msg.what = FM_ON;
        msg.arg1 = currCmd;
        msg.arg2 = sendNextJob == f75V ? FM_CHIP_OFF : FM_CHIP_ON;
        msg.obj = status;
        this.operationHandler.sendMessage(msg);
    }

    private void sendStatusEventCallback(int freq, int rssi, int snr, boolean radioIsOn, int rdsProgramType, String rdsProgramService, String rdsRadioText, String rdsProgramTypeName, boolean isMute, int currCmd, int sendNextJob) {
        Log.d(TAG, "sendStatusEventCallback: freq = " + freq + ", rssi = " + rssi + ", snr = " + snr + ", radioIsOn = " + radioIsOn + ", rdsProgramType = " + rdsProgramType + ", rdsProgramService = " + rdsProgramService + ", rdsRadioText = " + rdsRadioText + ", rdsProgramTypeName = " + rdsProgramTypeName + ", isMute = " + isMute);
        Log.d(TAG, "sendStatusEventCallback: currCmd = " + currCmd + ", sendNextJob = " + sendNextJob);
        try {
            int callbacks = this.mCallbacks.beginBroadcast();
            for (int i = FM_CHIP_ON; i < callbacks; i += FM_CHIP_OFF) {
                try {
                    ((IFmReceiverCallback) this.mCallbacks.getBroadcastItem(i)).onStatusEvent(freq, rssi, snr, radioIsOn, rdsProgramType, rdsProgramService, rdsRadioText, rdsProgramTypeName, isMute);
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
            }
            this.mCallbacks.finishBroadcast();
        } catch (IllegalStateException e_i) {
            e_i.printStackTrace();
        }
        if (!(FmReceiverServiceState.radio_state == 0 || FmReceiverServiceState.radio_state == FM_OFF)) {
            FmReceiverServiceState.radio_state = FM_ON;
        }
        if (sendNextJob > 0) {
            fetchNextJob(currCmd);
        }
    }

    private void sendSeekCompleteEventCallbackFromLocalStore(int currCmd, boolean sendNextJob) {
        FM_Search_Params search_st = new FM_Search_Params(FmReceiverServiceState.mFreq, FmReceiverServiceState.mRssi, FmReceiverServiceState.mSnr, FmReceiverServiceState.mSeekSuccess);
        Message msg = Message.obtain();
        msg.what = FM_OFF;
        msg.arg1 = currCmd;
        msg.arg2 = sendNextJob == f75V ? FM_CHIP_OFF : FM_CHIP_ON;
        msg.obj = search_st;
        this.operationHandler.sendMessage(msg);
    }

    private void sendSeekCompleteEventCallback(int freq, int rssi, int snr, boolean seekSuccess, int currCmd, int sendNextJob) {
        Log.d(TAG, "sendSeekCompleteEventCallback: freq = " + freq + ", rssi = " + rssi + ", snr = " + snr + ", seekSuccess = " + seekSuccess);
        Log.d(TAG, "sendSeekCompleteEventCallback: currCmd = " + currCmd + ", sendNextJob = " + sendNextJob);
        try {
            int callbacks = this.mCallbacks.beginBroadcast();
            for (int i = FM_CHIP_ON; i < callbacks; i += FM_CHIP_OFF) {
                try {
                    ((IFmReceiverCallback) this.mCallbacks.getBroadcastItem(i)).onSeekCompleteEvent(freq, rssi, snr, seekSuccess);
                } catch (Throwable t) {
                    Log.e(TAG, "sendSeekCompleteEventCallback", t);
                }
            }
            this.mCallbacks.finishBroadcast();
        } catch (IllegalStateException e_i) {
            e_i.printStackTrace();
        }
        if (!(FmReceiverServiceState.radio_state == 0 || FmReceiverServiceState.radio_state == FM_OFF)) {
            FmReceiverServiceState.radio_state = FM_ON;
        }
        if (sendNextJob > 0) {
            fetchNextJob(currCmd);
        }
    }

    private void sendRdsModeEventCallbackFromLocalStore() {
        int af = FmReceiverServiceState.mAfOn ? FM_CHIP_OFF : FM_CHIP_ON;
        int rds = FM_CHIP_ON;
        if (FmReceiverServiceState.mRdsOn) {
            rds = FmReceiverServiceState.mRdsType == 0 ? FM_CHIP_OFF : FM_ON;
        }
        Message msg = Message.obtain();
        msg.what = FM_TUNE_RADIO;
        msg.arg1 = rds;
        msg.arg2 = af;
        this.operationHandler.sendMessage(msg);
    }

    private void sendRdsModeEventCallback(int rdsMode, int alternateFreqMode) {
        Log.d(TAG, "sendRdsModeEventCallback: rdsMode = " + rdsMode + ", alternateFreqMode = " + alternateFreqMode);
        try {
            int callbacks = this.mCallbacks.beginBroadcast();
            for (int i = FM_CHIP_ON; i < callbacks; i += FM_CHIP_OFF) {
                try {
                    ((IFmReceiverCallback) this.mCallbacks.getBroadcastItem(i)).onRdsModeEvent(rdsMode, alternateFreqMode);
                } catch (Throwable t) {
                    Log.e(TAG, "sendRdsModeEventCallback", t);
                }
            }
            this.mCallbacks.finishBroadcast();
        } catch (IllegalStateException e_i) {
            e_i.printStackTrace();
        }
        if (!(FmReceiverServiceState.radio_state == 0 || FmReceiverServiceState.radio_state == FM_OFF)) {
            FmReceiverServiceState.radio_state = FM_ON;
        }
        fetchNextJob(FM_SET_RDS_MODE);
    }

    private void sendRdsDataEventCallbackFromLocalStore(int rdsDataType, int rdsIndex, String rdsText) {
        Message msg = Message.obtain();
        msg.what = FM_GET_STATUS;
        msg.arg1 = rdsDataType;
        msg.arg2 = rdsIndex;
        msg.obj = new String(rdsText);
        this.operationHandler.sendMessage(msg);
    }

    private void sendRdsDataEventCallback(int rdsDataType, int rdsIndex, String rdsText) {
        Log.d(TAG, "sendRdsDataEventCallback: rdsDataType = " + rdsDataType + ", rdsIndex = " + rdsIndex + "rdsText = " + rdsText);
        try {
            int callbacks = this.mCallbacks.beginBroadcast();
            for (int i = FM_CHIP_ON; i < callbacks; i += FM_CHIP_OFF) {
                try {
                    ((IFmReceiverCallback) this.mCallbacks.getBroadcastItem(i)).onRdsDataEvent(rdsDataType, rdsIndex, rdsText);
                } catch (Throwable t) {
                    Log.e(TAG, "sendRdsDataEventCallback", t);
                }
            }
            this.mCallbacks.finishBroadcast();
        } catch (IllegalStateException e_i) {
            e_i.printStackTrace();
        }
    }

    private void sendAudioModeEventCallbackFromLocalStore() {
        Message msg = Message.obtain();
        msg.what = FM_MUTE_AUDIO;
        msg.arg1 = FmReceiverServiceState.mAudioMode;
        this.operationHandler.sendMessage(msg);
    }

    private void sendAudioModeEventCallback(int audioMode) {
        Log.d(TAG, "sendAudioModeEventCallback: audioMode = " + audioMode);
        try {
            int callbacks = this.mCallbacks.beginBroadcast();
            for (int i = FM_CHIP_ON; i < callbacks; i += FM_CHIP_OFF) {
                try {
                    ((IFmReceiverCallback) this.mCallbacks.getBroadcastItem(i)).onAudioModeEvent(audioMode);
                } catch (Throwable t) {
                    Log.e(TAG, "sendAudioModeEventCallback", t);
                }
            }
            this.mCallbacks.finishBroadcast();
        } catch (IllegalStateException e_i) {
            e_i.printStackTrace();
        }
        if (!(FmReceiverServiceState.radio_state == 0 || FmReceiverServiceState.radio_state == FM_OFF)) {
            FmReceiverServiceState.radio_state = FM_ON;
        }
        fetchNextJob(FM_SET_AUDIO_MODE);
    }

    private void sendAudioPathEventCallbackFromLocalStore() {
        Message msg = Message.obtain();
        msg.what = FM_SEEK_STATION;
        msg.arg1 = FmReceiverServiceState.mAudioPath;
        this.operationHandler.sendMessage(msg);
    }

    private void sendAudioPathEventCallback(int audioPath) {
        Log.d(TAG, "sendAudioPathEventCallback: audioPath = " + audioPath);
        try {
            int callbacks = this.mCallbacks.beginBroadcast();
            for (int i = FM_CHIP_ON; i < callbacks; i += FM_CHIP_OFF) {
                try {
                    ((IFmReceiverCallback) this.mCallbacks.getBroadcastItem(i)).onAudioPathEvent(audioPath);
                } catch (Throwable t) {
                    Log.e(TAG, "sendAudioPathEventCallback", t);
                }
            }
            this.mCallbacks.finishBroadcast();
        } catch (IllegalStateException e_i) {
            e_i.printStackTrace();
        }
        if (!(FmReceiverServiceState.radio_state == 0 || FmReceiverServiceState.radio_state == FM_OFF)) {
            FmReceiverServiceState.radio_state = FM_ON;
        }
        fetchNextJob(FM_SET_AUDIO_PATH);
    }

    private void sendWorldRegionEventCallbackFromLocalStore() {
        Message msg = Message.obtain();
        msg.what = FM_SEEK_STATION_COMBO;
        msg.arg1 = FmReceiverServiceState.mWorldRegion;
        this.operationHandler.sendMessage(msg);
    }

    private void sendWorldRegionEventCallback(int worldRegion) {
        Log.d(TAG, "sendWorldRegionEventCallback: worldRegion = " + worldRegion);
        try {
            int callbacks = this.mCallbacks.beginBroadcast();
            for (int i = FM_CHIP_ON; i < callbacks; i += FM_CHIP_OFF) {
                try {
                    ((IFmReceiverCallback) this.mCallbacks.getBroadcastItem(i)).onWorldRegionEvent(worldRegion);
                } catch (Throwable t) {
                    Log.e(TAG, "sendWorldRegionEventCallback", t);
                }
            }
            this.mCallbacks.finishBroadcast();
        } catch (IllegalStateException e_i) {
            e_i.printStackTrace();
        }
        if (!(FmReceiverServiceState.radio_state == 0 || FmReceiverServiceState.radio_state == FM_OFF)) {
            FmReceiverServiceState.radio_state = FM_ON;
        }
        fetchNextJob(FM_SET_WORLD_REGION);
    }

    private void sendEstimateNflEventCallbackFromLocalStore() {
        Message msg = Message.obtain();
        msg.what = FM_SEEK_RDS_STATION;
        msg.arg1 = FmReceiverServiceState.mEstimatedNoiseFloorLevel;
        this.operationHandler.sendMessage(msg);
    }

    private void sendEstimateNflEventCallback(int nfl) {
        Log.d(TAG, "sendEstimateNflEventCallback: nfl = " + nfl);
        try {
            int callbacks = this.mCallbacks.beginBroadcast();
            for (int i = FM_CHIP_ON; i < callbacks; i += FM_CHIP_OFF) {
                try {
                    ((IFmReceiverCallback) this.mCallbacks.getBroadcastItem(i)).onEstimateNflEvent(nfl);
                } catch (Throwable t) {
                    Log.e(TAG, "sendEstimateNflEventCallback", t);
                }
            }
            this.mCallbacks.finishBroadcast();
        } catch (IllegalStateException e_i) {
            e_i.printStackTrace();
        }
        if (!(FmReceiverServiceState.radio_state == 0 || FmReceiverServiceState.radio_state == FM_OFF)) {
            FmReceiverServiceState.radio_state = FM_ON;
        }
        fetchNextJob(FM_ESTIMATE_NOISE_FLOOR_LEVEL);
    }

    private void sendLiveAudioQualityEventCallbackFromLocalStore(int rssi, int snr) {
        Message msg = Message.obtain();
        msg.what = FM_SEEK_STATION_ABORT;
        msg.arg1 = rssi;
        msg.arg2 = snr;
        this.operationHandler.sendMessage(msg);
    }

    private void sendLiveAudioQualityEventCallback(int rssi, int snr) {
        Log.d(TAG, "sendLiveAudioQualityEventCallback: rssi = " + rssi + ", snr = " + snr);
        try {
            int callbacks = this.mCallbacks.beginBroadcast();
            for (int i = FM_CHIP_ON; i < callbacks; i += FM_CHIP_OFF) {
                try {
                    ((IFmReceiverCallback) this.mCallbacks.getBroadcastItem(i)).onLiveAudioQualityEvent(rssi, snr);
                } catch (Throwable t) {
                    Log.e(TAG, "sendLiveAudioQualityEventCallback", t);
                }
            }
            this.mCallbacks.finishBroadcast();
        } catch (IllegalStateException e_i) {
            e_i.printStackTrace();
        }
    }

    private void sendVolumeEventCallbackFromLocalStore(int status, int volume) {
        Message msg = Message.obtain();
        msg.what = FM_SET_RDS_MODE;
        msg.arg1 = status;
        msg.arg2 = volume;
        this.operationHandler.sendMessage(msg);
    }

    private void sendVolumeEventCallback(int status, int volume) {
        Log.d(TAG, "sendVolumeEventCallback: status = " + status + ", volume = " + volume);
        try {
            int callbacks = this.mCallbacks.beginBroadcast();
            for (int i = FM_CHIP_ON; i < callbacks; i += FM_CHIP_OFF) {
                try {
                    ((IFmReceiverCallback) this.mCallbacks.getBroadcastItem(i)).onVolumeEvent(status, volume);
                } catch (Throwable t) {
                    Log.e(TAG, "sendVolumeEventCallback", t);
                }
            }
            this.mCallbacks.finishBroadcast();
        } catch (IllegalStateException e_i) {
            e_i.printStackTrace();
        }
        if (!(FmReceiverServiceState.radio_state == 0 || FmReceiverServiceState.radio_state == FM_OFF)) {
            FmReceiverServiceState.radio_state = FM_ON;
        }
        fetchNextJob(FM_SET_VOLUME);
    }

    public synchronized int setSnrThreshold(int snrThreshold) {
        int i = FM_TUNE_RADIO;
        synchronized (this) {
            Log.d(TAG, "setSnrThreshold: snrThreshold = " + snrThreshold);
            if (snrThreshold < 0 || snrThreshold > 31) {
                Log.e(TAG, "setSnrThreshold: Illegal snrThreshold = " + snrThreshold);
            } else if (FM_ON != FmReceiverServiceState.radio_state) {
                Log.w(TAG, "setSnrThreshold: STATE = " + FmReceiverServiceState.radio_state);
                i = FM_OFF;
            } else {
                FmReceiverServiceState.radio_state = FM_TUNE_RADIO;
                i = FM_CHIP_ON;
                try {
                    if (!setSnrThresholdNative(snrThreshold)) {
                        i = FM_ON;
                    }
                } catch (Exception e) {
                    i = FM_ON;
                    Log.e(TAG, "setSnrThreshold: setSnrThreshold failed", e);
                }
                FmReceiverServiceState.radio_state = FM_ON;
            }
        }
        return i;
    }

    public void onRadioOnEvent(int status) {
        Log.d(TAG, "onRadioOnEvent: status = " + status);
        if (status == 0) {
            FmReceiverServiceState.mRadioIsOn = f75V;
        }
        this.operationHandler.removeMessages(FM_CHIP_OFF);
        if (!FmReceiverServiceState.mRadioIsOn) {
            FmReceiverServiceState.radio_state = FM_CHIP_ON;
        }
        sendStatusEventCallbackFromLocalStore(FM_ON, f75V);
    }

    public void onRadioOffEvent(int status) {
        Log.d(TAG, "onRadioOffEvent: status = " + status);
        this.operationHandler.removeMessages(FM_CHIP_OFF);
        queueFMCommand(new FMJob(FM_CHIP_OFF));
    }

    public void onRadioMuteEvent(int status, boolean muted) {
        Log.d(TAG, "onRadioMuteEvent: status = " + status + ", muted = " + muted);
        if (status == 0) {
            FmReceiverServiceState.mIsMute = muted;
        }
        this.operationHandler.removeMessages(FM_CHIP_OFF);
        sendStatusEventCallbackFromLocalStore(FM_MUTE_AUDIO, f75V);
    }

    public void onRadioTuneEvent(int status, int rssi, int snr, int freq) {
        Log.d(TAG, "onRadioTuneEvent: status = " + status + ", rssi = " + rssi + ", snr = " + snr + ", freq = " + freq);
        this.operationHandler.removeMessages(FM_CHIP_OFF);
        if (status == 0) {
            FmReceiverServiceState.mRssi = rssi;
            FmReceiverServiceState.mSnr = snr;
            FmReceiverServiceState.mFreq = freq;
        }
        sendStatusEventCallbackFromLocalStore(FM_TUNE_RADIO, f75V);
    }

    public void onRadioSearchEvent(int rssi, int snr, int freq) {
        Log.d(TAG, "onRadioSearchEvent: rssi = " + rssi + ", snr = " + snr + ", freq = " + freq);
        this.operationHandler.removeMessages(FM_CHIP_OFF);
        FmReceiverServiceState.mRssi = rssi;
        FmReceiverServiceState.mFreq = freq;
        FmReceiverServiceState.mSnr = snr;
        FmReceiverServiceState.mSeekSuccess = f75V;
        sendSeekCompleteEventCallbackFromLocalStore(FM_CMD_ANY, false);
    }

    public void onRadioSearchCompleteEvent(int status, int rssi, int snr, int freq) {
        Log.d(TAG, "onRadioSearchCompleteEvent: status = " + status + ", rssi = " + rssi + ", snr = " + snr + ", freq = " + freq);
        this.operationHandler.removeMessages(FM_CHIP_OFF);
        FmReceiverServiceState.mRssi = rssi;
        FmReceiverServiceState.mSnr = snr;
        FmReceiverServiceState.mFreq = freq;
        FmReceiverServiceState.mSeekSuccess = status == 0 ? f75V : false;
        sendSeekCompleteEventCallbackFromLocalStore(FM_CMD_ANY, f75V);
    }

    public void onRadioAfJumpEvent(int status, int rssi, int freq) {
        Log.v(TAG, "onRadioAfJumpEvent: status = " + status + ", rssi = " + rssi + ", freq = " + freq + ")");
        this.operationHandler.removeMessages(FM_CHIP_OFF);
        FmReceiverServiceState.mRssi = rssi;
        FmReceiverServiceState.mFreq = freq;
        FmReceiverServiceState.mSeekSuccess = f75V;
        sendSeekCompleteEventCallbackFromLocalStore(FM_CMD_ANY, f75V);
    }

    public void onRadioAudioModeEvent(int status, int mode) {
        Log.d(TAG, "onRadioAudioModeEvent: status = " + status + ", mode = " + mode);
        this.operationHandler.removeMessages(FM_CHIP_OFF);
        if (status == 0) {
            FmReceiverServiceState.mAudioMode = mode;
        }
        sendAudioModeEventCallbackFromLocalStore();
    }

    public void onRadioAudioPathEvent(int status, int path) {
        Log.d(TAG, "onRadioAudioPathEvent: status = " + status + ", path = " + path);
        this.operationHandler.removeMessages(FM_CHIP_OFF);
        if (status == 0) {
            FmReceiverServiceState.mAudioPath = path;
        }
        sendAudioPathEventCallbackFromLocalStore();
    }

    public void onRadioAudioDataEvent(int status, int rssi, int snr, int mode) {
        Log.d(TAG, "onRadioAudioDataEvent: status = " + status + ", rssi = " + rssi + ", snr = " + snr + ", mode = " + mode);
        this.operationHandler.removeMessages(FM_CHIP_OFF);
        if (status == 0) {
            FmReceiverServiceState.mRssi = rssi;
            FmReceiverServiceState.mSnr = snr;
            FmReceiverServiceState.mAudioMode = mode;
        }
        sendLiveAudioQualityEventCallbackFromLocalStore(rssi, snr);
    }

    public void onRadioRdsModeEvent(int status, boolean rdsOn, boolean afOn, int rdsType) {
        Log.d(TAG, "onRadioRdsModeEvent: status = " + status + ", rdsOn = " + rdsOn + ", afOn = " + afOn + "rdsType = " + rdsType);
        this.operationHandler.removeMessages(FM_CHIP_OFF);
        if (status == 0) {
            FmReceiverServiceState.mRdsOn = rdsOn;
            FmReceiverServiceState.mAfOn = afOn;
            if (rdsOn) {
                FmReceiverServiceState.mRdsType = rdsType;
            }
            Log.d(TAG, "onRadioRdsModeEvent(rdsOn:" + Boolean.toString(rdsOn) + ", afOn:" + Boolean.toString(afOn) + ", rdsType:" + Integer.toString(FmReceiverServiceState.mRdsType) + ")");
        }
        FmReceiverServiceState.radio_op_state = FM_CHIP_ON;
        sendRdsModeEventCallbackFromLocalStore();
    }

    public void onRadioRdsTypeEvent(int status, int rdsType) {
        Log.d(TAG, "onRadioRdsTypeEvent(status:" + Integer.toString(status) + ", rdsType:" + Integer.toString(rdsType) + ")");
        if (status == 0) {
            FmReceiverServiceState.mRdsType = rdsType;
        }
        this.operationHandler.removeMessages(FM_CHIP_OFF);
        sendRdsModeEventCallbackFromLocalStore();
        FmReceiverServiceState.radio_op_state = FM_CHIP_ON;
    }

    public void onRadioRdsUpdateEvent(int status, int data, int index, String text) {
        Log.d(TAG, "onRadioRdsUpdateEvent(" + Integer.toString(status) + "," + Integer.toString(data) + "," + Integer.toString(index) + "," + text + ")");
        if (status == 0) {
            switch (data) {
                case FM_ON /*2*/:
                    FmReceiverServiceState.mRdsProgramType = index;
                    break;
                case FM_SEEK_STATION /*7*/:
                    FmReceiverServiceState.mRdsProgramService = text;
                    break;
                case FM_SEEK_STATION_COMBO /*8*/:
                    FmReceiverServiceState.mRdsProgramTypeName = text;
                    break;
                case FM_SEEK_RDS_STATION /*9*/:
                    FmReceiverServiceState.mRdsRadioText = text;
                    break;
            }
            sendRdsDataEventCallbackFromLocalStore(data, index, text);
        }
    }

    public void onRadioDeemphEvent(int status, int time) {
        Log.d(TAG, "onRadioDeemphEvent: status = " + status + ", time = " + time);
        this.operationHandler.removeMessages(FM_CHIP_OFF);
        FmReceiverServiceState.radio_state = FM_ON;
        fetchNextJob(FM_SET_WORLD_REGION);
    }

    public void onRadioScanStepEvent(int stepSize) {
        Log.d(TAG, "onRadioScanStepEvent: stepSize = " + stepSize);
        this.operationHandler.removeMessages(FM_CHIP_OFF);
        FmReceiverServiceState.radio_state = FM_ON;
        fetchNextJob(FM_SET_STEP_SIZE);
    }

    public void onRadioRegionEvent(int status, int region) {
        Log.d(TAG, "onRadioRegionEvent: status = " + status + ", region = " + region);
        this.operationHandler.removeMessages(FM_CHIP_OFF);
        FmReceiverServiceState.mWorldRegion = region;
        sendWorldRegionEventCallbackFromLocalStore();
    }

    public void onRadioNflEstimationEvent(int level) {
        Log.d(TAG, "onRadioNflEstimationEvent: level = " + level);
        this.operationHandler.removeMessages(FM_CHIP_OFF);
        FmReceiverServiceState.radio_state = FM_ON;
        FmReceiverServiceState.mEstimatedNoiseFloorLevel = level;
        sendEstimateNflEventCallbackFromLocalStore();
    }

    public void onRadioVolumeEvent(int status, int volume) {
        Log.d(TAG, "onRadioVolumeEvent: status = " + status + ", volume = " + volume);
        this.operationHandler.removeMessages(FM_CHIP_OFF);
        sendVolumeEventCallbackFromLocalStore(status, volume);
    }
}