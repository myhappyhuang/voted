package com.lucene.indexer;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.Term;
import org.apache.lucene.queryParser.QueryParser;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.File;
import java.io.IOException;

/**
 * Created by huangjinlong7 on 2017/9/27.
 */
public class SearchIndexer {
    private String indexDir = this.getClass().getResource("/").getPath() + "index";

    public void search(String queryStr) throws Exception {
        Directory dir = FSDirectory.open(new File(this.indexDir));
        IndexSearcher indexSearcher = new IndexSearcher(dir);
        QueryParser queryParser = new QueryParser(Version.LUCENE_30, "contents", new StandardAnalyzer(Version.LUCENE_30));
        Query query = queryParser.parse(queryStr);
        TopDocs hits = indexSearcher.search(query, 10);
        System.out.println("Found " + hits.totalHits + " condition:" + queryStr);
        for (ScoreDoc scoreDoc : hits.scoreDocs) {
            Document doc = indexSearcher.doc(scoreDoc.doc);
            System.out.println(doc.get("fullpath"));
        }
        indexSearcher.close();
    }

    public void searchTerm(String queryStr) throws IOException {
        Directory dir = FSDirectory.open(new File(this.indexDir));
        IndexSearcher indexSearcher = new IndexSearcher(dir);
        Query query = new TermQuery(new Term("contents", queryStr));
        TopDocs hits = indexSearcher.search(query, 10);
        System.out.println("Found " + hits.totalHits + " condition:" + queryStr);
        for (ScoreDoc scoreDoc : hits.scoreDocs) {
            Document doc = indexSearcher.doc(scoreDoc.doc);
            System.out.println(doc.get("fullpath"));
        }
        indexSearcher.close();
        dir.close();
    }
}
