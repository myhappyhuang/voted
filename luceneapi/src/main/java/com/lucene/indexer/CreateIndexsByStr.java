package com.lucene.indexer;

import org.apache.lucene.analysis.WhitespaceAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.store.RAMDirectory;

import java.io.File;
import java.io.IOException;

/**
 * Created by huangjinlong7 on 2017/9/28.
 */
public class CreateIndexsByStr {
    private String[] ids = {"1", "2"};
    private String[] unindexed = {"Netherlands", "Italy"};
    private String[] unstored = {"Amsterdam has lots of bridges", "Venice has lots of canals"};

    private String indexDir = this.getClass().getResource("/").getPath() + "index";

    public void createIndexs() throws IOException {
        Directory directory = FSDirectory.open(new File(this.indexDir));

        //Directory d = new RAMDirectory(directory);

        IndexWriter indexWriter = new IndexWriter(directory, new WhitespaceAnalyzer(), false, IndexWriter.MaxFieldLength.UNLIMITED);

        for(int i = 0; i < 2; i++) {
            Document doc = new Document();
            doc.add(new Field("id", ids[i], Field.Store.YES, Field.Index.NOT_ANALYZED));
            doc.add(new Field("country", unindexed[i], Field.Store.YES, Field.Index.NO));
            doc.add(new Field("contents", unstored[i], Field.Store.NO, Field.Index.ANALYZED));
            indexWriter.addDocument(doc);
        }
        indexWriter.commit();
        indexWriter.close();

    }

}
