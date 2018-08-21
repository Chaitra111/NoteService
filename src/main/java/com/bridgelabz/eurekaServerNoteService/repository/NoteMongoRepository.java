package com.bridgelabz.eurekaServerNoteService.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.eurekaServerNoteService.model.Label;
import com.bridgelabz.eurekaServerNoteService.model.NoteModel;

/**
 * @author Chaitra Ankolekar
 * Purpose :Repository which extends MongoRepository which already have built-in all CRUD operations
 */
@Repository
public interface NoteMongoRepository extends MongoRepository<NoteModel, String> {
	
	public List<NoteModel> findByemailId(String id);

	public Optional<NoteModel> findById(String id);

	public void deleteById(String id);

	public List<NoteModel> findNoteById(String id);

	//public void save(Label label);

	public List<NoteModel> findNoteByemailId(String emailId);
}