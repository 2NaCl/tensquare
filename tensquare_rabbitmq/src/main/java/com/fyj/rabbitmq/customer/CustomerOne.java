package com.fyj.rabbitmq.customer;


import org.springframework.amqp.rabbit.annotation.RabbitHandler;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@RabbitListener(queues = "TopicTest1")//声明为消费者
@Component//添加进去容器
public class CustomerOne {

    @RabbitHandler
    public void getMsg(String msg) {
        System.out.println("good.#分列模式消息消费："+msg);
    }

}
