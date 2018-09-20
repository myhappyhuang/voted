import org.elasticsearch.action.search.SearchRequestBuilder;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.action.search.SearchType;
import org.elasticsearch.client.transport.TransportClient;
import org.elasticsearch.common.settings.Settings;
import org.elasticsearch.common.transport.InetSocketTransportAddress;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.elasticsearch.search.SearchHits;
import org.elasticsearch.search.aggregations.Aggregation;
import org.elasticsearch.search.aggregations.AggregationBuilders;
import org.elasticsearch.search.aggregations.bucket.terms.StringTerms;
import org.elasticsearch.search.aggregations.bucket.terms.Terms;
import org.elasticsearch.transport.client.PreBuiltTransportClient;

import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Pattern;

/**
 * Created by huangjinlong7 on 2017/9/21.
 */
public class ElasticSearchApi {

    public static void main(String[] args) throws UnknownHostException {
        Settings settings = Settings.builder().put("client.transport.sniff", false).put("cluster.name","hik-ga-es").put("client.transport.ignore_cluster_name",false).build();
        TransportClient client = new PreBuiltTransportClient(settings);
        client.addTransportAddress(new InetSocketTransportAddress(InetAddress.getByName("10.33.49.44"), 9300));
        client.connectedNodes();
        SearchRequestBuilder srb = client.prepareSearch("index");

        //查询
        srb.setTypes("LOG_OPERATION");
        srb.setSearchType(SearchType.DFS_QUERY_THEN_FETCH);
        srb.setExplain(false);
        srb.setFrom(0);
        srb.setSize(1000);

        Pattern patternLeft = Pattern.compile("\\]");
        String s = "17102506305260312010";
        //s = "组节";
        String s1 = patternLeft.matcher(s).replaceAll("\\\\]");
        //拼装查询条件
        String queryStr = "content:" + s + "*";
        QueryStringQueryBuilder queryBuilder = new QueryStringQueryBuilder(queryStr);

        queryBuilder.analyzeWildcard(true);
        queryBuilder.field("treeNodePath");
        queryBuilder.field("appCode");
        queryBuilder.field("treeNodePathStr");
        queryBuilder.field("ip");
        queryBuilder.defaultOperator(Operator.AND);
        srb.setQuery(queryBuilder);
        //srb.setQuery(new WildcardQueryBuilder("ip", "10.6.*"));

        //拼装聚合
        // AggregationBuilder agg = AggregationBuilders.terms("resIndexCount").field("treeNodePath");
        //如果没有设置size，则默认取前10个聚合结果，所以要设置size
        //srb.addAggregation(AggregationBuilders.terms("resIndexCount").field("treeNodePathStr").size(Integer.MAX_VALUE));

        System.out.println(srb.toString());
        SearchResponse searchResponse = srb.execute().actionGet();
        SearchHits hits = searchResponse.getHits();
        System.out.println(hits.getTotalHits());
        SearchHit[] searchHists = hits.getHits();
        if(searchHists.length>0){
            for(SearchHit hit:searchHists){
                System.out.println(hit.getSource().get("ip") + ":" + hit.getSource().get("content") + ":" + hit.getSource().get("treeNodePathStr"));
            }
        }


//        Map<String, Aggregation> map = searchResponse.getAggregations().asMap();
//        StringTerms restIndexCount =  (StringTerms) map.get("resIndexCount");
//        Iterator<Terms.Bucket> res = restIndexCount.getBuckets().iterator();
//        int total = 0;
//        while (res.hasNext()) {
//            Terms.Bucket bucket = res.next();
//            System.out.println(bucket.getKeyAsString() + ":" + bucket.getDocCount());
//            total = total + Integer.valueOf(bucket.getDocCount() + "");
//        }
//        System.out.println(total);


    }

}
