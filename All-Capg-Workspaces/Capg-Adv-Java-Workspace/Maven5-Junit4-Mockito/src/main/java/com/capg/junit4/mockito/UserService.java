package com.capg.junit4.mockito;

public class UserService {
	private UserDao userDao;
	public UserService(UserDao userDao) {
		this.userDao=userDao;
	}
	public String getUsername(int id) {
		return userDao.findUsernameById(id);
	}
}