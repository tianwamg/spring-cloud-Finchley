package delayQueue;

import java.util.concurrent.DelayQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class DelayQueueTest {

    public static void main(String[] args) {
        DelayQueue<Message> queue = new DelayQueue<>();
        Message m1 = new Message(1,"aaa",3000);
        Message m2 = new Message(2,"bbb",10000);
        queue.offer(m1);
        queue.offer(m2);
        ExecutorService exec = Executors.newFixedThreadPool(1);
        exec.execute(new Consumer(queue));
        exec.shutdown();
    }
}
