//import org.apache.lucene.analysis.Analyzer;
//import org.apache.lucene.analysis.core.KeywordAnalyzer;
//import org.apache.lucene.analysis.miscellaneous.PerFieldAnalyzerWrapper;
//import org.apache.lucene.analysis.standard.StandardAnalyzer;
//import org.apache.lucene.index.DirectoryReader;
//import org.apache.lucene.index.IndexReader;
//import org.apache.lucene.queryparser.classic.MultiFieldQueryParser;
//import org.apache.lucene.queryparser.classic.QueryParser;
//import org.apache.lucene.search.IndexSearcher;
//import org.apache.lucene.search.Query;
//import org.apache.lucene.store.Directory;
//import org.apache.lucene.store.FSDirectory;
//import org.apache.lucene.util.Version;
//
//import java.io.File;
//import java.io.IOException;
//import java.util.*;

/**
 * Created by huangjinlong7 on 2017/9/22.
 */
public class LuceneApi {
    public static void main(String[] args) throws Exception {
//        Directory dir = FSDirectory.open(new File("E:/index/CAMERA_INFO"));
//        IndexReader reader = DirectoryReader.open(dir);
//        IndexSearcher searcher = new IndexSearcher(reader);
//
//        Map<String, Analyzer> analyzerMap = new HashMap<String, Analyzer>();
//        analyzerMap.put("appCode", new KeywordAnalyzer());
//        analyzerMap.put("originalIndexcode", new KeywordAnalyzer());
//        analyzerMap.put("treeNodeIndexcode", new KeywordAnalyzer());
//        analyzerMap.put("deviceIdx", new KeywordAnalyzer());
//        Analyzer analyzer = new PerFieldAnalyzerWrapper(new StandardAnalyzer(Version.LUCENE_40), analyzerMap);
//
//        List<String> fieldList = new ArrayList<String>();
//        fieldList.add("treeNodePath");
//        MultiFieldQueryParser queryParser = new MultiFieldQueryParser(Version.LUCENE_40,
//                fieldList.toArray(new String[fieldList.size()]), analyzer);
//        queryParser.setDefaultOperator(QueryParser.AND_OPERATOR);
//
//        String str = "treeNodePath:350783000000AAAA";
//        Query query = queryParser.parse(str);
//
//        GroupCollector gc = new GroupCollector();
//        gc.setCountField("treeNodePathStr");
//
//        searcher.search(query, gc);
//        Map<String, Integer> map = gc.getCount().getCountMap();
//        Set<String> r = map.keySet();
//        int total = 0;
//        for (String index : r) {
//            System.out.println(index + ":" + map.get(index));
//            total = total + map.get(index);
//        }
//        System.out.println(total);
//        System.out.println(query.toString());
    }
}
