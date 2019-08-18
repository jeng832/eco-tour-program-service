package com.kakaopay.ecotour.model.http;

import java.util.ArrayList;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonProperty;

public class GetProgramByRegionNameResponseBody {
	@JsonProperty("region")
	private Long region;
	@JsonProperty("programs")
	private List<Program> programs;
	
	public GetProgramByRegionNameResponseBody(Long region) {
		this.region = region;
		this.programs = new ArrayList<> ();
	}
	
	public void addProgram(String name, String theme) {
		programs.add(new Program(name, theme));
	}


	class Program {
		public Program(String n, String th) {
			name = n;
			theme =  th;
		}
		@JsonProperty("prgm_name")
		String name;
		@JsonProperty("theme")
		String theme;
	}
}
