package com.kakaopay.ecotour.manager.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.kakaopay.ecotour.manager.RecommendationManager;
import com.kakaopay.ecotour.model.recomm.ColumnStrings;
import com.kakaopay.ecotour.model.recomm.RecommendSourceData;
import com.kakaopay.ecotour.util.StringUtil;

import kr.co.shineware.nlp.komoran.constant.DEFAULT_MODEL;
import kr.co.shineware.nlp.komoran.core.Komoran;

@Component
public class RecommendationManagerImpl implements RecommendationManager {
	
	private Logger logger = LoggerFactory.getLogger(RecommendationManagerImpl.class);
	
	private Komoran komoran;
	
	private ColumnStrings themes;
	private ColumnStrings introductions;
	private ColumnStrings descriptions;
	
	@Value("${recomm.weight.theme}")
	private Double themeWeight;
	@Value("${recomm.weight.intro}")
	private Double introWeight;
	@Value("${recomm.weight.desc}")
	private Double descWeight;
	
	private Map<Long, String> regionMap;
	
	@PostConstruct
	private void init() {
		komoran = new Komoran(DEFAULT_MODEL.FULL);

		themes = new ColumnStrings();
		introductions = new ColumnStrings();
		descriptions = new ColumnStrings();
	
		regionMap = new ConcurrentHashMap<Long, String> ();
	}
	
	private int getDocumentFreq(List<String> documents, String term) {
		int count = 0;
		for(String doc : documents) {
			if(doc.contains(term)) {
				count++;
			}
		}
		
		return count;
	}
	
	private int getTermFreq(String document, String term) {
		return StringUtil.countTerm(document, term);
	}
		
	private List<String> analyzeTerms(String sentence) {
		return komoran.analyze(sentence).getNouns();
	}
	
	private int calculateTermFreq(Long docId, String term, ColumnStrings columnStrings) {
		String columnValue = columnStrings.getString(docId);
		return getTermFreq(columnValue, term);
	}
	
	private Map<Long, Map<String, Integer>> calculateTermFreqs(ColumnStrings columnStrings) {

		Map<Long, Map<String, Integer>> termFreqs = new HashMap<Long, Map<String, Integer>> ();
		
		Set<Long> docIds = columnStrings.getDocIds();
		Set<String> terms = getTerms(columnStrings);
		
		for(Long docId : docIds) {
			Map<String, Integer> termFreqsEachDoc = new HashMap<String, Integer> ();
			for(String term : terms) {
				int termFreq = calculateTermFreq(docId, term, columnStrings);
				termFreqsEachDoc.put(term, termFreq);
			}
			termFreqs.put(docId, termFreqsEachDoc);
		}
		
		return termFreqs;
	}
	
	private Set<String> getTerms(ColumnStrings columnStrings) {
		Set<String> terms = new HashSet<String> ();
		List<String> columnValues = columnStrings.getStrings();
		columnValues.forEach(val -> terms.addAll(analyzeTerms(val)));
		return terms;
	}

	private Map<String, Integer> calculateDocFreqs(ColumnStrings columnStrings) {
		Map<String, Integer> docFreqs = new HashMap<String, Integer> ();
		List<String> columnValues = columnStrings.getStrings();
		Set<String> terms = getTerms(columnStrings);
		terms.forEach(term -> docFreqs.put(term, getDocumentFreq(columnValues, term)));
		
		return docFreqs;
	}
	
	private Map<Long, Map<String, Double>> getTfIdfsByColumn(ColumnStrings columnStrings) {
		Map<Long, Map<String, Double>> resultTfIdfs = new HashMap<Long, Map<String, Double>> ();
		Map<String, Integer> docFreqs = calculateDocFreqs(columnStrings);
		Map<Long, Map<String, Integer>> termFreqs = calculateTermFreqs(columnStrings);
		Set<Long> docIds = termFreqs.keySet();
		Set<String> terms = docFreqs.keySet();
		
		for(Long docId : docIds) {
			Map<String, Double> docTfIdfs = new HashMap<String, Double> ();
			for(String term : terms) {
				int df = docFreqs.get(term);
				int tf = termFreqs.get(docId).get(term);
				Double tfIdf = new Double(tf) * (1 / new Double(df));
				docTfIdfs.put(term, tfIdf);
			}
			resultTfIdfs.put(docId, docTfIdfs);
		}
		
		return resultTfIdfs;
	}
	
	private ColumnStrings getRegionalColumnStrings(ColumnStrings columnStrings, String region) {
		ColumnStrings retVal = new ColumnStrings();
		regionMap.keySet().forEach(id -> {
			
			if(Optional.ofNullable(regionMap.get(id)).orElse(new String()).contains(region)) {
				retVal.setString(id, columnStrings.getString(id));
			}
		});
		
		
		return retVal;
	}
	
