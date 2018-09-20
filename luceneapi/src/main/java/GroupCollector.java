//import org.apache.lucene.index.AtomicReaderContext;
//import org.apache.lucene.search.Collector;
//import org.apache.lucene.search.FieldCache;
//import org.apache.lucene.search.Scorer;
//import org.apache.lucene.util.BytesRef;
//
//import java.io.IOException;

/**
 * Created by huangjinlong7 on 2017/9/23.
 */
//public class GroupCollector extends Collector {
//
////    private GroupField count = new GroupField();
////    //需要统计的字段
////    private String countField;
////
////    FieldCache.DocTerms terms = null;
////    int docBase;
////
////
////    public GroupField getCount() {
////        return count;
////    }
////
////    public void setCount(GroupField count) {
////        this.count = count;
////    }
////
////    public String getCountField() {
////        return countField;
////    }
////
////    public void setCountField(String countField) {
////        this.countField = countField;
////    }
////
////    @Override
////    public void setScorer(Scorer scorer) throws IOException {
////
////    }
////
////    @Override
////    public void collect(int doc) throws IOException {
////        final BytesRef ref = new BytesRef();
////        BytesRef result = terms.getTerm(doc, ref);
////        String value = result.utf8ToString();
////        count.addValue(value);
////    }
////
////    @Override
////    public void setNextReader(AtomicReaderContext context) throws IOException {
////        terms = FieldCache.DEFAULT.getTerms(context.reader(), countField);
////        docBase = context.docBase;
////    }
////
////    @Override
////    public boolean acceptsDocsOutOfOrder() {
////        return true;
////    }
//}
