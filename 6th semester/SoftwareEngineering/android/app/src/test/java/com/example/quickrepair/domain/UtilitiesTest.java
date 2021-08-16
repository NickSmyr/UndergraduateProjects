package com.example.quickrepair.domain;

import com.example.quickrepair.util.Utilities;

import org.junit.Assert;
import org.junit.Test;

import java.util.GregorianCalendar;

public class UtilitiesTest {
    @Test
    public void testGetYearMonthDay(){
        GregorianCalendar actualDate = new GregorianCalendar(2018, 1, 1 ,1 ,1,1);
        GregorianCalendar importantDate = new GregorianCalendar(2018, 1,1);

        GregorianCalendar newDate = Utilities.getYearMonthDay(actualDate);
        Assert.assertEquals(importantDate, newDate);
    }
}
