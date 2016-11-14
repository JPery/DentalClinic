package com.example.jpery.dentalclinic;

import android.app.Fragment;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

// Do not modify 

public class Arrangement {

    private int id;
    private String title;
    private Date date;
    private int owner;

    public Arrangement() {
    }

    public Arrangement(int id, String title, Date date, int owner) {
        this.id = id;
        this.title = title;
        this.date = date;
        this.owner = owner;
    }

    public Arrangement(String title, String date) {
        this.title = title;
        try {
            this.date = Constants.DATE_FORMAT.parse(date);
        } catch (ParseException e) {
            this.date = new Date();
        }
    }

    public Arrangement(Intent intent, int userID) {
        title = intent.getStringExtra(Constants.EXTRAS_TITLE);
        try {
            date = Constants.DATE_FORMAT.parse(intent.getStringExtra(Constants.EXTRAS_DATE));
        } catch (ParseException e) {
            date = new Date();
        }
        owner = userID;
    }

    public Arrangement(Fragment fragment) {
        Bundle bundle = fragment.getArguments();
        title = bundle.getString(Constants.EXTRAS_TITLE);
        try {
            date = Constants.DATE_FORMAT.parse(bundle.getString(Constants.EXTRAS_DATE));
        } catch (ParseException e) {
            date = new Date();
        }
    }

    public static void packageIntent(Intent intent, String title, String date) {
        intent.putExtra(Constants.EXTRAS_TITLE, title);
        intent.putExtra(Constants.EXTRAS_DATE, date);
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

    public void setDate(Date date) {
        this.date = date;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getOwner() {
        return owner;
    }

    public void setOwner(int owner) {
        this.owner = owner;
    }

    public String toString() {
        return title + System.getProperty("line.separator") + Constants.DATE_FORMAT.format(date);
    }

    public String toLog() {
        return "Title:" + title + Constants.ITEM_SEPARATOR + "Date:" + Constants.DATE_FORMAT.format(date);
    }
}
