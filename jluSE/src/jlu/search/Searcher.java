package jlu.search;

/**
 * Copyright Manning Publications Co.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific lan      
 */

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import jlu.web.ConstantFactory;
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

// From chapter 1

/**
 * This code was originally written for Erik's Lucene intro java.net article
 */
public class Searcher {

	public static void main(String[] args) {
		if (args.length != 2) {
			throw new IllegalArgumentException("Usage: java "
					+ Searcher.class.getName() + " <index dir> <query>");
		}

		String indexDir = args[0]; // 1
		String q = args[1]; // 2
		try {
			searchRetList(indexDir, q,10);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void search(String indexDir, String q) throws IOException,
			ParseException {

		Directory dir = FSDirectory.open(new File(indexDir));
		IndexSearcher is = new IndexSearcher(dir);

		QueryParser parser = new QueryParser(Version.LUCENE_30, "contents",
				new StandardAnalyzer(Version.LUCENE_30));
		Query query = parser.parse(q);
		long start = System.currentTimeMillis();
		TopDocs hits = is.search(query, 10);
		long end = System.currentTimeMillis();

		System.err.println("Found " + hits.totalHits + " document(s) (in "
				+ (end - start) + " milliseconds) that matched query '" + q
				+ "':");

		for (ScoreDoc scoreDoc : hits.scoreDocs) {
			Document doc = is.doc(scoreDoc.doc);
			// System.out.println(doc.get("title"));
			System.out.println(doc.get("fullpath"));
		}

		is.close();
	}

	/**
	 *  用于返回 查询q的topNum个结果 autoCompleted
	 * @param indexDir
	 * @param q
	 * @param topNum
	 * @return
	 * @throws IOException
	 * @throws ParseException
	 */
	public static List<SearchResultBean> searchRetList(String indexDir, String q,int topNum)
			throws IOException, ParseException {
		List<SearchResultBean> ret = new ArrayList<SearchResultBean>();
		Directory dir = FSDirectory.open(new File(indexDir));
		IndexSearcher is = new IndexSearcher(dir);

		QueryParser parser = new QueryParser(Version.LUCENE_30, "contents",
				new StandardAnalyzer(Version.LUCENE_30));
		Query query = parser.parse(q);
		TopDocs hits = is.search(query, topNum);
		for (ScoreDoc scoreDoc : hits.scoreDocs) {
			Document doc = is.doc(scoreDoc.doc);
			SearchResultBean srb = new SearchResultBean();
			srb.setTitle(doc.get("title"));
			srb.setFullpath(doc.get("fullpath"));
			ret.add(srb);
			Tolls tool= new Tolls();
			srb.setHighlightedAbstract(tool.autoCompl(doc, query,ConstantFactory.AUTO_COMPL_FRAGE_LENGTH));
			ret.add(srb);
		}
		is.close();
		return ret;
	}
}


/*
 * #1 Parse provided index directory #2 Parse provided query string #3 Open
 * index #4 Parse query #5 Search index #6 Write search stats #7 Retrieve
 * matching document #8 Display filename #9 Close IndexSearcher
 */
