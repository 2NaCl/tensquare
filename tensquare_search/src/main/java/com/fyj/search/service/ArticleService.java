package com.fyj.search.service;

import com.fyj.search.dao.ArticleDao;
import com.fyj.search.pojo.Article;
import com.fyj.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.elasticsearch.core.ElasticsearchTemplate;
import org.springframework.stereotype.Service;

@Service
public class ArticleService {

    @Autowired
    private ArticleDao articleDao;

    @Autowired
    private IdWorker idWorker;

    public void save(Article article) {
        article.setId(idWorker.nextId() + "");//不写这行也可以，就直接用默认的id了
        articleDao.save(article);
    }

    public Page<Article> findByKey(String key, int page, int size) {
        Pageable pageable = PageRequest.of(page - 1, size);
        return articleDao.findByTitleOrContentLike(key, key, pageable);
    }
}
