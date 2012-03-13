package jlu.search;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jlu.web.ConstantFactory;
import jlu.web.Pagination;
import jlu.web.Tolls;



import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.queryParser.ParseException;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.IndexSearcher;
import org.apache.lucene.search.Query;
import org.apache.lucene.search.ScoreDoc;
import org.apache.lucene.search.TopDocs;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

public class SearchByPage {
	 public static List<SearchResultBean> searchRetList(String indexDir, String q,Pagination pagination,int page)
	  throws IOException, ParseException {
			List<SearchResultBean> ret = new ArrayList<SearchResultBean>();
			Directory dir = FSDirectory.open(new File(indexDir)); 
			IndexSearcher is = new IndexSearcher(dir); 

			QueryParser parser = new QueryParser(Version.LUCENE_30, "contents", 
					new StandardAnalyzer(Version.LUCENE_30)); 
			Query query = parser.parse(q); 
			//long start = System.currentTimeMillis();
			TopDocs hits = is.search(query, 100);
			
			//long end = System.currentTimeMillis();
/*			System.err.println("Found " + hits.totalHits + 
					" document(s) (in " + (end - start) + 
					" milliseconds) that matched query '" + 
					q + "':"); */

			int resultCount=0;
			if(pagination != null)
			{
				pagination.setSize(ConstantFactory.PAGE_SIZE);
				pagination.setPage(page);
				resultCount = hits.totalHits;
				pagination.setResultCount(resultCount);
				pagination.setPageCount(resultCount/ConstantFactory.PAGE_SIZE + ((resultCount%ConstantFactory.PAGE_SIZE==0)?0:1));
			}
			if(resultCount==0) return new ArrayList<SearchResultBean>();
			int startIndex = (page-1)*ConstantFactory.PAGE_SIZE;
			int endIndex = page*ConstantFactory.PAGE_SIZE-1;
			for(int i=0;i<=endIndex && i<resultCount;i++){
				if(i>=startIndex){
					ScoreDoc scoreDoc = hits.scoreDocs[i];
					SearchResultBean sbean = new SearchResultBean();
					Document doc = is.doc(scoreDoc.doc); 
					sbean.setTitle(doc.get("title"));
					sbean.setFullpath(doc.get("fullpath"));
					Tolls tool= new Tolls();
					sbean.setHighlightedAbstract(tool.hightLight(doc, query,ConstantFactory.VIEW_CONTENT_FRAGE_LENGTH));
					ret.add(sbean);
				}
			}
			is.close(); 
			return ret;
	  }
	 public static void main(String[] args) {
		String indexDir = "F:\\Project\\IBMculb\\index";
		String key = "°Ù¶È";
		try {
			List<SearchResultBean> ret = new ArrayList<SearchResultBean>();
			ret = searchRetList(indexDir, key, new Pagination(), 1);
			System.out.println(ret);
		} catch (IOException e) {
			e.printStackTrace();
		} catch (ParseException e) {
			e.printStackTrace();
		}
	}
}
