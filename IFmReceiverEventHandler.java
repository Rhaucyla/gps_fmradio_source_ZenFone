package com.broadcom.fm.fmreceiver;

public interface IFmReceiverEventHandler {
    void onAudioModeEvent(int i);

    void onAudioPathEvent(int i);

    void onEstimateNoiseFloorLevelEvent(int i);

    void onLiveAudioQualityEvent(int i, int i2);

    void onRdsDataEvent(int i, int i2, String str);

    void onRdsModeEvent(int i, int i2);

    void onSeekCompleteEvent(int i, int i2, int i3, boolean z);

    void onStatusEvent(int i, int i2, int i3, boolean z, int i4, String str, String str2, String str3, boolean z2);

    void onVolumeEvent(int i, int i2);

    void onWorldRegionEvent(int i);
}
