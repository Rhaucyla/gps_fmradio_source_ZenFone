package com.broadcom.fm.fmreceiver;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.RemoteException;
import android.util.Log;
import com.broadcom.fm.fmreceiver.IFmReceiverService.Stub;

public class FmService extends Service {
    private static final boolean f77D = true;
    public static final String TAG = "FmService";
    private static final boolean f78V = true;
    private FmReceiverServiceStub mBinder;
    public FmNativehandler mSvcHandler;

    private static final class FmReceiverServiceStub extends Stub {
        private static final String TAG = "FmService";
        private FmService mSvc;

        public FmReceiverServiceStub(FmService service) {
            this.mSvc = service;
            Log.d(TAG, "FmReceiverServiceStub created" + this + "service" + service);
        }

        public void cleanUp() {
            this.mSvc.mSvcHandler.stop();
            this.mSvc.mSvcHandler.finish();
            this.mSvc.mSvcHandler = null;
        }

        public synchronized void registerCallback(IFmReceiverCallback cb) throws RemoteException {
            if (this.mSvc != null) {
                this.mSvc.mSvcHandler.registerCallback(cb);
            }
        }

        public synchronized void unregisterCallback(IFmReceiverCallback cb) throws RemoteException {
            if (this.mSvc != null) {
                this.mSvc.mSvcHandler.unregisterCallback(cb);
            }
        }

        public synchronized int turnOnRadio(int functionalityMask, char[] clientPackagename) throws RemoteException {
            int i;
            if (this.mSvc == null) {
                i = 2;
            } else {
                Log.d(TAG, "turnOnRadio() mSvc:" + this.mSvc);
                i = this.mSvc.mSvcHandler.turnOnRadio(functionalityMask, clientPackagename);
            }
            return i;
        }

        public synchronized int turnOffRadio() throws RemoteException {
            int i;
            if (this.mSvc == null) {
                i = 2;
            } else {
                i = this.mSvc.mSvcHandler.turnOffRadio();
            }
            return i;
        }

        public synchronized int cleanupFmService() {
            int i;
            if (this.mSvc == null) {
                i = 2;
            } else {
                i = this.mSvc.mSvcHandler.cleanupFmService();
            }
            return i;
        }

        public synchronized int tuneRadio(int freq) {
            int i;
            if (this.mSvc == null) {
                i = 2;
            } else {
                i = this.mSvc.mSvcHandler.tuneRadio(freq);
            }
            return i;
        }

        public synchronized int getStatus() {
            int i;
            if (this.mSvc == null) {
                i = 3;
            } else {
                i = this.mSvc.mSvcHandler.getStatus();
            }
            return i;
        }

        public synchronized int muteAudio(boolean mute) {
            int i;
            if (this.mSvc == null) {
                i = 3;
            } else {
                i = this.mSvc.mSvcHandler.muteAudio(mute);
            }
            return i;
        }

        public synchronized int seekStation(int scanMode, int minSignalStrength) {
            int i;
            if (this.mSvc == null) {
                i = 2;
            } else {
                i = this.mSvc.mSvcHandler.seekStation(scanMode, minSignalStrength);
            }
            return i;
        }

        public synchronized int seekRdsStation(int scanMode, int minSignalStrength, int rdsCondition, int rdsValue) {
            int i;
            if (this.mSvc == null) {
                i = 2;
            } else {
                i = this.mSvc.mSvcHandler.seekRdsStation(scanMode, minSignalStrength, rdsCondition, rdsValue);
            }
            return i;
        }

        public synchronized int seekStationAbort() {
            int i;
            if (this.mSvc == null) {
                i = 2;
            } else {
                i = this.mSvc.mSvcHandler.seekStationAbort();
            }
            return i;
        }

        public synchronized int seekStationCombo(int startFrequency, int endFrequency, int minSignalStrength, int scanDirection, int scanMethod, boolean multi_channel, int rdsType, int rdsTypeValue) {
            int i;
            if (this.mSvc == null) {
                i = 2;
            } else {
                i = this.mSvc.mSvcHandler.seekStationCombo(startFrequency, endFrequency, minSignalStrength, scanDirection, scanMethod, multi_channel, rdsType, rdsTypeValue);
            }
            return i;
        }

