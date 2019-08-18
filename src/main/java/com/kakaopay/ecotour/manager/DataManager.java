package com.kakaopay.ecotour.manager;

import java.util.List;

import com.kakaopay.ecotour.model.auth.SignInUserData;
import com.kakaopay.ecotour.model.http.GetCountProgramFromIntroResponseBody;
import com.kakaopay.ecotour.model.http.GetProgramByRegionNameResponseBody;
import com.kakaopay.ecotour.model.http.GetProgramResponseBody;
import com.kakaopay.ecotour.model.http.PostProgramRequestBody;
import com.kakaopay.ecotour.model.recomm.RecommendSourceData;

public interface DataManager {

	GetProgramResponseBody getProgram(Long progId);
	List<RecommendSourceData> savePrograms(List<PostProgramRequestBody> requestBodys);
	Long saveProgram(PostProgramRequestBody requestBody);
	Long saveProgram(Long progId, PostProgramRequestBody requestBody);
	GetCountProgramFromIntroResponseBody getCountProgramFromIntro(String keyword);
	List<GetProgramByRegionNameResponseBody> getProgramByRegion(String regionName);
	List<String> getDescriptions(String keyword);
	void deleteProgram(Long progId);
	String getProgramCode(Long docId);
	List<GetProgramResponseBody> getProgramByRegionCode(Long regionCode);
	
	SignInUserData getUserData(String id);
	void saveUser(String id, String encodedPassword);
}
