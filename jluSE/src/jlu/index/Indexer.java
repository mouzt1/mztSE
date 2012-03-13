package jlu.index;

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
import java.io.FileFilter;
import java.io.IOException;

import jlu.web.ConstantFactory;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

// From chapter 1

/**
 * This code was originally written for Erik's Lucene intro java.net article
 */
public class Indexer {
	private IndexWriter writer;
	private TextProcessor textProcessor;
	
	public Indexer(String indexDir) throws IOException {
		Directory dir = FSDirectory.open(new File(indexDir));
		writer = new IndexWriter(dir, new StandardAnalyzer(Version.LUCENE_30),
				true, IndexWriter.MaxFieldLength.UNLIMITED);
		textProcessor = new TextProcessor();
	}

	public void close() throws IOException {
		writer.close();
	}

	public int index(String dataDir, FileFilter filter) throws Exception {

		File[] files = new File(dataDir).listFiles();

		for (File f : files) {
			if (!f.isDirectory() && !f.isHidden() && f.exists() && f.canRead()
					&& (filter == null || filter.accept(f))) {
				indexFile(f);
			}
		}

		return writer.numDocs();
	}

	private static class TextFilesFilter implements FileFilter {
		public boolean accept(File path) {
			return path.getName().toLowerCase().endsWith(".txt");
		}
	}
	private void indexFile(File f) throws Exception {
		System.out.println("Indexing " + f.getCanonicalPath());
		Document doc = getDocument(f);
		writer.addDocument(doc);
	}
	
	protected Document getDocument(File f) throws Exception {
		Document doc = new Document();
		textProcessor.setFile(f);
		doc.add(new Field("contents", textProcessor.getContents(),
				Field.Store.YES, Field.Index.ANALYZED));
		doc.add(new Field("title", textProcessor.getTitle(), Field.Store.YES,
				Field.Index.NOT_ANALYZED));
		doc.add(new Field("fullpath", f.getCanonicalPath(), Field.Store.YES,
				Field.Index.NOT_ANALYZED));
		return doc;
		/*
		Document doc = new Document();
		doc.add(new Field("contents", new FileReader(f)));
		doc.add(new Field("filename", f.getName(), Field.Store.YES,
				Field.Index.NOT_ANALYZED));
		doc.add(new Field("fullpath", f.getCanonicalPath(), Field.Store.YES,
				Field.Index.NOT_ANALYZED));
		return doc;*/
	}


	public static void main(String[] args) throws Exception {

		String indexDir = ConstantFactory.INDEX_DIR;
		String dataDir = ConstantFactory.DATA_DIR;

		long start = System.currentTimeMillis();
		Indexer indexer = new Indexer(indexDir);
		int numIndexed=0;;
		try {
			numIndexed = indexer.index(dataDir, new TextFilesFilter());
		}catch(Exception e){
			e.printStackTrace();
		} finally {
			indexer.close();
		}
		long end = System.currentTimeMillis();
		System.out.println("Indexing " + numIndexed + " files took "
				+ (end - start) + " milliseconds");
	}
}
