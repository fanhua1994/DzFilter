package com.zhiweism.hanlp_demo;

import com.zhiweism.text.keywords.KeywordUtils;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {
    	String result = KeywordUtils.delTag("4324<html>2342342</html>3<script type='javascript'>rrrrr</script>啊啊啊");
    	System.out.println(result);
    }
}
