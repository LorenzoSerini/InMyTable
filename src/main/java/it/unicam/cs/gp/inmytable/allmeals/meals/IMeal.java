package it.unicam.cs.gp.inmytable.allmeals.meals;

import it.unicam.cs.gp.inmytable.user.IUser;
import it.unicam.cs.gp.inmytable.user.User;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Set;

public interface IMeal extends Food{

   void addUser(IUser user);

   int getMaxNumberUsers();

   int getPlacesAvailable();

   boolean isFreeSubscription();

   ConsumationType getConsummationType();

   String getIngredients();

   Set<IUser> getUserList();

}
