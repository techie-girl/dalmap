/**
 * Creates a listview used to see a list of buildings.
 * The values of the building list are hard-coded.
 * A button is created for logout on this activity.
 * Activity listens for listview item clicks.
 * If an item is clicked, it displays building info.
 *
 * @author Scott
 * @author Aqil
 * @author Arazoo
 * @author Chris
 * @author Jacob
 * @author Jaewoong
 */
package com.example.scott.dalmapproject;

import org.junit.BeforeClass;
import org.junit.Test;
import static org.junit.Assert.*;

public class LoginTests {

    static String pw = "password";
    static String ids = "id";

    static Login login;

    @BeforeClass
    public static void init(){

        login = new Login();
        login.setPasswords(pw);
        login.setIds(ids);
    }

    @Test
    public void check_password(){

        assertEquals("password", login.getPasswords());
    }

    @Test
    public void check_ids(){

        assertEquals("id", login.getIds());
    }
}
