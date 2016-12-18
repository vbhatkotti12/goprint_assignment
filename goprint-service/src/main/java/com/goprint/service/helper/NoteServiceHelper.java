package com.goprint.service.helper;

import org.springframework.stereotype.Component;

import com.goprint.entity.Note;
import com.goprint.model.NoteModel;
/**
 * Helper for NoteService
 * @author vinod Bhatkotti
 *
 */
@Component
public class NoteServiceHelper {
	/**
	 * Set and Return Note 
	 * @param title
	 * @param note
	 * @param noteObj
	 */
	public void setInfo(String title,String note,Note noteObj){
		noteObj.setTitle(title);
		noteObj.setNote(note);
	}
	/**
	 * Convert Note Entity to Model
	 * @param note
	 * @return
	 */
	public NoteModel convertNoteToNoteModel(Note note){
		NoteModel noteModel = new NoteModel();
		if(note != null){
			noteModel.setId(note.getId());
			noteModel.setTitle(note.getTitle());
			noteModel.setNote(note.getNote());
		}
		return noteModel;
	}



}
