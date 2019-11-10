package com.fyj;

import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

import java.util.HashMap;
import java.util.Map;

/**
 * 添加数据
 */
public class MongoTest3 {
    public static void main(String[] args) {
        //链接mongo服务器
        MongoClient client = new MongoClient("127.0.0.1");
        //得到要操作的数据库
        MongoDatabase spitDB = client.getDatabase("spitDB");
        //得到要操作的集合（表）
        MongoCollection<Document> spit = spitDB.getCollection("spit");
        //添加一条记录
        Map<String, Object> map = new HashMap<>();
        map.put("content", "时间就像一阵风");
        map.put("visits", 100.0);
        Document document = new Document(map);
        spit.insertOne(document);

        //添加之后进行简单查询
        FindIterable<Document> documents = spit.find();
        for (Document document1 : documents) {
            System.out.println("内容：" + document1.getString("content"));
            System.out.println("访问量：" + document1.getDouble("visits"));
        }

        client.close();

    }
}
