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
import play.*;
import play.mvc.*;
 
import java.util.*;
 
import models.*;
 
@With(Secure.class)
public class Admin extends Controller {
    
    @Before
    static void setConnectedUser() {
        if(Security.isConnected()) {
            User user = User.find("byEmail", Security.connected()).first();
            renderArgs.put("user", user.nombre);
        }
    }
 
    public static void index() {
        render();
    }
    static void onDisconnected() {
    Application.index();
}


static void onAuthenticated() {
    Admin.index();
}

}
