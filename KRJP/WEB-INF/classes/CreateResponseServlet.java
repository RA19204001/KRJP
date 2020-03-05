import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateResponseServlet extends HttpServlet{
	private static final String DEFAULT_NAME="–¼–³‚µ‚³‚ñ";
	private static int thread_id;
	
	public void doPost(HttpServletRequest req,HttpServletResponse res) throws IOException,ServletException{
		req.setCharacterEncoding("Windows-31J");
		String res_sentence=req.getParameter("res_sentence");
		String res_name=req.getParameter("res_name");
		
		
		thread_id=Integer.parseInt(req.getParameter("thread_id"));
		
		if(res_sentence!=null){
			if(res_name.isEmpty()){
				res_name=getDefaultName();
			}
			response(thread_id,convertSentence(res_sentence),res_name);
		}
		
		ResponseServlet rs=new ResponseServlet();
		rs.doPost(req,res);
	}
	private static void response(int i,String s,String n){
		DBAccess.responseInsert(i,s,n);
	}
	private static String convertSentence(String snt){
		snt=space2br(snt);
		snt=generateTag(snt);
		return snt;
		
	}
	
	private static String generateTag (String snt){
		String tagChar="&gt&gt";
		int tagPlace=snt.indexOf(tagChar);
		if(tagPlace==-1){
			return snt;
		}else{
			int tagLength=10;
			if(snt.length()-tagPlace<tagLength){
				tagLength=snt.length()-tagPlace;
			}
			String cutSnt=snt.substring(tagPlace+6,tagPlace+tagLength);//
			byte[] byteSnt=cutSnt.getBytes();
			int idLength=0;
			for(int c=0;c<byteSnt.length;c++){
				if(48<=byteSnt[c]&&byteSnt[c]<=57){
					idLength++;
				}else{
					break;
				}
			}
			if(idLength==0){
				return snt;
			}else{
				String tagNumber=cutSnt.substring(0,idLength);
				if(Integer.parseInt(tagNumber)<=DBAccess.max_res_numberSelect(thread_id)){
					String tag=tagChar.concat(tagNumber);
					String convertedTag="<a href=\"#"+tagNumber+"\">"+tag+"</a>";
					return snt.replace(tag,convertedTag);
				}else{
					return snt;
				}
			}
		}
	}
	private static String space2br(String snt){
		return snt.replace("&","&amp").replace("<","&lt").replace(">","&gt").replace("\"","&quot").replace("'","&#39").replace("\r\n", "<br>");
	}
	private static String getDefaultName(){
		return DEFAULT_NAME;
	}
}