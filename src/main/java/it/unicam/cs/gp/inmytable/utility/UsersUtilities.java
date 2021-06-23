package it.unicam.cs.gp.inmytable.utility;

import it.unicam.cs.gp.inmytable.user.User;

import java.time.LocalDate;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class UsersUtilities {

    /**
     * used to check email
     * @param userMap user map
     * @param email email
     * @return if email is correct return true else false
     */
    public static boolean checkEmail(Map<String, User> userMap, String email){
        if(emailValidator(email)) {
            for (String key : userMap.keySet()) {
                if(userMap.get(key).getEmail().equals(email)) return false;
            }
            return true;
        }
        return false;
    }

    /**
     * used to check fiscal code
     * @param userMap user map
     * @param fiscalCode fiscal code
     * @return if fiscal code is correct return true else false
     */
    public static boolean checkFiscalCode(Map<String, User> userMap,String fiscalCode){
        if(fiscalCodeValidator(fiscalCode)) {
            for (String key : userMap.keySet()) {
                if(userMap.get(key).getFiscalCode().equals(fiscalCode)) return false;
            }
            return true;
        }
        return false;
    }

    /**
     * used to check birth
     * @param birth birth
     * @return if birth>=18 return true else false
     */
    public static boolean checkBirth(LocalDate birth){
        if(birth.until(LocalDate.now()).getYears()>=18) return true;
        return false;
    }

    /**
     * used to check id
     * @param userMap user map
     * @param id the id
     * @return if id is correct return true else false
     */
    public static boolean checkId(Map<String, User> userMap, String id){
        for (String key : userMap.keySet()) {
            if(userMap.get(key).getId().equals(id)) return false;
        }
        return true;
    }


    private static boolean fiscalCodeValidator(String fiscalCode){
        String regex = "[a-zA-Z]{6}\\d\\d[a-zA-Z]\\d\\d[a-zA-Z]\\d\\d\\d[a-zA-Z]";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(fiscalCode);
        return matcher.matches();
    }

    private static boolean emailValidator(String email) {
        String regex = "[a-zA-Z0-9._-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,4}";
        Pattern pattern = Pattern.compile(regex);
        Matcher matcher = pattern.matcher(email);
        return matcher.matches();
    }

    /**
     * check password of the user
     * @param user the user
     * @param password password
     * @return if password is correct return true else false
     */
    public static boolean checkPassword(User user, String password){
        return user.getPassword() == password.hashCode();
    }



}

