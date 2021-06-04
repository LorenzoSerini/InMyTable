package it.unicam.cs.gp.inmytable.persistence;

import it.unicam.cs.gp.inmytable.user.IUser;

import java.sql.SQLException;

public interface UserPersistence {

    /**
     * Update user information
     * @param user the user to update
     * @param email if != null update email
     * @param password if != null update password
     * @param city if != null update city
     * @param address if != null update address
     * @param id if != null update id
     * @param telephoneNumber if != null update telephone number
     * @throws Exception
     */
    void updateUser(IUser user, String email, int password, String city, String address, String id, String telephoneNumber, boolean available) throws Exception;

}
