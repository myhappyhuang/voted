import demonredis.voted.article.serviceImpl.ArticleOperatorServiceImpl;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.LinkedBlockingQueue;
import org.junit.Test;
/**
 * Created by huangjinlong7 on 2017/7/1.
 */
public class VoetedTest {

    @Test
    public void testPostArticle(){
        ArticleOperatorServiceImpl imp = new ArticleOperatorServiceImpl();
        System.out.println(imp.postArticle("huangjinlong", "天龙八部秘籍整理", "www.tianlong.com"));
    }

    @Test
    public void testgetArticles(){
        ArticleOperatorServiceImpl imp = new ArticleOperatorServiceImpl();
        imp.getArticles(1, "");
    }

    @Test
    public void testvoteArticles(){
        ArticleOperatorServiceImpl imp = new ArticleOperatorServiceImpl();
        imp.voteArticle("3", "jun");

        ArrayBlockingQueue<String> queue = new ArrayBlockingQueue<String>(2);
        LinkedBlockingQueue linkedBlockingQueue = new LinkedBlockingQueue();

    }

}
