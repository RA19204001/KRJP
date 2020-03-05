import java.io.IOException;
import java.util.ArrayList;
import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class CreateThreadServlet extends HttpServlet{
	public void doPost(HttpServletRequest req,HttpServletResponse res) throws IOException,ServletException{
		req.setCharacterEncoding("Windows-31J");
		System.out.println(req.getParameter("thread_name"));
		DBAccess.threadInsert(req.getParameter("thread_name"));
		ThreadListServlet ths=new ThreadListServlet();
		ths.doPost(req,res);
	}
}