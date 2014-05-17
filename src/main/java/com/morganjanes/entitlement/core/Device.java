package com.morganjanes.entitlement.core;

import com.google.code.siren4j.annotations.Siren4JEntity;
import com.google.code.siren4j.annotations.Siren4JSubEntity;
import org.skife.jdbi.v2.sqlobject.stringtemplate.UseStringTemplate3StatementLocator;

import java.util.Date;

@Siren4JEntity(name = "device", uri = "/devices/{id}")
@UseStringTemplate3StatementLocator
public class Device {

    private String deviceId;
    private String deviceType;
    private Date dateCreated;
    @Siren4JSubEntity(uri = "/devices/{parent.id}/owner")
    private User owner;

    public Device(){}

    public Device(String deviceId, String deviceType){
        this.deviceId = deviceId;
        this.deviceType = deviceType;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Device device = (Device) o;

        if (!deviceId.equals(device.deviceId)) return false;

        return true;
    }

    @Override
    public int hashCode() {
        return deviceId.hashCode();
    }

    public User getOwner() {
        return owner;
    }

    public void setOwner(User owner) {
        this.owner = owner;
    }

    public Date getDateCreated() {
        return dateCreated;
    }

    public void setDateCreated(Date dateCreated) {
        this.dateCreated = dateCreated;
    }

    public String getDeviceType() { return deviceType; }

    public void setDeviceType(String deviceType) {
        this.deviceType = deviceType;
    }

    public String getDeviceId() {
        return deviceId;
    }

    public void setDeviceId(String deviceId) {
        this.deviceId = deviceId;
    }
}
