package com.goprint.testcases;


import java.util.List;

import org.junit.Assert;
import org.junit.FixMethodOrder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.MethodSorters;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.goprint.model.NoteModel;
import com.goprint.service.NotesService;

/**
 * Test Suite for Note Operations
 * @author Vinod Bhatkotti
 *
 */

@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(locations = { "file:src/main/resources/META-INF/spring-service.xml" })
@FixMethodOrder(MethodSorters.NAME_ASCENDING)
public class NoteServiceTest    {

	@Autowired
	NotesService notesService;
	
	private Long userId = 3L;
	
	@Test
	public void testAInsert(){	
        
		NoteModel noteModel = notesService.addNote(userId, "Peronal", "Assignment deliver by 19 dec");
		Assert.assertTrue(noteModel.getId() != null);
	}

	@Test
	public void testCGetNotesByUserId(){
		
		List<NoteModel> notes = notesService.findByUserId(userId);		
		Assert.assertTrue(notes != null && notes.size() > 0);
	}
	@Test
	public void testDGetNoteById(){
		NoteModel noteModel = notesService.addNote(userId, "PeronalGet", "Assignment deliver by 19 dec");
		NoteModel note = notesService.findById(userId,noteModel.getId());
		Assert.assertTrue(note != null && note.getId() != null);
	}
	
	@Test
	public void testEUpdateNote(){
		
		NoteModel noteModel = notesService.addNote(userId, "PeronalAdd", "Assignment deliver by 19 dec");
		noteModel = notesService.updateNote(noteModel.getId(), "ProfessionalUpdate", "Assignment Delivered");
		Assert.assertTrue(noteModel.getId()!=null);
	}
	
	@Test
	public void testFremoveNote(){
		NoteModel noteModel = notesService.addNote(userId, "PeronalRemove11", "Assignment deliver by 19 dec");
		Assert.assertTrue(notesService.remove(noteModel.getId()));
	}
	
	@Test
	public void testGremoveAllNotes(){
		boolean isDelete = notesService.removeAll(userId);
		Assert.assertTrue(isDelete);
	}
}
