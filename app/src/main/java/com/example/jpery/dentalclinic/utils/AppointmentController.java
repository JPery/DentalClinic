package com.example.jpery.dentalclinic.utils;

import com.example.jpery.dentalclinic.model.Arrangement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JPery on 11/11/2016
 */
public class AppointmentController {

    List<Arrangement> list = new ArrayList<>();

    private static AppointmentController ourInstance = new AppointmentController();

    public static AppointmentController getInstance() {
        return ourInstance;
    }

    private AppointmentController() {
    }

    public List<Arrangement> getList(){
        return list;
    }
}
