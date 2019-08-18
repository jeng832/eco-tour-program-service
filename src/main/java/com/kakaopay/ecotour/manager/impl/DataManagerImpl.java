package com.kakaopay.ecotour.manager.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Component;

import com.kakaopay.ecotour.dao.entity.EcoTourProgram;
import com.kakaopay.ecotour.dao.entity.Region;
import com.kakaopay.ecotour.dao.entity.User;
import com.kakaopay.ecotour.dao.repository.EcoTourProgramRepository;
import com.kakaopay.ecotour.dao.repository.RegionRepository;
import com.kakaopay.ecotour.dao.repository.UserRepository;
import com.kakaopay.ecotour.exception.ProgramIsNotExistException;
import com.kakaopay.ecotour.exception.SignInFailedException;
import com.kakaopay.ecotour.manager.DataManager;
import com.kakaopay.ecotour.model.auth.SignInUserData;
import com.kakaopay.ecotour.model.http.GetCountProgramFromIntroResponseBody;
import com.kakaopay.ecotour.model.http.GetProgramByRegionNameResponseBody;
import com.kakaopay.ecotour.model.http.GetProgramResponseBody;
import com.kakaopay.ecotour.model.http.PostProgramRequestBody;
import com.kakaopay.ecotour.model.recomm.RecommendSourceData;
import com.kakaopay.ecotour.util.CodeUtil;

@Component
public class DataManagerImpl implements DataManager{

	private Logger logger = LoggerFactory.getLogger(DataManagerImpl.class);
	@Autowired
	private EcoTourProgramRepository ecoTourRepo;
	
	@Autowired
	private RegionRepository regionRepo;
	
	@Autowired
	private UserRepository userRepo;

	@Override
	public GetProgramResponseBody getProgram(Long progId) {
		EcoTourProgram program = ecoTourRepo.findById(progId)
				.orElseThrow(() -> new ProgramIsNotExistException("Program for id [" + progId + "] is Not Exist"));
		GetProgramResponseBody programRes = new GetProgramResponseBody();
		programRes.setId(program.getId());
		programRes.setName(program.getName());
		programRes.setRegion(program.getRegion().getRegionName());
		programRes.setTheme(program.getTheme());
		programRes.setIntroduction(program.getIntroduction());
		programRes.setDescription(program.getDescription());
		return programRes;
	}
	
	@Override
	public List<GetProgramResponseBody> getProgramByRegionCode(Long regionCode) {
		List<GetProgramResponseBody> response = new ArrayList<GetProgramResponseBody> ();
		List<EcoTourProgram> program = ecoTourRepo.findByRegionCode(regionCode);
	
		program.forEach(prog -> {
			GetProgramResponseBody programRes = new GetProgramResponseBody();
			programRes.setId(prog.getId());
			programRes.setName(prog.getName());
			programRes.setRegion(prog.getRegion().getRegionName());
			programRes.setTheme(prog.getTheme());
			programRes.setIntroduction(prog.getIntroduction());
			programRes.setDescription(prog.getDescription());
			response.add(programRes);
		});
		
		return response;
	}
	
	@Override
	public List<RecommendSourceData> savePrograms(List<PostProgramRequestBody> requestBodys) {
		List<Region> regionList = new ArrayList<Region>();
		
		Set<String> regionNameSet = new HashSet<> ();
		requestBodys.forEach(body -> regionNameSet.add(body.getRegion().trim()));
		regionNameSet.iterator().forEachRemaining(regionName -> regionList.add(new Region(regionName)));
		
		regionRepo.saveAll(regionList);

		List<EcoTourProgram> list = new ArrayList<>();
		for(PostProgramRequestBody body : requestBodys) {
			EcoTourProgram prog = new EcoTourProgram();
			prog.setName(body.getName().trim());
			prog.setTheme(body.getTheme().trim());
			
			Region reg = regionRepo.findByRegionName(body.getRegion().trim());

			prog.setRegion(reg);
			prog.setIntroduction(body.getIntroduction().trim());
			prog.setDescription(body.getDescription().trim());
			
			prog.setProgCode(CodeUtil.makeMd5Code(getMd5Seed(body)));
			list.add(prog);
		}
		
		List<RecommendSourceData> savedData = new ArrayList<RecommendSourceData> ();
		
		ecoTourRepo.saveAll(list).forEach(prog -> {
			RecommendSourceData data = new RecommendSourceData();
			data.setId(prog.getId());
			data.setTheme(prog.getTheme());
			data.setIntroduction(prog.getIntroduction());
			data.setDescription(prog.getDescription());
			data.setRegion(prog.getRegion().getRegionName());
			savedData.add(data);
		});
		return savedData;
	}
	
