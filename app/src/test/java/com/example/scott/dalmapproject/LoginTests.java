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
