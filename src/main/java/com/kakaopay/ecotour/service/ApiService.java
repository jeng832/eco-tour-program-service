package com.kakaopay.ecotour.service;

import java.util.List;

import com.kakaopay.ecotour.model.http.GetCountKeywordFromDescResponseBody;
import com.kakaopay.ecotour.model.http.GetCountProgramFromIntroResponseBody;
import com.kakaopay.ecotour.model.http.GetProgramByRegionNameResponseBody;
import com.kakaopay.ecotour.model.http.GetProgramRecommendationResponseBody;
import com.kakaopay.ecotour.model.http.GetProgramResponseBody;
import com.kakaopay.ecotour.model.http.PostProgramRequestBody;

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
