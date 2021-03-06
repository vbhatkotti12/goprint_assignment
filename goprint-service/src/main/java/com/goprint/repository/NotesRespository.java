package com.goprint.repository;

import java.util.List;


import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.transaction.annotation.Transactional;

import com.goprint.entity.Note;
/**
 * Respository for CRUD operations on Note
 * @author vinod bhatkotti
 *
 */
public interface NotesRespository extends JpaRepository<Note, Long> {
	/**
	 * Deletes note by id
	 */
	@Modifying
	@Query("delete from Note where id = ?1")
	void delete(Long entityId);
	/**
	 * Deletes all notes of User
	 * @param entityId
	 */
	@Modifying
	@Query("delete from Note n where n.userId.id = ?1")
	@Transactional
	void deleteByUserId(Long entityId);
	/**
	 * Gets all notes of User
	 * @param entityId
	 * @return
	 */
	@Query("select n from Note n where n.userId.id = ?1")
	List<Note> getByUserId(Long entityId);
	
	/**
	 * Gets all notes of User
	 * @param entityId
	 * @return
	 */
	@Query("select n from Note n where n.userId.id = ?1 and n.id = ?2")
	Note findNote(Long entityId,Long noteId);
}
