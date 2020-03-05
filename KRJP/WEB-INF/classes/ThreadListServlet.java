import java.io.IOException;

import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ThreadListServlet extends HttpServlet{
	public void doPost(HttpServletRequest req,HttpServletResponse res) throws IOException,ServletException{
		req.setCharacterEncoding("Windows-31J");
		req.setAttribute("thread_table",DBAccess.threadSelect());
		RequestDispatcher dispatcher=req.getRequestDispatcher("threadList");
		dispatcher.forward(req,res);
	}
}