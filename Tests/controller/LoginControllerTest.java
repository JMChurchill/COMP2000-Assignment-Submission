package controller;

import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.*;

public class LoginControllerTest {
    @Before
    public void setUp() throws Exception {
    }
    @After
    public void tearDown() throws Exception {

    }

    @Test
    public void logIntoAccountTest() {
        String userName = "user";
        String password = "password";

        LoginController loginController = new LoginController();
        loginController.logIntoAccount(userName, password);

        assertEquals(true,LoginController.isLoggedIn());
    }

    @Test
    public void logOutTest() {
        LoginController.logOut();

        assertEquals(false,LoginController.isLoggedIn());
    }
}