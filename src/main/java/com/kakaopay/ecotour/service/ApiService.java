package com.kakaopay.ecotour.service;

import java.util.List;

import com.kakaopay.ecotour.model.GetCountKeywordFromDescResponseBody;
import com.kakaopay.ecotour.model.GetCountProgramFromIntroResponseBody;
import com.kakaopay.ecotour.model.GetProgramByRegionNameResponseBody;
import com.kakaopay.ecotour.model.GetProgramRecommendationResponseBody;
import com.kakaopay.ecotour.model.GetProgramResponseBody;
import com.kakaopay.ecotour.model.PostProgramRequestBody;

public interface ApiService {
	void init();
	List<GetProgramByRegionNameResponseBody> getProgramByRegionName(String regionName);
	GetCountProgramFromIntroResponseBody getCountProgramFromIntro(String keyword);
	GetCountKeywordFromDescResponseBody getCountKeywordFromDesc(String keyword);
	void postProgram(PostProgramRequestBody requestBody);
	GetProgramResponseBody getProgram(Long id);
	List<GetProgramResponseBody> getProgramByRegionCode(Long regionCode);
	void putProgram(Long progId, PostProgramRequestBody requestBody);
	void deleteProgram(Long progId);
	GetProgramRecommendationResponseBody getProgramRecommendation(String keyword, String region);
	List<GetProgramRecommendationResponseBody> getProgramRecommendation(String keyword, String region, int topK);
}
