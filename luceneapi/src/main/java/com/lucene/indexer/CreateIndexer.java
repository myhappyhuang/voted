package com.lucene.indexer;

import org.apache.lucene.analysis.standard.StandardAnalyzer;
import org.apache.lucene.document.Document;
import org.apache.lucene.document.Field;
import org.apache.lucene.index.IndexWriter;
import org.apache.lucene.store.Directory;
import org.apache.lucene.store.FSDirectory;
import org.apache.lucene.util.Version;

import java.io.*;

/**
 * Created by huangjinlong7 on 2017/9/27.
 */
public class CreateIndexer {
   private String indexDir = this.getClass().getResource("/").getPath() + "index";
   private String dataDir = this.getClass().getResource("/").getPath() + "/data";
   public static IndexWriter writer;
   private static CreateIndexer createIndexer = new CreateIndexer();

   /**
   *@Description:单例模式实现IndexWriter的管理
   *@Date:10:53 2017/9/27
   * @param
   */
   public static CreateIndexer getInstance(){
       return createIndexer;
   }

   public void initWriter() throws IOException {
       Directory dir = FSDirectory.open(new File(this.indexDir));
       writer = new IndexWriter(dir, new StandardAnalyzer(Version.LUCENE_30), true, IndexWriter.MaxFieldLength.UNLIMITED);
   }

   public void close(){
       if (writer != null) {
           try {
               writer.close();
           } catch (IOException e) {
               e.printStackTrace();
           }
       }
   }

    public int createIndexs(FileFilter filter) throws IOException {
       File[] files = new File(this.dataDir).listFiles();
        for (File f : files) {
            if (filter == null || filter.accept(f)) {
                indexFile(f);
            }
        }
        return writer.numDocs(); //返回被索引的文档数量
    }

    protected Document getDocument(File f) throws IOException {
        Document doc = new Document();
        doc.add(new Field("contents", new FileReader(f)));
        doc.add(new Field("filename", f.getName(), Field.Store.YES, Field.Index.NOT_ANALYZED));
        doc.add(new Field("fullpath", f.getCanonicalPath(), Field.Store.YES, Field.Index.NOT_ANALYZED));
        return doc;
    }

    private void indexFile(File file) throws IOException {
        Document doc = getDocument(file);
        writer.addDocument(doc);
    }





}
