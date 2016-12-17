package com.goprint.service.impl;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.goprint.entity.UserDetail;
import com.goprint.exception.ServiceException;
import com.goprint.repository.UserDetailRepository;
import com.goprint.service.UserDetailService;
/**
 * Service Implementaion for User DB Operations
 * @author vinod Bhatkotti
 *
 */
@Service
public class UserDetailServiceImpl implements UserDetailService{

	private static final Logger logger = Logger.getLogger(NotesServiceImpl.class);
	@Resource
	private UserDetailRepository userDetailRepository;
	/**
	 * Gets the user by Id
	 * @param id
	 * @return
	 */
	@Transactional
	public Long findById(String userName,String password) throws ServiceException {
		try{
			return userDetailRepository.findUser(userName,password);
		}
		catch(DataAccessException e){
			logger.error("Error in findById",e);
			throw new ServiceException(e);
		}
	}
}
