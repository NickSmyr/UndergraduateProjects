package com.example.quickrepair.domain;

import com.example.quickrepair.domain.Evaluation;
import com.example.quickrepair.domain.Payment;
import com.example.quickrepair.domain.PaymentType;
import com.example.quickrepair.domain.Repair;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.GregorianCalendar;
import java.util.Objects;

public class PaymentUnitTest
{
    Payment payment;

    @Before
    public void setUp()
    {
        payment = new Payment();
    }

    @Test(expected = NullPointerException.class)
    public void nullRepair()
    {
        payment.setRepair(null);
    }

    @Test
    public void okRepair()
    {
        payment.setRepair(new Repair());
        Assert.assertEquals(new Repair(), payment.getRepair());
    }

    @Test(expected = NullPointerException.class)
    public void nullDate()
    {
        payment.setDate(null);
    }

    @Test
    public void okDate()
    {
        GregorianCalendar date = new GregorianCalendar(2019, 12, 12);
        payment.setDate(date);
        Assert.assertEquals(date, payment.getDate());
    }

    @Test(expected = NullPointerException.class)
    public void nullPaymentType()
    {
        payment.setPaymentType(null);
    }

    @Test
    public void okPaymentType()
    {
        PaymentType paymentType = PaymentType.CARD;
        payment.setPaymentType(paymentType);
        Assert.assertEquals(paymentType, payment.getPaymentType());
    }

    @Test
    public void okUid()
    {
        payment.setUid(100);
        Assert.assertEquals(100, payment.getUid());
    }

    @Test(expected = IllegalArgumentException.class)
    public void badCost()
    {
        payment.setCost(0);
    }

    @Test
    public void okCost()
    {
        payment.setCost(140.0);
        Assert.assertEquals(140.0, payment.getCost(), 0.0001);
    }

    @Test
    public void memberMethods()
    {
        payment.setDate(new GregorianCalendar(2020, 18, 19));
        payment.setPaymentType(PaymentType.CARD);
        payment.setRepair(new Repair());

        Assert.assertEquals(false, payment.equals(null));
        Assert.assertEquals(true, payment.equals(payment));
        Assert.assertEquals(false, payment.equals(new Evaluation()));

        Payment p1 = new Payment(new Repair(), new GregorianCalendar(2020, 18, 20), PaymentType.CARD);
        Assert.assertEquals(false, payment.equals(p1));

        Payment p2 = new Payment(new Repair(), new GregorianCalendar(2020, 18, 19), PaymentType.CASH);
        Assert.assertEquals(false, payment.equals(p2));

        Payment p3 = new Payment(new Repair(), new GregorianCalendar(2020, 18, 19), PaymentType.CARD);
        Assert.assertEquals(true, payment.equals(p3));

        Assert.assertTrue(payment.equals(p3) && p3.equals(payment));
        Assert.assertTrue(payment.hashCode() == p3.hashCode());
    }

    @Test
    public void testHashCode(){
        Assert.assertEquals(Objects.hash(payment.getRepair(), payment.getDate(), payment.getPaymentType()), payment.hashCode());
    }
}
