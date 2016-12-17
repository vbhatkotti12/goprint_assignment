package com.goprint.model;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class NoteModel {

	Long id;
	
	@NotEmpty
	@Length(max = 50)
	String title;
	
	@NotEmpty
	@Length(max = 1000)
	String note;
	
	String responseMsg;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public String getNote() {
		return note;
	}
	public void setNote(String note) {
		this.note = note;
	}
	@Override
	public String toString(){
		return "id = "+id +"& title ="+title+"& Note="+note;
	}


}
