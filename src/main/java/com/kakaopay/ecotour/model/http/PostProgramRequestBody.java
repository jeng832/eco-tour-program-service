package com.kakaopay.ecotour.model.http;

import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class PostProgramRequestBody {
	@JsonProperty("name")
	@NotNull
	private String name;
	@JsonProperty("theme")
	@NotNull
	private String theme;
	@JsonProperty("region")
	@NotNull
	private String region;
	@JsonProperty("introduction")
	@NotNull
	private String introduction;
	@JsonProperty("description")
	@NotNull
	private String description;
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
	}
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getIntroduction() {
		return introduction;
	}
	public void setIntroduction(String introduction) {
		this.introduction = introduction;
	}
	public String getDescription() {
		return description;
	}
	public void setDescription(String description) {
		this.description = description;
	}
	@Override
	public String toString() {
		return "PostProgramRequestBody [name=" + name + ", theme=" + theme + ", region=" + region + ", introduction="
				+ introduction + ", description=" + description + "]";
	}
}
