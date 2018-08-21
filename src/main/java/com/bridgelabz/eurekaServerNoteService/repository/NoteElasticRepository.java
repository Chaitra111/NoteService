package com.bridgelabz.eurekaServerNoteService.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;

import com.bridgelabz.eurekaServerNoteService.model.Label;
import com.bridgelabz.eurekaServerNoteService.model.NoteModel;

public interface NoteElasticRepository extends ElasticsearchRepository<NoteModel, String> {

	public List<NoteModel> findByemailId(String id);

	public Optional<NoteModel> findById(String id);

	public void deleteById(String id);

	public List<NoteModel> findNoteByemailId(String emailId);

	public List<NoteModel> findByNoteId(String noteId);
	
	//public void save(NoteModel note);
	
	//public void save(Label label);
}
