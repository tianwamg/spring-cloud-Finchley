package countdownlatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public enum ObtainAndHandlerThreadPool {
	INSTANCE;
	private final Logger LOGGER = LoggerFactory.getLogger(ObtainAndHandlerThreadPool.class);
	private final ThreadPoolExecutor obtainExecutor; 
	private final ThreadPoolExecutor handleExecutor; 
	
	private ObtainAndHandlerThreadPool(){
		obtainExecutor = new ThreadPoolExecutor(0,Integer.MAX_VALUE,30,TimeUnit.SECONDS,
				new SynchronousQueue<Runnable>(),new SimpleThreadFactory("dataHandlerThread"));
		
		obtainExecutor.allowCoreThreadTimeOut(true);
		
		int handleThreadSize = Runtime.getRuntime().availableProcessors();
		
		handleExecutor = new ThreadPoolExecutor(handleThreadSize,handleThreadSize,60,TimeUnit.SECONDS,
				new LinkedBlockingQueue<Runnable>(),new SimpleThreadFactory("dataHandlerThread"));
		
		handleExecutor.allowCoreThreadTimeOut(true);
	}
	
	public void execute(ObtainAndHandler obtainAndHandler){
		if(obtainAndHandler==null)
			throw new NullPointerException();
		obtainExecutor.execute(new ObtainTask(obtainAndHandler));
	}
	
	public void shutdown(){
		obtainExecutor.shutdown();
		handleExecutor.shutdown();
	}
	
	private class ObtainTask implements Runnable{
		private ObtainAndHandler obtainAndHandler;
		
		private ObtainTask(ObtainAndHandler obtainAndHandler){
			this.obtainAndHandler = obtainAndHandler;
		}
		
		@Override
		public void run() {
			try{
				obtainAndHandler.obtainData();
			}finally{
				try{
					handleExecutor.execute(new HandleTask(obtainAndHandler));
				}catch(Exception e){
					obtainAndHandler.done();
					LOGGER.error("出错!!!!!!!",e);
				}
			}
		}
	}
	
	private static class HandleTask implements Runnable{
		private final ObtainAndHandler obtainAndHandler;
		
		public HandleTask(ObtainAndHandler obtainAndHandler){
			this.obtainAndHandler = obtainAndHandler;
		}

		@Override
		public void run() {
			try{
				obtainAndHandler.handleData();
			}finally{
				obtainAndHandler.done();
			}
		}
	}
	
}
