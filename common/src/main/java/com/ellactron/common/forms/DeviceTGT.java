package com.ellactron.common.forms;

import java.util.Date;

/**
 * Created by ji.wang on 2017-05-15.
 */
public class DeviceTGT {
    byte[] deviceId;
    String account;
    Date timeStamp;

    public byte[] getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(byte[] deviceId) {
        this.deviceId = deviceId;
    }

    public String getAccount() {
        return account;
    }

    public void setAccount(String account) {
        this.account = account;
    }

    public Date getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(Date timeStamp) {
        this.timeStamp = timeStamp;
    }
}
