package servlet;

import java.io.File;
import java.io.FileReader;
import java.io.IOException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class getContentSer extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String txtUrl = req.getParameter("TextUrl");
		txtUrl = new String(txtUrl.getBytes("ISO-8859-1"), "GB18030");
		try {

			FileReader fin = new FileReader(new File(txtUrl));
			char[] b = new char[1];
			StringBuffer oustr = new StringBuffer();
			while (fin.read(b, 0, 1) != -1) {
				String tmp = new String(b);
				oustr.append(tmp);
			}
			req.setAttribute("oustr", oustr);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

		RequestDispatcher rd = req.getRequestDispatcher("contentShow.jsp");
		rd.forward(req, resp);
	}
}
