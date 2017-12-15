package com.zhiweism.hanlp_demo;

import java.util.Scanner;

import org.apache.curator.framework.recipes.cache.NodeCacheListener;

import com.zhiweism.text.utils.ZookeeperClient;

/**
 * Hello world!
 */
public class App implements Runnable
{
    public static void main( String[] args )
    {
    	Thread thread = new Thread(new App());
    	thread.start();
    	
    	System.out.println("等待关闭服务");
    	
    	Scanner sc = new Scanner(System.in);
		sc.nextLine();
		
		System.out.println("程序已经关闭");
    }

	public void run() {
		try {
			System.out.println("线程已经启动");
			ZookeeperClient zookeeperClient = ZookeeperClient.getInstance();
			
			NodeCacheListener listener = new NodeCacheListener() {
				public void nodeChanged() throws Exception {
					System.out.println("检测到数据变化");
				}
			};
			
			zookeeperClient.connect("127.0.0.1:2181");
			zookeeperClient.addNodelistener("/dong16",listener);
			zookeeperClient.createNode("/dong16", "ok");
			zookeeperClient.close();
			
			//zookeeperClient.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
