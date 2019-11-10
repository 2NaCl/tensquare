package com.fyj;


import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;
import org.bson.Document;

/**
 * 复杂查询
 */
public class MongoTest2 {
    public static void main(String[] args) {
        //链接mongo服务器
        MongoClient client = new MongoClient("127.0.0.1");
        //得到要操作的数据库
        MongoDatabase spitDB = client.getDatabase("spitDB");
        //得到要操作的集合
        MongoCollection<Document> spit = spitDB.getCollection("spit");
        //封装查询条件
        //BasicDBObject bson = new BasicDBObject("visits", 10);//查询visits=10的那一列
        BasicDBObject bson = new BasicDBObject("visits", new BasicDBObject("$gt", 100));//$gt是大于的意思，查询visits>1000的消息
        //得到集合中所有的文档
        FindIterable<Document> documents = spit.find(bson);
        //遍历数据
        for (Document document : documents) {
            System.out.println("内容：" + document.getString("content"));
            System.out.println("访问量：" + document.getDouble("visits"));
        }
        client.close();
    }
}
