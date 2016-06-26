package com.broadcom.fm.fmreceiver;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IFmReceiverService extends IInterface {

    public static abstract class Stub extends Binder implements IFmReceiverService {
        private static final String DESCRIPTOR = "com.broadcom.fm.fmreceiver.IFmReceiverService";
        static final int TRANSACTION_cleanupFmService = 26;
        static final int TRANSACTION_estimateNoiseFloorLevel = 22;
        static final int TRANSACTION_getIsMute = 7;
        static final int TRANSACTION_getMonoStereoMode = 5;
        static final int TRANSACTION_getRadioIsOn = 4;
        static final int TRANSACTION_getStatus = 11;
        static final int TRANSACTION_getTunedFrequency = 6;
        static final int TRANSACTION_init = 1;
        static final int TRANSACTION_muteAudio = 12;
        static final int TRANSACTION_registerCallback = 2;
        static final int TRANSACTION_seekRdsStation = 15;
        static final int TRANSACTION_seekStation = 13;
        static final int TRANSACTION_seekStationAbort = 16;
        static final int TRANSACTION_seekStationCombo = 14;
        static final int TRANSACTION_setAudioMode = 18;
        static final int TRANSACTION_setAudioPath = 19;
        static final int TRANSACTION_setFMVolume = 24;
        static final int TRANSACTION_setLiveAudioPolling = 23;
        static final int TRANSACTION_setRdsMode = 17;
        static final int TRANSACTION_setSnrThreshold = 25;
        static final int TRANSACTION_setStepSize = 20;
        static final int TRANSACTION_setWorldRegion = 21;
        static final int TRANSACTION_tuneRadio = 10;
        static final int TRANSACTION_turnOffRadio = 8;
        static final int TRANSACTION_turnOnRadio = 9;
        static final int TRANSACTION_unregisterCallback = 3;

        private static class Proxy implements IFmReceiverService {
            private IBinder mRemote;

            Proxy(IBinder remote) {
                this.mRemote = remote;
            }

            public IBinder asBinder() {
                return this.mRemote;
            }

            public String getInterfaceDescriptor() {
                return Stub.DESCRIPTOR;
            }

            public void init() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_init, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void registerCallback(IFmReceiverCallback cb) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(cb != null ? cb.asBinder() : null);
                    this.mRemote.transact(Stub.TRANSACTION_registerCallback, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void unregisterCallback(IFmReceiverCallback cb) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(cb != null ? cb.asBinder() : null);
                    this.mRemote.transact(Stub.TRANSACTION_unregisterCallback, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean getRadioIsOn() throws RemoteException {
                boolean _result = false;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_getRadioIsOn, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getMonoStereoMode() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_getMonoStereoMode, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getTunedFrequency() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_getTunedFrequency, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean getIsMute() throws RemoteException {
                boolean _result = false;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_getIsMute, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = true;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int turnOffRadio() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_turnOffRadio, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int turnOnRadio(int functionalityMask, char[] clientPackagename) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(functionalityMask);
                    _data.writeCharArray(clientPackagename);
                    this.mRemote.transact(Stub.TRANSACTION_turnOnRadio, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    _reply.readCharArray(clientPackagename);
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int tuneRadio(int freq) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(freq);
                    this.mRemote.transact(Stub.TRANSACTION_tuneRadio, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int getStatus() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_getStatus, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int muteAudio(boolean mute) throws RemoteException {
                int i = 0;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (mute) {
                        i = Stub.TRANSACTION_init;
                    }
                    _data.writeInt(i);
                    this.mRemote.transact(Stub.TRANSACTION_muteAudio, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int seekStation(int scanDirection, int minSignalStrength) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(scanDirection);
                    _data.writeInt(minSignalStrength);
                    this.mRemote.transact(Stub.TRANSACTION_seekStation, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int seekStationCombo(int startFreq, int endFreq, int minSignalStrength, int dir, int scanMethod, boolean multiChannel, int rdsType, int rdsTypeValue) throws RemoteException {
                int i = 0;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(startFreq);
                    _data.writeInt(endFreq);
                    _data.writeInt(minSignalStrength);
                    _data.writeInt(dir);
                    _data.writeInt(scanMethod);
                    if (multiChannel) {
                        i = Stub.TRANSACTION_init;
                    }
                    _data.writeInt(i);
                    _data.writeInt(rdsType);
                    _data.writeInt(rdsTypeValue);
                    this.mRemote.transact(Stub.TRANSACTION_seekStationCombo, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int seekRdsStation(int scanDirection, int minSignalStrength, int rdsCondition, int rdsValue) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(scanDirection);
                    _data.writeInt(minSignalStrength);
                    _data.writeInt(rdsCondition);
                    _data.writeInt(rdsValue);
                    this.mRemote.transact(Stub.TRANSACTION_seekRdsStation, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int seekStationAbort() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_seekStationAbort, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int setRdsMode(int rdsMode, int rdsFeatures, int afMode, int afThreshold) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(rdsMode);
                    _data.writeInt(rdsFeatures);
                    _data.writeInt(afMode);
                    _data.writeInt(afThreshold);
                    this.mRemote.transact(Stub.TRANSACTION_setRdsMode, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int setAudioMode(int audioMode) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(audioMode);
                    this.mRemote.transact(Stub.TRANSACTION_setAudioMode, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int setAudioPath(int audioPath) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(audioPath);
                    this.mRemote.transact(Stub.TRANSACTION_setAudioPath, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int setStepSize(int stepSize) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(stepSize);
                    this.mRemote.transact(Stub.TRANSACTION_setStepSize, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int setWorldRegion(int worldRegion, int deemphasisTime) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(worldRegion);
                    _data.writeInt(deemphasisTime);
                    this.mRemote.transact(Stub.TRANSACTION_setWorldRegion, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int estimateNoiseFloorLevel(int nflLevel) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(nflLevel);
                    this.mRemote.transact(Stub.TRANSACTION_estimateNoiseFloorLevel, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int setLiveAudioPolling(boolean liveAudioPolling, int signalPollInterval) throws RemoteException {
                int i = 0;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (liveAudioPolling) {
                        i = Stub.TRANSACTION_init;
                    }
                    _data.writeInt(i);
                    _data.writeInt(signalPollInterval);
                    this.mRemote.transact(Stub.TRANSACTION_setLiveAudioPolling, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int setFMVolume(int volume) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(volume);
                    this.mRemote.transact(Stub.TRANSACTION_setFMVolume, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int setSnrThreshold(int snrThreshold) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(snrThreshold);
                    this.mRemote.transact(Stub.TRANSACTION_setSnrThreshold, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int cleanupFmService() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_cleanupFmService, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IFmReceiverService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IFmReceiverService)) {
                return new Proxy(obj);
            }
            return (IFmReceiverService) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            boolean _result;
            int _result2;
            switch (code) {
                case TRANSACTION_init /*1*/:
                    data.enforceInterface(DESCRIPTOR);
                    init();
                    reply.writeNoException();
                    return true;
                case TRANSACTION_registerCallback /*2*/:
                    data.enforceInterface(DESCRIPTOR);
                    registerCallback(com.broadcom.fm.fmreceiver.IFmReceiverCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case TRANSACTION_unregisterCallback /*3*/:
                    data.enforceInterface(DESCRIPTOR);
                    unregisterCallback(com.broadcom.fm.fmreceiver.IFmReceiverCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    return true;
                case TRANSACTION_getRadioIsOn /*4*/:
                    data.enforceInterface(DESCRIPTOR);
                    _result = getRadioIsOn();
                    reply.writeNoException();
                    reply.writeInt(_result ? TRANSACTION_init : 0);
                    return true;
                case TRANSACTION_getMonoStereoMode /*5*/:
                    data.enforceInterface(DESCRIPTOR);
                    _result2 = getMonoStereoMode();
                    reply.writeNoException();
                    reply.writeInt(_result2);
                    return true;
                case TRANSACTION_getTunedFrequency /*6*/:
                    data.enforceInterface(DESCRIPTOR);
                    _result2 = getTunedFrequency();
                    reply.writeNoException();
                    reply.writeInt(_result2);
                    return true;
                case TRANSACTION_getIsMute /*7*/:
                    data.enforceInterface(DESCRIPTOR);
                    _result = getIsMute();
                    reply.writeNoException();
                    reply.writeInt(_result ? TRANSACTION_init : 0);
                    return true;
                case TRANSACTION_turnOffRadio /*8*/:
                    data.enforceInterface(DESCRIPTOR);
                    _result2 = turnOffRadio();
                    reply.writeNoException();
                    reply.writeInt(_result2);
                    return true;
                case TRANSACTION_turnOnRadio /*9*/:
                    data.enforceInterface(DESCRIPTOR);
                    int _arg0 = data.readInt();
                    char[] _arg1 = data.createCharArray();
                    _result2 = turnOnRadio(_arg0, _arg1);
                    reply.writeNoException();
                    reply.writeInt(_result2);
                    reply.writeCharArray(_arg1);
                    return true;
                case TRANSACTION_tuneRadio /*10*/:
                    data.enforceInterface(DESCRIPTOR);
                    _result2 = tuneRadio(data.readInt());
                    reply.writeNoException();
                    reply.writeInt(_result2);
                    return true;
                case TRANSACTION_getStatus /*11*/:
                    data.enforceInterface(DESCRIPTOR);
                    _result2 = getStatus();
                    reply.writeNoException();
                    reply.writeInt(_result2);
                    return true;
                case TRANSACTION_muteAudio /*12*/:
                    data.enforceInterface(DESCRIPTOR);
                    _result2 = muteAudio(data.readInt() != 0);
                    reply.writeNoException();
                    reply.writeInt(_result2);
                    return true;
                case TRANSACTION_seekStation /*13*/:
                    data.enforceInterface(DESCRIPTOR);
                    _result2 = seekStation(data.readInt(), data.readInt());
                    reply.writeNoException();
                    reply.writeInt(_result2);
                    return true;
                case TRANSACTION_seekStationCombo /*14*/:
                    data.enforceInterface(DESCRIPTOR);
                    _result2 = seekStationCombo(data.readInt(), data.readInt(), data.readInt(), data.readInt(), data.readInt(), data.readInt() != 0, data.readInt(), data.readInt());
                    reply.writeNoException();
                    reply.writeInt(_result2);
                    return true;
                case TRANSACTION_seekRdsStation /*15*/:
                    data.enforceInterface(DESCRIPTOR);
                    _result2 = seekRdsStation(data.readInt(), data.readInt(), data.readInt(), data.readInt());
                    reply.writeNoException();
                    reply.writeInt(_result2);
                    return true;
                case TRANSACTION_seekStationAbort /*16*/:
                    data.enforceInterface(DESCRIPTOR);
                    _result2 = seekStationAbort();
                    reply.writeNoException();
                    reply.writeInt(_result2);
                    return true;
                case TRANSACTION_setRdsMode /*17*/:
                    data.enforceInterface(DESCRIPTOR);
                    _result2 = setRdsMode(data.readInt(), data.readInt(), data.readInt(), data.readInt());
                    reply.writeNoException();
                    reply.writeInt(_result2);
                    return true;
                case TRANSACTION_setAudioMode /*18*/:
                    data.enforceInterface(DESCRIPTOR);
                    _result2 = setAudioMode(data.readInt());
                    reply.writeNoException();
                    reply.writeInt(_result2);
                    return true;
                case TRANSACTION_setAudioPath /*19*/:
                    data.enforceInterface(DESCRIPTOR);
                    _result2 = setAudioPath(data.readInt());
                    reply.writeNoException();
                    reply.writeInt(_result2);
                    return true;
                case TRANSACTION_setStepSize /*20*/:
                    data.enforceInterface(DESCRIPTOR);
                    _result2 = setStepSize(data.readInt());
                    reply.writeNoException();
                    reply.writeInt(_result2);
                    return true;
                case TRANSACTION_setWorldRegion /*21*/:
                    data.enforceInterface(DESCRIPTOR);
                    _result2 = setWorldRegion(data.readInt(), data.readInt());
                    reply.writeNoException();
                    reply.writeInt(_result2);
                    return true;
                case TRANSACTION_estimateNoiseFloorLevel /*22*/:
                    data.enforceInterface(DESCRIPTOR);
                    _result2 = estimateNoiseFloorLevel(data.readInt());
                    reply.writeNoException();
                    reply.writeInt(_result2);
                    return true;
                case TRANSACTION_setLiveAudioPolling /*23*/:
                    data.enforceInterface(DESCRIPTOR);
                    _result2 = setLiveAudioPolling(data.readInt() != 0, data.readInt());
                    reply.writeNoException();
                    reply.writeInt(_result2);
                    return true;
                case TRANSACTION_setFMVolume /*24*/:
                    data.enforceInterface(DESCRIPTOR);
                    _result2 = setFMVolume(data.readInt());
                    reply.writeNoException();
                    reply.writeInt(_result2);
                    return true;
                case TRANSACTION_setSnrThreshold /*25*/:
                    data.enforceInterface(DESCRIPTOR);
                    _result2 = setSnrThreshold(data.readInt());
                    reply.writeNoException();
                    reply.writeInt(_result2);
                    return true;
                case TRANSACTION_cleanupFmService /*26*/:
                    data.enforceInterface(DESCRIPTOR);
                    _result2 = cleanupFmService();
                    reply.writeNoException();
                    reply.writeInt(_result2);
                    return true;
                case 1598968902:
                    reply.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    int cleanupFmService() throws RemoteException;

    int estimateNoiseFloorLevel(int i) throws RemoteException;

    boolean getIsMute() throws RemoteException;

    int getMonoStereoMode() throws RemoteException;

    boolean getRadioIsOn() throws RemoteException;

    int getStatus() throws RemoteException;

    int getTunedFrequency() throws RemoteException;

    void init() throws RemoteException;

    int muteAudio(boolean z) throws RemoteException;

    void registerCallback(IFmReceiverCallback iFmReceiverCallback) throws RemoteException;

    int seekRdsStation(int i, int i2, int i3, int i4) throws RemoteException;

    int seekStation(int i, int i2) throws RemoteException;

    int seekStationAbort() throws RemoteException;

    int seekStationCombo(int i, int i2, int i3, int i4, int i5, boolean z, int i6, int i7) throws RemoteException;

    int setAudioMode(int i) throws RemoteException;

    int setAudioPath(int i) throws RemoteException;

    int setFMVolume(int i) throws RemoteException;

    int setLiveAudioPolling(boolean z, int i) throws RemoteException;

    int setRdsMode(int i, int i2, int i3, int i4) throws RemoteException;

    int setSnrThreshold(int i) throws RemoteException;

    int setStepSize(int i) throws RemoteException;

    int setWorldRegion(int i, int i2) throws RemoteException;

    int tuneRadio(int i) throws RemoteException;

    int turnOffRadio() throws RemoteException;

    int turnOnRadio(int i, char[] cArr) throws RemoteException;

    void unregisterCallback(IFmReceiverCallback iFmReceiverCallback) throws RemoteException;
}
