package com.kakaopay.ecotour.dao.entity;

import java.sql.Timestamp;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;


@Entity
public class EcoTourProgram {
	@Id
	@GeneratedValue(strategy=GenerationType.IDENTITY)
	@Column(name="id", nullable = false)
	private Long id;
	@Column(name="name", nullable = false)
	private String name;
	@Column(name="theme", nullable = false)
	private String theme;
	
	@ManyToOne(targetEntity = Region.class, fetch = FetchType.EAGER)
	@JoinColumn(name="code")
	private Region region;

	@Column(name="introduction")
	private String introduction;
	@Column(name="description", length = 500)
	private String description;
	
	@Column(name="prog_code", nullable = false)
	private String progCode;
	@Column(name="created_time")
	@CreationTimestamp
	private Timestamp createdTime;
	@Column(name="modified_time")
	@UpdateTimestamp
	private Timestamp modifiedTime;
	
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
	public Region getRegion() {
		return region;
	}
	public void setRegion(Region region) {		
//		List<EcoTourProgram> progs = region.getPrograms();
//		for(EcoTourProgram prog : progs) {
//			if(prog.getRegion().getRegionName().equals(region.getRegionName())) {
//				this.region = prog.getRegion();
//				return;
//			}
//		}
		this.region = region;
//		region.getPrograms().add(this);
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
	public String getProgCode() {
		return progCode;
	}
	public void setProgCode(String progCode) {
		this.progCode = progCode;
	}
	public Timestamp getCreatedTime() {
		return createdTime;
	}
	public void setCreatedTime(Timestamp createdTime) {
		this.createdTime = createdTime;
	}
	public Timestamp getModifiedTime() {
		return modifiedTime;
	}
	public void setModifiedTime(Timestamp modifiedTime) {
		this.modifiedTime = modifiedTime;
	}
	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("EcoTourProgram [id=");
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
		builder.append(", progCode=");
		builder.append(progCode);
		builder.append(", createdTime=");
		builder.append(createdTime);
		builder.append(", modifiedTime=");
		builder.append(modifiedTime);
		builder.append("]");
		return builder.toString();
	}
}
