package com.bridgelabz.eurekaServerNoteService.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.bridgelabz.eurekaServerNoteService.model.Label;

/**
 * @author Chaitra Ankolekar
 * Purpose :LabelRepository which extends MongoRepository which already have built-in all CRUD operations
 */
@Repository
public interface LabelRepository extends MongoRepository<Label, String> {

	Optional<Label> findByLabelNameAndUserId(String labelName, long userId);

	Optional<Label> findByLabelName(String labelName);

	List<Label> findAllByLabelId(String id);

}
