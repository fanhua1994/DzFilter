## Spring使用指南
> DzFilter支持在Spring项目中
### 配置ActiveMQ数据源
```
<!-- 配置数据源配置文件 -->
<context:property-placeholder location="classpath*:dzfilter_config.properties" />
<!-- 真正可以产生Connection的ConnectionFactory，由对应的 JMS服务厂商提供 -->
<bean id="targetConnectionFactory" class="org.apache.activemq.ActiveMQConnectionFactory">
        <property name="brokerURL" value="${dzfilter.cluster.activemq}" />
        <property name="userName" value="${dzfilter.cluster.username}" />
        <property name="password" value="${dzfilter.cluster.password}" />
</bean>

<!-- 配置JMS连接工长 -->
<bean id="connectionFactory"
        class="org.springframework.jms.connection.CachingConnectionFactory">
        <constructor-arg ref="targetConnectionFactory" />
        <property name="sessionCacheSize" value="100" />
</bean>
```
### 配置通道名
```
 <bean id="topicDestination" class="org.apache.activemq.command.ActiveMQTopic">
        <constructor-arg value="${dzfilter.cluster.channel_name}"/>
</bean>
```
### 配置监听器
```
<!-- 消息监听器 -->
<bean id="consumerMessageListener"   class="com.hengyi.dzfilter.listener.SyncMessageListener" />
<!-- 消息监听容器 -->
<bean id="jmsContainer"
        class="org.springframework.jms.listener.DefaultMessageListenerContainer">
        <property name="connectionFactory" ref="connectionFactory" />
        <property name="destination" ref="topicDestination" />
        <property name="messageListener" ref="consumerMessageListener" />
</bean>
```

