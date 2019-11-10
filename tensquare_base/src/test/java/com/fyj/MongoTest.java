package com.fyj;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 * 简单查询
 */
public class MongoTest {
    public static void main(String[] args) {

        //链接mongo服务器
        MongoClient client = new MongoClient("127.0.0.1");
        //得到要操作的数据库
        MongoDatabase spitDB = client.getDatabase("spitDB");
        //得到要操作的集合
        MongoCollection<Document> spit = spitDB.getCollection("spit");
        //得到集合中所有的文档
        FindIterable<Document> documents = spit.find();
        //遍历数据
        for (Document document : documents) {
            System.out.println("内容：" + document.getString("content"));
            System.out.println("访问量：" + document.getDouble("visits"));
        }
    }
}
