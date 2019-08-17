package com.kakaopay.ecotour.model;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetCountRequestBody {
	@JsonProperty("keyword")
	@NotNull
	@NotEmpty
	@NotBlank
	private String keyword;

	public String getKeyword() {
		return keyword;
	}

	public void setKeyword(String keyword) {
		this.keyword = keyword;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("GetCountRequestBody [keyword=");
		builder.append(keyword);
		builder.append("]");
		return builder.toString();
	}
}
