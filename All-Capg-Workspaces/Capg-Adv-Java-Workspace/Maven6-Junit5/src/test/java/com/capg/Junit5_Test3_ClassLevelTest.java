package com.capg;

import static org.junit.jupiter.api.Assertions.assertEquals;
import org.junit.jupiter.api.*;

public class Junit5_Test3_ClassLevelTest {

    @BeforeAll
    static void beforeAll() {
        System.out.println("Before all tests");
    }

    @Test
    void sampleTest() {
        assertEquals(4, 2 + 2);
    }

    @AfterAll
    static void afterAll() {
        System.out.println("After all tests");
    }
}