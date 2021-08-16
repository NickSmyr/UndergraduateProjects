package com.example.quickrepair.view.Base;

public interface BaseView
{
    /**
     * Display a message in the event of an error.
     *
     * @param title The title of the error.
     * @param message The message of the error.
     */
    void showErrorMessage(String title, String message);
}
