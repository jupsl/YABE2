/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package controllers;

/**
 *
 * @author juan
 */
import models.*;
public class Security extends Secure.Security {
    static boolean authenticate(String username, String password) {
    return User.connect(username, password) != null;
}
    static boolean check(String profile) {
    if("admin".equals(profile)) {
        Boolean us = User.find("byEmail", connected()).<User>first().isAdmin;
        if(us!=null)
        return us;
        else
            return false;
    }
    return false;
}
}
