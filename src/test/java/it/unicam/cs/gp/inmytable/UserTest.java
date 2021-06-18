package it.unicam.cs.gp.inmytable;

import it.unicam.cs.gp.inmytable.controllers.GuestController;
import it.unicam.cs.gp.inmytable.exception.UnknownUsernameException;
import it.unicam.cs.gp.inmytable.persistence.AuthenticationDB;
import org.junit.jupiter.api.Test;


import static org.junit.jupiter.api.Assertions.assertThrows;

public class UserTest {

    @Test
    void login() throws Exception {
        GuestController g = new GuestController(new AuthenticationDB());
        assertThrows(UnknownUsernameException.class, ()-> g.logIn("pippo", "pAS"), UnknownUsernameException.MESSAGE + "pippo");

    }
}
