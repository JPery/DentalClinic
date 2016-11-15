package com.example.jpery.dentalclinic.utils;

import com.example.jpery.dentalclinic.model.Arrangement;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by JPery on 11/11/2016
 */
public class ArrangementsController {

    List<Arrangement> list = new ArrayList<>();

    private static ArrangementsController ourInstance = new ArrangementsController();

    public static ArrangementsController getInstance() {
        return ourInstance;
    }

    private ArrangementsController() {
    }

    public List<Arrangement> getList(){
        return list;
    }
}