        public synchronized int setRdsMode(int rdsMode, int rdsFeatures, int afMode, int afThreshold) {
            int i;
            if (this.mSvc == null) {
                i = 2;
            } else {
                i = this.mSvc.mSvcHandler.setRdsMode(rdsMode, rdsFeatures, afMode, afThreshold);
            }
            return i;
        }

        public synchronized int setAudioMode(int audioMode) {
            int i;
            if (this.mSvc == null) {
                i = 2;
            } else {
                i = this.mSvc.mSvcHandler.setAudioMode(audioMode);
            }
            return i;
        }

        public synchronized int setAudioPath(int audioPath) {
            int i;
            if (this.mSvc == null) {
                i = 2;
            } else {
                i = this.mSvc.mSvcHandler.setAudioPath(audioPath);
            }
            return i;
        }

        public synchronized int setStepSize(int stepSize) {
            int i;
            if (this.mSvc == null) {
                i = 2;
            } else {
                i = this.mSvc.mSvcHandler.setStepSize(stepSize);
            }
            return i;
        }

        public synchronized int setFMVolume(int volume) {
            int i;
            if (this.mSvc == null) {
                i = 2;
            } else {
                i = this.mSvc.mSvcHandler.setFMVolume(volume);
            }
            return i;
        }

        public synchronized int setSnrThreshold(int snrThreshold) {
            int i;
            if (this.mSvc == null) {
                i = 2;
            } else {
                i = this.mSvc.mSvcHandler.setSnrThreshold(snrThreshold);
            }
            return i;
        }

        public synchronized int setWorldRegion(int worldRegion, int deemphasisTime) {
            int i;
            if (this.mSvc == null) {
                i = 2;
            } else {
                i = this.mSvc.mSvcHandler.setWorldRegion(worldRegion, deemphasisTime);
            }
            return i;
        }

        public synchronized int estimateNoiseFloorLevel(int nflLevel) throws RemoteException {
            int i;
            if (this.mSvc == null) {
                i = 2;
            } else {
                i = this.mSvc.mSvcHandler.estimateNoiseFloorLevel(nflLevel);
            }
            return i;
        }

        public synchronized int setLiveAudioPolling(boolean liveAudioPolling, int signalPollInterval) throws RemoteException {
            int i;
            if (this.mSvc == null) {
                i = 2;
            } else {
                i = this.mSvc.mSvcHandler.setLiveAudioPolling(liveAudioPolling, signalPollInterval);
            }
            return i;
        }

        public synchronized int getMonoStereoMode() throws RemoteException {
            int i;
            if (this.mSvc == null) {
                i = 2;
            } else {
                i = this.mSvc.mSvcHandler.getMonoStereoMode();
            }
            return i;
        }

        public synchronized int getTunedFrequency() throws RemoteException {
            int i;
            if (this.mSvc == null) {
                i = 2;
            } else {
                i = this.mSvc.mSvcHandler.getTunedFrequency();
            }
            return i;
        }

        public synchronized boolean getIsMute() throws RemoteException {
            boolean z;
            if (this.mSvc == null) {
                z = false;
            } else {
                z = this.mSvc.mSvcHandler.getIsMute();
            }
            return z;
        }

        public void init() throws RemoteException {
        }

        public boolean getRadioIsOn() throws RemoteException {
            if (this.mSvc == null) {
                return false;
            }
            return this.mSvc.mSvcHandler.getRadioIsOn();
        }
    }

    public FmService() {
        this.mSvcHandler = new FmNativehandler(this);
    }

    public void onCreate() {
        super.onCreate();
        this.mBinder = new FmReceiverServiceStub(this);
        this.mBinder.mSvc.mSvcHandler.start();
        Log.v(TAG, "FM Service  onCreate");
    }

    public int onStartCommand(Intent intent, int flags, int startId) {
        return 2;
    }

    public void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy");
        this.mBinder.cleanUp();
        this.mBinder = null;
        Log.d(TAG, "onDestroy done");
    }

    public IBinder onBind(Intent intent) {
        Log.d(TAG, "Binding service...");
        return this.mBinder;
    }
}