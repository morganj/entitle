package com.morganjanes.entitlement.core;

import org.junit.Assert;
import org.junit.Test;

public class DeviceTest {
    @Test
    public void testEquals_Symmetric() {
        Device x = new Device();  // equals and hashCode check name field value
        x.setDeviceId("1");
        Device y = new Device();
        y.setDeviceId("1");
        Assert.assertTrue(x.equals(y) && y.equals(x));
        Assert.assertTrue(x.equals(x));
        Assert.assertTrue(x.hashCode() == y.hashCode());
    }

    @Test
    public void testNotEquals() {
        Device y = new Device();
        y.setDeviceId("1");
        Device z = new Device();
        z.setDeviceId("2");
        Assert.assertFalse(y.equals(""));
        Assert.assertFalse(y.equals(z) && z.equals(y));
        Assert.assertFalse(y.hashCode() == z.hashCode());
    }
}
