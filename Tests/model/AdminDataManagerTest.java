package model;

import org.junit.Test;

import static org.junit.Assert.*;

public class AdminDataManagerTest {

    @Test
    public void findUserTest() {
        AdminDataManager adminDataManager = new AdminDataManager();

        String username = "user";
        String password = "password";

        boolean success=adminDataManager.findUser(username, password);

        assertEquals(true,success);
    }
}