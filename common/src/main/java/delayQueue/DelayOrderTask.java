package delayQueue;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class DelayOrderTask<T extends Runnable> implements Delayed {

    private final long time;

    private final T task;


    public DelayOrderTask(long timeout,T task){
        this.time = System.nanoTime()+timeout;
        this.task = task;
    }

    public T getTask(){
        return task;
    }

    @Override
    public long getDelay(TimeUnit unit) {
        return 0;
    }

    @Override
    public int compareTo(Delayed o) {
        DelayOrderTask other = (DelayOrderTask) o;
        long diff = time - other.time;
        if(diff >0){
            return 1;
        }else if(diff <0){
            return -1;
        }else {
            return 0;
        }
    }
}
