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
    private int kindOfIntervention;
    private String comment;
    private Date date;
    private int owner;

    public Arrangement() {
    }

    public Arrangement(Intent intent, int userID) {
        kindOfIntervention = intent.getIntExtra(Constants.EXTRAS_KIND_OF_INTERVENTION,-1);
        try {
            date = DATE_FORMAT.parse(intent.getStringExtra(Constants.EXTRAS_DATE));
        } catch (ParseException e) {
            date = new Date();
        }
        comment = intent.getStringExtra(Constants.EXTRAS_COMMENT);
        owner = userID;
    }

    public Arrangement(Fragment fragment) {
        Bundle bundle = fragment.getArguments();
        kindOfIntervention = bundle.getInt(Constants.EXTRAS_KIND_OF_INTERVENTION);
        try {
            date = DATE_FORMAT.parse(bundle.getString(Constants.EXTRAS_DATE));
        } catch (ParseException e) {
            date = new Date();
        }
        comment = bundle.getString(Constants.EXTRAS_COMMENT);
    }

    public static void packageFragment(Fragment fragment, int kindOfIntervention, String date, String comment) {
        Bundle bundle = new Bundle();
        bundle.putInt(Constants.EXTRAS_KIND_OF_INTERVENTION, kindOfIntervention);
        bundle.putString(Constants.EXTRAS_DATE, date);
        bundle.putString(Constants.EXTRAS_COMMENT, comment);
        fragment.setArguments(bundle);
    }

    public int getKindOfIntervention() {
        return kindOfIntervention;
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

    public String getComment() {
        return comment;
    }

    public String toString() {
        return kindOfIntervention + System.getProperty("line.separator") + DATE_FORMAT.format(date);
    }
}
