package com.kakaopay.ecotour.util;

import static org.junit.Assert.assertEquals;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.core.io.ClassPathResource;

import com.kakaopay.ecotour.model.http.PostProgramRequestBody;

@SpringBootTest
public class CsvUtilTest {
	@Test
	public void testReadCsv() {
		String loc = null;
		List<PostProgramRequestBody> bodys = new ArrayList<>(); 
		try {
			loc = new ClassPathResource("initial_data.csv").getURI().getPath();
			bodys = CsvUtil.readCsv(loc);
		} catch (IOException e) {
			
			e.printStackTrace();
		}
		assertEquals(110, bodys.size());
	}
	
}
