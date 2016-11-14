package com.example.jpery.dentalclinic;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by j.pery on 11/11/16.
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
