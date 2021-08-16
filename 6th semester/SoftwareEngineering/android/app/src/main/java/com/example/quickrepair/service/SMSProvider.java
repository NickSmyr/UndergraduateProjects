package com.example.quickrepair.service;

public interface SMSProvider
{
    void sendSMS(String receiverNumber, String message);
}
