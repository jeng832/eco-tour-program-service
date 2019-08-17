package com.kakaopay.ecotour.util;

import static org.junit.Assert.assertEquals;

import org.junit.Test;

public class CodeUtilTest {
	@Test
	public void testMakeMd5Code() {
		String seed = "test";
		String code = CodeUtil.makeMd5Code(seed);
		assertEquals("098f6bcd4621d373cade4e832627b4f6", code);
	}
}
