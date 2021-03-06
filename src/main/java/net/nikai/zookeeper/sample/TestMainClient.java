package net.nikai.zookeeper.sample;

import java.io.IOException;

import org.apache.log4j.xml.DOMConfigurator;
import org.apache.zookeeper.WatchedEvent;
import org.apache.zookeeper.Watcher;
import org.apache.zookeeper.ZooKeeper;

/**
 * @author <a href="nikai.net.cn">Nikai</a>
 * @version 2017年3月22日 下午10:32:54
 */
public class TestMainClient implements Watcher
{
	protected static ZooKeeper zk = null;
	protected static Integer mutex;
	int sessionTimeout = 10000;
	/**
	 * 
	 */
	protected String root;

	public TestMainClient(String connectString)
	{
		if (zk == null)
		{
			try
			{

				String configFile = this.getClass().getResource("/").getPath()
						+ "log4j.xml";
				DOMConfigurator.configure(configFile);
				System.out.println("创建一个新的连接:");
				zk = new ZooKeeper(connectString, sessionTimeout, this);
				mutex = new Integer(-1);
			}
			catch (IOException e)
			{
				zk = null;
			}
		}
	}

	synchronized public void process(WatchedEvent event)
	{
		synchronized (mutex)
		{
			mutex.notify();
		}
	}
}
