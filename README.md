# copyright
> 不管您是个人还是公司都可以随意使用`DzFilter`,公司或个人用于商业情况时，必须要[issues](https://github.com/fanhua1994/DzFilter/issues)中提出使用说明得到同意后方可使用。任何人可以随意修改源码，修改后请务必发一份修改的后的源码到作者邮箱（90fanhua@gmail.com）。


# 公告
+ 目前1.0.7已经完善，暂时不支持sqlite强一致性，不能100%保证一致。
+ 修复了集群情况下，重复通知的BUG。

# 使用指南
+ [Spring配置指南](https://github.com/fanhua1994/DzFilter/blob/master/spring-use-guide.md)
+ [SpringMvcDemo](https://github.com/fanhua1994/DzFilterSpringDemo)
+ SpringBoot尽情期待...
+ Java se 尽情期待...

# DzFilter
目前最新版：1.0.7[[更新日志](https://github.com/fanhua1994/DzFilter/blob/master/log.md)]

使用DFA算法实现的敏感词过滤。主要用于实现数据文本的内容安全,反垃圾,智能鉴黄,敏感词过滤,不良信息检测，携带文本的关键词获取。
+ 过滤SQL脚本
+ 过滤中文字符
+ 过滤英文字符
+ 过滤script标签
+ 过滤html标签
+ 过滤数字
+ 过滤字母
+ 过滤汉字
+ 自定义过滤，可由后台自动删除添加。提供完善的API。


# 如何使用
## 导入以下仓库
```
<repositories>
	<repository>
	    <id>jitpack.io</id>
	    <url>https://jitpack.io</url>
	</repository>
</repositories>
```
## 添加Dependency
```
<dependency>
    <groupId>com.github.fanhua1994</groupId>
    <artifactId>DzFilter</artifactId>
    <version>1.0.7</version>
</dependency>
```

## 功能详解
### 过滤敏感词
```
String filter(String content)
```
### 判断是否包含敏感词
```
boolean existFilter(String content)
```


## 配置数据库
> 如果```DzFilter```您需要使用在分布式的系统或同时在多个程序中使用```DzFilter```并且需要保持一致性，那么我们建议您使用mysql数据库，如果是单击应用我们建议您使用sqlite数据库。数据库文件在项目目录下的database下。放到对应目录  配置好就行了。如果想使用mysql的话，请自行导入mysql驱动，并将database目录下的java_filter.sql导入mysql然后在dzfilter_config.properties配置 用户名密码等信息即可。
```
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>5.1.45</version>
</dependency>
```
Mysql配置文件如下
```
dzfilter.db.driver=org.sqlite.JDBC
dzfilter.db.dburl=jdbc:mysql://120.77.245.101:3306/filter_word?useUnicode\=true&characterEncoding\=utf-8&autoReconnect\=true
dzfilter.db.is_mysql=true
#如何您是使用mysql请配置以下信息
dzfilter.db.dbuser=root
dzfilter.db.dbpass=*****

# 如果不是集群 请忽略以下配置
dzfilter.cluster.open=true
dzfilter.cluster.activemq=tcp://192.168.0.106:61616
dzfilter.cluster.server_id=192.168.0.105
dzfilter.cluster.channel_name=dzfilter
dzfilter.cluster.username=admin
dzfilter.cluster.password=admin
```
SQLite配置如下
```
dzfilter.db.driver=org.sqlite.JDBC
dzfilter.db.dburl=jdbc:sqlite:/home/fanhua/database/data_filter20171211.db
dzfilter.db.is_mysql=false
#不是Mysql不用填写
dzfilter.db.dbuser=root
dzfilter.db.dbpass=***

# 目前在SQLite数据库上暂时不支持集群模式
dzfilter.cluster.open=false
dzfilter.cluster.activemq=tcp://192.168.0.106:61616
dzfilter.cluster.server_id=192.168.0.105
dzfilter.cluster.channel_name=dzfilter
dzfilter.cluster.username=admin
dzfilter.cluster.password=admin
```

## 集群配置
如果您想要在多个项目中使用DzFilter请先安装activemq消息队列，在配置文件中进行配置，每个项目最好不要放到一台服务区，`dzfilter.cluster.server_id`参数对数据至关重要，请保证全局唯一。`dzfilter.cluster.open`请保证为true开启状态，
多个项目之间需要保证`dzfilter.cluster.channel_name`参数一致。
您只需要在多个项目中同时使用Activemq消费者监听`com.hengyi.dzfilter.listener.SyncMessageListener`即可。

## 部分核心API

### 添加自定义关键词过滤
```
Boolean res = TextUtils.addFilter(String keywords);
```
### 删除自定义关键词过滤
```
boolean res = TextUtils.delFilter(String keywords);
```
### 分页获取添加的关键词
```
List<Map<String,Object>> rows = TextUtils.getDataPage(int page,int num);//第一个参数页数 1开始 第二个参数每页条数
List<Map<String,Object>> rows = TextUtils.getDataOffset(int offset,int limit);第一个参数偏移量 0开始 第二个参数每页条数
```
### 自定义关键词总数
```
int total = TextUtils.getDataTotal();
```
### 刷新敏感词
```
TextUtils.sync(boolean sync);
```

`sync`参数是否同步刷新整个集群。仅mysql数据库生效、

### 关键词提取
```
String extractKeyword(String content,int count,boolean isPinyin,String separator)
List<String> extractKeyword(String content,int count);
```
`content`需要提取关键词的文本，
`count`取药提取的关键词总数，会按照投票选举。
`isPinyin`是否将关键词转化为拼音。
`separator`设置分隔符。

demo: 
```
String k = TextUtils.extractKeyword("今天是我的生日",3,true,"#");
k = "jintian#shengri#wode";
```


```
Copyright 2018 fanhua1994

Licensed under the Apache License, Version 2.0 (the "License");
you may not use this file except in compliance with the License.
You may obtain a copy of the License at

http://www.apache.org/licenses/LICENSE-2.0

Unless required by applicable law or agreed to in writing, software
distributed under the License is distributed on an "AS IS" BASIS,
WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
See the License for the specific language governing permissions and
limitations under the License.
```