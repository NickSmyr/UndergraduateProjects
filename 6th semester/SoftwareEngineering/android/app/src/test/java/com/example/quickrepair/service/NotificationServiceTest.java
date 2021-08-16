package com.example.quickrepair.service;

import com.example.quickrepair.dao.Initializer;
import com.example.quickrepair.memorydao.MemoryInitializer;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.GregorianCalendar;

public class NotificationServiceTest
{
    private NotificationService notificationService;
    private SMSProviderStub smsProvider;
    private EmailProviderStub emailProvider;

    @Before
    public void setup()
    {
        Initializer initializer = new MemoryInitializer();
        notificationService = new NotificationService();
        emailProvider = new EmailProviderStub();
        smsProvider = new SMSProviderStub();

        initializer.prepareData();

        notificationService.setEmailProvider(emailProvider);
        notificationService.setSmsProvider(smsProvider);

        notificationService.setRepairRequestDAO(initializer.getRepairRequestDAO());
    }

    @Test
    public void notifyTechnicianEmailTest()
    {
        notificationService.notifyTechnician(1);

        ArrayList<String> emails = new ArrayList<>();
        emails.add("aggelidisgiannis@repair.gr");

        Assert.assertEquals(emails, emailProvider.getReceiverEmails());
    }

    @Test
    public void notifyTechnicianSMSTest()
    {
        notificationService.notifyTechnician(10);

        ArrayList<String> phoneNumbers = new ArrayList<>();
        phoneNumbers.add("1010101010");

        Assert.assertEquals(phoneNumbers, smsProvider.getReceiverNumbers());
    }

    @Test
    public void notifyRejectCustomerTest()
    {
        notificationService.notifyCustomer(9);

        ArrayList<String> emails = new ArrayList<>();
        emails.add("katerina@repair.gr");

        GregorianCalendar gregorianCalendar = new GregorianCalendar(2020,1,10,10,0);
        String message = "Your repair request for " + gregorianCalendar + " was rejected by ΓΙΑΝΝΗΣ ΑΓΓΕΛΪΔΗΣ";

        ArrayList<String> messages = new ArrayList<>();
        messages.add(message);

        Assert.assertEquals(emails, emailProvider.getReceiverEmails());
        Assert.assertEquals(messages, emailProvider.getMessages());
    }

    @Test
    public void notifyConfirmCustomerTest()
    {
        notificationService.notifyCustomer(3);

        ArrayList<String> emails = new ArrayList<>();
        emails.add("anna@repair.gr");

        GregorianCalendar gregorianCalendar = new GregorianCalendar(2020,11,12,9,0);
        String message = "Your repair request for " + gregorianCalendar + " was confirmed by ΓΙΑΝΝΗΣ ΑΓΓΕΛΪΔΗΣ";

        ArrayList<String> messages = new ArrayList<>();
        messages.add(message);

        Assert.assertEquals(emails, emailProvider.getReceiverEmails());
        Assert.assertEquals(messages, emailProvider.getMessages());
    }
}
