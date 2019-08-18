package com.kakaopay.ecotour.model.http;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetProgramRecommendationResponseBody {

	@JsonProperty("program")
	private String program;
	
	public GetProgramRecommendationResponseBody(String progCode) {
		this.program = progCode;
	}
	public GetProgramRecommendationResponseBody() {
		
	}

}
