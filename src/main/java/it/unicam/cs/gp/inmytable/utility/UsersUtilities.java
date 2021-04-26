package it.unicam.cs.gp.inmytable.utility;

import it.unicam.cs.gp.inmytable.user.User;

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

     public static UsersUtilities getInstance(){
         if( usersUtilities == null)
             usersUtilities = new UsersUtilities();
         return usersUtilities;
     }

     public boolean checkUsername(String username){
         return !usernames.containsKey(username);
     }

     public boolean checkEmail(String email){
         if (emailValidator(email) && !emails.contains(email)) return true;
         return false;

     }

     public boolean checkFiscalCode(String fiscalCode){
         if(fiscalCodeValidator(fiscalCode) && !fiscalCodes.contains(fiscalCode)) return true;
         return false;
     }

     public boolean checkId(String id){
         if (!ids.contains(id)) return true;
         return false;
     }
     private boolean fiscalCodeValidator(String fiscalCode){
         String regex = "[a-zA-Z]{6}\\d\\d[a-zA-Z]\\d\\d[a-zA-Z]\\d\\d\\d[a-zA-Z]";
         Pattern pattern = Pattern.compile(regex);
         Matcher matcher = pattern.matcher(fiscalCode);
         if(matcher.matches()) {
             return true;
         }
         return false;
     }
    private boolean emailValidator(String email) {
        String regex = "[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        if(matcher.matches()) {
            return true;
        }
        return false;
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

