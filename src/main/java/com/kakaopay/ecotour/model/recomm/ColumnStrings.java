package com.kakaopay.ecotour.model.recomm;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class ColumnStrings {
	private Map<Long, String> strings;
	
	public ColumnStrings() {
		strings = new HashMap<Long, String> ();
	}
	public void setString(Long docuId, String value) {
		strings.put(docuId, value);
	}
	
	public void delDocu(Long docId) {
		strings.remove(docId);
	}
	
	public String getFlatString() {
		StringBuffer sb = new StringBuffer();
		strings.keySet().forEach(str -> sb.append(str).append(" "));
		return sb.toString();
	}
	
	public List<String> getStrings() {
		return new ArrayList<String>(strings.values());
	}
	public String getString(Long docId) {
		return strings.get(docId);
	}
	
	public Set<Long> getDocIds() {
		return strings.keySet();
	}
}
