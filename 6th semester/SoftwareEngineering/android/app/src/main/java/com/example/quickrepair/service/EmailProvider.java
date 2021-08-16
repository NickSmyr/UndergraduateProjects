package com.example.quickrepair.service;

public interface EmailProvider
{
    void sendEmail(String receiverEmail, String title, String message);
}
