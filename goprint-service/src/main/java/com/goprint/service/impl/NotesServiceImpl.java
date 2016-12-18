package com.goprint.service.impl;

import java.util.ArrayList;
import java.util.Date;

import java.util.List;

import javax.annotation.Resource;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.goprint.entity.Note;
import com.goprint.entity.UserDetail;
import com.goprint.exception.ServiceException;
import com.goprint.model.NoteModel;
import com.goprint.repository.NotesRespository;
import com.goprint.service.NotesService;
import com.goprint.service.UserDetailService;
import com.goprint.service.helper.NoteServiceHelper;

/**
 * Service Implementation for Note Operations
 * @author vinod bhatkotti
 * 
 */
@Service
public class NotesServiceImpl implements NotesService {

	private static final Logger logger = Logger.getLogger(NotesServiceImpl.class);
	@Autowired
	private UserDetailService userDetailService;

	@Resource
	private NotesRespository notesRespository;
	
	@Autowired
	private NoteServiceHelper notesServiceHelper;

	/**
	 * Finds the Note By Id
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	@Transactional
	public NoteModel findById(Long userId,Long noteId) throws ServiceException {
		try{
			logger.debug("Executes Find By Id -- Params:-" +noteId);
			Note note =  notesRespository.findNote(userId,noteId);
			logger.debug("Returns Note Object");
			return notesServiceHelper.convertNoteToNoteModel(note);
		}
		catch(DataAccessException e){
			logger.error("Error in findById",e);
			throw new ServiceException(e);
		}

	}
	/**
	 * Find the Notes of the User
	 * @param id
	 * @return
	 * @throws ServiceException
	 */
	@Transactional
	public List<NoteModel> findByUserId(Long id) throws ServiceException {		
		List<NoteModel> noteModels = new ArrayList<NoteModel>();
		try{
			logger.debug("Executes findByUserId --- Params:-" +id);
			List<Note> notes = notesRespository.getByUserId(id);			
			for(Note note : notes){				
				noteModels.add(notesServiceHelper.convertNoteToNoteModel(note));
			}
			return noteModels;
		}
		catch(DataAccessException e){
			logger.error("Error in findByUserId",e);
			throw new ServiceException(e);
		}
	}
	/**
	 * Add the Note of User
	 * @param userId
	 * @param title
	 * @param note
	 * @return
	 * @throws ServiceException
	 */
	@Transactional
	public NoteModel addNote(Long userId,String title,String note) throws ServiceException {
		try{
			logger.debug("Executes addNote(userId,title,note) --- Params:-" +userId+", "+title+ ", "+note);
			UserDetail userDetail =  new UserDetail();	
			userDetail.setId(userId);
			Note noteObj = new Note();
			notesServiceHelper.setInfo(title, note,noteObj);
			noteObj.setUserId(userDetail);
			noteObj.setCreateTime(new Date());
			noteObj = notesRespository.save(noteObj);
			return notesServiceHelper.convertNoteToNoteModel(noteObj);
		}
		catch(DataAccessException e){
			logger.error("Error in addNote",e);
			throw new ServiceException(e);
		}
	}
	/**
	 * Update Note of User By NoteId
	 * @param id
	 * @param title
	 * @param note
	 * @return
	 * @throws ServiceException
	 */
	@Transactional
	public NoteModel updateNote(Long id,String title,String note) throws ServiceException {
		try{
			Note noteDb =  notesRespository.findOne(id);		 
			notesServiceHelper.setInfo(title, note,noteDb);
			noteDb.setUpdateTime(new Date());
			noteDb = notesRespository.save(noteDb);
			return notesServiceHelper.convertNoteToNoteModel(noteDb);
		}
		catch(DataAccessException e){
			logger.error("Error in updateNote",e);
			throw new ServiceException(e);
		}
	}
	/**
	 * Remove All Notes of the User
	 * @param userId
	 * @return
	 * @throws ServiceException
	 */
	@Transactional
	public boolean removeAll(Long userId) throws ServiceException {		
		logger.debug("Executes RemoveAll By UserId :-"+userId);
		try{
			notesRespository.deleteByUserId(userId);
			return true;
		}
		catch(DataAccessException e){
			logger.error("Error in removeAll",e);
			throw new ServiceException(e);
		}

	}
	/**
	 * Remove Note by NoteId
	 * @param noteId
	 * @return
	 * @throws ServiceException
	 */
	@Transactional
	public boolean remove(Long noteId) throws ServiceException{		
		try{
			notesRespository.delete(noteId);
			return true;
		}
		catch(DataAccessException e){
			logger.error("Error in remove",e);
			throw new ServiceException(e);
		}

	}
	
}
