package delayQueue1;

import java.util.Map;
import java.util.concurrent.DelayQueue;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class DelayOrderQueueManager {

    private static int thread_num = 10;
    // 固定大小线程池
    private ExecutorService executor;
    // 守护线程
    private Thread daemonThread;
    // 延时队列
    private DelayQueue<DelayOrderTask> delayQueue;
    private static DelayOrderQueueManager instance = new DelayOrderQueueManager();

    private TimeUnit unit = TimeUnit.MINUTES;


    private DelayOrderQueueManager() {
        executor = Executors.newFixedThreadPool(thread_num);
        delayQueue = new DelayQueue<>();
        init();
    }

    public static DelayOrderQueueManager getInstance() {
        return instance;
    }

    /**
     * 初始化
     */
    public void init() {
        daemonThread = new Thread(new Runnable() {
            @Override
            public void run() {
                execute();
            }
        });
        daemonThread.start();
    }

    private void execute() {
        while (true) {
            Map<Thread, StackTraceElement[]> map = Thread.getAllStackTraces();
            int taskNum = delayQueue.size();
            System.out.println("当前延时任务数量:" + taskNum);
            try {
                // 从延时队列中获取任务
                DelayOrderTask delayOrderTask = delayQueue.take();
                if (delayOrderTask != null) {
                    Message task = delayOrderTask.getTask();
                    if (null == task) {
                        continue;
                    }
                    // 提交到线程池执行task
                    executor.execute(task);
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    /**
     * 添加任务
     * @param task
     */
    public void put(Message task,long time) {
        // 获取延时时间
        long timeout = TimeUnit.NANOSECONDS.convert(time,unit);
        // 将任务封装成实现Delayed接口的消息体
        DelayOrderTask delayOrder = new DelayOrderTask(timeout, task);
        //先删除之前存在的
        delayQueue.remove(delayOrder);
        delayQueue.put(delayOrder);
    }

    /**
     * 删除任务
     * @param task
     * @return
     */
    public boolean removeTask(Message task,long time) {
        // 获取延时时间
        long timeout = TimeUnit.NANOSECONDS.convert(time, unit);
        // 将任务封装成实现Delayed接口的消息体
        DelayOrderTask delayOrder = new DelayOrderTask(timeout, task);

        return delayQueue.remove(delayOrder);
    }
}