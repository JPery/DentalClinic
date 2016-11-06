package com.example.jpery.dentalclinic;

import android.content.Intent;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;

// Do not modify 

public class Arrangement {

	public static final String ITEM_SEP = System.getProperty("line.separator");
	public final static String TITLE = "title";
	public final static String DATE = "date";

	public final static SimpleDateFormat FORMAT = new SimpleDateFormat(
			"yyyy-MM-dd HH:mm:ss", Locale.US);

	private String mTitle = new String();
	private Date mDate = new Date();

	public Arrangement(String title, Date date) {
		this.mTitle = title;
		this.mDate = date;
	}

	// Create a new Arrangement from data packaged in an Intent
	public Arrangement(Intent intent) {
		mTitle = intent.getStringExtra(Arrangement.TITLE);
		try {
			mDate = Arrangement.FORMAT.parse(intent.getStringExtra(Arrangement.DATE));
		} catch (ParseException e) {
			mDate = new Date();
		}
	}

	public String getTitle() {
		return mTitle;
	}

	public void setTitle(String title) {
		mTitle = title;
	}

	public Date getDate() {
		return mDate;
	}

	public void setDate(Date date) {
		mDate = date;
	}

	// Take a set of String data values and 
	// package them for transport in an Intent
	public static void packageIntent(Intent intent, String title, String date) {
		intent.putExtra(Arrangement.TITLE, title);
		intent.putExtra(Arrangement.DATE, date);
	}

	public String toString() {
		return mTitle + ITEM_SEP + ITEM_SEP + ITEM_SEP
				+ FORMAT.format(mDate);
	}

	public String toLog() {
		return "Title:" + mTitle + ITEM_SEP + "Priority:" + ITEM_SEP + "Status:" + ITEM_SEP + "Date:" + FORMAT.format(mDate);
	}
}
