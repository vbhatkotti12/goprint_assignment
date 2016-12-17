package com.goprint.entity;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
/**
 *  User Note Entity
 * @author vinod Bhatkotti
 *
 */
@Entity
@Table(name="user_note")
public class Note extends BaseEntity implements Serializable {
	/**
	 * 
	 */
	private static final long serialVersionUID = -6676668761932546797L;

	/**
	 * 
	 */
	
	
	@Column(name="title", nullable=false, length=50)
	private String title; 
	
	@Column(name="note", nullable=false, length=1000)
	private String note;
	
	@ManyToOne
	@JoinColumn(name="user_id")
	private UserDetail userId;
	
	public UserDetail getUserId() {
		return userId;
	}
	public void setUserId(UserDetail userId) {
		this.userId = userId;
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
	
}
