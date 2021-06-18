package it.unicam.cs.gp.inmytable;


import it.unicam.cs.gp.inmytable.allmeals.MealStates;
import it.unicam.cs.gp.inmytable.allmeals.PaymentType;
import it.unicam.cs.gp.inmytable.allmeals.mealrequest.IMealRequest;
import it.unicam.cs.gp.inmytable.allmeals.meals.*;
import it.unicam.cs.gp.inmytable.allmeals.*;
import it.unicam.cs.gp.inmytable.allmeals.Food;
import it.unicam.cs.gp.inmytable.allmeals.meals.IMeal;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.time.LocalDate;
import java.time.LocalTime;

import it.unicam.cs.gp.inmytable.allmeals.MealManager;
import it.unicam.cs.gp.inmytable.allmeals.mealrequest.MealRequest;
import it.unicam.cs.gp.inmytable.notification.SubscriptionManager;
import it.unicam.cs.gp.inmytable.notification.SubscriptionNotification;
import it.unicam.cs.gp.inmytable.notification.SubscriptionStates;
import it.unicam.cs.gp.inmytable.user.*;


public class NotificationTest {
    public NotificationTest() throws Exception {
    }

   /* @Test
    void testJoinToMealNotification() throws Exception {
        //build two users
        IUser user = new User("pippo","pippo@gmail.com","00000","Pippo", "Pluto","pippo".hashCode(), LocalDate.now(), "FISCALCODICE", "IDCODICE", "Macerata, Via prova 15", null, true);
        IUser user1 = new User("pluto","pippo@gmail.com","00000","Pluto", "Pluto","pippo".hashCode(), LocalDate.now(), "FISCALCODICE", "IDCODICE", "Macerata, Via prova 15", null, true);
        //create a meal
        Meal meal = new Meal(user1,3,LocalDate.parse("2021-05-29"), LocalTime.now(),LocalDate.parse("2021-05-29"),LocalTime.now(),"",false,"", "", ConsummationType.AT_HOME,"","", PaymentType.EXCHANGE,"0");
        //build a subscription manager
        SubscriptionManager subscriptionManager = new SubscriptionManager();
        //user joins to the meal
        subscriptionManager.joinToMealNotification(user, meal.getHomeOwner(), meal, "");
        //check user1 cannot join to his meal
        assertThrows(IllegalArgumentException.class , ()-> subscriptionManager.joinToMealNotification(user1, meal.getHomeOwner(), meal,""));
        //check user1 receive the notification
        assertTrue(!user1.getMealNotifications().isEmpty());
        var subscriptionNotification = user1.getMealNotifications().get(0);
        //user1 accept the subscription
        subscriptionManager.acceptMealNotification(user1, subscriptionNotification.getSubscription().getUser(), subscriptionNotification.getSubscription(), subscriptionNotification.getMsg());
        //check the subscription is accepted
        assertTrue(subscriptionNotification.getSubscription().getState() == SubscriptionStates.ACCEPTED);
        //check user1 cannot refuse the subscription anymore
        assertThrows(IllegalArgumentException.class, ()-> subscriptionManager.refuseMealNotification(user1, subscriptionNotification.getSubscription().getUser(), subscriptionNotification.getSubscription(), subscriptionNotification.getMsg()));
        assertTrue(subscriptionNotification.getSubscription().getState() == SubscriptionStates.ACCEPTED);
        //check user cannot accept his subscription
        assertThrows(IllegalArgumentException.class, ()-> subscriptionManager.acceptMealNotification(user, subscriptionNotification.getSubscription().getUser(),  subscriptionNotification.getSubscription(),"") );

    }*/

