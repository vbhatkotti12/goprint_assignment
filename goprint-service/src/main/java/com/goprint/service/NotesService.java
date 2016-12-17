package com.goprint.service;

import java.util.List;

import com.goprint.exception.ServiceException;
import com.goprint.model.NoteModel;
/**
 * Service Interface for Note Operations
 * @author vinod bhatkotti
 * 
 */
public interface NotesService {
	public NoteModel findById(Long userId,Long noteId)  throws ServiceException;
	public List<NoteModel> findByUserId(Long id) throws ServiceException;
	
}
