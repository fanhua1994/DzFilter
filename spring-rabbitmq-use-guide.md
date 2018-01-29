### 配置文档如下
摘自https://www.cnblogs.com/libra0920/p/6230421.html
```
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
     xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
     xmlns:context="http://www.springframework.org/schema/context"
     xmlns:util="http://www.springframework.org/schema/util"
     xmlns:aop="http://www.springframework.org/schema/aop"
     xmlns:tx="http://www.springframework.org/schema/tx"
     xmlns:rabbit="http://www.springframework.org/schema/rabbit"
     xmlns:p="http://www.springframework.org/schema/p"
     xsi:schemaLocation="
     http://www.springframework.org/schema/context
     http://www.springframework.org/schema/context/spring-context-3.0.xsd
     http://www.springframework.org/schema/beans
     http://www.springframework.org/schema/beans/spring-beans-3.0.xsd
     http://www.springframework.org/schema/util
     http://www.springframework.org/schema/util/spring-util-3.0.xsd
     http://www.springframework.org/schema/aop
     http://www.springframework.org/schema/aop/spring-aop-3.0.xsd
     http://www.springframework.org/schema/tx
     http://www.springframework.org/schema/tx/spring-tx-3.0.xsd
     http://www.springframework.org/schema/rabbit
     http://www.springframework.org/schema/rabbit/spring-rabbit-1.0.xsd">
     
    <!-- RabbitMQ start -->
    <!-- 连接配置 -->
    <rabbit:connection-factory id="connectionFactory" host="${mq.host}" username="${mq.username}"
        password="${mq.password}" port="${mq.port}"  />
        
    <rabbit:admin connection-factory="connectionFactory"/>
    
    <!-- 消息队列客户端 -->
    <rabbit:template id="amqpTemplate" exchange="${mq.queue}_exchange" connection-factory="connectionFactory"  />
    
    <!-- queue 队列声明 -->
    <!-- 
        durable 是否持久化 
        exclusive 仅创建者可以使用的私有队列，断开后自动删除 
        auto-delete 当所有消费端连接断开后，是否自动删除队列 -->
    <rabbit:queue id="test_queue" name="${mq.queue}_testQueue" durable="true" auto-delete="false" exclusive="false" />
    
    <!-- 交换机定义 -->
    <!-- 
        交换机：一个交换机可以绑定多个队列，一个队列也可以绑定到多个交换机上。
        如果没有队列绑定到交换机上，则发送到该交换机上的信息则会丢失。
        
        direct模式:消息与一个特定的路由器完全匹配，才会转发
        topic模式:按模式匹配
     -->
    <rabbit:topic-exchange name="${mq.queue}_exchange" durable="true" auto-delete="false">
        <rabbit:bindings>
            <!-- 设置消息Queue匹配的pattern (direct模式为key) -->
            <rabbit:binding queue="test_queue" pattern="${mq.queue}_patt"/>
        </rabbit:bindings>
    </rabbit:topic-exchange>
    
    <bean name="rabbitmqService" class="com.enh.mq.RabbitmqService"></bean>
    
    <!-- 配置监听 消费者 -->
    <rabbit:listener-container connection-factory="connectionFactory" acknowledge="auto">
        <!-- 
            queues 监听队列，多个用逗号分隔 
            ref 监听器 -->
        <rabbit:listener queues="test_queue" ref="rabbitmqService"/>
    </rabbit:listener-container>
</beans>
```