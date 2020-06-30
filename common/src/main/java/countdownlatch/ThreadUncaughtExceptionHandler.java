package countdownlatch;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.Thread.UncaughtExceptionHandler;

public class ThreadUncaughtExceptionHandler implements UncaughtExceptionHandler{

	private static final Logger LOGGER = LoggerFactory.getLogger(ThreadUncaughtExceptionHandler.class);
	
	@Override
	public void uncaughtException(Thread t, Throwable e) {
		LOGGER.error(t.getName(),e);
	}

}
