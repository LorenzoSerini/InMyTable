package it.unicam.cs.gp.inmytable.controllers;

import it.unicam.cs.gp.inmytable.allmeals.ConsumationType;
import it.unicam.cs.gp.inmytable.allmeals.MealManager;
import it.unicam.cs.gp.inmytable.allmeals.MealStates;
import it.unicam.cs.gp.inmytable.allmeals.PaymentType;

public class MealsController {
    private MealManager mealManager;

    public MealsController(){
        mealManager = new MealManager();
    }

    public void cook(String date, String expireDate, int maxNumUsers, String mealType, boolean freeSubscription, String place, MealStates mealStates, ConsumationType consumationType, String description, PaymentType paymentType) {
        //Meal avrà al suo interno anche colui che lo creerà cioè il padrone di casa, di aggiungerlo se ne occuperà mealManager !

        /*mealManager.createMeal(//tutti gli attributi che mi servono!, qui ci sarà anche l'attributo homeHowner di
                //tipo User che verrà passato dal cook a seconda di che utente è loggato!
                );*/

        System.out.println("HO PUBBLICATO IL PASTO: \n\n"
        +"- Date: "+date+"\n"
                +"- Expire Date: "+expireDate+"\n"
                +"- Max Users: "+maxNumUsers+"\n"
                +"- Meal Type: "+mealType+"\n"
                +"- Free Subscription: "+freeSubscription+"\n"
                +"- Place: "+place+"\n"
                +"- Meal States: "+mealStates.toString()+"\n" //Questo attributo NON SArà PRESENTE NE QUI NE IN MEALMANAGER PERCHè QUANDO SI INVOCA IL METODO createMeal, il meal verrà impostato direttamente su pending
                +"- Consumation Type: "+consumationType.toString()+"\n"
                +"- Description: "+description+"\n"
                +"- PaymentType: "+paymentType+"\n"); //SE il tipo sara a pagamento andrà messo l'attributo price in meal e sarà baratto l'attributo cosa barattare
                //FORSE CONVERREBBE CREARE UN'interfaccia meal, una classe astratta che raggruppa tutto e poi tre classi meal che saranno freeMeal, PaymentMeal e BarattoMeal
                //perche quella gratis non avrà l'attributo price ne cosa barattare, quella a pagamento avrà l'attributo price e quell baratto l'attributo baratto
                //Oppure è meglio creare l'attributo string price e quella free lo setterà automaticamente a 0 mentre le altre due passeranno l'attributo.

    }
}