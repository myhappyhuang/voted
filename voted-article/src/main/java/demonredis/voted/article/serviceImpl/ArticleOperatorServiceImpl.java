package demonredis.voted.article.serviceImpl;

import com.demonredis.voted.article.service.ArticleOperatorService;
import com.demoredis.voted.RedisUtill;
import redis.clients.jedis.Jedis;

import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * Created by huangjinlong7 on 2017/6/30.
 */
public class ArticleOperatorServiceImpl implements ArticleOperatorService {

    private final Jedis jedis = RedisUtill.getInstance();
    //article:time key
    private final String ARTICLE_TIME = "articleTime";
    //article:score key
    private final String ARTICLE_SCORE = "articleScore";

    //page limit
    private final Long PAGE_LIMIT = 10L;

    public String postArticle(String user, String title, String link) {
        //获取文章id
        Long articleId = jedis.incr("articleId");

        //voted set key
        String voted = "voted:" + articleId;
        //article hash key
        String article = "article:" + articleId;

        Long time = System.currentTimeMillis();

        //create article to article
        Map<String, String> cont = new HashMap<String, String>();
        cont.put("title", title);
        cont.put("link", link);
        cont.put("poster", user);
        cont.put("votes", 1 + "");
        cont.put("time", String.valueOf(time));
        jedis.hmset(article, cont);
        //add user to voted
        jedis.sadd(voted, user);
        //add article to article-time
        jedis.zadd(ARTICLE_TIME, time, article);
        //add article to article-score
        jedis.zadd(ARTICLE_SCORE,432, article);

        return article;
    }

    public String getArticles(int page, String order) {
        Long start = (page - 1) * PAGE_LIMIT;
        Long end = page * PAGE_LIMIT;
        Set<String> set = jedis.zrange("articleScore", start, end);
        Iterator<String> ite = set.iterator();
        while(ite.hasNext()){
            Map<String, String> map = jedis.hgetAll(ite.next());
            System.out.println(map.get("title"));
            System.out.println(map.get("votes"));
        }
        return null;
    }

    public boolean voteArticle(String articleId, String user){
        double time = jedis.zscore(ARTICLE_TIME, "article:" + articleId);
        if(time + 7 * 24 * 60 * 60 * 1000 > System.currentTimeMillis()){
            if(jedis.sadd("voted:" + articleId, user) > 0){
                jedis.zincrby(ARTICLE_TIME, 432, "article:" + articleId);
                jedis.hincrBy("article:" + articleId, "votes", 1);
            }
        }
        return false;
    }
}
