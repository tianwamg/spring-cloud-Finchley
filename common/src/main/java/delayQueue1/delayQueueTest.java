package delayQueue1;

import java.util.Date;

public class delayQueueTest {

    public static void main(String[] args) {
        System.out.println("001=="+new Date());
        DelayOrderQueueManager.getInstance().put(new Message("12",0,"1",1f,1f),3);
        System.out.println("002==="+new Date());
        DelayOrderQueueManager.getInstance().put(new Message("13",0,"1",1f,1f),1);
        DelayOrderQueueManager.getInstance().removeTask(new Message("12",0,"",0,0),10);
    }
}
