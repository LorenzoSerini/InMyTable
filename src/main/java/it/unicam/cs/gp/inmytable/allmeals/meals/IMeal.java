package it.unicam.cs.gp.inmytable.allmeals.meals;


import it.unicam.cs.gp.inmytable.allmeals.Food;
import it.unicam.cs.gp.inmytable.user.IUser;
import java.util.Set;

public interface IMeal extends Food {

   /**
    * used to add a user to the meal
    * @param user the user to add to the meal
    */
   void addUser(IUser user);

   /**
    * returns the maximum number of users who can participate in the meal
    * @return max number users
    */
   int getMaxNumberUsers();

   /**
    * return number of places available
    * @return number of places available
    */
   int getPlacesAvailable();

   /**
    * return true if is free subscription else false
    * @return true if is free subscription else false
    */
   boolean isFreeSubscription();

   /**
    * return the meal ingredients
    * @return the meal ingredients
    */
   String getIngredients();

   /**
    * return users set
    * @return user set
    */
   Set<IUser> getUserList();

}
