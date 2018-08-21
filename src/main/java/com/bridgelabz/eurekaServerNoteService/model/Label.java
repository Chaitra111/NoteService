package com.bridgelabz.eurekaServerNoteService.model;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;

import io.swagger.annotations.ApiModelProperty;

/**
 * @author Chaitra Ankolekar
 * Purpose :Label pojo class
 */
//@Document(collection="label")
@Document(indexName="label",type="Label")
public class Label {
	
	@Id
	@ApiModelProperty(hidden=true)
	private String labelId;
	
	private String labelName;
	
	@ApiModelProperty(hidden=true)
	private String userId;
	
	public String getLabelId() {
		return labelId;
	}

	public void setLabelId(String labelId) {
		this.labelId = labelId;
	}

	public String getLabelName() {
		return labelName;
	}

	public void setLabelName(String labelName) {
		this.labelName = labelName;
	}

	public String getUserId() {
		return userId;
	}

	public void setUserId(String userId) {
		this.userId = userId;
	}	
}

