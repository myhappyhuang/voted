package com.demonredis.voted.article.service;

/**
 * Created by huangjinlong7 on 2017/6/30.
 */
public interface ArticleOperatorService {

    String postArticle(String user, String title, String link);

    String getArticles(int page, String order);

    boolean voteArticle(String articleId, String user);
}
