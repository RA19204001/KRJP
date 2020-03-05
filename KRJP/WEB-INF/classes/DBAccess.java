import bean.ThreadBean;
import bean.ResponseBean;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.sql.PreparedStatement;

public class DBAccess{
	private static final String DATABASE_CLASS = "oracle.jdbc.driver.OracleDriver";
	private static final String DATABASE_LOGIN_INFO = "jdbc:oracle:thin:@localhost:1521:orcl";
	private static final String DATABASE_LOGIN_USER = "asakura";
	private static final String DATABASE_LOGIN_PW = "ryo";
	
	
	private static Connection getOracleConnection() throws Exception{
		Class.forName(DATABASE_CLASS);
		Connection connection = DriverManager.getConnection(DATABASE_LOGIN_INFO,
			DATABASE_LOGIN_USER, DATABASE_LOGIN_PW);
		return connection;
	}
	private static PreparedStatement prepare(String sql){
		PreparedStatement pst=null;
		try{
			Connection connection=getOracleConnection();
			pst=connection.prepareStatement(sql);
		}catch(Exception e){
			e.printStackTrace();
		}
			return pst;
	}
	private static ResultSet select(String sql){
		ResultSet resultSet=null;
		try{
			Connection connection=getOracleConnection();
			Statement statement = connection.createStatement();
			resultSet = statement.executeQuery(sql);
		}catch(Exception e){
			e.printStackTrace();
		}
		return resultSet;
	}
	
	
	public static void threadInsert(String thread_title){
		try{
			int thread_id=getMaxThread_id()+1;
			PreparedStatement pst=prepare("INSERT INTO THREAD_TABLE" +
	                    " values ('"+thread_id+"',?,sysdate)");
			pst.setString(1,thread_title);
			pst.executeUpdate();
		}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static ArrayList<ThreadBean> threadSelect() {
		ArrayList<ThreadBean> _thre = new ArrayList<ThreadBean>();
		try{
			ResultSet resultSet = select("select thread_id,thread_title,to_char(latest_res,'yyyy-mm-dd hh24:mi:ss') "+
				" from thread_table ORDER BY latest_res desc");
			while (resultSet.next()){
			ThreadBean thre = new ThreadBean();
			thre.setThread_id(Integer.parseInt(resultSet.getString(1)));
			thre.setThread_title(resultSet.getString(2));
			thre.setLatest_res(resultSet.getString(3));
			_thre.add(thre);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return _thre;
    }
	
	public static void responseInsert(int thread_id, String res_sentence,String res_name){
		try{
			int res_number=getMaxRes_number(thread_id)+1;
			PreparedStatement pst=prepare("INSERT INTO RESPONSE_TABLE" +
				" values (res_id.nextval,?,?,?,?, sysdate)");
			pst.setInt(1,thread_id);
			pst.setString(2,res_sentence);
			pst.setString(3,res_name);
			pst.setInt(4,res_number);
			pst.executeUpdate();
			
			Connection connection=getOracleConnection();
			Statement statement = connection.createStatement();
			statement.executeUpdate("UPDATE thread_table SET latest_res = sysdate WHERE thread_id='"+thread_id+"'");
			}catch(Exception e){
			e.printStackTrace();
		}
	}
	
	public static ArrayList<ResponseBean> responseSelect(int thread_id) {
		ArrayList<ResponseBean> _resp = new ArrayList<ResponseBean>();
		try{
		ResultSet resultSet = select("select res_id,thread_id,res_sentence,res_name,res_number,to_char(res_date,'yyyy-mm-dd hh24:mi:ss') "+
			" from response_table WHERE thread_id="+thread_id+" ORDER BY res_number asc");
			while (resultSet.next()) {
				ResponseBean resp = new ResponseBean();
				resp.setRes_id(Integer.parseInt(resultSet.getString(1)));
				resp.setThread_id(Integer.parseInt(resultSet.getString(2)));
				resp.setRes_sentence(resultSet.getString(3));
				resp.setRes_name(resultSet.getString(4));
				resp.setRes_number(Integer.parseInt(resultSet.getString(5)));
				resp.setRes_date(resultSet.getString(6));
				_resp.add(resp);
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return _resp;
	}
	
	public static String titleSelect(int thread_id){
		String thread_title="0";
		try{
			ResultSet resultSet=select("SELECT thread_title "+
				" FROM thread_table WHERE thread_id='"+thread_id+"'");
			resultSet.next();
			thread_title=resultSet.getString(1);
		}catch(Exception e){
			e.printStackTrace();
		}
		return thread_title;
	}
	
	public static int getMaxRes_number(int thread_id) {
		int res_number=0;
		try{
			ResultSet resultSet=select("SELECT max(res_number) "+
				" FROM response_table WHERE thread_id='"+thread_id+"'");
			resultSet.next();
			if(resultSet.getString(1)!=null){
				res_number=Integer.parseInt(resultSet.getString(1));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return res_number;
	}
	
	private static int getMaxThread_id() {
		int thread_id=0;
		try{
			ResultSet resultSet=select("SELECT max(thread_id) "+
				" FROM thread_table");
			resultSet.next();
			if(resultSet.getString(1)!=null){
				thread_id=Integer.parseInt(resultSet.getString(1));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return thread_id;
	}
	
	
	public static int max_res_numberSelect(int thread_id) {
		int max_res_number=0;
		try{
			ResultSet resultSet=select("SELECT max(res_number) "+
				" FROM response_table where thread_id='"+thread_id+"'");
			resultSet.next();
			if(resultSet.getString(1)!=null){
				max_res_number=Integer.parseInt(resultSet.getString(1));
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		return max_res_number;
	}
}