	Map<String, Map<Long, Double>> getFinalScores(ColumnStrings themesRegionalColumnStrings
			,ColumnStrings introductionsRegionalColumnStrings, ColumnStrings descriptionsRegionalColumnStrings) {
		
		Map<String, Map<Long, Double>> finalScores = new HashMap<String, Map<Long, Double>> ();
		
		Map<Long, Map<String, Double>> totalThemesTfIdfs = getTfIdfsByColumn(themesRegionalColumnStrings);
		Map<Long, Map<String, Double>> totalIntroductionsTfIdfs = getTfIdfsByColumn(introductionsRegionalColumnStrings);
		Map<Long, Map<String, Double>> totalDescriptionsTfIdfs = getTfIdfsByColumn(descriptionsRegionalColumnStrings);
		
		Set<Long> totalDocIds = new HashSet<Long> ();
		totalDocIds.addAll(totalThemesTfIdfs.keySet());
		totalDocIds.addAll(totalIntroductionsTfIdfs.keySet());
		totalDocIds.addAll(totalDescriptionsTfIdfs.keySet());
		
		
		for(Long docId : totalDocIds) {
			Map<String, Double> themesTfIdfs = Optional.ofNullable(totalThemesTfIdfs.get(docId)).orElse(new HashMap<String, Double> ());
			Map<String, Double> introductionsTfIdfs = Optional.ofNullable(totalIntroductionsTfIdfs.get(docId)).orElse(new HashMap<String, Double> ());
			Map<String, Double> descriptionsTfIdfs = Optional.ofNullable(totalDescriptionsTfIdfs.get(docId)).orElse(new HashMap<String, Double> ());
			
			
			Set<String> docTerms = new HashSet<String> ();
			docTerms.addAll(themesTfIdfs.keySet());
			docTerms.addAll(introductionsTfIdfs.keySet());
			docTerms.addAll(descriptionsTfIdfs.keySet());
			
			for(String term : docTerms) {
				Map<Long, Double> docScores = Optional.ofNullable(finalScores.get(term)).orElse(new HashMap<Long, Double> ());
				
				Double termTotalScore = (Optional.ofNullable(themesTfIdfs.get(term)).orElse(new Double(0)) * this.themeWeight) +
						(Optional.ofNullable(introductionsTfIdfs.get(term)).orElse(new Double(0)) * this.introWeight) +
						(Optional.ofNullable(descriptionsTfIdfs.get(term)).orElse(new Double(0)) * this.descWeight);
				
				docScores.put(docId, termTotalScore);
				finalScores.put(term, docScores);
			}	
		}
		
		
		return finalScores;
	}
	
	private List<Long> recommendProgram(String keyword, String region, int k) {
		List<Long> topKDocIds = new ArrayList<Long> ();
		ColumnStrings themesRegionalColumnStrings = getRegionalColumnStrings(this.themes, region);
		ColumnStrings introductionsRegionalColumnStrings = getRegionalColumnStrings(this.introductions, region);
		ColumnStrings descriptionsRegionalColumnStrings = getRegionalColumnStrings(this.descriptions, region);	
		
		Map<String, Map<Long, Double>> finalScores = getFinalScores(themesRegionalColumnStrings, 
				introductionsRegionalColumnStrings, descriptionsRegionalColumnStrings);
		
		List<String> analyzedKeywords = analyzeTerms(keyword);
		logger.info("recommendProgram analyzedKeywords: " + analyzedKeywords.toString());
		Map<Long, Double> mergedFinalScore = new HashMap<Long, Double> ();
		analyzedKeywords.forEach(kw -> {
			Map<Long, Double> finalScoresForKw = Optional.ofNullable(finalScores.get(kw)).orElse(new HashMap<Long, Double> ());
			
			finalScoresForKw.keySet().forEach(docId -> {
				Double currentScore = Optional.ofNullable(mergedFinalScore.get(docId)).orElse(new Double(0));
				mergedFinalScore.put(docId, currentScore + finalScoresForKw.get(docId));
			});
		
		});
		
		List<Long> scoreOrderedDocIds = new ArrayList<Long> ();
		Optional.ofNullable(mergedFinalScore).ifPresent(map -> {
			logger.info("recommendProgram mergedFinalScore: " + mergedFinalScore.toString());
			scoreOrderedDocIds.addAll(map.keySet());
			
			Collections.sort(scoreOrderedDocIds, new Comparator<Long>() {

				@Override
				public int compare(Long o1, Long o2) {
					Double v1 = map.get(o1);
					Double v2 = map.get(o2);
					
					return v2.compareTo(v1);
				}
			});	
		});
		logger.info("recommendProgram topDocs: " + scoreOrderedDocIds.toString());
		int size = scoreOrderedDocIds.size();
		for(int i = 0; (i < k) && (i < size); i++) {
			topKDocIds.add(scoreOrderedDocIds.get(i));
		}
		
		return topKDocIds;
	}
	
	@Override
	public Long recommendTopProgram(String keyword, String region) {
		logger.info("recommendTopProgram keyword: " + keyword + ", region: " + region);
		List<Long> topDocs = recommendProgram(keyword, region, 1);
		if(topDocs.size() != 0) {
			return topDocs.get(0);
		}
		return null;
	}
	
	@Override
	public List<Long> recommendTopProgram(String keyword, String region, int topK) {
		logger.info("recommendTopProgram keyword: " + keyword + ", region: " + region + ", topK: " + topK);
		return recommendProgram(keyword, region, topK);
	}

	@Override
	public void deleteStrings(Long progId) {
		themes.delDocu(progId);
		introductions.delDocu(progId);
		descriptions.delDocu(progId);
	}

	@Override
	public void saveStrings(RecommendSourceData data) {
		themes.setString(data.getId(), data.getTheme());
		introductions.setString(data.getId(), data.getIntroduction());
		descriptions.setString(data.getId(), data.getDescription());
		regionMap.put(data.getId(), data.getRegion());
	}

	
	@Override
	public void saveMultiStrings(List<RecommendSourceData> data) {
		data.forEach(d -> {
			saveStrings(d);
		});
	}
}
