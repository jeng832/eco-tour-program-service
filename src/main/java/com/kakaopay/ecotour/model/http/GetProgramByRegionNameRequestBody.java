package com.kakaopay.ecotour.model.http;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetProgramByRegionNameRequestBody {
	@JsonProperty("region")
	@NotNull
	@NotEmpty
	@NotBlank
	private String region;

	public String getRegion() {
		return region;
	}

	public void setRegion(String region) {
		this.region = region;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GetProgramByRegionRequestBody [region=");
		builder.append(region);
		builder.append("]");
		return builder.toString();
	}
}
