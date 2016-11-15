package com.example.jpery.dentalclinic.model;

import android.app.Fragment;
import android.content.Intent;
import android.os.Bundle;

import com.example.jpery.dentalclinic.utils.Constants;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

public class Arrangement {
    public final static SimpleDateFormat DATE_FORMAT = new SimpleDateFormat(Constants.DATE_FORMAT_STRING, Locale.US);
    private int id;
    private String title;
    private Date date;
    private int owner;

    public Arrangement() {
    }

    public Arrangement(Intent intent, int userID) {
        title = intent.getStringExtra(Constants.EXTRAS_TITLE);
        try {
            date = DATE_FORMAT.parse(intent.getStringExtra(Constants.EXTRAS_DATE));
        } catch (ParseException e) {
            date = new Date();
        }
        owner = userID;
    }

    public Arrangement(Fragment fragment) {
        Bundle bundle = fragment.getArguments();
        title = bundle.getString(Constants.EXTRAS_TITLE);
        try {
            date = DATE_FORMAT.parse(bundle.getString(Constants.EXTRAS_DATE));
        } catch (ParseException e) {
            date = new Date();
        }
    }

    public static void packageFragment(Fragment fragment, String title, String date) {
        Bundle bundle = new Bundle();
        bundle.putString(Constants.EXTRAS_TITLE, title);
        bundle.putString(Constants.EXTRAS_DATE, date);
        fragment.setArguments(bundle);
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Date getDate() {
        return date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String toString() {
        return title + System.getProperty("line.separator") + DATE_FORMAT.format(date);
    }
}
