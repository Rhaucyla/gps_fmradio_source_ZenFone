package com.broadcom.fm.fmreceiver;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IFmReceiverCallback extends IInterface {

    public static abstract class Stub extends Binder implements IFmReceiverCallback {
        private static final String DESCRIPTOR = "com.broadcom.fm.fmreceiver.IFmReceiverCallback";
        static final int TRANSACTION_onAudioModeEvent = 5;
        static final int TRANSACTION_onAudioPathEvent = 6;
        static final int TRANSACTION_onEstimateNflEvent = 8;
        static final int TRANSACTION_onLiveAudioQualityEvent = 9;
        static final int TRANSACTION_onRdsDataEvent = 4;
        static final int TRANSACTION_onRdsModeEvent = 3;
        static final int TRANSACTION_onSeekCompleteEvent = 2;
        static final int TRANSACTION_onStatusEvent = 1;
        static final int TRANSACTION_onVolumeEvent = 10;
        static final int TRANSACTION_onWorldRegionEvent = 7;

        private static class Proxy implements IFmReceiverCallback {
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

            public void onStatusEvent(int freq, int rssi, int snr, boolean radioIsOn, int rdsProgramType, String rdsProgramService, String rdsRadioText, String rdsProgramTypeName, boolean isMute) throws RemoteException {
                int i = Stub.TRANSACTION_onStatusEvent;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(freq);
                    _data.writeInt(rssi);
                    _data.writeInt(snr);
                    _data.writeInt(radioIsOn ? Stub.TRANSACTION_onStatusEvent : 0);
                    _data.writeInt(rdsProgramType);
                    _data.writeString(rdsProgramService);
                    _data.writeString(rdsRadioText);
                    _data.writeString(rdsProgramTypeName);
                    if (!isMute) {
                        i = 0;
                    }
                    _data.writeInt(i);
                    this.mRemote.transact(Stub.TRANSACTION_onStatusEvent, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void onSeekCompleteEvent(int freq, int rssi, int snr, boolean seeksuccess) throws RemoteException {
                int i = 0;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(freq);
                    _data.writeInt(rssi);
                    _data.writeInt(snr);
                    if (seeksuccess) {
                        i = Stub.TRANSACTION_onStatusEvent;
                    }
                    _data.writeInt(i);
                    this.mRemote.transact(Stub.TRANSACTION_onSeekCompleteEvent, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void onRdsModeEvent(int rdsMode, int alternateFreqHopEnabled) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(rdsMode);
                    _data.writeInt(alternateFreqHopEnabled);
                    this.mRemote.transact(Stub.TRANSACTION_onRdsModeEvent, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void onRdsDataEvent(int rdsDataType, int rdsIndex, String rdsText) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(rdsDataType);
                    _data.writeInt(rdsIndex);
                    _data.writeString(rdsText);
                    this.mRemote.transact(Stub.TRANSACTION_onRdsDataEvent, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void onAudioModeEvent(int audioMode) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(audioMode);
                    this.mRemote.transact(Stub.TRANSACTION_onAudioModeEvent, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void onAudioPathEvent(int audioPath) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(audioPath);
                    this.mRemote.transact(Stub.TRANSACTION_onAudioPathEvent, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void onWorldRegionEvent(int worldRegion) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(worldRegion);
                    this.mRemote.transact(Stub.TRANSACTION_onWorldRegionEvent, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void onEstimateNflEvent(int nfl) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(nfl);
                    this.mRemote.transact(Stub.TRANSACTION_onEstimateNflEvent, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void onLiveAudioQualityEvent(int rssi, int snr) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(rssi);
                    _data.writeInt(snr);
                    this.mRemote.transact(Stub.TRANSACTION_onLiveAudioQualityEvent, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void onVolumeEvent(int status, int volume) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(status);
                    _data.writeInt(volume);
                    this.mRemote.transact(Stub.TRANSACTION_onVolumeEvent, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }
        }

        public Stub() {
            attachInterface(this, DESCRIPTOR);
        }

        public static IFmReceiverCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IFmReceiverCallback)) {
                return new Proxy(obj);
            }
            return (IFmReceiverCallback) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case TRANSACTION_onStatusEvent /*1*/:
                    data.enforceInterface(DESCRIPTOR);
                    onStatusEvent(data.readInt(), data.readInt(), data.readInt(), data.readInt() != 0, data.readInt(), data.readString(), data.readString(), data.readString(), data.readInt() != 0);
                    reply.writeNoException();
                    return true;
                case TRANSACTION_onSeekCompleteEvent /*2*/:
                    data.enforceInterface(DESCRIPTOR);
                    onSeekCompleteEvent(data.readInt(), data.readInt(), data.readInt(), data.readInt() != 0);
                    reply.writeNoException();
                    return true;
                case TRANSACTION_onRdsModeEvent /*3*/:
                    data.enforceInterface(DESCRIPTOR);
                    onRdsModeEvent(data.readInt(), data.readInt());
                    reply.writeNoException();
                    return true;
                case TRANSACTION_onRdsDataEvent /*4*/:
                    data.enforceInterface(DESCRIPTOR);
                    onRdsDataEvent(data.readInt(), data.readInt(), data.readString());
                    reply.writeNoException();
                    return true;
                case TRANSACTION_onAudioModeEvent /*5*/:
                    data.enforceInterface(DESCRIPTOR);
                    onAudioModeEvent(data.readInt());
                    reply.writeNoException();
                    return true;
                case TRANSACTION_onAudioPathEvent /*6*/:
                    data.enforceInterface(DESCRIPTOR);
                    onAudioPathEvent(data.readInt());
                    reply.writeNoException();
                    return true;
                case TRANSACTION_onWorldRegionEvent /*7*/:
                    data.enforceInterface(DESCRIPTOR);
                    onWorldRegionEvent(data.readInt());
                    reply.writeNoException();
                    return true;
                case TRANSACTION_onEstimateNflEvent /*8*/:
                    data.enforceInterface(DESCRIPTOR);
                    onEstimateNflEvent(data.readInt());
                    reply.writeNoException();
                    return true;
                case TRANSACTION_onLiveAudioQualityEvent /*9*/:
                    data.enforceInterface(DESCRIPTOR);
                    onLiveAudioQualityEvent(data.readInt(), data.readInt());
                    reply.writeNoException();
                    return true;
                case TRANSACTION_onVolumeEvent /*10*/:
                    data.enforceInterface(DESCRIPTOR);
                    onVolumeEvent(data.readInt(), data.readInt());
                    reply.writeNoException();
                    return true;
                case 1598968902:
                    reply.writeString(DESCRIPTOR);
                    return true;
                default:
                    return super.onTransact(code, data, reply, flags);
            }
        }
    }

    void onAudioModeEvent(int i) throws RemoteException;

    void onAudioPathEvent(int i) throws RemoteException;

    void onEstimateNflEvent(int i) throws RemoteException;

    void onLiveAudioQualityEvent(int i, int i2) throws RemoteException;

    void onRdsDataEvent(int i, int i2, String str) throws RemoteException;

    void onRdsModeEvent(int i, int i2) throws RemoteException;

    void onSeekCompleteEvent(int i, int i2, int i3, boolean z) throws RemoteException;

    void onStatusEvent(int i, int i2, int i3, boolean z, int i4, String str, String str2, String str3, boolean z2) throws RemoteException;

    void onVolumeEvent(int i, int i2) throws RemoteException;

    void onWorldRegionEvent(int i) throws RemoteException;
}
