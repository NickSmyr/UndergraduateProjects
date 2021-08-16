package com.example.quickrepair.service;

import java.util.ArrayList;

public class SMSProviderStub implements SMSProvider
{
    private ArrayList<String> receiverNumbers;
    private ArrayList<String> messages;

    public SMSProviderStub()
    {
        receiverNumbers = new ArrayList<>();
        messages = new ArrayList<>();
    }

    @Override
    public void sendSMS(String receiverNumber, String message)
    {
        receiverNumbers.add(receiverNumber);
        messages.add(message);
    }

    public ArrayList<String> getMessages()
    {
        return messages;
    }

    public ArrayList<String> getReceiverNumbers()
    {
        return receiverNumbers;
    }
}
