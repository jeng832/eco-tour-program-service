package com.kakaopay.ecotour.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetProgramRecommendationRequestBody {
	@JsonProperty("region")
	@NotNull
	@NotEmpty
	@NotBlank
	private String region;
	
	@JsonProperty("keyword")
	@NotNull
	@NotEmpty
	@NotBlank
	private String keyword;
	public String getRegion() {
		return region;
	}
	public void setRegion(String region) {
		this.region = region;
	}
	public String getKeyword() {
		return keyword;
	}
	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GetProgramRecommendationRequestBody [region=");
		builder.append(region);
		builder.append(", keyword=");
		builder.append(keyword);
		builder.append("]");
		return builder.toString();
	}

}
