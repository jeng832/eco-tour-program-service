package com.kakaopay.ecotour.model.recomm;

public class RecommendSourceData {
	private Long id;
	private String theme;
	private String introduction;
	private String description;
	private String region;
	
	public RecommendSourceData(Long id, String theme, String introduction, String description, String region) {
		this.id = id;
		this.theme = theme;
		this.introduction = introduction;
		this.description = description;
		this.region = region;
	}
	public RecommendSourceData() {	}
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
	public String getTheme() {
		return theme;
	}
	public void setTheme(String theme) {
		this.theme = theme;
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
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	
	
}
