package com.example.jpery.dentalclinic.utils;

import com.example.jpery.dentalclinic.model.Appointment;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JPery on 11/11/2016
 */
public class AppointmentController {

    List<Appointment> list = new ArrayList<>();

    private static AppointmentController ourInstance = new AppointmentController();

    public static AppointmentController getInstance() {
        return ourInstance;
    }

    private AppointmentController() {
    }

    public boolean deleteElementbyId(int appointmentID){
        boolean deleted = false;
        for (Appointment appointment:list) {
            if(appointment.getId() == appointmentID){
                list.remove(appointment);
                deleted=true;
                break;
            }
        }
        return deleted;
    }

    public List<Appointment> getList(){
        return list;
    }
}
