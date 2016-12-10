package com.example.jpery.dentalclinic.model;
/**
 * Created by JPery on 9/11/16
 */
public class User {

    private Integer id;
    private String name;
    private String password;

    public User(){}

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }
    public String getName() {
        return name;
    }
}
