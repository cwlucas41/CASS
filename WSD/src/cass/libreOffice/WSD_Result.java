package cass.libreOffice;

public class WSD_Result{
	public int SynonymCount;
	public int SynsetCount;
	public String[][] Synonyms;
	
	public WSD_Result(){}
	public WSD_Result(int synonymCount, int sysnetCount, String[][] synonyms) {
		this.SynonymCount = synonymCount;
		this.SynsetCount = synonymCount;
		this.Synonyms = synonyms;
	}
};

