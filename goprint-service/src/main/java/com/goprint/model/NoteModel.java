package com.goprint.model;

import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonView;
/**
 * Model for JSON and Service layer
 * @author vinod Bhatkotti
 *
 */
public class NoteModel {
	@JsonView(NoteModelView.ViewWithId.class)
	Long id;
	
	@NotEmpty
	@Length(max = 50)
	@JsonView(NoteModelView.ViewWithoutId.class)
	String title;
	
	@NotEmpty
	@Length(max = 1000)
	@JsonView(NoteModelView.ViewWithoutId.class)
	String note;
	
	String responseMsg;
	@JsonIgnore
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
