package com.zhiweism.text.utils;

import java.io.IOException;
import java.util.List;

import org.apache.curator.RetryPolicy;
import org.apache.curator.framework.CuratorFramework;
import org.apache.curator.framework.CuratorFrameworkFactory;
import org.apache.curator.framework.recipes.cache.NodeCache;
import org.apache.curator.framework.recipes.cache.NodeCacheListener;
import org.apache.curator.retry.ExponentialBackoffRetry;
import org.apache.zookeeper.CreateMode;
import org.apache.zookeeper.KeeperException;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.data.Stat;

public class ZookeeperClient implements Watcher
{
	private static ZookeeperClient instance = null;
    private CuratorFramework cf = null;
    private NodeCache nc= null;
    private static int SESSION_TIME_OUT = 2000;
    
    
    public static synchronized ZookeeperClient getInstance() {
    	if(instance == null) {
	    	synchronized (ZookeeperClient.class) {
	    		if(instance == null) {
	    			instance = new ZookeeperClient();
	    		}
			}
    	}
    	return instance;
    }

    /**
     * 连接zookeeper
     * @param host
     * @throws IOException
     * @throws InterruptedException
     */
    public void connect(String host) throws IOException, InterruptedException{
    	RetryPolicy retryPolicy = new ExponentialBackoffRetry(1000, 3);
    	
    	cf = CuratorFrameworkFactory.builder()
                .connectString(host) //连接地址
                //session超时，默认是6000ms
                .sessionTimeoutMs(SESSION_TIME_OUT) 
                //默认超时连接时间为15s
                .connectionTimeoutMs(15000)
                .retryPolicy(retryPolicy)
                .build();
    	cf.start();
    }
    
    
    public void addNodelistener(String path) throws Exception {
    	 //监听
    	nc = new NodeCache(cf, path, false);
        NodeCacheListener listener = new NodeCacheListener() {
			
			public void nodeChanged() throws Exception {
				// TODO Auto-generated method stub
				System.out.println("节点发生变化");
			}
		};
        nc.getListenable().addListener(listener);
        nc.start();
    }
    
    public void removeNodeListener() throws IOException {
    	if(nc != null)
    		nc.close();
    }

    /**
     * 根据路径创建节点，并且设置节点数据
     * @param path
     * @param data
     * @return
     * @throws Exception 
     */
    public String createNode(String path,byte[] data) throws Exception{
        return cf.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(path,data);
    }
    
    public String createNode(String path,String data) throws Exception{
        return cf.create().creatingParentsIfNeeded().withMode(CreateMode.PERSISTENT).forPath(path,data.getBytes());
    }
    
    public String createNode(String path,byte[] data,CreateMode mode) throws Exception{
        return cf.create().creatingParentsIfNeeded().withMode(mode).forPath(path,data);
    }
    

    /**
     * 根据路径获取所有孩子节点
     * @param path
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public List<String> getChildren(String path) throws Exception{
        return cf.getChildren().forPath(path);
    }

    public Stat setData(String path, byte[] data) throws Exception{
        return cf.setData().forPath(path,data);
    }

    /**
     * 根据路径获取节点数据
     * @param path
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public byte[] getByteData(String path) throws Exception {
        return cf.getData().forPath(path);
    }

    /**
     * 根据路径获取节点数据
     * @param path
     * @return
     * @throws KeeperException
     * @throws InterruptedException
     */
    public String getData(String path) throws Exception {
        byte[] buffer = cf.getData().forPath(path);
        return new String(buffer,"UTF-8");
    }


    /**
     * 删除节点
     * @param path
     * @throws InterruptedException
     * @throws KeeperException
     */
    public void deleteNode(String path) throws Exception {
        cf.delete().forPath(path);
    }

    /**
     * 关闭zookeeper连接
     * @throws InterruptedException
     * @throws IOException 
     */
    public void close() throws Exception{
        if(cf != null)
        	cf.close();
        removeNodeListener();
    }

    /**
     * 判断节点是否存在
     * @param path
     * @return
     */
    public boolean exist(String path){
        try {
            Stat s =  cf.checkExists().forPath(path);
            return s != null;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * 主动刷新
     * @param path
     * @throws Exception 
     */
    public void sync(String path) throws Exception{
        cf.sync().forPath(path);
    }

	public void process(WatchedEvent event) {
		// TODO Auto-generated method stub
		
	}
}