    /*@Test
    void testAcceptMealNotification() throws Exception {
        //create two users
        IUser user = new User("pippo","pippo@gmail.com","00000","Pippo", "Pluto","pippo".hashCode(), LocalDate.now(), "FISCALCODICE", "IDCODICE", "Macerata, Via prova 15", null, true);
        IUser user1 = new User("pluto","pippo@gmail.com","00000","Pluto", "Pluto","pippo".hashCode(), LocalDate.now(), "FISCALCODICE", "IDCODICE", "Macerata, Via prova 15", null, true);
        //create the meal
        Meal meal = new Meal(user1,3,LocalDate.parse("2021-05-29"), LocalTime.now(),LocalDate.parse("2021-05-29"),LocalTime.now(),"",false,"", "", ConsummationType.AT_HOME,"","", PaymentType.EXCHANGE,"0");
        //create the subscription manager
        SubscriptionManager subscriptionManager = new SubscriptionManager();
        //user joins to the meal
        subscriptionManager.joinToMealNotification(user, meal.getHomeOwner(), meal, "");
        SubscriptionNotification<IUser, IMeal> subscriptionNotification = user1.getMealNotifications().get(0);
        //user1 accept the subscription
        subscriptionManager.acceptMealNotification(user1, subscriptionNotification.getSubscription().getUser(), subscriptionNotification.getSubscription(), subscriptionNotification.getMsg());
        //check the subscription is accepted
        assertTrue(subscriptionNotification.getSubscription().getState() == SubscriptionStates.ACCEPTED);
        //check user1 cannot refuse the subscription anymore
        assertThrows(IllegalArgumentException.class, ()-> subscriptionManager.refuseMealNotification(user1, subscriptionNotification.getSubscription().getUser(), subscriptionNotification.getSubscription(), subscriptionNotification.getMsg()));
        assertTrue(subscriptionNotification.getSubscription().getState() == SubscriptionStates.ACCEPTED);
        //check user cannot accept his subscription
        assertThrows(IllegalArgumentException.class, ()-> subscriptionManager.acceptMealNotification(user, subscriptionNotification.getSubscription().getUser(),  subscriptionNotification.getSubscription(),"") );
    }*/

    /*@Test
    void testAcceptPublicRequestNotification() throws Exception {
        //build two users
        IUser user = new User("pippo","pippo@gmail.com","00000","Pippo", "Pluto","pippo".hashCode(), LocalDate.now(), "FISCALCODICE", "IDCODICE", "Macerata, Via prova 15", null, true);
        IUser user1 = new User("pluto","pippo@gmail.com","00000","Pluto", "Pluto","pippo".hashCode(), LocalDate.now(), "FISCALCODICE", "IDCODICE", "Macerata, Via prova 15", null, true);
        //create a public meal request
        MealRequest request = MealManager.getInstance().createPublicMealRequest(user, "mealType", ConsummationType.AT_HOME,
                PaymentType.CASH, "", LocalDate.parse("2021-05-29"),
                LocalTime.now(), LocalDate.parse("2021-05-29"),
                LocalTime.now(), "0", "", "", 4);
        //build a subscription manager
        SubscriptionManager subscriptionManager = new SubscriptionManager();
        subscriptionManager.acceptPublicRequestNotification(user1 , user , request, "");
        //check the new state of the request
        assertFalse(request.getState() == MealStates.FULL);
        //check user cannot accept his request
        assertThrows(IllegalArgumentException.class, ()->subscriptionManager.acceptPublicRequestNotification(user, request.getHost(), request,"" ));
        //check user1 is the homeOwner in the request
        assertEquals(user1, request.getHomeOwner());
    }*/

    @Test
    void testSendPrivateRequestNotification() throws Exception {
        //create two users
        IUser user = new User("pippo","pippo@gmail.com","00000","Pippo", "Pluto","pippo".hashCode(), LocalDate.now(), "FISCALCODICE", "IDCODICE", "Macerata, Via prova 15", null, true);
        IUser user1 = new User("pluto","pippo@gmail.com","00000","Pluto", "Pluto","pippo".hashCode(), LocalDate.now(), "FISCALCODICE", "IDCODICE", "Macerata, Via prova 15", null, true);
        //build a subscription Manager
        SubscriptionManager subscriptionManager = new SubscriptionManager();
        //create a private meal request
        MealRequest request1 = MealManager.getInstance().createPrivateMealRequest(user, "", ConsummationType.AT_HOME, PaymentType.CASH, "",LocalDate.parse("2021-05-29"),
                LocalTime.now(), LocalDate.parse("2021-05-29"),
                LocalTime.now(), "0", "", "", 2, user1);
        //send a notification for a private meal request
        subscriptionManager.sendPrivateRequestNotification(user, user1, request1, "");
        //check user1 receive the notification
        assertFalse(user1.getMealRequestNotifications().isEmpty());
        //check user cannot send a request to himself
        assertThrows(IllegalArgumentException.class, ()-> subscriptionManager.sendPrivateRequestNotification(user,user, request1, ""));

    }

