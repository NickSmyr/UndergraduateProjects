package com.example.quickrepair.service;

import com.example.quickrepair.dao.RepairRequestDAO;
import com.example.quickrepair.dao.TechnicianDAO;
import com.example.quickrepair.domain.RepairRequest;

public class NotificationService
{
    private RepairRequestDAO repairRequestDAO;

    private EmailProvider emailProvider;

    private SMSProvider smsProvider;

    public void notifyTechnician(int repairRequestID)
    {
        RepairRequest repairRequest = repairRequestDAO.find(repairRequestID);

        String title = "New repair request";
        String message = "New repair request for " + repairRequest.getConductionDate().toString();

        switch (repairRequest.getJob().getTechnician().getNotificationMethod())
        {
            case SMS:
                smsProvider.sendSMS(repairRequest.getJob().getTechnician().getPhoneNumber(), message);
                break;
            case EMAIL:
                emailProvider.sendEmail(repairRequest.getJob().getTechnician().getEmail(), title, message);
                break;
            default:
                break;
        }
    }

    public void notifyCustomer(int repairRequestID)
    {
        RepairRequest repairRequest = repairRequestDAO.find(repairRequestID);

        String message = "Your repair request for " + repairRequest.getConductionDate();

        switch (repairRequest.getStatus())
        {
            case REJECTED:
                message += " was rejected by " + repairRequest.getJob().getTechnician().getName() + " " + repairRequest.getJob().getTechnician().getSurname();
                break;
            case CONFIRMED:
                message += " was confirmed by " + repairRequest.getJob().getTechnician().getName() + " " + repairRequest.getJob().getTechnician().getSurname();
        }

        emailProvider.sendEmail(repairRequest.getCustomer().getEmail(), "Your repair request got answered", message);
    }

    public void setRepairRequestDAO(RepairRequestDAO repairRequestDAO)
    {
        this.repairRequestDAO = repairRequestDAO;
    }

    public void setEmailProvider(EmailProvider emailProvider)
    {
        this.emailProvider = emailProvider;
    }

    public void setSmsProvider(SMSProvider smsProvider)
    {
        this.smsProvider = smsProvider;
    }
}
