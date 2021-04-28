package it.unicam.cs.gp.inmytable.view.spring.controllers;

import it.unicam.cs.gp.inmytable.user.User;

import javax.servlet.http.HttpSession;

public class BaseController {

    public static void setLogUser(User logUser, HttpSession session){
        session.setAttribute("logUser",logUser);
    }

    public static boolean isLoggedIn(HttpSession session){
        return BaseController.getLogUser(session) != null;
    }

    public static void logout(HttpSession session){
        setLogUser(null,session);
    }

    public static User getLogUser(HttpSession session){
       return (User)session.getAttribute("logUser");
    }
}