	private String getMd5Seed(PostProgramRequestBody body) {
		StringBuffer sb = new StringBuffer();
		sb.append(body.getName().trim());
		sb.append(body.getTheme().trim());
		sb.append(body.getRegion().trim());
		sb.append(body.getDescription().trim());
		sb.append(body.getIntroduction().trim());
		return sb.toString();
	}

	@Override
	public Long saveProgram(PostProgramRequestBody requestBody) {
		return saveProgram(null, requestBody);
	}
	

	@Override
	public Long saveProgram(Long progId, PostProgramRequestBody requestBody) {
		logger.info("saveProgram progId: " + progId + ", requestBody: " + requestBody);
		EcoTourProgram program = null;
		try {
			program = Optional.ofNullable(progId)
					.map(id -> ecoTourRepo.findById(id).get())
					.orElse(new EcoTourProgram());
			
		} catch (NoSuchElementException e) {
			throw new ProgramIsNotExistException("Program for id [" + progId + "] is Not Exist. It Cannot modify.");
		}
		
		program.setName(requestBody.getName().trim());
		program.setTheme(requestBody.getTheme().trim());
				
		Region reg = regionRepo.findByRegionName(requestBody.getRegion().trim());
		if(reg == null) {
			reg = new Region(requestBody.getRegion().trim());
			regionRepo.saveAndFlush(reg);
		}
		program.setRegion(reg);
		program.setIntroduction(requestBody.getIntroduction().trim());
		program.setDescription(requestBody.getDescription().trim());
		program.setProgCode(CodeUtil.makeMd5Code(getMd5Seed(requestBody)));
		
		program = ecoTourRepo.saveAndFlush(program);
	
		
		return program.getId();
		
	}
	
	@Override
	public GetCountProgramFromIntroResponseBody getCountProgramFromIntro(String keyword) {
		logger.info("getCountFromIntro keyword: " + keyword);
		Optional.ofNullable(keyword).orElseThrow(() -> new ProgramIsNotExistException("Program for keyword [" + keyword + "] is Not Exist"));
		GetCountProgramFromIntroResponseBody response = new GetCountProgramFromIntroResponseBody(keyword);
		List<EcoTourProgram> programs = ecoTourRepo.findByIntroductionContaining(keyword);
		Map<String, Integer> regionCount = new HashMap<>(); 
		
		for(EcoTourProgram program : programs) {
			String region = program.getRegion().getRegionName();
			regionCount.put(region, Optional.ofNullable(regionCount.get(region))
					.map(c -> c + 1)
					.orElse(1));	
		}
		
		regionCount.keySet().forEach(key -> response.addProgram(key, regionCount.get(key)));
		return response;
	}
	
	@Override
	public List<GetProgramByRegionNameResponseBody> getProgramByRegion(String regionName) {
		logger.info("GetProgramByRegionResponseBody regionName: " + regionName);
		if(regionName.trim() == "") {
			throw new ProgramIsNotExistException("region is empty");
		}

		List<Region> regions = regionRepo.findByRegionNameContaining(regionName);
		
		Set<EcoTourProgram> programs = new HashSet<>();
		regions.forEach(reg -> ecoTourRepo.findByRegionCode(reg.getCode()).forEach(prog -> programs.add(prog)) );
		
		List<GetProgramByRegionNameResponseBody> response = new ArrayList<> ();
		for(Region region : regions) {
			GetProgramByRegionNameResponseBody elem = new GetProgramByRegionNameResponseBody(region.getCode());
			for(EcoTourProgram prog : programs) {
				if(prog.getRegion().getCode() == region.getCode()) {
					elem.addProgram(prog.getName(), prog.getTheme());
				}
			}
			response.add(elem);
		}
			
		return response;
	}
	
	@Override
	public List<String> getDescriptions(String keyword) {
		logger.info("getDescriptions keyword: " + keyword);
		List<String> documents = new ArrayList<>();
		ecoTourRepo.findByDescriptionContaining(keyword).forEach(prog -> documents.add(prog.getDescription()));
		return documents;
	}

	@Override
	public void deleteProgram(Long progId) {
		try {
			ecoTourRepo.deleteById(progId);
		} catch (EmptyResultDataAccessException e) {
			throw new ProgramIsNotExistException("Program for progId [" + progId + "] is Not Exist. No need to delete");
		}
		
	}
	@Override
	public String getProgramCode(Long docId) {
		return ecoTourRepo.findById(docId).get().getProgCode();
	}

	@Override
	public SignInUserData getUserData(String id) {

		User user = userRepo.findByUserId(id).orElseThrow(() -> new SignInFailedException());
		
		return new SignInUserData(user.getPassword(), user.getRoles());
	}

	@Override
	public void saveUser(String id, String encodedPassword) {
		// TODO Auto-generated method stub
		User user = new User();
		user.setUserId(id);
		user.setPassword(encodedPassword);
		user.SetRoles(Collections.singletonList("ROLE_USER"));
		userRepo.save(user);
		
	}


}
