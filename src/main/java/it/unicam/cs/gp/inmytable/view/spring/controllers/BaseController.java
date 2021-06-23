package it.unicam.cs.gp.inmytable.view.spring.controllers;

import it.unicam.cs.gp.inmytable.user.User;

import javax.servlet.http.HttpSession;

public class BaseController {

    /**
     * set log user
     * @param logUser log user
     * @param session session
     */
    public static void setLogUser(User logUser, HttpSession session){
        session.setAttribute("logUser",logUser);
    }

    /**
     * if logged in return true lese false
     * @param session session
     * @return if logged in return true lese false
     */
    public static boolean isLoggedIn(HttpSession session){
        return BaseController.getLogUser(session) != null;
    }

    /**
     * this method is used to logout
     * @param session session
     */
    public static void logout(HttpSession session){
        setLogUser(null,session);
    }

    /**
     * return log user
     * @param session session
     * @return log user
     */
    public static User getLogUser(HttpSession session){
       return (User)session.getAttribute("logUser");
    }
}
