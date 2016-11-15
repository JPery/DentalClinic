package com.example.jpery.dentalclinic.model;
/**
 * Created by JPery on 9/11/16
 */
public class User {

    Integer id;
    String name;
    String password;

    public User(){}

    public User(String name, String password) {
        this.name = name;
        this.password = password;
    }

    public User(Integer id, String name, String password) {
        this.id = id;
        this.name = name;
        this.password = password;
    }

    public Integer getId() {
        return id;
    }
    public String getName() {
        return name;
    }
    public String getPassword() { return password; }
}
