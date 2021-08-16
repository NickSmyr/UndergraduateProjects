package com.example.quickrepair;

import android.app.Application;

public class QuickRepairApplication extends Application
{
    public final static String YEAR_EXTRA = "year";
    public final static String MONTH_EXTRA = "month";
    public final static String DAY_EXTRA = "day";
    public final static String TECHNICIAN_ID_EXTRA = "technician_id";
    public final static String CUSTOMER_ID_EXTRA = "customer_id";
    public final static String JOBTYPE_ID_EXTRA = "job_type_id";
    public final static String INITIALIZED_EXTRA = "initialized";

    public static final String REPAIR_REQUEST_ID_EXTRA = "repair_request_id";
    public static final String REDIRECT_TO_SEARCH_EXTRA = "redirect_to_search";


    //REQUEST CODES
    public static final int RESULT_INVALID = 0;
    public static final int RESULT_DENIED = 1;
    public static final int REQUEST_CODE_LOGIN = 2;
}
