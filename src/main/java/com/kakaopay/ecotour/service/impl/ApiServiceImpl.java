package com.kakaopay.ecotour.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.kakaopay.ecotour.exception.ProgramIsNotExistException;
import com.kakaopay.ecotour.manager.DataManager;
import com.kakaopay.ecotour.manager.RecommendationManager;
import com.kakaopay.ecotour.model.GetCountKeywordFromDescResponseBody;
import com.kakaopay.ecotour.model.GetCountProgramFromIntroResponseBody;
import com.kakaopay.ecotour.model.GetProgramByRegionNameResponseBody;
import com.kakaopay.ecotour.model.GetProgramRecommendationResponseBody;
import com.kakaopay.ecotour.model.GetProgramResponseBody;
import com.kakaopay.ecotour.model.PostProgramRequestBody;
import com.kakaopay.ecotour.model.recomm.RecommendSourceData;
import com.kakaopay.ecotour.service.ApiService;
import com.kakaopay.ecotour.util.CsvUtil;
import com.kakaopay.ecotour.util.StringUtil;

@Service
@Transactional
public class ApiServiceImpl implements ApiService {

	private Logger logger = LoggerFactory.getLogger(ApiServiceImpl.class);
	
	@Autowired
	private RecommendationManager recomMgr;
	
	@Autowired
	private DataManager dataMgr;
	
	@Value("${init.csv.file}")
	private String csvFile;
	
	@PostConstruct
	private void importCsvFile() {
		String loc = null;
		List<PostProgramRequestBody> bodys = new ArrayList<>(); 
		try {
			loc = new ClassPathResource(csvFile).getURI().getPath();
			logger.info("csv location: " + loc);
			bodys = CsvUtil.readCsv(loc);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		List<RecommendSourceData> savedData = dataMgr.savePrograms(bodys);
		
		recomMgr.saveMultiStrings(savedData);
	}
	
	@Override
	public List<GetProgramByRegionNameResponseBody> getProgramByRegionName(String regionName) {
		logger.info("GetProgramByRegionResponseBody regionName: " + regionName);
		Optional.ofNullable(regionName)
				.orElseThrow(() -> new ProgramIsNotExistException("regionName [" + regionName + "] is Null"));
		return dataMgr.getProgramByRegion(regionName);
	}

	@Override
	public GetProgramResponseBody getProgram(Long id) {
		Optional.ofNullable(id)
				.orElseThrow(() -> new ProgramIsNotExistException("id [" + id + "] is Null"));
		return dataMgr.getProgram(id);
	}
	
	@Override
	public List<GetProgramResponseBody> getProgramByRegionCode(Long regionCode) {
		Optional.ofNullable(regionCode)
				.orElseThrow(() -> new ProgramIsNotExistException("regionCode [" + regionCode + "] is Null"));
		return dataMgr.getProgramByRegionCode(regionCode);
	}

	@Override
	public void postProgram(PostProgramRequestBody requestBody) {
		Optional.ofNullable(requestBody)
				.orElseThrow(() -> new ProgramIsNotExistException("requestBody [" + requestBody + "] is Null"));
		Long savedId = dataMgr.saveProgram(requestBody);
		recomMgr.saveStrings(new RecommendSourceData(savedId, requestBody.getTheme(), requestBody.getIntroduction(), requestBody.getDescription(), requestBody.getRegion()));
	}
	
	@Override
	public void putProgram(Long progId, PostProgramRequestBody requestBody) {
		Optional.ofNullable(progId)
				.orElseThrow(() -> new ProgramIsNotExistException("progId [" + progId + "] is Null"));
		Optional.ofNullable(requestBody)
				.orElseThrow(() -> new ProgramIsNotExistException("requestBody [" + requestBody + "] is Null"));
		Long savedId = dataMgr.saveProgram(progId, requestBody);
		recomMgr.saveStrings(new RecommendSourceData(savedId, requestBody.getTheme(), requestBody.getIntroduction(), requestBody.getDescription(), requestBody.getRegion()));
	}

	@Override
	public void deleteProgram(Long progId) {
		logger.info("deleteProgram progId: " + progId);
		Optional.ofNullable(progId)
				.orElseThrow(() -> new ProgramIsNotExistException("progId [" + progId + "] is Null"));
		dataMgr.deleteProgram(progId);
		recomMgr.deleteStrings(progId);
	}
	
	@Override
	public GetCountProgramFromIntroResponseBody getCountProgramFromIntro(String keyword) {
		logger.info("GetCountFromIntroResponseBody keyword: " + keyword);
		Optional.ofNullable(keyword)
				.orElseThrow(() -> new ProgramIsNotExistException("keyword [" + keyword + "] is Null"));
		return dataMgr.getCountProgramFromIntro(keyword);
	}


	@Override
	public GetCountKeywordFromDescResponseBody getCountKeywordFromDesc(String keyword) {
		Optional.ofNullable(keyword)
				.orElseThrow(() -> new ProgramIsNotExistException("keyword [" + keyword + "] is Null"));
		List<String> documents = dataMgr.getDescriptions(keyword);
		int count = StringUtil.countTerm(documents, keyword);
		
		return new GetCountKeywordFromDescResponseBody(keyword, count);
	}

	@Override
	public GetProgramRecommendationResponseBody getProgramRecommendation(String keyword, String region) {
		Optional.ofNullable(keyword)
				.orElseThrow(() -> new ProgramIsNotExistException("keyword [" + keyword + "] is Null"));
		Optional.ofNullable(keyword)
				.orElseThrow(() -> new ProgramIsNotExistException("region [" + region + "] is Null"));
		Long docId = recomMgr.recommendTopProgram(keyword, region);
		if(docId == null) {
			return new GetProgramRecommendationResponseBody();
		}
		return new GetProgramRecommendationResponseBody(dataMgr.getProgramCode(docId));
	}
	
	@Override
	public List<GetProgramRecommendationResponseBody> getProgramRecommendation(String keyword, String region, int topK) {
		Optional.ofNullable(keyword)
				.orElseThrow(() -> new ProgramIsNotExistException("keyword [" + keyword + "] is Null"));
		Optional.ofNullable(keyword)
				.orElseThrow(() -> new ProgramIsNotExistException("region [" + region + "] is Null"));
		if(topK < 1) {
			throw new ProgramIsNotExistException("topK must be positive integer");
		}
		List<Long> docIds = recomMgr.recommendTopProgram(keyword, region, topK);
		List<GetProgramRecommendationResponseBody> response = new ArrayList<GetProgramRecommendationResponseBody> ();
		docIds.forEach(docId -> {
			response.add(new GetProgramRecommendationResponseBody(dataMgr.getProgramCode(docId)));
		});
		return response;
	}
}
