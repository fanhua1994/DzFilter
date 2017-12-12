# JavaFilterDemo
使用DFA算法实现的敏感词过滤，包括关键词提取，自动摘要，过滤html标签等。主要用于实现数据的清洗和数据快速定位。

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
    <version>1.0.0</version>
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

## 配置sqlite数据库
> 数据库文件在项目目录下的database下。放到对应目录  配置好就行了。如果想使用mysql的话，请自行导入mysql驱动，并将database目录下的java_filter.sql导入mysql然后在filter_config.properties配置 用户名密码等信息即可。
```
dbhelper.driver=org.sqlite.JDBC
dbhelper.dburl=jdbc:sqlite:F:\\data_filter20171211.db
dbhelper.dbuser=
dbhelper.dbpass=
dbhelper.is_mysql=false
```


