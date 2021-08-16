package com.example.quickrepair.domain;

import androidx.annotation.NonNull;

import java.util.Objects;

/**
 * Immutable class that represents an address
 */
public final class Address
{
    private String streetName;
    private String number;

    /**
     * Constructor
     * @param streetName street's name
     * @param number number
     */
    public Address(String streetName, String number)
    {
        setStreetName(streetName);
        setNumber(number);
    }

    /**
     * returns street's name
     * @return streetName
     */
    public String getStreetName()
    {
        return streetName;
    }

    /**
     * set street's name
     * @param streetName street's name
     */
    private void setStreetName(String streetName)
    {
        if (streetName == null)
        {
            throw new NullPointerException("streetName can't be null");
        }

        this.streetName = streetName;
    }

    /**
     * return number
     * @return address's number
     */
    public String getNumber()
    {
        return number;
    }

    /**
     * set number
     * @param number address's number
     */
    private void setNumber(String number)
    {
        if (number == null) throw new NullPointerException();

        //Only accept numbers if they are 1 or more digits long
        if (!number.matches("\\d+"))
        {
            throw new IllegalArgumentException("Address number is invalid");
        }

        this.number = number;
    }


    @Override
    public boolean equals(Object o)
    {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Address address = (Address) o;
        return Objects.equals(streetName, address.streetName) && Objects.equals(number, address.number);
    }

    @Override
    public int hashCode()
    {
        return Objects.hash(streetName, number);
    }

    @NonNull
    @Override
    public String toString() {
        return getStreetName() + " "+getNumber();
    }
}
