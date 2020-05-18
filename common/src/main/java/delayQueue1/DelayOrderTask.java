package delayQueue1;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayOrderTask implements Delayed {
    private final long time;
    private final Message task; // 任务类，也就是之前定义的任务类

    /**
     * @param timeout
     *            超时时间(秒)
     * @param task
     *            任务
     */
    public DelayOrderTask(long timeout, Message task) {
        this.time = System.nanoTime() + timeout;
        this.task = task;
    }

    @Override
    public int compareTo(Delayed o) {
        // TODO Auto-generated method stub
        DelayOrderTask other = (DelayOrderTask) o;
        long diff = time - other.time;
        if (diff > 0) {
            return 1;
        } else if (diff < 0) {
            return -1;
        } else {
            return 0;
        }
    }

    @Override
    public long getDelay(TimeUnit unit) {
        // TODO Auto-generated method stub
        return unit.convert(this.time - System.nanoTime(), TimeUnit.NANOSECONDS);
    }

    @Override
    public boolean equals(Object o) {
        DelayOrderTask that = (DelayOrderTask) o;
        return that.task.getUserid().equals(task.getUserid());
    }

    @Override
    public int hashCode() {
        int result = (int) (time ^ (time >>> 32));
        result = 31 * result + (task != null ? task.hashCode() : 0);
        return result;
    }

    public Message getTask() {
        return task;
    }
}
