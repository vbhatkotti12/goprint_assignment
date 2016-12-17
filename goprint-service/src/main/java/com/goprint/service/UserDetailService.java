package com.goprint.service;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.goprint.entity.UserDetail;
import com.goprint.exception.ServiceException;
import com.goprint.repository.UserDetailRepository;

/**
 * Service interface for User DB Operations
 * @author vinod Bhatkotti
 *
 */

public interface UserDetailService {	
	public Long findById(String userName,String password) throws ServiceException;
}
