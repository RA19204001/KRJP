package bean;

public class ResponseBean{
	private int res_id;
	private int thread_id;
	private String res_sentence;
	private String res_name;
	private int res_number;
	private String res_date;
	
	public void setRes_id(int i){
		res_id=i;
	}
	public int getRes_id(){
		return res_id;
	}
	public void setThread_id(int i){
		thread_id=i;
	}
	public int getThread_id(){
		return thread_id;
	}
	public void setRes_sentence(String s){
		res_sentence=s;
	}
	public String getRes_sentence(){
		return res_sentence;
	}
	public void setRes_name(String s){
		res_name=s;
	}
	public String getRes_name(){
		return res_name;
	}
	public void setRes_number(int i){
		res_number=i;
	}
	public int getRes_number(){
		return res_number;
	}
	public void setRes_date(String s){
		res_date=s;
	}
	public String getRes_date(){
		return res_date;
	}
}