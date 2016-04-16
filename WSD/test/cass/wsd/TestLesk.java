package cass.wsd;

import static org.junit.Assert.*;

import java.util.List;

import org.junit.Test;

import cass.languageTool.Language;

public class TestLesk {

	@Test
	public void test() {
		WSD wsd = new WSD("", "", "", Language.TEST);
		List<ScoredSense> ranked = wsd.rankSynsetsUsing(Algorithm.LESK);
	}

}
