package com.asus.fm;

import android.app.Notification;
import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IFMRemoteService extends IInterface {

    public static abstract class Stub extends Binder implements IFMRemoteService {
        private static final String DESCRIPTOR = "com.asus.fm.IFMRemoteService";
        static final int TRANSACTION_event = 1;
        static final int TRANSACTION_getBoolInfo = 2;
        static final int TRANSACTION_getExtraInfo = 13;
        static final int TRANSACTION_getIntInfo = 3;
        static final int TRANSACTION_getStringInfo = 4;
        static final int TRANSACTION_nativeCommand = 8;
        static final int TRANSACTION_registerCallback = 9;
        static final int TRANSACTION_setBoolInfo = 5;
        static final int TRANSACTION_setExtraInfo = 14;
        static final int TRANSACTION_setIntInfo = 6;
        static final int TRANSACTION_setStringInfo = 7;
        static final int TRANSACTION_startForeground = 11;
        static final int TRANSACTION_stopForeground = 12;
        static final int TRANSACTION_unregisterCallback = 10;

        private static class Proxy implements IFMRemoteService {
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

            public void event(int evt, int arg1, int arg2, String freq, ExtraInfo info) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(evt);
                    _data.writeInt(arg1);
                    _data.writeInt(arg2);
                    _data.writeString(freq);
                    if (info != null) {
                        _data.writeInt(Stub.TRANSACTION_event);
                        info.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.mRemote.transact(Stub.TRANSACTION_event, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean getBoolInfo(int type) throws RemoteException {
                boolean _result = false;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(type);
                    this.mRemote.transact(Stub.TRANSACTION_getBoolInfo, _data, _reply, 0);
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

            public int getIntInfo(int type) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(type);
                    this.mRemote.transact(Stub.TRANSACTION_getIntInfo, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public String getStringInfo(int type) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(type);
                    this.mRemote.transact(Stub.TRANSACTION_getStringInfo, _data, _reply, 0);
                    _reply.readException();
                    String _result = _reply.readString();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int setBoolInfo(int type, boolean info) throws RemoteException {
                int i = 0;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(type);
                    if (info) {
                        i = Stub.TRANSACTION_event;
                    }
                    _data.writeInt(i);
                    this.mRemote.transact(Stub.TRANSACTION_setBoolInfo, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int setIntInfo(int type, int info) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeInt(info);
                    this.mRemote.transact(Stub.TRANSACTION_setIntInfo, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int setStringInfo(int type, String info) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(type);
                    _data.writeString(info);
                    this.mRemote.transact(Stub.TRANSACTION_setStringInfo, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int nativeCommand(int type) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(type);
                    this.mRemote.transact(Stub.TRANSACTION_nativeCommand, _data, _reply, 0);
                    _reply.readException();
                    int _result = _reply.readInt();
                    return _result;
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public boolean registerCallback(IFMRemoteCallback cb) throws RemoteException {
                boolean _result = false;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeStrongBinder(cb != null ? cb.asBinder() : null);
                    this.mRemote.transact(Stub.TRANSACTION_registerCallback, _data, _reply, 0);
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

            public void unregisterCallback() throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    this.mRemote.transact(Stub.TRANSACTION_unregisterCallback, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void startForeground(int id, Notification notification) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(id);
                    if (notification != null) {
                        _data.writeInt(Stub.TRANSACTION_event);
                        notification.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.mRemote.transact(Stub.TRANSACTION_startForeground, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public void stopForeground(boolean removeNotification) throws RemoteException {
                int i = 0;
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    if (removeNotification) {
                        i = Stub.TRANSACTION_event;
                    }
                    _data.writeInt(i);
                    this.mRemote.transact(Stub.TRANSACTION_stopForeground, _data, _reply, 0);
                    _reply.readException();
                } finally {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public ExtraInfo getExtraInfo(int type) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    ExtraInfo _result;
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(type);
                    this.mRemote.transact(Stub.TRANSACTION_getExtraInfo, _data, _reply, 0);
                    _reply.readException();
                    if (_reply.readInt() != 0) {
                        _result = (ExtraInfo) ExtraInfo.CREATOR.createFromParcel(_reply);
                    } else {
                        _result = null;
                    }
                    _reply.recycle();
                    _data.recycle();
                    return _result;
                } catch (Throwable th) {
                    _reply.recycle();
                    _data.recycle();
                }
            }

            public int setExtraInfo(int type, ExtraInfo info) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(type);
                    if (info != null) {
                        _data.writeInt(Stub.TRANSACTION_event);
                        info.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.mRemote.transact(Stub.TRANSACTION_setExtraInfo, _data, _reply, 0);
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

        public static IFMRemoteService asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IFMRemoteService)) {
                return new Proxy(obj);
            }
            return (IFMRemoteService) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            int i = 0;
            int _arg0;
            boolean _result;
            int _result2;
            switch (code) {
                case TRANSACTION_event /*1*/:
                    ExtraInfo _arg4;
                    data.enforceInterface(DESCRIPTOR);
                    _arg0 = data.readInt();
                    int _arg1 = data.readInt();
                    int _arg2 = data.readInt();
                    String _arg3 = data.readString();
                    if (data.readInt() != 0) {
                        _arg4 = (ExtraInfo) ExtraInfo.CREATOR.createFromParcel(data);
                    } else {
                        _arg4 = null;
                    }
                    event(_arg0, _arg1, _arg2, _arg3, _arg4);
                    reply.writeNoException();
                    return true;
                case TRANSACTION_getBoolInfo /*2*/:
                    data.enforceInterface(DESCRIPTOR);
                    _result = getBoolInfo(data.readInt());
                    reply.writeNoException();
                    if (_result) {
                        i = TRANSACTION_event;
                    }
                    reply.writeInt(i);
                    return true;
                case TRANSACTION_getIntInfo /*3*/:
                    data.enforceInterface(DESCRIPTOR);
                    _result2 = getIntInfo(data.readInt());
                    reply.writeNoException();
                    reply.writeInt(_result2);
                    return true;
                case TRANSACTION_getStringInfo /*4*/:
                    data.enforceInterface(DESCRIPTOR);
                    String _result3 = getStringInfo(data.readInt());
                    reply.writeNoException();
                    reply.writeString(_result3);
                    return true;
                case TRANSACTION_setBoolInfo /*5*/:
                    boolean _arg12;
                    data.enforceInterface(DESCRIPTOR);
                    _arg0 = data.readInt();
                    if (data.readInt() != 0) {
                        _arg12 = true;
                    } else {
                        _arg12 = false;
                    }
                    _result2 = setBoolInfo(_arg0, _arg12);
                    reply.writeNoException();
                    reply.writeInt(_result2);
                    return true;
                case TRANSACTION_setIntInfo /*6*/:
                    data.enforceInterface(DESCRIPTOR);
                    _result2 = setIntInfo(data.readInt(), data.readInt());
                    reply.writeNoException();
                    reply.writeInt(_result2);
                    return true;
                case TRANSACTION_setStringInfo /*7*/:
                    data.enforceInterface(DESCRIPTOR);
                    _result2 = setStringInfo(data.readInt(), data.readString());
                    reply.writeNoException();
                    reply.writeInt(_result2);
                    return true;
                case TRANSACTION_nativeCommand /*8*/:
                    data.enforceInterface(DESCRIPTOR);
                    _result2 = nativeCommand(data.readInt());
                    reply.writeNoException();
                    reply.writeInt(_result2);
                    return true;
                case TRANSACTION_registerCallback /*9*/:
                    data.enforceInterface(DESCRIPTOR);
                    _result = registerCallback(com.asus.fm.IFMRemoteCallback.Stub.asInterface(data.readStrongBinder()));
                    reply.writeNoException();
                    if (_result) {
                        i = TRANSACTION_event;
                    }
                    reply.writeInt(i);
                    return true;
                case TRANSACTION_unregisterCallback /*10*/:
                    data.enforceInterface(DESCRIPTOR);
                    unregisterCallback();
                    reply.writeNoException();
                    return true;
                case TRANSACTION_startForeground /*11*/:
                    Notification _arg13;
                    data.enforceInterface(DESCRIPTOR);
                    _arg0 = data.readInt();
                    if (data.readInt() != 0) {
                        _arg13 = (Notification) Notification.CREATOR.createFromParcel(data);
                    } else {
                        _arg13 = null;
                    }
                    startForeground(_arg0, _arg13);
                    reply.writeNoException();
                    return true;
                case TRANSACTION_stopForeground /*12*/:
                    boolean _arg02;
                    data.enforceInterface(DESCRIPTOR);
                    if (data.readInt() != 0) {
                        _arg02 = true;
                    } else {
                        _arg02 = false;
                    }
                    stopForeground(_arg02);
                    reply.writeNoException();
                    return true;
                case TRANSACTION_getExtraInfo /*13*/:
                    data.enforceInterface(DESCRIPTOR);
                    ExtraInfo _result4 = getExtraInfo(data.readInt());
                    reply.writeNoException();
                    if (_result4 != null) {
                        reply.writeInt(TRANSACTION_event);
                        _result4.writeToParcel(reply, TRANSACTION_event);
                        return true;
                    }
                    reply.writeInt(0);
                    return true;
                case TRANSACTION_setExtraInfo /*14*/:
                    ExtraInfo _arg14;
                    data.enforceInterface(DESCRIPTOR);
                    _arg0 = data.readInt();
                    if (data.readInt() != 0) {
                        _arg14 = (ExtraInfo) ExtraInfo.CREATOR.createFromParcel(data);
                    } else {
                        _arg14 = null;
                    }
                    _result2 = setExtraInfo(_arg0, _arg14);
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

    void event(int i, int i2, int i3, String str, ExtraInfo extraInfo) throws RemoteException;

    boolean getBoolInfo(int i) throws RemoteException;

    ExtraInfo getExtraInfo(int i) throws RemoteException;

    int getIntInfo(int i) throws RemoteException;

    String getStringInfo(int i) throws RemoteException;

    int nativeCommand(int i) throws RemoteException;

    boolean registerCallback(IFMRemoteCallback iFMRemoteCallback) throws RemoteException;

    int setBoolInfo(int i, boolean z) throws RemoteException;

    int setExtraInfo(int i, ExtraInfo extraInfo) throws RemoteException;

    int setIntInfo(int i, int i2) throws RemoteException;

    int setStringInfo(int i, String str) throws RemoteException;

    void startForeground(int i, Notification notification) throws RemoteException;

    void stopForeground(boolean z) throws RemoteException;

    void unregisterCallback() throws RemoteException;
}
