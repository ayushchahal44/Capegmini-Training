package com.capg.springboot.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.capg.springboot.Exceptions.UserNotFoundException;
import com.capg.springboot.entity.User;
import com.capg.springboot.repository.IUserRepository;

/*/*****************************************************************************
 *ILogin ServiceImpl Class 
 *
 * Created By: 
 * Date: 
 *******************************************************************************/

@Service
@Transactional
public class IUserServiceImpl implements IUserService {
  @Autowired
  private IUserRepository iuserrepository;
  
//*********************************************************************************************************************	
	
	/* Method: validateUser 
	 * Description: It checks whether the user is valid or not
	 * @Override: It indicates that the child class method is over-writing its base class method.
     * return: It returns the user. 
     */
  
  @Override
  public User validateUser(String id) throws UserNotFoundException {
	  User user_info=iuserrepository.findByUserId(id);
	  if(user_info==null)
		  throw new UserNotFoundException("User Not Found");
	  return user_info;
  }
  
  
//*********************************************************************************************************************	
	
	/* Method: addUser 
	 * Description: It adds the user
	 * @Override: It indicates that the child class method is over-writing its base class method.
     * return: It returns the user. 
     */
  
  @Override
  public User addUser(User user) {
	  User user_info=iuserrepository.save(user);
	  return user_info;
  }
  
//*********************************************************************************************************************	
	
	/* Method: removeUser 
	 * Description: It remove the user
	 * @Override: It indicates that the child class method is over-writing its base class method.
     * return: It returns the user. 
     */
  
  @Override
  public User removeUser(User user) {
	  
	  iuserrepository.delete(user);
	  return user;
  }
   

}
