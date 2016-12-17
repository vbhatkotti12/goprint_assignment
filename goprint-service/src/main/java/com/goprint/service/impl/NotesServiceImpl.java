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
			return convertNoteToNoteModel(note);
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
				noteModels.add(convertNoteToNoteModel(note));
			}
			return noteModels;
		}
		catch(DataAccessException e){
			logger.error("Error in findByUserId",e);
			throw new ServiceException(e);
		}
	}
	
	private NoteModel convertNoteToNoteModel(Note note){
		NoteModel noteModel = new NoteModel();
		if(note != null){
			noteModel.setId(note.getId());
			noteModel.setTitle(note.getTitle());
			noteModel.setNote(note.getNote());
		}
		return noteModel;
	}
}
