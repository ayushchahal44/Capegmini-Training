package com.capg.junit4.mockito;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.MockitoJUnitRunner;

import static org.junit.Assert.*;
import static org.mockito.Mockito.*;
//Enables Mockito
/*
 With Mockito (Advantage)
No database needed
Fast execution
Tests only business logic
 * 
 */

@RunWith(MockitoJUnitRunner.class)
public class UserServiceTest {
    //Creates mock object
    @Mock
    private UserDao userDao;   // Mocked dependency
    //Injects mock into service
    @InjectMocks
    private UserService userService; // Class under test

    @Test
    public void testGetUsername() {
               
        // Step 1: Define mock behavior
        when(userDao.findUsernameById(1)).thenReturn("Ram");

        // Step 2: Call service method
        String result = userService.getUsername(1);

        // Step 3: Verify result
        assertEquals("Ram", result);
              //Verifies method call
        // Step 4: Verify interaction
        verify(userDao).findUsernameById(1);
    }
}