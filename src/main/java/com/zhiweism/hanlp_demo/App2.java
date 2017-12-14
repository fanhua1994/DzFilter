package com.zhiweism.hanlp_demo;

import java.util.Scanner;

import com.zhiweism.text.utils.ZookeeperClient;

/**
 * Hello world!
 *
 */
public class App2 implements Runnable
{
	
    public static void main( String[] args )
    {
    	Thread thread = new Thread(new App2());
    	thread.start();
    	
    	System.out.println("等待关闭服务");
    	
    	Scanner sc=new Scanner(System.in);
		sc.nextLine();
		
		System.out.println("程序已经关闭");
    }

	public void run() {
		try {
			System.out.println("线程已经启动");
			ZookeeperClient zookeeperClient = ZookeeperClient.getInstance();
			zookeeperClient.connect("127.0.0.1:2181");
			zookeeperClient.setData("/dong16", "345".getBytes());
			zookeeperClient.close();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
