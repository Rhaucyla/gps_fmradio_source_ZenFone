package com.asus.fm;

import android.os.Binder;
import android.os.IBinder;
import android.os.IInterface;
import android.os.Parcel;
import android.os.RemoteException;

public interface IFMRemoteCallback extends IInterface {

    public static abstract class Stub extends Binder implements IFMRemoteCallback {
        private static final String DESCRIPTOR = "com.asus.fm.IFMRemoteCallback";
        static final int TRANSACTION_update = 1;

        private static class Proxy implements IFMRemoteCallback {
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

            public void update(int cmd, int ret, int reason, ExtraInfo info) throws RemoteException {
                Parcel _data = Parcel.obtain();
                Parcel _reply = Parcel.obtain();
                try {
                    _data.writeInterfaceToken(Stub.DESCRIPTOR);
                    _data.writeInt(cmd);
                    _data.writeInt(ret);
                    _data.writeInt(reason);
                    if (info != null) {
                        _data.writeInt(Stub.TRANSACTION_update);
                        info.writeToParcel(_data, 0);
                    } else {
                        _data.writeInt(0);
                    }
                    this.mRemote.transact(Stub.TRANSACTION_update, _data, _reply, 0);
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

        public static IFMRemoteCallback asInterface(IBinder obj) {
            if (obj == null) {
                return null;
            }
            IInterface iin = obj.queryLocalInterface(DESCRIPTOR);
            if (iin == null || !(iin instanceof IFMRemoteCallback)) {
                return new Proxy(obj);
            }
            return (IFMRemoteCallback) iin;
        }

        public IBinder asBinder() {
            return this;
        }

        public boolean onTransact(int code, Parcel data, Parcel reply, int flags) throws RemoteException {
            switch (code) {
                case TRANSACTION_update /*1*/:
                    ExtraInfo _arg3;
                    data.enforceInterface(DESCRIPTOR);
                    int _arg0 = data.readInt();
                    int _arg1 = data.readInt();
                    int _arg2 = data.readInt();
                    if (data.readInt() != 0) {
                        _arg3 = (ExtraInfo) ExtraInfo.CREATOR.createFromParcel(data);
                    } else {
                        _arg3 = null;
                    }
                    update(_arg0, _arg1, _arg2, _arg3);
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

    void update(int i, int i2, int i3, ExtraInfo extraInfo) throws RemoteException;
}
