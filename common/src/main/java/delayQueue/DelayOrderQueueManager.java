package delayQueue;

import java.util.Map;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicLong;

public class DelayOrderQueueManager {

    private final static int DEFAULT_THREAM_NUM = 5;

    private static int thread_num = DEFAULT_THREAM_NUM;

    //固定大小线程池
    private ExecutorService service;

    //守护线程
    private Thread daemonThread;

    private DelayQueue<DelayOrderTask<?>> delayQueue;

    private static final AtomicLong atomic = new AtomicLong(0);
    private final long n = 1;
    private static DelayOrderQueueManager instance = new DelayOrderQueueManager();

    private DelayOrderQueueManager(){
        service = Executors.newFixedThreadPool(thread_num);
        delayQueue = new DelayQueue<>();
        init();
    }

    public static DelayOrderQueueManager getInstance(){
        return instance;
    }

    public void init(){
        daemonThread = new Thread(()->{
            execute();
        });
        daemonThread.setName("DelayQueueMonitor.");
        daemonThread.start();
    }

    private void execute(){
        while (true){
            Map<Thread,StackTraceElement[]> map = Thread.getAllStackTraces();
            System.out.println("当前线程存活数量:"+map.size());
            int taskNum = delayQueue.size();
            System.out.println("当前延时任务数量:"+taskNum);
            try{
                //从延时队列中取任务
                DelayOrderTask<?> delayOrderTask = delayQueue.take();
                if(delayOrderTask != null){
                    Runnable task = delayOrderTask.getTask();
                    if(task == null){
                        continue;
                    }
                    //提交到线程池执行task
                    service.execute(task);
                }
            }catch (Exception e){
                e.printStackTrace();
            }
        }
    }

    /**
     * 添加任务
     * @param task
     * @param time
     * @param unit
     */
    public void put(Runnable task, long time, TimeUnit unit){
        //获取延时时间
        long timeout = TimeUnit.NANOSECONDS.convert(time,unit);
        //将任务封装成实现Delayed接口的消息体
        DelayOrderTask<?> delayOrderTask = new DelayOrderTask<>(timeout,task);
        //将消息体放到延时队列中
        delayQueue.put(delayOrderTask);
    }

    /**
     * 删除任务
     * @param task
     * @return
     */
    public boolean removeTask(DelayOrderTask task){
        return delayQueue.remove(task);
    }
}
