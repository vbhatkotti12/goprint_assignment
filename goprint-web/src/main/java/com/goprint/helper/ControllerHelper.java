package com.goprint.helper;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import com.goprint.authentication.UserInfo;

@Component
public class ControllerHelper {
	
	/**
	 * Get Field Errors
	 * @param bindingResults
	 * @return
	 */
	public ResponseEntity<Map<String,Object>> handleValidationException(BindingResult bindingResults) {
		Map<String,Object> responseMap = new HashMap<String,Object>();

		List<ObjectError> errors = bindingResults.getAllErrors();
		List<String> errorMap = new ArrayList<String>();
		for(ObjectError error : errors){
			if(error instanceof FieldError){
				errorMap.add(((FieldError)error).getField() + " field has error :- "+((FieldError)error).getDefaultMessage());				
			}
			else{
				errorMap.add(error.getCode() + " Field has error :- "+error.getDefaultMessage());
			}
		}
		responseMap.put("message", "Validation Errors");
		responseMap.put("validation_errors", errorMap);
		return new ResponseEntity<Map<String,Object>>(responseMap, HttpStatus.BAD_REQUEST);
	}
	/**
	 * Get UserDetails After Auth
	 * @return
	 */
	public UserInfo getUserDetails(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo)auth.getPrincipal();
		return userInfo;
	}
	/**
	 * Get UserId After Auth
	 * @return
	 */
	public Long getUserId(){
		return getUserDetails().getId();
	}


}
