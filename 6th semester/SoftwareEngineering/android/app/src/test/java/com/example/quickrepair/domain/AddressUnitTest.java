package com.example.quickrepair.domain;

import com.example.quickrepair.domain.Address;

import org.junit.Before;
import org.junit.Test;
import org.junit.Assert;

public class AddressUnitTest
{
    Address exampleAddress;

    @Before
    public void setUpTests()
    {
        exampleAddress = new Address("aaaa", "1");
    }

    @Test
    public void testExampleAddress()
    {
        Address addr = new Address("Ath", "123");
        Assert.assertEquals(addr.getNumber(), "123");
        Assert.assertEquals(addr.getStreetName(), "Ath");
    }

    @Test(expected = IllegalArgumentException.class)
    public void newAddressWithIncorrectNumber()
    {
        Address addr = new Address("14", "aaaa");
    }

    @Test(expected = NullPointerException.class)
    public void testSetStreetName()
    {
        Address addr = new Address(null, "aaaa");

    }

    @Test
    public void equalsTest()
    {
        Address addr = new Address("aaaa", "1");
        Assert.assertEquals(addr, exampleAddress);
        Address addr1 = new Address("aaaa1", "1");
        Assert.assertNotEquals(addr, addr1);
        Address addr2 = new Address("aaaa", "12");
        Assert.assertNotEquals(addr, addr2);
    }

    @Test
    public void hashCodeTest()
    {
        Address address = new Address("aaaa", "1");

        Assert.assertTrue(address.equals(exampleAddress) && exampleAddress.equals(address));
        Assert.assertTrue(address.hashCode() == exampleAddress.hashCode());
    }
}
