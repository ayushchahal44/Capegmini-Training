package com.capg.ram.web;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;
 

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

 

@Controller
public class LoginController {

    @Autowired
   private LoginService loginService;
	
	//private LoginService loginService= new LoginService();
    @RequestMapping("/login.spring")
    public ModelAndView loginValid(HttpServletRequest request,
                                   HttpServletResponse response) {

        String user = request.getParameter("user");
        String pass = request.getParameter("pass");

        ModelAndView mav = new ModelAndView();

        if (loginService.checkLogin(user, pass))	
            mav.setViewName("/Success.jsp");
        else
            mav.setViewName("/Failed.jsp");

        return mav;
    }
}