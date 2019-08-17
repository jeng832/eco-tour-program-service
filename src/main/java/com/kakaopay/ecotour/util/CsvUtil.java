package com.kakaopay.ecotour.util;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVRecord;

import com.kakaopay.ecotour.model.PostProgramRequestBody;

public class CsvUtil {
	public static List<PostProgramRequestBody> readCsv(String location) throws IOException {
		List<PostProgramRequestBody> retVal = new ArrayList<>();
		//Reader in = new FileReader(location);
		BufferedReader in = new BufferedReader(new InputStreamReader(new FileInputStream(location), "EUC-KR"));
		in.readLine();
		Iterable<CSVRecord> records = CSVFormat.DEFAULT.parse(in);

		for (CSVRecord record : records) {
			PostProgramRequestBody body = new PostProgramRequestBody();
			body.setName(record.get(1));
		    body.setTheme(record.get(2));
		    body.setRegion(record.get(3));
		    body.setIntroduction(record.get(4));
		    body.setDescription(record.get(5));
		    retVal.add(body);
		}
		return retVal;
		
	}
}
