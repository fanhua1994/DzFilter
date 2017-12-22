# JavaFilter
使用DFA算法实现的敏感词过滤。主要用于实现数据文本的内容安全,反垃圾,智能鉴黄,敏感词过滤,不良信息检测，携带文本的关键词获取，自动摘要。
+ 过滤SQL脚本
+ 过滤中文字符
+ 过滤英文字符
+ 过滤script标签
+ 过滤html标签
+ 过滤数字
+ 过滤字母
+ 过滤汉字
+ 自定义过滤，可由后台自动删除添加。提供完善的API。位于FilterDao文件下。

# 文本校验框架
## Maven 
```
<repositories>
	<repository>
	    <id>jitpack.io</id>
	    <url>https://jitpack.io</url>
	</repository>
</repositories>
```
```
<dependency>
    <groupId>com.github.fanhua1994</groupId>
    <artifactId>java_validation</artifactId>
    <version>1.0.0</version>
</dependency>
```
文档如下：
https://github.com/fanhua1994/java_validation

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
    <artifactId>JavaFilter</artifactId>
    <version>1.0.5</version>
</dependency>
```

## TextUtils使用请看源码

## 过滤日志
> 过滤库仅仅加载一次，会全部加载到内存，所以要注意不能大于5000个。
```
加载时间 : 229233221ns
加载时间 : 229ms
我是***，我为自己代言
```

## 配置数据库
> 如果该程序您需要使用在分布式的系统或同时在多个程序中使用该程序并且需要保持一致性，那么我们建议您使用mysql数据库，如果是单击应用我们建议您使用sqlite数据库。数据库文件在项目目录下的database下。放到对应目录  配置好就行了。如果想使用mysql的话，请自行导入mysql驱动，并将database目录下的java_filter.sql导入mysql然后在filter_config.properties配置 用户名密码等信息即可。
```
<!-- https://mvnrepository.com/artifact/mysql/mysql-connector-java -->
<dependency>
    <groupId>mysql</groupId>
    <artifactId>mysql-connector-java</artifactId>
    <version>5.1.45</version>
</dependency>
```
Mysql配置文件如下
```
dbhelper.driver=com.mysql.jdbc.Driver
dbhelper.dburl=jdbc:mysql://120.77.245.101:3306/filter_word?useUnicode\=true&characterEncoding\=utf-8&autoReconnect\=true
dbhelper.dbuser=root
dbhelper.dbpass=****
dbhelper.is_mysql=true
```
SQLite配置文件如下
```
dbhelper.driver=org.sqlite.JDBC
dbhelper.dburl=jdbc:sqlite:F:\\data_filter20171211.db
dbhelper.dbuser=
dbhelper.dbpass=
dbhelper.is_mysql=false
```

## 如何自定义过滤服务

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

