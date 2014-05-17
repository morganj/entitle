package com.morganjanes.entitlement.core;

import org.junit.Assert;
import org.junit.Test;

import java.util.Date;

/**
 * Created by mjanes on 4/9/14.
 */
public class AccountTest {
    @Test
    public void testEquals_Symmetric() {
        Account x = new Account();  // equals and hashCode check name field value
        x.setAccountId("1");
        x.setDateCreated(new Date());
        Account y = new Account();
        y.setAccountId("1");
        y.setDateCreated(new Date());
        Assert.assertTrue(x.equals(y) && y.equals(x));
        Assert.assertTrue(x.equals(x));
        Assert.assertTrue(x.hashCode() == y.hashCode());
    }

    @Test
    public void testNotEquals() {
        Account y = new Account();
        y.setAccountId("1");
        Account z = new Account();
        z.setAccountId("2");
        Assert.assertFalse(y.equals(""));
        Assert.assertFalse(y.equals(z) && z.equals(y));
        Assert.assertFalse(y.hashCode() == z.hashCode());
    }
    
}
