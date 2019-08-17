package com.kakaopay.ecotour.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kakaopay.ecotour.dao.entity.EcoTourProgram;

public interface EcoTourProgramRepository extends JpaRepository<EcoTourProgram, Long> {
	List<EcoTourProgram> findByRegionCode(Long code);
	List<EcoTourProgram> findByIntroductionContaining(String keyword);
	List<EcoTourProgram> findByDescriptionContaining(String keyword);
}
