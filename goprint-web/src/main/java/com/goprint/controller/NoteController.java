package com.goprint.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.goprint.authentication.UserInfo;
import com.goprint.exception.ServiceException;
import com.goprint.model.NoteModel;
import com.goprint.service.NotesService;
import com.goprint.util.MessagePropertyResolver;
/**
 * Rest Controller for Crud Operations on Note
 * @author vinod Bhatkotti
 *
 */
@RestController
public class NoteController {

	private static final Logger logger = Logger.getLogger(NoteController.class);
	@Autowired 
	NotesService noteService;
	@Autowired
	MessagePropertyResolver messagePropertyResolver;
	/**
	 * Gets All user Notes
	 * @return
	 */
	@GetMapping("/notes")
	public @ResponseBody ResponseEntity<?> getUserNotes() {
		Map<String,Object> responseMap = new HashMap<String,Object>();
		try{
			List<NoteModel> notes = noteService.findByUserId(getUserId());
			if(notes.isEmpty()){
				responseMap.put(messagePropertyResolver.getMessage("map.key.message", new Object[]{}, Locale.US), 
						messagePropertyResolver.getMessage("notes.nofound", new Object[]{}, Locale.US));
				return new ResponseEntity<Map<String,Object>>(responseMap, HttpStatus.NOT_FOUND);
			}
			responseMap.put(messagePropertyResolver.getMessage("map.key.message", new Object[]{}, Locale.US), 
					messagePropertyResolver.getMessage("notes.found", new Object[]{}, Locale.US));
			responseMap.put(messagePropertyResolver.getMessage("map.key.data", new Object[]{}, Locale.US), notes);
			return new ResponseEntity<Map<String,Object>>(responseMap, HttpStatus.OK);
		}
		catch(ServiceException e){
			logger.error("Error in getUserNode Params -- Userid= "+getUserId(),e);
			responseMap.put(messagePropertyResolver.getMessage("map.key.error", new Object[]{}, Locale.US),
					messagePropertyResolver.getMessage("notes.error.allnotes", new Object[]{}, Locale.US) );
			return new ResponseEntity<Map<String,Object>>(responseMap, HttpStatus.EXPECTATION_FAILED);
		}
	}
	/**
	 * Get Note By User
	 * @param id
	 * @return
	 */
	@GetMapping("/notes/{id}")
	public @ResponseBody ResponseEntity<?> getUserNote(@PathVariable Long id) {
		Map<String,Object> responseMap = new HashMap<String,Object>();
		try{
			NoteModel note = noteService.findById(getUserId(), id);
			if (null == note.getId()) {
				responseMap.put(messagePropertyResolver.getMessage("map.key.message", new Object[]{}, Locale.US), 
						messagePropertyResolver.getMessage("notes.noidfound", new Object[]{id}, Locale.US));
				return new ResponseEntity<Map<String,Object>>(responseMap, HttpStatus.NOT_FOUND);			
			}
			responseMap.put(messagePropertyResolver.getMessage("map.key.message", new Object[]{}, Locale.US)
					, messagePropertyResolver.getMessage("notes.found", new Object[]{}, Locale.US));
			responseMap.put(messagePropertyResolver.getMessage("map.key.data", new Object[]{}, Locale.US)
					, note);
			return new ResponseEntity<Map<String,Object>>(responseMap, HttpStatus.OK);
		}
		catch(ServiceException e){
			logger.error("Error in getUserNode Params -- noteId= "+id+" && Userid= "+getUserId(),e);
			responseMap.put(messagePropertyResolver.getMessage("map.key.error", new Object[]{}, Locale.US)
					, messagePropertyResolver.getMessage("notes.error.note", new Object[]{id}, Locale.US));
			return new ResponseEntity<Map<String,Object>>(responseMap, HttpStatus.EXPECTATION_FAILED);
		}
	}
	/**
	 * Get User After Auth
	 * @return
	 */
	private Long getUserId(){
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		UserInfo userInfo = (UserInfo)auth.getPrincipal();
		return userInfo.getId();
	}

}
