package com.capg.junit4;



/*
Methods annotated with the @Before annotation are run before each test. 
This is useful when we want to execute some common code before running a test.
 */
import static org.junit.Assert.assertEquals;
import org.junit.*;
public class BeforeAfter {
    @BeforeClass
    public static void init() {
        System.out.println("Before all tests");
    }
    @Test
    public void testCase() {
        assertEquals(6, 3 + 3);
    }
    @AfterClass
    public static void cleanUp() {
        System.out.println("After all tests");
    }
}