package countdownlatch;

public interface ObtainAndHandler {

	void obtainData(); //获取数据
	
	void handleData(); //处理数据
	
	void done();       //最后操作
}
