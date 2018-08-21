package com.bridgelabz.eurekaServerNoteService.model;

import java.io.Serializable;
import java.util.List;

/**
 * @author Chaitra Ankolekar
 * Purpose :Description pojo class
 */
public class Description implements Serializable {

	private static final long serialVersionUID = 1L;

	private List<String> simpleDescription;
	private List<Link> linkDescription;

	public Description() {
	}

	public List<String> getSimpleDescription() {
		return simpleDescription;
	}

	public void setSimpleDescription(List<String> simpleDescription) {
		this.simpleDescription = simpleDescription;
	}

	public List<Link> getLinkDescription() {
		return linkDescription;
	}

	public void setLinkDescription(List<Link> linkDescription) {
		this.linkDescription = linkDescription;
	}

	@Override
	public String toString() {
		return "Description [description=" + simpleDescription + ", linkDescription=" + linkDescription + "]";
	}
}
