package it.unicam.cs.gp.inmytable;

import it.unicam.cs.gp.inmytable.allmeals.ConsumationType;
import it.unicam.cs.gp.inmytable.allmeals.MealManager;
import it.unicam.cs.gp.inmytable.allmeals.mealrequest.*;
import it.unicam.cs.gp.inmytable.allmeals.meals.*;
import it.unicam.cs.gp.inmytable.allmeals.*;
import it.unicam.cs.gp.inmytable.notification.SubscriptionManager;
import it.unicam.cs.gp.inmytable.user.IUser;
import it.unicam.cs.gp.inmytable.user.User;
import org.junit.jupiter.api.Test;

import java.time.LocalDate;
import java.time.LocalTime;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;

class FoodTest {
//    public FoodTest(){
//
//    }
//    @Test
//    void mealTest() throws Exception {
//        //build one user
//        IUser user1 = new User("pluto","pippo@gmail.com","00000","Pluto", "Pluto","pippo".hashCode(), LocalDate.now(), "FISCALCODICE", "IDCODICE", "Macerata, Via prova 15", null, true);
//
//        //check the state is pending
//        Meal meal = new Meal(user1,3,LocalDate.parse("2021-05-29"), LocalTime.parse("18:00:00"),LocalDate.parse("2021-05-29"),LocalTime.parse("18:00:00"),"",false,"", ConsumationType.AT_HOME,"","", PaymentType.EXCHANGE,"0");
//        assertTrue(meal.getState() == MealStates.PENDING);
//
//        //check that the status of the meal is expired when the date has passed
//        Meal meal1 = new Meal(user1,3,LocalDate.parse("2021-04-29"), LocalTime.parse("18:00:00"),LocalDate.parse("2021-04-29"),LocalTime.parse("18:00:00"),"",false,"", ConsumationType.AT_HOME,"","", PaymentType.EXCHANGE,"0");
//        assertTrue(meal1.getState() == MealStates.EXPIRED);
//
//    }
//
//    @Test
//    void mealRequestTest() throws Exception {
//        //build one user
//        IUser user1 = new User("pluto","pippo@gmail.com","00000","Pluto", "Pluto","pippo".hashCode(), LocalDate.now(), "FISCALCODICE", "IDCODICE", "Macerata, Via prova 15", null, true);
//
//        //check the state is pending
//        MealRequest request = MealManager.getInstance().createPublicMealRequest(user1, "mealType", ConsumationType.AT_HOME,
//                PaymentType.CASH, "", LocalDate.parse("2021-05-29"),
//                LocalTime.parse("17:10:00"), LocalDate.parse("2021-05-29"),
//                LocalTime.parse("17:10:00"), "0", "", "", 4);
//        assertTrue(request.getState() == MealStates.PENDING);
//
//        //check that the status of the meal request is expired when the date has passed
//        MealRequest request1 = MealManager.getInstance().createPublicMealRequest(user1, "mealType", ConsumationType.AT_HOME,
//                PaymentType.CASH, "", LocalDate.parse("2021-04-29"),
//                LocalTime.parse("17:10:00"), LocalDate.parse("2021-04-29"),
//                LocalTime.parse("17:10:00"), "0", "", "", 4);
//        assertTrue(request1.getState() == MealStates.EXPIRED);
//        //check date cannot be before now
//        /*assertThrows(IllegalArgumentException.class , ()-> new MealRequest(user1, "mealType", ConsumationType.AT_HOME,
//                PaymentType.CASH, "", LocalDate.parse("2021-04-29"),
//                LocalTime.now(), LocalDate.parse("2021-05-29"),
//                LocalTime.now(), "0", "", "", 4) );
//        //check expiry date cannot be before meal date
//        assertThrows(IllegalArgumentException.class , ()-> new MealRequest(user1, "mealType", ConsumationType.AT_HOME,
//                PaymentType.CASH, "", LocalDate.parse("2021-05-29"),
//                LocalTime.now(), LocalDate.parse("2021-04-29"),
//                LocalTime.now(), "0", "", "", 4) );
//        //check expiry time cannot be after meal request time
//        assertThrows(IllegalArgumentException.class , ()-> new MealRequest(user1, "mealType", ConsumationType.AT_HOME,
//                PaymentType.CASH, "", LocalDate.parse("2021-05-29"),
//                LocalTime.parse("17:00:00"), LocalDate.parse("2021-05-29"),
//                LocalTime.parse("18:00:00"), "0", "", "", 4) );
//        //check meal time cannot be before now
//        assertThrows(IllegalArgumentException.class , ()-> new MealRequest(user1, "mealType", ConsumationType.AT_HOME,
//                PaymentType.CASH, "", LocalDate.now(),
//                LocalTime.parse("16:00:00"), LocalDate.now(),
//                LocalTime.parse("15:00:00"), "0", "", "", 4) );*/
//
//
//
//
//
//    }

}