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
	public NoteModel addNote(Long userId,String title,String note) throws ServiceException;
	public NoteModel updateNote(Long id,String title,String note) throws ServiceException;
	public boolean removeAll(Long userId) throws ServiceException;
	public boolean remove(Long noteId) throws ServiceException;
	
}
