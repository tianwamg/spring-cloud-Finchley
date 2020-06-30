package countdownlatch;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;


public class SimpleThreadFactory implements ThreadFactory{
	private final AtomicInteger count = new AtomicInteger();
	private final String threadName;
	
	public SimpleThreadFactory(String threadName){
		this.threadName = threadName;
	}
	
	@Override
	public Thread newThread(Runnable r) {
		Thread thread = new Thread(r);
		thread.setName(threadName+"-"+count.incrementAndGet());
		thread.setUncaughtExceptionHandler(new ThreadUncaughtExceptionHandler());
		return thread;
	}

}
