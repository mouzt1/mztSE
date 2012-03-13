package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jlu.search.SearchResultBean;
import jlu.search.Searcher;
import jlu.web.ConstantFactory;

import org.apache.lucene.queryParser.ParseException;

public class SearchSer extends HttpServlet {

	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(req, resp);
	}

	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// TODO Auto-generated method stub
		String indexDir = ConstantFactory.INDEX_DIR;
		String key = req.getParameter("keyword");
		try {
			List<SearchResultBean> docList = Searcher.searchRetList(indexDir, key,20);
			req.setAttribute("retList", docList);

		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		RequestDispatcher rd = req.getRequestDispatcher("ibm.jsp");
		rd.forward(req, resp);
		System.out.println("*****************");
	}
}
