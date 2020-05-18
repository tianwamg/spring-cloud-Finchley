package delayQueue;

import java.util.concurrent.Delayed;
import java.util.concurrent.TimeUnit;

public class Message implements Delayed {

    private int id;
    private String body;//消息内容
    private long executeTime;//延时时长

    public int getId(){
        return id;
    }

    public String getBody(){
        return body;
    }

    public long getExecuteTime(){
        return executeTime;
    }

    public Message(int id,String body,long delayTime){
        this.id = id;
        this.body = body;
        this.executeTime = TimeUnit.NANOSECONDS.convert(delayTime,TimeUnit.MILLISECONDS)+System.nanoTime();
    }

    /**
     * 延时队列是否到期；负数已经到期否则未到期
     * @param unit
     * @return
     */
    @Override
    public long getDelay(TimeUnit unit) {
        return unit.convert(this.executeTime-System.nanoTime(),TimeUnit.NANOSECONDS);
    }

    @Override
    public int compareTo(Delayed o) {
        Message msg = (Message) o;
        return Integer.valueOf(this.id)>Integer.valueOf(msg.id)?1:
                (Integer.valueOf(this.id)<Integer.valueOf(msg.id)?-1:0);
    }

}
