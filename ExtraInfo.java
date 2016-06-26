package com.asus.fm;

import android.content.Intent;
import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class ExtraInfo implements Parcelable {
    public static final Creator<ExtraInfo> CREATOR;
    public static final int TYPE_COMBINE_ALL = 20;
    public static final int TYPE_COMBINE_STRING_FLOAT = 13;
    public static final int TYPE_COMBINE_STRING_INT = 11;
    public static final int TYPE_COMBINE_STRING_LONG = 12;
    public static final int TYPE_INTENT = 21;
    public static final int TYPE_ONLY_FLOAT = 4;
    public static final int TYPE_ONLY_INT = 2;
    public static final int TYPE_ONLY_LONG = 3;
    public static final int TYPE_ONLY_STRING = 1;
    public static final int TYPE_UNKNOWN = 0;
    private float mFloat;
    private int mInt;
    private Intent mIntent;
    private long mLong;
    private String mString;
    private int mType;

    /* renamed from: com.asus.fm.ExtraInfo.1 */
    static class C00001 implements Creator<ExtraInfo> {
        C00001() {
        }

        public ExtraInfo createFromParcel(Parcel source) {
            return new ExtraInfo(null);
        }

        public ExtraInfo[] newArray(int size) {
            return new ExtraInfo[size];
        }
    }

    public int describeContents() {
        int count = 0;
        if (this.mString != "") {
            count = 0 + TYPE_ONLY_STRING;
        }
        if (this.mInt != 0) {
            count += TYPE_ONLY_STRING;
        }
        if (this.mLong != 0) {
            count += TYPE_ONLY_STRING;
        }
        if (this.mFloat != 0.0f) {
            count += TYPE_ONLY_STRING;
        }
        if (this.mIntent != null) {
            return count + 10;
        }
        return count;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeInt(this.mType);
        dest.writeString(this.mString);
        dest.writeInt(this.mInt);
        dest.writeLong(this.mLong);
        dest.writeFloat(this.mFloat);
        dest.writeParcelable(this.mIntent, 0);
    }

    static {
        CREATOR = new C00001();
    }

    public ExtraInfo(String stringInfo) {
        this.mType = 0;
        this.mString = "";
        this.mInt = 0;
        this.mLong = 0;
        this.mFloat = 0.0f;
        this.mIntent = null;
        this.mType = TYPE_ONLY_STRING;
        this.mString = stringInfo;
    }

    public ExtraInfo(int intInfo) {
        this.mType = 0;
        this.mString = "";
        this.mInt = 0;
        this.mLong = 0;
        this.mFloat = 0.0f;
        this.mIntent = null;
        this.mType = TYPE_ONLY_INT;
        this.mInt = intInfo;
    }

    public ExtraInfo(long longInfo) {
        this.mType = 0;
        this.mString = "";
        this.mInt = 0;
        this.mLong = 0;
        this.mFloat = 0.0f;
        this.mIntent = null;
        this.mType = TYPE_ONLY_LONG;
        this.mLong = longInfo;
    }

    public ExtraInfo(float floatInfo) {
        this.mType = 0;
        this.mString = "";
        this.mInt = 0;
        this.mLong = 0;
        this.mFloat = 0.0f;
        this.mIntent = null;
        this.mType = TYPE_ONLY_FLOAT;
        this.mFloat = floatInfo;
    }

    public ExtraInfo(String stringInfo, int intInfo) {
        this.mType = 0;
        this.mString = "";
        this.mInt = 0;
        this.mLong = 0;
        this.mFloat = 0.0f;
        this.mIntent = null;
        this.mType = TYPE_COMBINE_STRING_INT;
        this.mString = stringInfo;
        this.mInt = intInfo;
    }

    public ExtraInfo(String stringInfo, long longInfo) {
        this.mType = 0;
        this.mString = "";
        this.mInt = 0;
        this.mLong = 0;
        this.mFloat = 0.0f;
        this.mIntent = null;
        this.mType = TYPE_COMBINE_STRING_LONG;
        this.mString = stringInfo;
        this.mLong = longInfo;
    }

    public ExtraInfo(String stringInfo, float floatInfo) {
        this.mType = 0;
        this.mString = "";
        this.mInt = 0;
        this.mLong = 0;
        this.mFloat = 0.0f;
        this.mIntent = null;
        this.mType = TYPE_COMBINE_STRING_FLOAT;
        this.mString = stringInfo;
        this.mFloat = floatInfo;
    }

    public ExtraInfo(String stringInfo, int intInfo, long longInfo, float floatInfo) {
        this.mType = 0;
        this.mString = "";
        this.mInt = 0;
        this.mLong = 0;
        this.mFloat = 0.0f;
        this.mIntent = null;
        this.mType = TYPE_COMBINE_ALL;
        this.mString = stringInfo;
        this.mInt = intInfo;
        this.mLong = longInfo;
        this.mFloat = floatInfo;
    }

    public ExtraInfo(Intent intent) {
        this.mType = 0;
        this.mString = "";
        this.mInt = 0;
        this.mLong = 0;
        this.mFloat = 0.0f;
        this.mIntent = null;
        this.mType = TYPE_INTENT;
        this.mIntent = intent;
    }

    public int getType() {
        return this.mType;
    }

    public String getString() {
        return this.mString;
    }

    public int getInt() {
        return this.mInt;
    }

    public long getLong() {
        return this.mLong;
    }

    public float getFloat() {
        return this.mFloat;
    }

    public Intent getIntent() {
        return this.mIntent;
    }

    private ExtraInfo(Parcel in) {
        this.mType = 0;
        this.mString = "";
        this.mInt = 0;
        this.mLong = 0;
        this.mFloat = 0.0f;
        this.mIntent = null;
        this.mType = in.readInt();
        this.mString = in.readString();
        this.mInt = in.readInt();
        this.mLong = in.readLong();
        this.mFloat = in.readFloat();
        this.mIntent = (Intent) in.readParcelable(null);
    }
}
