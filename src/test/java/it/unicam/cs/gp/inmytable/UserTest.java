package it.unicam.cs.gp.inmytable;

import it.unicam.cs.gp.inmytable.controllers.GuestController;
import it.unicam.cs.gp.inmytable.exception.AlreadyExistingException;
import it.unicam.cs.gp.inmytable.exception.UnknownUsernameException;
import it.unicam.cs.gp.inmytable.persistence.AuthenticationDB;
import it.unicam.cs.gp.inmytable.persistence.AuthenticationPersistence;
import it.unicam.cs.gp.inmytable.user.User;
import org.junit.jupiter.api.Test;

import java.sql.PreparedStatement;
import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class UserTest {

    @Test
    void login() throws Exception {
        GuestController g = new GuestController(new AuthenticationDB());
        assertThrows(UnknownUsernameException.class, ()-> g.logIn("pippo", "pAS"), UnknownUsernameException.MESSAGE + "pippo");

    }

    @Test
    void signIn() throws Exception {
        AuthenticationDB a = new AuthenticationDB();
        GuestController g = new GuestController(a);
      //  g.signIn("pluto","pippo@gmail.com","00000","Pluto", "Pluto","pippo", LocalDate.parse("1993-05-12"), "IDProva", "GFCASG93D71T543O", "Macerata, Via prova 15", null, true);
        assertThrows(AlreadyExistingException.class, ()->g.signIn("pluto","pippo@gmail.com","00000","Pluto", "Pluto","pippo", LocalDate.now(), "FISCALCODICE", "IDCODICE", "Macerata, Via prova 15", null, true), AlreadyExistingException.MESSAGE + "pluto");
        String sql = "delete from User where Username='pluto'";
        PreparedStatement prepStat = a.getConnection().prepareStatement(sql);
        prepStat.executeUpdate();
    }
}
