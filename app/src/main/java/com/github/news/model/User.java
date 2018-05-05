package com.github.news.model;

import org.litepal.crud.DataSupport;

/**
 * author： xuyafan
 * description:
 */
public class User extends DataSupport {


    private int id;
    private String username;
    private String password;


    public User(String userName, String password) {
        this.username = userName;
        this.password = password;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


}
