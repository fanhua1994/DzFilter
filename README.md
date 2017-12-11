# JavaFilterDemo
使用DFA算法实现的敏感词过滤，包括关键词提取，自动摘要，过滤html标签等。

## 过滤日志
> 过滤库仅仅加载一次，会全部加载到内存，所以要注意不能大于5000个。
```
加载时间 : 229233221ns
加载时间 : 229ms
我是***，我为自己代言
```

## 配置sqlite数据库
> 数据库文件在项目目录下的database下。放到对应目录  配置好就行了
```
dbhelper.driver=org.sqlite.JDBC
dbhelper.dburl=jdbc:sqlite:F:\\data_filter20171211.db
dbhelper.dbuser=
dbhelper.dbpass=
dbhelper.is_mysql=false
```

## 加入以下maven
```
<dependency>
    <groupId>com.hankcs</groupId>
    <artifactId>hanlp</artifactId>
    <version>portable-1.5.2</version>
</dependency>

<dependency>
	<groupId>org.xerial</groupId>
	<artifactId>sqlite-jdbc</artifactId>
	<version>3.7.2</version>
</dependency> 
```
