package it.unicam.cs.gp.inmytable.user;

import it.unicam.cs.gp.inmytable.allmeals.mealrequest.IMealRequest;
import it.unicam.cs.gp.inmytable.allmeals.meals.IMeal;
import it.unicam.cs.gp.inmytable.notification.SimpleNotification;
import it.unicam.cs.gp.inmytable.notification.SubscriptionNotification;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class User implements IUser{

    private String username;
    private String email;
    private String telephoneNumber;
    private String firstName;
    private String lastName;
    private int password;
    private String fiscalCode;
    private String id;
    private String city;
    private String address;
    private boolean availableToRequests;
    private LocalDate birth;
    private FeedbackBox feedbackBox;
    private List<SimpleNotification<IUser>> simpleNotifications;
    private List<SubscriptionNotification<IUser, IMeal>> mealNotifications;
    private List<SubscriptionNotification<IUser, IMealRequest>> mealRequestNotifications;

    /**
     * Build a new user
     *
     * @param username        username of the user, it must be unique in the system
     * @param email           email of the user
     * @param telephoneNumber telephone number of the user
     * @param firstName       user first name
     * @param lastName        user last name
     * @param password        user's password
     * @param birth           user birth day
     */
    public User(String username, String email, String telephoneNumber, String firstName, String lastName, int password,
                LocalDate birth, String id, String fiscalCode, String city, String address, boolean availableToRequests) {
        if (username == null || email == null || telephoneNumber == null || firstName == null || lastName == null ||
                password == 0 || birth == null) throw new NullPointerException("One of the parameter is null!");
        if (LocalDate.now().isBefore(birth)) throw new IllegalArgumentException("You cannot travel in time!!");
        this.username = username;
        this.email = email;
        this.telephoneNumber = telephoneNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.birth = birth;
        this.fiscalCode = fiscalCode;
        this.id = id;
        this.city = city;
        this.address=address;
        this.availableToRequests=availableToRequests;
        this.feedbackBox = new FeedbackBox();
        this.simpleNotifications = new ArrayList<>();
        mealNotifications = new ArrayList<>();
        mealRequestNotifications = new ArrayList<>();
    }

    @Override
    public void setUsername(String username){ this.username=username; }

    @Override
    public String getUsername() {
        return username;
    }

    @Override
    public void setEmail(String email){this.email=email;}

    @Override
    public String getEmail() {
        return email;
    }

    @Override
    public void setTelephoneNumber(String telephoneNumber){this.telephoneNumber=telephoneNumber;}

    @Override
    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    @Override
    public String getFirstName() {
        return firstName;
    }

    @Override
    public String getLastName() {
        return lastName;
    }

    @Override
    public void setPassword(int password){this.password=password;}

    @Override
    public int getPassword() {
        return password;
    }

    @Override
    public LocalDate getBirth() {
        return birth;
    }

    @Override
    public String getFiscalCode(){return fiscalCode;}

    @Override
    public void setId(String id){this.id=id;}

    @Override
    public String getId(){return id;}

    @Override
    public void setCity(String city){this.city=city;}

    @Override
    public String getCity(){return city;}

    @Override
    public void setAddress(String address){this.address=address;}

    @Override
    public String getAddress(){return address;}

    @Override
    public void setAvailableToRequests(boolean bool){this.availableToRequests = bool;}

    @Override
    public boolean getAvailableToRequests(){return availableToRequests;}

    @Override
    public FeedbackBox getFeedbackBox() {
        return feedbackBox;
    }

    @Override
    public List<SimpleNotification<IUser>> getSimpleNotifications(){
        return this.simpleNotifications;
    }

    @Override
    public List<SubscriptionNotification<IUser, IMeal>> getMealNotifications(){
        return this.mealNotifications;
    }

    @Override
    public List<SubscriptionNotification<IUser, IMealRequest>> getMealRequestNotifications(){
        return this.mealRequestNotifications;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof User)) return false;
        User user = (User) o;
        return Objects.equals(username, user.username);
    }

    @Override
    public int hashCode() {
        return Objects.hash(username);
    }

}