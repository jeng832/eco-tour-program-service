package com.kakaopay.ecotour.util;

import java.util.List;

public class StringUtil {
	public static int countTerm(List<String> sentences, String term) {
		int count = 0;
		
		for(String sentence : sentences) {
			count += StringUtil.countTerm(sentence, term);
		}
		
		return count;
		
	}
	public static int countTerm(String sentence, String term) {
		int count = 0;
		if (!sentence.isEmpty() && !term.isEmpty()) {
			for (int i = 0; (i = sentence.indexOf(term, i)) != -1; i += term.length()) {
				count++;
			}
		}
		return count;
	}

	public static String removeNewLineChar(String str) {
		return str.replaceAll("(\r\n|\r|\n|\n\r)", " ");
	}
}
