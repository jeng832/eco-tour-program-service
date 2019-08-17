package com.kakaopay.ecotour.controller;

import java.util.List;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.kakaopay.ecotour.model.GetCountRequestBody;
import com.kakaopay.ecotour.model.GetCountKeywordFromDescResponseBody;
import com.kakaopay.ecotour.model.GetCountProgramFromIntroResponseBody;
import com.kakaopay.ecotour.model.GetProgramByRegionNameRequestBody;
import com.kakaopay.ecotour.model.GetProgramByRegionNameResponseBody;
import com.kakaopay.ecotour.model.GetProgramRecommendationRequestBody;
import com.kakaopay.ecotour.model.GetProgramRecommendationResponseBody;
import com.kakaopay.ecotour.model.GetProgramResponseBody;
import com.kakaopay.ecotour.model.PostProgramRequestBody;
import com.kakaopay.ecotour.service.ApiService;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;

@RestController
public class ApiController {
	private Logger logger = LoggerFactory.getLogger(ApiController.class);
	
	@Autowired
	ApiService apiService;
	
    @RequestMapping(value="/health", method=RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> health() {
    	logger.info("Health Checking");
        return ResponseEntity.ok().build();
    }
    @RequestMapping(value="/init", method=RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<?> getProgram() {
    	logger.info("init csv file");
    	apiService.init();
        return ResponseEntity.ok().build();
    }
    
	@ApiImplicitParams({
		@ApiImplicitParam(name = "X-AUTH-TOKEN", required = true, dataType = "String", paramType = "header")
	})
    @RequestMapping(value="/program/id/{progId}", method=RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<GetProgramResponseBody> getProgram(@PathVariable @Valid Long progId) {
    	logger.info("getProgram id: " + progId);
    	GetProgramResponseBody body = apiService.getProgram(progId);
        return new ResponseEntity<GetProgramResponseBody>(body, HttpStatus.OK);
    }
	@ApiImplicitParams({
		@ApiImplicitParam(name = "X-AUTH-TOKEN", required = true, dataType = "String", paramType = "header")
	})
    @RequestMapping(value="/program/region/code/{regionCode}", method=RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<GetProgramResponseBody>> getProgramByRegionCode(@PathVariable @Valid Long regionCode) {
    	logger.info("getProgramByRegionCode id: " + regionCode);
    	List<GetProgramResponseBody> body = apiService.getProgramByRegionCode(regionCode);
        return new ResponseEntity<List<GetProgramResponseBody>>(body, HttpStatus.OK);
    }
	@ApiImplicitParams({
		@ApiImplicitParam(name = "X-AUTH-TOKEN", required = true, dataType = "String", paramType = "header")
	})
    @RequestMapping(value="/program/region/name", method=RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<GetProgramByRegionNameResponseBody>> getProgramByRegionName(@RequestBody @Valid GetProgramByRegionNameRequestBody request) {
    	logger.info("getProgramByRegionName body: " + request.toString());
    	List<GetProgramByRegionNameResponseBody> bodys = apiService.getProgramByRegionName(request.getRegion());
    	return new ResponseEntity<List<GetProgramByRegionNameResponseBody>>(bodys, HttpStatus.OK);
    }
	@ApiImplicitParams({
		@ApiImplicitParam(name = "X-AUTH-TOKEN", required = true, dataType = "String", paramType = "header")
	})
    @RequestMapping(value="/program", method=RequestMethod.POST)
    @ResponseBody
    public ResponseEntity<?> postProgram(@RequestBody @Valid PostProgramRequestBody requestBody) {
    	logger.info("postProgram requestBody: " + requestBody.toString());
    	apiService.postProgram(requestBody);
        return ResponseEntity.ok().build();
    }
	@ApiImplicitParams({
		@ApiImplicitParam(name = "X-AUTH-TOKEN", required = true, dataType = "String", paramType = "header")
	})
    @RequestMapping(value="/program/id/{progId}", method=RequestMethod.PUT)
    @ResponseBody
    public ResponseEntity<?> putProgram(@PathVariable @Valid Long progId, @RequestBody @Valid PostProgramRequestBody requestBody) {
    	logger.info("putProgram progId: " + progId + ", requestBody: "+ requestBody);
    	apiService.putProgram(progId, requestBody);
        return ResponseEntity.ok().build();
    }
	@ApiImplicitParams({
		@ApiImplicitParam(name = "X-AUTH-TOKEN", required = true, dataType = "String", paramType = "header")
	})
    @RequestMapping(value="/program/id/{progId}", method=RequestMethod.DELETE)
    @ResponseBody
    public ResponseEntity<?> deleteProgram(@PathVariable @Valid Long progId) {
    	logger.info("deleteProgram progId: "+ progId);
    	apiService.deleteProgram(progId);
        return ResponseEntity.ok().build();
    }
    

	@ApiImplicitParams({
		@ApiImplicitParam(name = "X-AUTH-TOKEN", required = true, dataType = "String", paramType = "header")
	})
    @RequestMapping(value="/count/program/from_intro", method=RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<GetCountProgramFromIntroResponseBody> getCountProgramFromIntro(@RequestBody @Valid GetCountRequestBody request) {
    	logger.info("getCountProgramFromIntro body: " + request.toString());
    	GetCountProgramFromIntroResponseBody body = apiService.getCountProgramFromIntro(request.getKeyword());
    	return new ResponseEntity<GetCountProgramFromIntroResponseBody>(body, HttpStatus.OK);
    }
	@ApiImplicitParams({
		@ApiImplicitParam(name = "X-AUTH-TOKEN", required = true, dataType = "String", paramType = "header")
	})
    @RequestMapping(value="/count/keyword/from_desc", method=RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<GetCountKeywordFromDescResponseBody> getCountKeywordFromDesc(@RequestBody @Valid GetCountRequestBody request) {
    	logger.info("getCountKeywordFromDesc body: " + request.toString());
    	GetCountKeywordFromDescResponseBody body = apiService.getCountKeywordFromDesc(request.getKeyword());
    	return new ResponseEntity<GetCountKeywordFromDescResponseBody>(body, HttpStatus.OK);
    }
	@ApiImplicitParams({
		@ApiImplicitParam(name = "X-AUTH-TOKEN", required = true, dataType = "String", paramType = "header")
	})
    @RequestMapping(value="/program/recommendation", method=RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<GetProgramRecommendationResponseBody> getProgramRecommendation(@RequestBody @Valid GetProgramRecommendationRequestBody request) {
    	logger.info("getProgramRecommendation body: " + request.toString());
    	GetProgramRecommendationResponseBody body = apiService.getProgramRecommendation(request.getKeyword(), request.getRegion());
    	return new ResponseEntity<GetProgramRecommendationResponseBody>(body, HttpStatus.OK);
    }
	@ApiImplicitParams({
		@ApiImplicitParam(name = "X-AUTH-TOKEN", required = true, dataType = "String", paramType = "header")
	})
    @RequestMapping(value="/program/recommendation/topk/{topK}", method=RequestMethod.GET)
    @ResponseBody
    public ResponseEntity<List<GetProgramRecommendationResponseBody>> getProgramRecommendation(@PathVariable @Valid Integer topK, @RequestBody @Valid GetProgramRecommendationRequestBody request) {
    	logger.info("getProgramRecommendation body: " + request.toString());
    	List<GetProgramRecommendationResponseBody> body = apiService.getProgramRecommendation(request.getKeyword(), request.getRegion(), topK);
    	return new ResponseEntity<List<GetProgramRecommendationResponseBody>>(body, HttpStatus.OK);
    }
}
