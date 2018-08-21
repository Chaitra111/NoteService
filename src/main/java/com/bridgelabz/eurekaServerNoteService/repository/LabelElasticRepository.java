package com.bridgelabz.eurekaServerNoteService.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.elasticsearch.repository.ElasticsearchRepository;
import com.bridgelabz.eurekaServerNoteService.model.Label;

/**
 * @author Chaitra Ankolekar
 * Purpose :LabelElasticRepository 
 */
public interface LabelElasticRepository extends ElasticsearchRepository<Label, String>{

	Optional<Label> findByLabelNameAndUserId(String labelName, long userId);

	List<Label> findAllByUserId(String LabelId);

	List<Label> findAllByLabelId(String labelId);
}
