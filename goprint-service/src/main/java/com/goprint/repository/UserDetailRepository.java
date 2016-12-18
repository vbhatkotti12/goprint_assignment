package com.goprint.repository;
import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.goprint.entity.Note;
import com.goprint.entity.UserDetail;

/**
 * Respository for User DB Operations
 * @author vinod Bhatkotti
 *
 */
public interface UserDetailRepository extends JpaRepository<UserDetail, Long> {
	/**
	 * Gets the user details by email and password
	 * @param userName
	 * @param password
	 * @return
	 */
	@Query("select u.id from  UserDetail u where u.email = ?1  and u.password = ?2")
	Long findUser(String userName,String password);
}
