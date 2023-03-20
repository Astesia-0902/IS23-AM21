package org.am21.model.user;

import org.am21.controller.UserController;

public class UserProfile {
    public String nickname;
    public UserController controller;

    public UserProfile(String name,UserController controller){
        this.nickname = name;
        this.controller = controller;
    }
}
