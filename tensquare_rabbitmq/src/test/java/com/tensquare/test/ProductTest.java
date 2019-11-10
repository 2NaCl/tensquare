package com.tensquare.test;


import com.fyj.rabbitmq.RabbitApplication;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)//替代Junit测试环境
@SpringBootTest(classes = RabbitApplication.class)//指定程序的入口
public class ProductTest {

    @Autowired
    private RabbitTemplate rabbitTemplate;

    /**
     * 直接模式
     */
    @Test
    public void sendMsg(){
        rabbitTemplate.convertAndSend("DirectTestQueue","test1");
    }

    /**
     * 分列模式
     */
    @Test
    public void sengMsg2() {
        rabbitTemplate.convertAndSend("FanoutExchange1","","分列模式测试");
    }

    /**
     * 主题模式
     */
    @Test
    public void sendMsg3() {
        rabbitTemplate.convertAndSend("TopicExchange1","good.abc","主题模式测试");
    }



}
