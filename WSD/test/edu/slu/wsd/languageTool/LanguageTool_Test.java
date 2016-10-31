package edu.slu.wsd.languageTool;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import edu.slu.wsd.languageTool.Language;
import edu.slu.wsd.languageTool.LanguageTool;
import edu.slu.wsd.languageTool.wordNet.CASSWordSense;

public class LanguageTool_Test {

	LanguageTool lt= new LanguageTool(Language.EN);
	
	@Test
	public void distanceTest() {
		CASSWordSense s1 = new CASSWordSense("administration", "administration%1:04:00::", "noun", 0);
		CASSWordSense s2 = new CASSWordSense("basketball", "basketball%1:04:00::", "noun", 0);
		
		List<CASSWordSense> chain1 = lt.getHypernymAncestors(s1);
		List<CASSWordSense> chain2 = lt.getHypernymAncestors(s2);
		Integer dist = lt.getHypernymDistanceScore(chain1, chain2);
		assertNotNull(dist);
		assertTrue(dist > 0);
	}
	
	@Test
	public void hypernymTest() {
		CASSWordSense sense = new CASSWordSense("musket ball", "musket_ball%1:06:00::", "noun", 0);
		List<CASSWordSense> ancestors = lt.getHypernymAncestors(sense);
		assertNotNull(ancestors);
		assertTrue(!ancestors.isEmpty());
	}
	
	@Test
	public void tokenizeAndLemmatizeTest() {
		String string = "A method of tending to or managing the affairs of a some group of people (especially the group's business affairs)";
		List<String> stems = lt.tokenizeAndLemmatize(string);
		System.out.println(stems);
	}

}
