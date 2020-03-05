import java.io.IOException;

import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ResponseServlet extends HttpServlet{
	public void doPost(HttpServletRequest req,HttpServletResponse res) throws IOException,ServletException{
		req.setCharacterEncoding("Windows-31J");
		int thread_id=Integer.parseInt(req.getParameter("thread_id"));
		
		req.setAttribute("thread_id",thread_id);
		req.setAttribute("thread_title",DBAccess.titleSelect(thread_id));
		req.setAttribute("response_table",DBAccess.responseSelect(thread_id));
		RequestDispatcher dispatcher=req.getRequestDispatcher("response");
		dispatcher.forward(req,res);
	}
}