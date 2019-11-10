package com.fyj.search.pojo;

import org.springframework.data.annotation.Id;
import org.springframework.data.elasticsearch.annotations.Document;
import org.springframework.data.elasticsearch.annotations.Field;

import java.io.Serializable;

/**
 * 添加document
 */
@Document(indexName = "tensquare_article",type = "article")
public class Article implements Serializable {

    @Id
    private String id;//对应刚才json的id，唯一标识

    /**
     * 是否索引，就是看该字段是否能被搜索到
     * 是否分词，就表示搜索的时候是整体匹配还是分词匹配
     * 是否存储，就是是否在页面上显示
     */
    @Field(index = true,searchAnalyzer = "ik_max_word",analyzer = "ik_max_word")//此注解对应着type中的列
    // 后面是分词器，分词按照analyzer分，搜索分词按照searchAnalyzer搜索，两边必须一致

    private String title;
    @Field(index = true,searchAnalyzer = "ik_max_word",analyzer = "ik_max_word")
    private String content;

    private String state;//审核状态

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }
}
