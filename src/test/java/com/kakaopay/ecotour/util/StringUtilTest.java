package com.kakaopay.ecotour.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class StringUtilTest {
	@Test
	public void testCountTerm() {
		String sentence = "ABC DEF GHI ABC";
		String term = "ABC";
		assertEquals(2, StringUtil.countTerm(sentence, term));
	}
	
	@Test
	public void testRemoveNewLineChar() {
		String origin = "ABC\nDEF\rGHI\n\rJKL\r\nMNO";
		String target = "ABC DEF GHI  JKL MNO";
		assertEquals(target, StringUtil.removeNewLineChar(origin));
		
	}
}
