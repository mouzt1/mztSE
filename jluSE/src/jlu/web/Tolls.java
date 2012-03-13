package jlu.web;

import java.io.StringReader;

import org.apache.lucene.analysis.TokenStream;
import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.highlight.Highlighter;
import org.apache.lucene.search.highlight.QueryScorer;
import org.apache.lucene.search.highlight.SimpleFragmenter;
import org.apache.lucene.search.highlight.SimpleHTMLFormatter;
import org.apache.lucene.util.Version;

public class Tolls {
	public String hightLight(Document doc,Query query,int fragmentLength){
		String result="";
		SimpleHTMLFormatter formatter = new SimpleHTMLFormatter(
				"<b><font color='red'>", "</font></b>");
			
		TokenStream tokens = new StandardAnalyzer(Version.LUCENE_30)
		.tokenStream("contents", new StringReader(doc.get("contents")));
		QueryScorer scorer = new QueryScorer(query, "contents");
		
		Highlighter highlighter = new Highlighter(formatter, scorer);
		highlighter.setTextFragmenter(new SimpleFragmenter(fragmentLength));
		try {
			result = highlighter.getBestFragments(tokens, doc.get("contents"), 5, "бнбн");
			
//					.substring(0, Math.min(maxAstractNum, result.length())));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return result;
	}
	
	public String autoCompl(Document doc,Query query,int fragmentLength){
		String result="";
		SimpleHTMLFormatter formatter = new SimpleHTMLFormatter(
				" ", " ");
			
		TokenStream tokens = new StandardAnalyzer(Version.LUCENE_30)
		.tokenStream("contents", new StringReader(doc.get("contents")));
		QueryScorer scorer = new QueryScorer(query, "contents");
		
		Highlighter highlighter = new Highlighter(formatter, scorer);
		highlighter.setTextFragmenter(new SimpleFragmenter(fragmentLength));
		try {
			result = highlighter.getBestFragments(tokens, doc.get("contents"), 1, "бнбн");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return result;
	}
}
