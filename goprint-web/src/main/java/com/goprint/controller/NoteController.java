package com.goprint.controller;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import javax.validation.Valid;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.fasterxml.jackson.annotation.JsonView;
import com.goprint.authentication.UserInfo;
import com.goprint.exception.ServiceException;
import com.goprint.helper.ControllerHelper;
import com.goprint.model.NoteModel;
import com.goprint.model.NoteModelView;
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
	@Autowired
	ControllerHelper controllerHelper;
	/**
	 * Gets All user Notes
	 * @return
	 */
	@GetMapping("/notes")
	
	public @JsonView(NoteModelView.ViewWithId.class) @ResponseBody ResponseEntity<?> getUserNotes() {
		Map<String,Object> responseMap = new HashMap<String,Object>();
		UserInfo userInfo = controllerHelper.getUserDetails();
		Long userId = controllerHelper.getUserId();
		responseMap.put(messagePropertyResolver.getMessage("map.key.user", new Object[]{}, Locale.US), userInfo.getUserName());
		try{
			List<NoteModel> notes = noteService.findByUserId(userId);
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
			logger.error("Error in getUserNode Params -- Userid= "+userId,e);
			responseMap.put(messagePropertyResolver.getMessage("map.key.exception", new Object[]{}, Locale.US),
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
	public @JsonView(NoteModelView.ViewWithId.class) @ResponseBody ResponseEntity<?> getUserNote(@PathVariable Long id) {
		Map<String,Object> responseMap = new HashMap<String,Object>();
		UserInfo userInfo = controllerHelper.getUserDetails();
		Long userId = controllerHelper.getUserId();
		responseMap.put(messagePropertyResolver.getMessage("map.key.user", new Object[]{}, Locale.US), userInfo.getUserName());		
		try{
			NoteModel note = noteService.findById(userId, id);
			if (null == note.getId()) {
				responseMap.put(messagePropertyResolver.getMessage("map.key.message", new Object[]{}, Locale.US), 
						messagePropertyResolver.getMessage("notes.noidfound", new Object[]{}, Locale.US));
				responseMap.put(messagePropertyResolver.getMessage("map.key.data1", new Object[]{}, Locale.US),id);
				return new ResponseEntity<Map<String,Object>>(responseMap, HttpStatus.NOT_FOUND);			
			}
			responseMap.put(messagePropertyResolver.getMessage("map.key.message", new Object[]{}, Locale.US)
					, messagePropertyResolver.getMessage("notes.idfound", new Object[]{}, Locale.US));
			responseMap.put(messagePropertyResolver.getMessage("map.key.data", new Object[]{}, Locale.US)
					, note);
			return new ResponseEntity<Map<String,Object>>(responseMap, HttpStatus.OK);
		}
		catch(ServiceException e){
			logger.error("Error in getUserNode Params -- noteId= "+id+" && Userid= "+userId,e);
			responseMap.put(messagePropertyResolver.getMessage("map.key.exception", new Object[]{}, Locale.US)
					, messagePropertyResolver.getMessage("notes.error.note", new Object[]{}, Locale.US));
			responseMap.put(messagePropertyResolver.getMessage("map.key.data1", new Object[]{}, Locale.US),id);
			return new ResponseEntity<Map<String,Object>>(responseMap, HttpStatus.EXPECTATION_FAILED);
		}
	}
	/**
	 * Delete Note
	 * @param id
	 * @return
	 */
	@DeleteMapping("/notes/{id}")
	public @ResponseBody ResponseEntity<?> deleteNote(@PathVariable Long id) {
		Map<String,Object> responseMap = new HashMap<String,Object>();
		UserInfo userInfo = controllerHelper.getUserDetails();
		Long userId = controllerHelper.getUserId();
		responseMap.put(messagePropertyResolver.getMessage("map.key.user", new Object[]{}, Locale.US), userInfo.getUserName());
		responseMap.put(messagePropertyResolver.getMessage("map.key.data1", new Object[]{}, Locale.US), id);
		try{
			NoteModel note = noteService.findById(userId, id);
			if (null == note.getId()) {
				responseMap.put(messagePropertyResolver.getMessage("map.key.error", new Object[]{}, Locale.US), 
						messagePropertyResolver.getMessage("notes.nodelete", new Object[]{}, Locale.US) 
						+ " " +messagePropertyResolver.getMessage("notes.noidfound", new Object[]{}, Locale.US));
				return new ResponseEntity<Map<String,Object>>(responseMap, HttpStatus.NOT_FOUND);
			}			
			boolean isDelete = noteService.remove(id);
			responseMap.put(messagePropertyResolver.getMessage("map.key.message", new Object[]{}, Locale.US),messagePropertyResolver.getMessage("notes.deleted", new Object[]{userInfo.getUserName()}, Locale.US) );
			
			return new ResponseEntity<Map<String,Object>>(responseMap, HttpStatus.OK);
		}
		catch(ServiceException e){
			logger.error("Error in deleteNote Params -- noteId= "+id+" && Userid= "+userId,e);
			responseMap.put(messagePropertyResolver.getMessage("map.key.exception", new Object[]{}, Locale.US)
					, messagePropertyResolver.getMessage("notes.error.delete", new Object[]{}, Locale.US));
			return new ResponseEntity<Map<String,Object>>(responseMap, HttpStatus.EXPECTATION_FAILED);
		}

	}
	/**
	 * Deletes All User Notes
	 * @return
	 */
	@DeleteMapping("/notes")
	public ResponseEntity<?> deleteAllNote() {
		Map<String,Object> responseMap = new HashMap<String,Object>();
		UserInfo userInfo = controllerHelper.getUserDetails();
		Long userId = userInfo.getId();
		responseMap.put(messagePropertyResolver.getMessage("map.key.user", new Object[]{}, Locale.US), userInfo.getUserName());
		try{
			List<NoteModel> notes = noteService.findByUserId(userId);
			if(notes.isEmpty()){
				responseMap.put(messagePropertyResolver.getMessage("map.key.error", new Object[]{}, Locale.US), 
						messagePropertyResolver.getMessage("notes.nofound", new Object[]{}, Locale.US));
				return new ResponseEntity<Map<String,Object>>(responseMap, HttpStatus.NOT_FOUND);
			}
			boolean isDelete = noteService.removeAll(userId);
			responseMap.put(messagePropertyResolver.getMessage("map.key.message", new Object[]{}, Locale.US)
					, messagePropertyResolver.getMessage("notes.alldeleted", new Object[]{}, Locale.US));
			return new ResponseEntity<Map<String,Object>>(responseMap, HttpStatus.OK);
		}
		catch(ServiceException e){
			logger.error("Error in deleteAllNote Params -- Userid= "+userId,e);
			responseMap.put(messagePropertyResolver.getMessage("map.key.exception", new Object[]{}, Locale.US)
					,messagePropertyResolver.getMessage("notes.error.alldelete", new Object[]{}, Locale.US) );
			return new ResponseEntity<Map<String,Object>>(responseMap, HttpStatus.EXPECTATION_FAILED);
		}

	}
	/**
	 * Add Note to User
	 * @param noteModel
	 * @param bindingResults
	 * @return
	 */
	@PostMapping("/notes")
	@JsonView(NoteModelView.ViewWithoutId.class)
	public ResponseEntity<?> addNote(@JsonView(NoteModelView.ViewWithoutId.class) @RequestBody @Valid NoteModel noteModel,BindingResult bindingResults) {
		Map<String,Object> responseMap = new HashMap<String,Object>();
		UserInfo userInfo = controllerHelper.getUserDetails();
		Long userId = controllerHelper.getUserId();
		responseMap.put(messagePropertyResolver.getMessage("map.key.user", new Object[]{}, Locale.US), userInfo.getUserName());
		try{
			if(bindingResults.getAllErrors().size() > 0){				
				return controllerHelper.handleValidationException(bindingResults);
			}
			noteModel = noteService.addNote(userId, noteModel.getTitle(), noteModel.getNote());
			if (null != noteModel.getId()) {
				responseMap.put(messagePropertyResolver.getMessage("map.key.message", new Object[]{}, Locale.US)
						,messagePropertyResolver.getMessage("notes.added", new Object[]{}, Locale.US) );
				responseMap.put(messagePropertyResolver.getMessage("map.key.data", new Object[]{}, Locale.US), noteModel);
				responseMap.put(messagePropertyResolver.getMessage("map.key.data1", new Object[]{}, Locale.US), noteModel.getId());
				return new ResponseEntity<Map<String,Object>>(responseMap, HttpStatus.OK);
			}
			responseMap.put(messagePropertyResolver.getMessage("map.key.error", new Object[]{}, Locale.US)
					,messagePropertyResolver.getMessage("notes.noadded", new Object[]{}, Locale.US) );
			responseMap.put(messagePropertyResolver.getMessage("map.key.data", new Object[]{}, Locale.US),noteModel);
			return new ResponseEntity<Map<String,Object>>(responseMap, HttpStatus.EXPECTATION_FAILED);
		}
		catch(ServiceException e){
			responseMap.put(messagePropertyResolver.getMessage("map.key.exception", new Object[]{}, Locale.US),
					messagePropertyResolver.getMessage("notes.error.add", new Object[]{}, Locale.US) );
			responseMap.put(messagePropertyResolver.getMessage("map.key.data", new Object[]{}, Locale.US),noteModel);
			logger.error("Error in addNote Params -- NoteModel= "+noteModel+" && Userid= "+userId,e);
			return new ResponseEntity<Map<String,Object>>(responseMap, HttpStatus.EXPECTATION_FAILED);
		}

	}
	/**
	 * Updates Note By User
	 * @param noteModel
	 * @param bindingResults
	 * @return
	 */
	@PutMapping("/notes/{id}")
	@JsonView(NoteModelView.ViewWithoutId.class)
	public ResponseEntity<?> updateNote(@PathVariable Long id,@JsonView(NoteModelView.ViewWithoutId.class) @Valid @RequestBody NoteModel noteModel,BindingResult bindingResults) {
		Map<String,Object> responseMap = new HashMap<String,Object>();
		UserInfo userInfo = controllerHelper.getUserDetails();
		Long userId = controllerHelper.getUserId();
		responseMap.put(messagePropertyResolver.getMessage("map.key.user", new Object[]{}, Locale.US), userInfo.getUserName());
		responseMap.put(messagePropertyResolver.getMessage("map.key.data1", new Object[]{}, Locale.US), id);
		try{
			if(bindingResults.getAllErrors().size() > 0){
				return controllerHelper.handleValidationException(bindingResults);
			}
			NoteModel note = noteService.findById(userId, id);
			if (null == note.getId()) {
				responseMap.put(messagePropertyResolver.getMessage("map.key.error", new Object[]{}, Locale.US), 
						messagePropertyResolver.getMessage("notes.noupdated", new Object[]{}, Locale.US) 
						+ " " + messagePropertyResolver.getMessage("notes.noidfound", new Object[]{}, Locale.US));
				return new ResponseEntity<Map<String,Object>>(responseMap, HttpStatus.NOT_FOUND);
			}
			noteModel = noteService.updateNote(id, noteModel.getTitle(), noteModel.getNote());
			responseMap.put(messagePropertyResolver.getMessage("map.key.message", new Object[]{}, Locale.US), 
					messagePropertyResolver.getMessage("notes.updated", new Object[]{}, Locale.US));
			responseMap.put(messagePropertyResolver.getMessage("map.key.data", new Object[]{}, Locale.US), noteModel);
			return new ResponseEntity<Map<String,Object>>(responseMap, HttpStatus.OK);
		}
		catch(ServiceException e){
			responseMap.put(messagePropertyResolver.getMessage("map.key.exception", new Object[]{}, Locale.US), 
					messagePropertyResolver.getMessage("notes.error.update", new Object[]{}, Locale.US));
			responseMap.put(messagePropertyResolver.getMessage("map.key.data", new Object[]{}, Locale.US),noteModel);
			logger.error("Error in updateNote Params -- NoteModel= "+noteModel+" && Userid= "+userId,e);
			return new ResponseEntity<	Map<String,Object>>(responseMap, HttpStatus.EXPECTATION_FAILED);
		}
	}
	
}
