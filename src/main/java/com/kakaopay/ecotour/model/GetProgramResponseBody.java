package com.kakaopay.ecotour.model;



public class GetProgramResponseBody {
	private Long id;
	private String name;
	private String theme;
	private String region;
	private String introduction;
	private String description;
	
	public Long getId() {
		return id;
	}
	public void setId(Long id) {
		this.id = id;
	}
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
		StringBuilder builder = new StringBuilder();
		builder.append("getProgramRequestBody [id=");
		builder.append(id);
		builder.append(", name=");
		builder.append(name);
		builder.append(", theme=");
		builder.append(theme);
		builder.append(", region=");
		builder.append(region);
		builder.append(", introduction=");
		builder.append(introduction);
		builder.append(", description=");
		builder.append(description);
		builder.append("]");
		return builder.toString();
	}
	
	
}
