package com.capg.ram.web;

 

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;



@Service
public class LoginService {
   // @Autowired
    //private LoginDAO loginDAO;
	
	private LoginDAO loginDAO= new LoginDAO();
    public boolean checkLogin(String user, String pass) {
        return loginDAO.validateUser(user, pass);
    }
}