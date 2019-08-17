package com.kakaopay.ecotour.model;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetCountKeywordFromDescResponseBody {
	@JsonProperty("keyword")
	private String keyword;
	@JsonProperty("count")
	private int count;
	
	public GetCountKeywordFromDescResponseBody(String keyword, int count) {
		this.keyword = keyword;
		this.count = count;
	}
}
