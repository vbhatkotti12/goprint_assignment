package com.goprint.entity;

import java.io.Serializable;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.OneToMany;
import javax.persistence.Table;

/**
 * User Entity
 * @author vinod Bhatkotti
 *
 */
@Entity
@Table(name="user_detail")
public class UserDetail extends BaseEntity implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -6676668761932546797L;

	/**
	 * 
	 */
	
	
	@Column(name="email", nullable=false,unique=true, length=100)
	private String email;
	
	@Column(name="password",nullable=false)
	private String password;
	
	@OneToMany(mappedBy = "userId",cascade=CascadeType.ALL)
	private List<Note> notes;
	
	public String getPassword() {
		return password;
	}


	public void setPassword(String password) {
		this.password = password;
	}	
	
	public List<Note> getNotes() {
		return notes;
	}


	public void setNotes(List<Note> notes) {
		this.notes = notes;
	}	


	public String getEmail() {
		return email;
	}


	public void setEmail(String email) {
		this.email = email;
	}



}
