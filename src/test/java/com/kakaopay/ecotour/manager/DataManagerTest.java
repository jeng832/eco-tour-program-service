package com.kakaopay.ecotour.manager;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import com.kakaopay.ecotour.exception.ProgramIsNotExistException;
import com.kakaopay.ecotour.model.GetProgramResponseBody;
import com.kakaopay.ecotour.model.PostProgramRequestBody;

@RunWith(SpringRunner.class)
@SpringBootTest
public class DataManagerTest {

	@Autowired
	DataManager dMgr;
	
	@Test
	public void testPostAndGetProgram() {
		PostProgramRequestBody testBody = new PostProgramRequestBody();
		testBody.setName("test name");
		testBody.setTheme("test theme");
		testBody.setRegion("test region");
		testBody.setIntroduction("test intro");
		testBody.setDescription("test desc");
		Long savedId = dMgr.saveProgram(testBody);
		
		GetProgramResponseBody getBody = dMgr.getProgram(savedId);
		assertEquals(testBody.getName(), getBody.getName());
		assertEquals(testBody.getTheme(), getBody.getTheme());
		assertEquals(testBody.getRegion(), getBody.getRegion());
		assertEquals(testBody.getIntroduction(), getBody.getIntroduction());
		assertEquals(testBody.getDescription(), getBody.getDescription());
	}
	
	@Test(expected = ProgramIsNotExistException.class)
	public void testPostAndDeleteProgram() {
		PostProgramRequestBody testBody = new PostProgramRequestBody();
		testBody.setName("test name");
		testBody.setTheme("test theme");
		testBody.setRegion("test region");
		testBody.setIntroduction("test intro");
		testBody.setDescription("test desc");
		Long savedId = dMgr.saveProgram(testBody);
		
		dMgr.deleteProgram(savedId);
		dMgr.getProgram(savedId);
	}
}
