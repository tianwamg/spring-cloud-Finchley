package delayQueue1;

import java.util.Date;

public class Message  implements Runnable {

    private String userid;
    private int type;
    private String channelid;
    private float paymoney;
    private float presentedmoney;

    public Message(String userid,int type,String channelid,float paymoney,float presentedmoney){
        this.userid = userid;
        this.type = type;
        this.channelid = channelid;
        this.paymoney = paymoney;
        this.presentedmoney = presentedmoney;
    }


    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    @Override
    public void run() {
        //相关业务逻辑处理
        System.out.println("userId:"+userid+"==="+new Date());
    }
}