package it.unicam.cs.gp.inmytable.user;

import it.unicam.cs.gp.inmytable.notification.NotificationManager;

import java.time.LocalDate;
import java.util.Objects;

public class User {

    private String username;
    private String email;
    private String telephoneNumber;
    private String firstName;
    private String lastName;
    private int password;
    private LocalDate birth;
    private NotificationManager notificationManager;
    private FeedbackBox feedbackBox;

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
                LocalDate birth) {
        if (username == null || email == null || telephoneNumber == null || firstName == null || lastName == null || birth == null) throw new NullPointerException("One of the parameter is null!");
        if (LocalDate.now().isBefore(birth)) throw new IllegalArgumentException("You cannot travel in time!!");
        this.username = username;
        this.email = email;
        this.telephoneNumber = telephoneNumber;
        this.firstName = firstName;
        this.lastName = lastName;
        this.password = password;
        this.birth = birth;
        this.notificationManager = new NotificationManager(this);
        this.feedbackBox = new FeedbackBox();
    }

    public NotificationManager getNotificationManager() {
        return notificationManager;
    }

    public String getUsername() {
        return username;
    }

    public String getEmail() {
        return email;
    }

    public String getTelephoneNumber() {
        return telephoneNumber;
    }

    public String getFirstName() {
        return firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public int getPassword() {
        return password;
    }

    public LocalDate getBirth() {
        return birth;
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

    public FeedbackBox getFeedbackBox() {
        return feedbackBox;
    }
}