package com.example.austin.inthemood;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class UserTest {
    /**
     * Test get name.
     */
    @Test
    public void testGetName() {
        User user = new User("Alice", "A123");
        assertEquals("Alice", user.getName());
    }

    /**
     * Test set name.
     */
    @Test
    public void testSetName() {
        User user = new User("Theodore teh Great", "T123");
        user.setName("Theodore the Great");
        assertEquals("Theodore the Great", user.getName());
    }

    /**
     * Test get password.
     */
    @Test
    public void testGetPassword() {
        User user = new User("Alice", "A123");
        assertEquals("A123", user.getPassword());

    }

    /**
     * Test set password.
     */
    @Test
    public void testSetPassword() {
        User user = new User("Theodore teh Great", "T123");
        user.setPassword("T123");
        assertEquals("T123", user.getPassword());
    }
}