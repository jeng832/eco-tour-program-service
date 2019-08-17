package com.kakaopay.ecotour.dao.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;


@Entity
public class Region {
	@Id
	@GeneratedValue(strategy=GenerationType.TABLE)
	@Column(name = "code")
	private Long code;
	@Column(name = "region_name")
	private String regionName;
	
//	@OneToMany(mappedBy = "region", fetch = FetchType.EAGER)
//	private List<EcoTourProgram> programs;
	
	public Region() {
//		this.programs = new ArrayList<>();
	}
	public Region(String region) {
		this.regionName = region;
//		this.programs = new ArrayList<>();
	}

	public Long getCode() {
		return code;
	}
	public void setCode(Long code) {
		this.code = code;
	}
	public String getRegionName() {
		return regionName;
	}
	public void setRegionName(String region) {
		this.regionName = region;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("Region [code=");
		builder.append(code);
		builder.append(", regionName=");
		builder.append(regionName);
		builder.append("]");
		return builder.toString();
	}

//	public List<EcoTourProgram> getPrograms() {
//		return programs;
//	}
//
//	public void setPrograms(List<EcoTourProgram> programs) {
//		this.programs = programs;
//	}
}
