package com.automyz.model;

import java.util.Map;

public class Page {
	private long id;
	private String name;
	private String description;
	private String type;
	private String subType;
	private Map<String, Object> properties;

	public long getId() {
		return id;
	}

	public Page setId(long id) {
		this.id = id;
		return this;
	}

	public String getName() {
		return this.name;
	}

	public Page setName(String argName) {
		this.name = argName;
		return this;
	}

	public String getDescription() {
		return this.description;
	}

	public Page setDescription(String argDescription) {
		this.description = argDescription;
		return this;
	}

	public String getType() {
		return this.type;
	}

	public Page setType(String argType) {
		this.type = argType;
		return this;
	}

	public String getSubType() {
		return this.subType;
	}

	public Page setSubType(String argSubType) {
		this.subType = argSubType;
		return this;
	}

	public Map<String, Object> getProperties() {
		return this.properties;
	}

	public Page setProperties(Map<String, Object> argProperties) {
		this.properties = argProperties;
		return this;
	}

}
