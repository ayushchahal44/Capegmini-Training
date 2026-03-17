package com.capg.springboot.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.capg.springboot.entity.User;

/*/*****************************************************************************
 *ILogin Repository Class 
 *
 * Created By: 
 * Date: 
 *******************************************************************************/

public interface IUserRepository extends JpaRepository<User,String> {

	public User findByUserId(String id);
}
