package it.unicam.cs.gp.inmytable;

import it.unicam.cs.gp.inmytable.allmeals.meals.ConsumationType;
import it.unicam.cs.gp.inmytable.allmeals.meals.Meal;
import it.unicam.cs.gp.inmytable.allmeals.meals.PaymentType;
import it.unicam.cs.gp.inmytable.controllers.UserController;
import it.unicam.cs.gp.inmytable.notification.NotificationStates;
import it.unicam.cs.gp.inmytable.notification.Subscription;
import it.unicam.cs.gp.inmytable.user.User;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;

public class SubscriptionManagerTest {


    @Test
    void subscriptionTest() throws Exception{
       User user = new User("pippo","pippo@gmail.com","00000","Pippo", "Pluto","pippo".hashCode(), LocalDate.now(), "FISCALCODICE", "IDCODICE", "Macerata, Via prova 15", true);
       User user1 = new User("pluto","pippo@gmail.com","00000","Pluto", "Pluto","pippo".hashCode(), LocalDate.now(), "FISCALCODICE", "IDCODICE", "Macerata, Via prova 15", true);

       Meal meal = new Meal(user1,3,LocalDate.parse("2021-04-29"), LocalTime.now(),LocalDate.parse("2021-04-29"),LocalTime.now(),"",false,"", ConsumationType.AT_HOME,"","", PaymentType.EXCHANGE,"0");

       UserController userController = new UserController(user);
       user.getNotificationManager();
       userController.joinToMeal(meal);
       UserController userController1 = new UserController(user1);
       assertFalse(userController1.showNotification().isEmpty());
       Subscription s = (Subscription) userController1.showNotification().get(0);

       userController1.acceptSubscription(s);
       System.out.println(userController1.showNotification().get(0).toString());
       assertTrue(s.getNotificationState().equals(NotificationStates.ACCEPTED));
       assertThrows(IllegalArgumentException.class,()->userController1.refuseSubscription(s));

   }
}
