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
        User user = new User("Alice");
        assertEquals("Alice", user.getName());
    }

    /**
     * Test set name.
     */
    @Test
    public void testSetName() {
        User user = new User("Theodore teh Great");
        user.setName("Theodore the Great");
        assertEquals("Theodore the Great", user.getName());
    }
}