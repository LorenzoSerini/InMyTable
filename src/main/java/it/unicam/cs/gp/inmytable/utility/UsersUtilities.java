package it.unicam.cs.gp.inmytable.utility;

import it.unicam.cs.gp.inmytable.user.User;

import java.time.LocalDate;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UsersUtilities {
    private static Map<String, User> usernames;
    private static Set<String> emails;
    private static Set<String> fiscalCodes;
    private static Set<String> ids;
    private static UsersUtilities usersUtilities;

     private UsersUtilities(){
         usernames = new HashMap<>();
         emails = new HashSet<>();
         fiscalCodes = new HashSet<>();
         ids = new HashSet<>();
     }


    private UsersUtilities(Map<String, User> usernamesMap){
         this();
        usernames.putAll(usernamesMap);
        for(String key:usernames.keySet()){
            emails.add(usernames.get(key).getEmail());
            fiscalCodes.add(usernames.get(key).getFiscalCode());
            ids.add(usernames.get(key).getId());
        }
    }


     public static UsersUtilities getInstance(){
         if( usersUtilities == null)
             usersUtilities = new UsersUtilities();
         return usersUtilities;
     }

    public static UsersUtilities getInstance(Map<String, User> usernamesMap){
        if( usersUtilities == null)
            usersUtilities = new UsersUtilities(usernamesMap);
        return usersUtilities;
    }

     public boolean checkUsername(String username){
         return !usernames.containsKey(username);
     }

     //TODO: SI POSSONO METTERE EMAIL UGUALI?
     public boolean checkEmail(String email){
         return emailValidator(email) && !emails.contains(email);
     }

     public boolean checkFiscalCode(String fiscalCode){
         return fiscalCodeValidator(fiscalCode) && !fiscalCodes.contains(fiscalCode);
     }

    public boolean checkBirth(LocalDate birth){
         if(birth.until(LocalDate.now()).getYears()>=18) return true;
         return false;
    }

     public boolean checkId(String id){
         return !ids.contains(id);
     }

     private boolean fiscalCodeValidator(String fiscalCode){
         String regex = "[a-zA-Z]{6}\\d\\d[a-zA-Z]\\d\\d[a-zA-Z]\\d\\d\\d[a-zA-Z]";
         Pattern pattern = Pattern.compile(regex);
         Matcher matcher = pattern.matcher(fiscalCode);
         return matcher.matches();
     }

    private boolean emailValidator(String email) {
        String regex = "[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    public boolean checkPassword(User user, String password){
         return user.getPassword() == password.hashCode();
    }

    public User getUser(String username){
         return usernames.get(username);
    }

    public void insertUser(User user){
         usernames.put(user.getUsername(), user);
    }
}

