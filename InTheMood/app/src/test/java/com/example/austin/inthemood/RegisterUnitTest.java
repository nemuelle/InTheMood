package com.example.austin.inthemood;

import org.junit.Test;
import static org.junit.Assert.*;

/**
 * Created by Austin on 2017-03-11.
 */

public class RegisterUnitTest {
    @Test
    public void testRegisterFirstUser() {
        dataControler controller = new dataControler();
        String firstName = "first";
        String firstPass = "wow";
        User firstUser = new User(firstName, firstPass);

        controller.addToUserList(firstUser);
        User addedUser = controller.verifyLogIn(firstName, firstPass);

        assertEquals(addedUser.getName(), firstName);
        assertEquals(addedUser.getPassword(), firstPass);

    }

    @Test
    public void testValidRegistration() {
        dataControler controller = new dataControler();
        String inputName = "";
        String inputPass = "";

    }
}
