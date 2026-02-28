package com.capg;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.function.Executable;
import static org.junit.jupiter.api.Assertions.*;

public class Junit5_Test5_Exception {

	@Test
    void exceptionTest() {

        Executable executable = new Executable() {
            @Override
            public void execute() throws Throwable {
                int x = 10 / 0;   // ArithmeticException
            }
        };

        assertThrows(ArithmeticException.class, executable);
    }
}