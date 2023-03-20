package org.am21.controller;

import org.am21.model.user.UserProfile;

public class UserController {
    public UserProfile user;

    public UserController(String name){
        this.user = new UserProfile(name,this);

    }

    public void changeName(String newName){
        user.nickname = newName;
    }

    public void joinGame(){}

    public void leaveGame(){}

    public void openSettings(){}
}
