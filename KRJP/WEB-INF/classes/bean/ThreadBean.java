package bean;

public class ThreadBean{
	private int thread_id;
	private String thread_title;
	private String latest_res;
	
	public void setThread_id(int i){
		thread_id=i;
	}
	public int getThread_id(){
		return thread_id;
	}
	
	public void setThread_title(String s){
		thread_title=s;
	}
	public String getThread_title(){
		return thread_title;
	}
	
	public void setLatest_res(String s){
		latest_res=s;
	}
	public String getLatest_res(){
		return latest_res;
	}
}