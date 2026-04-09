package com.capg.junit4;

import org.junit.Test;
import static org.junit.Assert.*;

//Assertion Methods
public class AssertTest {

    @Test
    public void testAssertions() {
        assertEquals(10, 5 + 5);
        assertTrue(10 > 5);
        assertFalse(5 > 10);
        assertNull(null);
        assertNotNull("JUnit");
    }
}