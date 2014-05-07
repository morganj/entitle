package com.morganjanes.entitlement.core;

import org.junit.Assert;
import org.junit.Test;

public class UserTest {

    @Test
    public void testEquals_Symmetric() {
        User x = new User("john@localhost.io", "John", "Doe" );  // equals and hashCode check name field value
        User y = new User("john@localhost.io", "John", "Doe");
        Assert.assertTrue(x.equals(y) && y.equals(x));
        Assert.assertTrue(x.equals(x));
        Assert.assertTrue(x.hashCode() == y.hashCode());
    }

    @Test
    public void testNotEquals() {
        User y = new User("john@localhost.io", "John", "Smith");
        User z = new User( "johnny@localhost.io", "John", "Smith");
        Assert.assertFalse(y.equals(""));
        Assert.assertFalse(y.equals(z) && z.equals(y));
        Assert.assertFalse(y.hashCode() == z.hashCode());
    }

    @Test
    public void testIsValidPassword(){
        User user = new User();
        user.setPassword("asdf");
        Assert.assertTrue(user.isValidPassword("asdf"));

    }
}
