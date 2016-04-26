package cass.languageTool;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import cass.languageTool.wordNet.CASSWordSense;

public class LanguageTool_Test {

	@Test
	public void distanceTest() {
		CASSWordSense s1 = new CASSWordSense("administration", "administration%1:04:00::", "noun", 0);
		CASSWordSense s2 = new CASSWordSense("basketball", "basketball%1:04:00::", "noun", 0);
		
		LanguageTool lt = new LanguageTool(Language.EN);
		List<CASSWordSense> chain1 = lt.getHypernymAncestors(s1);
		List<CASSWordSense> chain2 = lt.getHypernymAncestors(s2);
		Integer dist = lt.getHypernymDistanceScore(chain1, chain2);
		assertNotNull(dist);
		assertTrue(dist > 0);
	}
	
	@Test
	public void hypernymTest() {
		CASSWordSense sense = new CASSWordSense("musket ball", "musket_ball%1:06:00::", "noun", 0);
		LanguageTool lt = new LanguageTool(Language.EN);
		List<CASSWordSense> ancestors = lt.getHypernymAncestors(sense);
		assertNotNull(ancestors);
		assertTrue(!ancestors.isEmpty());
	}

}