    @Test
    void testAcceptPrivateRequestNotification() throws Exception{
        //build two users
        IUser user = new User("pippo","pippo@gmail.com","00000","Pippo", "Pluto","pippo".hashCode(), LocalDate.now(), "FISCALCODICE", "IDCODICE", "Macerata, Via prova 15", null, true);
        IUser user1 = new User("pluto","pippo@gmail.com","00000","Pluto", "Pluto","pippo".hashCode(), LocalDate.now(), "FISCALCODICE", "IDCODICE", "Macerata, Via prova 15", null, true);
        //build subscription manager
        SubscriptionManager subscriptionManager = new SubscriptionManager();
        //create a private meal request
        MealRequest request1 = MealManager.getInstance().createPrivateMealRequest(user, "", ConsummationType.AT_HOME, PaymentType.CASH, "",LocalDate.parse("2021-05-29"),
                LocalTime.now(), LocalDate.parse("2021-05-29"),
                LocalTime.now(), "0", "", "", 2, user1);
        //send the notification from user to user1
        subscriptionManager.sendPrivateRequestNotification(user, user1, request1, "");
        //check user1 receives the notification
        assertFalse(user1.getMealRequestNotifications().isEmpty());
        SubscriptionNotification<IUser, IMealRequest> subscriptionNotification = user1.getMealRequestNotifications().get(0);
        subscriptionManager.acceptPrivateRequestNotification(user1, user, subscriptionNotification.getSubscription(), subscriptionNotification.getMsg());
        //check the subscription is accepted
        assertTrue(subscriptionNotification.getSubscription().getState() == SubscriptionStates.ACCEPTED);
        //check user1 cannot refuse the subscription anymore
        subscriptionManager.refusePrivateRequestNotification(user1, user , subscriptionNotification.getSubscription(), subscriptionNotification.getMsg());
        assertTrue(subscriptionNotification.getSubscription().getState() == SubscriptionStates.ACCEPTED);
        //user cannot accept his own subscription
        assertThrows(IllegalArgumentException.class, ()-> subscriptionManager.acceptPrivateRequestNotification(user, subscriptionNotification.getSubscription().getUser(), subscriptionNotification.getSubscription(),""));
        //check user1 is the homeOwner in the request
        assertEquals(user1, request1.getHomeOwner());
    }

    @Test
    void testRefusePrivateRequestNotification() throws Exception {
        //build two users
        IUser user = new User("pippo","pippo@gmail.com","00000","Pippo", "Pluto","pippo".hashCode(), LocalDate.now(), "FISCALCODICE", "IDCODICE", "Macerata, Via prova 15", null, true);
        IUser user1 = new User("pluto","pippo@gmail.com","00000","Pluto", "Pluto","pippo".hashCode(), LocalDate.now(), "FISCALCODICE", "IDCODICE", "Macerata, Via prova 15", null, true);
        //build subscription manager
        SubscriptionManager subscriptionManager = new SubscriptionManager();
        //create a private meal request
        MealRequest request1 = MealManager.getInstance().createPrivateMealRequest(user, "", ConsummationType.AT_HOME, PaymentType.CASH, "",LocalDate.parse("2021-05-29"),
                LocalTime.now(), LocalDate.parse("2021-05-29"),
                LocalTime.now(), "0", "", "", 2, user1);
        //send the notification from user to user1
        subscriptionManager.sendPrivateRequestNotification(user, user1, request1, "");
        //check user1 receives the notification
        assertFalse(user1.getMealRequestNotifications().isEmpty());
        SubscriptionNotification<IUser, IMealRequest> subscriptionNotification = user1.getMealRequestNotifications().get(0);
        subscriptionManager.refusePrivateRequestNotification(user1, user, subscriptionNotification.getSubscription(), subscriptionNotification.getMsg());
        //check the subscription is accepted
        assertTrue(subscriptionNotification.getSubscription().getState() == SubscriptionStates.REFUSED);
        //check user1 cannot refuse the subscription anymore
        subscriptionManager.refusePrivateRequestNotification(user1, user , subscriptionNotification.getSubscription(), subscriptionNotification.getMsg());
        assertTrue(subscriptionNotification.getSubscription().getState() == SubscriptionStates.REFUSED);
        //user cannot refuse his own subscription
        assertThrows(IllegalArgumentException.class, ()-> subscriptionManager.refusePrivateRequestNotification(user, subscriptionNotification.getSubscription().getUser(), subscriptionNotification.getSubscription(),""));


    }
}
