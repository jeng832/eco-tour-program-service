package com.kakaopay.ecotour.model;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetCountProgramFromIntroResponseBody {
	@JsonProperty("keyword")
	private String keyword;
	@JsonProperty("programs")
	private List<Program> programs;
	
	public GetCountProgramFromIntroResponseBody(String keyword) {
		this.keyword = keyword;
		this.programs = new ArrayList<> ();
	}
	
	public void addProgram(String region, int count) {
		programs.add(new Program(region, count));
	}
	
	class Program {
		public Program(String region, int count) {
			this.region = region;
			this.count = count;
		}
		@JsonProperty("region")
		String region;
		@JsonProperty("count")
		int count;
	}
	
	
}
