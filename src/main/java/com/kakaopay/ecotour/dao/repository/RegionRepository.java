package com.kakaopay.ecotour.dao.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.kakaopay.ecotour.dao.entity.Region;

public interface RegionRepository extends JpaRepository<Region, Long> {
	Region findByRegionName(String regionName);
	List<Region> findByRegionNameContaining(String regionName);

}
