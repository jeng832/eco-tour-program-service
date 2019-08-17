package com.kakaopay.ecotour.manager;

import java.util.List;

import com.kakaopay.ecotour.model.recomm.RecommendSourceData;

public interface RecommendationManager {
	
	void deleteStrings(Long progId);
	Long recommendTopProgram(String keyword, String region);
	List<Long> recommendTopProgram(String keyword, String region, int topK);
	void saveMultiStrings(List<RecommendSourceData> data);
	void saveStrings(RecommendSourceData data);
	


}
