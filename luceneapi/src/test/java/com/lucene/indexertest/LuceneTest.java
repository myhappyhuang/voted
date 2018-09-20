package com.lucene.indexertest;

import com.lucene.indexer.CreateIndexer;
import com.lucene.indexer.CreateIndexsByStr;
import com.lucene.indexer.IndexerManager;
import com.lucene.indexer.SearchIndexer;
import com.lucene.util.TestFileFilter;
import org.apache.lucene.analysis.SimpleAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexReader;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.index.Term;
import org.apache.lucene.search.*;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.junit.Test;

import java.io.File;
import java.io.IOException;

/**
 * Created by huangjinlong7 on 2017/9/27.
 */
public class LuceneTest {
    String path = this.getClass().getResource("/").getPath() + "/index";

    @Test
    public void createIndexer() throws IOException {
        CreateIndexer createIndexer = CreateIndexer.getInstance();
        createIndexer.initWriter();
        System.out.println(createIndexer.createIndexs(new TestFileFilter()));
        createIndexer.close();


    }

    @Test
    public void searchIndexer() throws Exception {
        SearchIndexer si = new SearchIndexer();
        si.search("china");
        si.searchTerm("world");
        CreateIndexsByStr cis = new CreateIndexsByStr();
        cis.createIndexs();
    }

    @Test
    public void deleteDocument() throws IOException {
        CreateIndexer createIndexer = CreateIndexer.getInstance();
        createIndexer.initWriter();
        IndexWriter writer =  CreateIndexer.writer;
        writer.deleteDocuments(new Term("id", "2"));
        writer.commit();
        System.out.println(writer.hasDeletions());
        System.out.println(writer.maxDoc());
        System.out.println(writer.numDocs());
        writer.close();
        System.out.println();

    }

    @Test
    public void indexWriterLockTest() throws IOException {
        Directory dir = FSDirectory.open(new File(this.getClass().getResource("").getPath() + "/index"));
        IndexWriter w1 = new IndexWriter(dir, new SimpleAnalyzer(), false, IndexWriter.MaxFieldLength.UNLIMITED);
        IndexWriter w2 = null;

        w2 = new IndexWriter(dir, new SimpleAnalyzer(), false, IndexWriter.MaxFieldLength.UNLIMITED);
        w1.close();
    }

    @Test
    public void createIndex() throws IOException {
        String path = this.getClass().getResource("/").getPath() + "/index";
        IndexerManager.getInstance().createIndex(path);
    }

    @Test
    public void searchTerm() throws IOException {
        String path = this.getClass().getResource("/").getPath() + "/index";
        Directory dir = IndexerManager.getInstance().getDirectory(path);
        IndexSearcher indexSearcher = new IndexSearcher(dir);

        Term t = new Term("contents", "lots");
        Query q = new TermQuery(t);
        TopDocs docs = indexSearcher.search(q, 10);
        System.out.println(docs.totalHits);
        for(ScoreDoc doc : docs.scoreDocs){
            Document d = indexSearcher.doc(doc.doc);
            System.out.println(d.get("id") + ":" + d.get("contents") + ":" + d.get("country"));
        }
    }

    @Test
    public void nearTimeSearch() throws Exception {
        IndexerManager indexerManager = IndexerManager.getInstance();
        indexerManager.createIndex(path);

        IndexWriter iw = indexerManager.getIndexWriter();

        IndexReader ir = iw.getReader();
        IndexSearcher is = new IndexSearcher(ir);

        Term t = new Term("contents", "lots");
        Query q = new TermQuery(t);

        TopDocs docs = is.search(q, 10);
        System.out.println(docs.totalHits);
        for(ScoreDoc doc : docs.scoreDocs){
            Document d = is.doc(doc.doc);
            System.out.println(d.get("id") + ":" + d.get("contents") + ":" + d.get("country"));
        }

        Thread.sleep(30000);
    }

    @Test
    public void nearTimeSearchCompare() throws Exception {
        IndexerManager indexerManager = IndexerManager.getInstance();
        indexerManager.createIndex(path);
        IndexWriter iw = indexerManager.getIndexWriter();
        iw.deleteAll();
        iw.commit();
        indexerManager.createIndex(path);


        Directory dir = indexerManager.getDirectory(path);
        IndexReader ir = IndexReader.open(dir);
        IndexSearcher is = new IndexSearcher(ir);

        Term t = new Term("contents", "lots");
        Query q = new TermQuery(t);

        TopDocs docs = is.search(q, 10);
        System.out.println(docs.totalHits);
        for(ScoreDoc doc : docs.scoreDocs){
            Document d = is.doc(doc.doc);
            System.out.println(d.get("id") + ":" + d.get("contents") + ":" + d.get("country"));
        }

        iw.commit();
        iw.close();
        dir.close();



    }
}
