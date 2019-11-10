package com.fyj.search.controller;

import com.fyj.entity.PageResult;
import com.fyj.entity.Result;
import com.fyj.entity.StatusCode;
import com.fyj.search.pojo.Article;
import com.fyj.search.service.ArticleService;
import com.fyj.util.IdWorker;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.annotation.Id;
import org.springframework.data.domain.Page;
import org.springframework.web.bind.annotation.*;

@RestController
@CrossOrigin
@RequestMapping("/article")
public class ArticleController {

    @Autowired
    private ArticleService articleService;

    @PostMapping
    public Result save(@RequestBody Article article) {
        articleService.save(article);
        return new Result(true, StatusCode.OK, "添加成功");
    }

    @GetMapping("/{key}/{page}/{size}")
    public Result findByKey(@PathVariable String key,@PathVariable int page,@PathVariable int size) {
        Page<Article> pageData = articleService.findByKey(key, page, size);
        return new Result (true, StatusCode.OK, "查询成功", new PageResult<Article>(pageData.getTotalElements(), pageData.getContent()));
    }

}
