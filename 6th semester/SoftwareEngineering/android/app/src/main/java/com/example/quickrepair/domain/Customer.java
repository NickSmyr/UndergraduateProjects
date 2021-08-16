package com.example.quickrepair.domain;

import java.util.GregorianCalendar;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;

public class Customer extends User
{
    /**
     * All Customer's Requests
     */
    private Set<RepairRequest> requests;

    public Customer()
    {
        requests = new HashSet<>();
        setNotificationMethod(NotificationMethod.EMAIL);
    }

    /**
     *  Costumer's constructor
     * @param name  Costumer's name
     * @param surname  Costumer's surname
     * @param phoneNumber  Costumer's phone number
     * @param email  Costumer's email
     * @param bankAccount  Costumer's bankAccount
     * @param username  Costumer's username
     * @param password  Costumer's password
     */
    public Customer(String name, String surname, String phoneNumber, String email, String bankAccount, String username, String password)
    {
        super(name, surname, phoneNumber, email, bankAccount, username, password);
        requests = new HashSet<>();
    }

    /**
     * add a new request for the customer
     * @param request Customer's new request
     */
    public void addRequest(RepairRequest request)
    {
        if (request == null)
        {
            throw new IllegalArgumentException("Null RepairRequest");
        }
        requests.add(request);
    }

    /**
     * return repair requests
     * @return set with customer's repair requests
     */
    public Set<RepairRequest> getRequests()
    {
        return requests;
    }

    /**
     * Create a Repair Request for a specific job.
     *
     * @param dateNow  The date of creation.
     * @param date     The date of conduction.
     * @param job      The requested job.
     * @param comments Additional comment of the customer for the technician.
     * @param address  The address of the appointment.
     * @return The created repair request.
     */
    public RepairRequest requestRepair(GregorianCalendar dateNow, GregorianCalendar date, Job job, String comments, Address address)
    {
        RepairRequest repairRequest = new RepairRequest(this, job, dateNow, date, address, comments);

        requests.add(repairRequest);
        job.addRepairRequest(repairRequest);
        return repairRequest;
    }

    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        Customer customer = (Customer) o;
        return Objects.equals(requests, customer.requests);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(super.hashCode());
    }
}
