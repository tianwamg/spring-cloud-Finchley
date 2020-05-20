package delayQueue;

import java.util.concurrent.TimeUnit;

public class DelayOrderTest {

    public static void main(String[] args) {
        DelayOrderWorker worker1 = new DelayOrderWorker();
        DelayOrderWorker worker2 = new DelayOrderWorker();
        DelayOrderWorker worker3 = new DelayOrderWorker();
        DelayOrderQueueManager manager = DelayOrderQueueManager.getInstance();
        manager.put(worker1,3000, TimeUnit.MILLISECONDS);
        manager.put(worker2,6000, TimeUnit.MILLISECONDS);
        manager.put(worker3,9000, TimeUnit.MILLISECONDS);
    }
}
