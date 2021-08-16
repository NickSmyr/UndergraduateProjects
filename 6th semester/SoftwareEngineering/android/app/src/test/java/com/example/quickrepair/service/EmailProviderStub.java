package com.example.quickrepair.service;

import java.util.ArrayList;

public class EmailProviderStub implements EmailProvider
{
    private ArrayList<String> receiverEmails;
    private ArrayList<String> titles;
    private ArrayList<String> messages;

    public EmailProviderStub()
    {
        receiverEmails = new ArrayList<>();
        titles = new ArrayList<>();
        messages = new ArrayList<>();
    }

    @Override
    public void sendEmail(String receiverEmail, String title, String message)
    {
        receiverEmails.add(receiverEmail);
        titles.add(title);
        messages.add(message);
    }

    public ArrayList<String> getMessages()
    {
        return messages;
    }

    public ArrayList<String> getReceiverEmails()
    {
        return receiverEmails;
    }

    public ArrayList<String> getTitles()
    {
        return titles;
    }
}
