package com.kakaopay.ecotour.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.kakaopay.ecotour.exception.ProgramIsNotExistException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class ApiServiceTest {

	@Autowired
	ApiService service;
	
	@Test(expected = ProgramIsNotExistException.class)
	public void testNullGetProgramByRegionName() {
		service.getProgramByRegionName(null);
	}
	
	@Test(expected = ProgramIsNotExistException.class)
	public void testNullGetCountProgramFromIntro() {
		service.getCountProgramFromIntro(null);
	}
	@Test(expected = ProgramIsNotExistException.class)
	public void testNullGetCountKeywordFromDesc() {
		service.getCountKeywordFromDesc(null);
	}
	
	@Test(expected = ProgramIsNotExistException.class)
	public void testNullPostProgram() {
		service.postProgram(null);
	}
	
	@Test(expected = ProgramIsNotExistException.class)
	public void testNullGetProgram() {
		service.getProgram(null);
	}
	
	
	@Test(expected = ProgramIsNotExistException.class)
	public void testNullGetProgramByRegionCode() {
		service.getProgramByRegionCode(null);
	}
	
	@Test(expected = ProgramIsNotExistException.class)
	public void testNullPutProgram() {
		service.putProgram(null, null);
	}
	
	@Test(expected = ProgramIsNotExistException.class)
	public void testNullDeleteProgram() {
		service.deleteProgram(null);
	}
			
	@Test(expected = ProgramIsNotExistException.class)
	public void testDeleteNotExistProgram() {
		service.deleteProgram(-1L);
	}
	
	@Test(expected = ProgramIsNotExistException.class)
	public void testNullGetProgramRecommendation() {
		service.getProgramRecommendation(null, null);
	}
	
	
}
