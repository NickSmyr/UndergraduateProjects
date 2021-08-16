package com.example.quickrepair.view.Customer.RepairRequests;

public interface ItemSelectionListener<T> {
    /**
     * The method will be called by the adapter, whenever the user clicks on a list item
     * @param item Repair Request
     */
    void onItemSelected(T item);

}