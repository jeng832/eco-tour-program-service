package com.kakaopay.ecotour.util;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class StringUtil {
	public static int countTerm(List<String> sentences, String term) {
		int count = 0;
		sentences = Optional.ofNullable(sentences).orElse(new ArrayList<String> ());
		for(String sentence : sentences) {
			count += StringUtil.countTerm(sentence, term);
		}
		
		return count;
		
	}
	public static int countTerm(String sentence, String term) {
		int count = 0;
		term = Optional.ofNullable(term).orElse("");
		sentence = Optional.ofNullable(sentence).orElse("");
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
