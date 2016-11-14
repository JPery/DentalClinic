package com.example.jpery.dentalclinic;

import java.text.SimpleDateFormat;
import java.util.Locale;

/**
 * Created by j.pery on 12/11/16.
 */

public final class Constants {
    public static final String API_URL = "http://10.0.2.2:9000";
    public static final String API_USER_ID = "userID";
    public static final String EXTRAS_TITLE = "Title";
    public static final String EXTRAS_DATE = "Date";
    public static final String DATEPICKER = "datepicker";
    public static final String TIMEPICKER = "timepicker";
    public static final String DATE_FORMAT_STRING = "yyyy-MM-dd'T'HH:mm:ss";
    public final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(DATE_FORMAT_STRING, Locale.US);
    public static final String ITEM_SEPARATOR = System.getProperty("line.separator");
}
