# 1.0.5【已发布】
1.修复敏感词问题。

# 1.0.6【未发布】
1.修复敏感词未同步更新造成的问题；
2.添加文本校验功能。
3.目前正在解决分布式的部署情况下，敏感词不同步问题，目前考虑使用zookeeper实现自动同步；
4.解决因实现自动关键词提取而导致的包体太大的问题。
## 关键词替换方案已找到
以下是与HanLp对比的结果
```
 String key = "威远县隶属四川省内江市，地处内江市西北部，位于四川盆地中南部，地跨北纬29°22′～29°47′，东经104°16′～104°53′之间。东邻内江市市中区，南连自贡市大安区和贡井区，西界自贡市荣县，北衔资中县，西北与眉山市仁寿县、乐山市井研县接壤。";
	 List<String> data = HanLP.extractKeyword(key, 10);
	 System.out.println("HanKp:" + data.toString());
	 try {
		List<String> data2 = KeywordUtils.getKeyWords(key, 10);
		System.out.println("IK:" + data2.toString());
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
```
输出如下：我们搜索的是威远县  HanLp并没有显示相关内容
```
HanKp:[内江市, 自贡市, 眉山市, 西北, 资中县, 荣县, 四川, 中南部, 之间, 南连]
IK:[内江市, 自贡市, 大安区, 市中区, 四川盆地, 威远县, 资中县, 荣县, 四川省, 中南部]
```