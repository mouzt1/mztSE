package servlet;

import java.io.IOException;
import java.util.List;
import java.util.ArrayList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jlu.search.SearchResultBean;
import jlu.search.Searcher;
import jlu.web.ConstantFactory;
import jlu.web.List2Json;


public class AutoCompletedSer extends HttpServlet {

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		try{
			List<SearchResultBean> autoComplRet = new ArrayList<SearchResultBean>();
			String key = req.getParameter("name_startsWith");
			autoComplRet = Searcher.searchRetList(ConstantFactory.INDEX_DIR,key, ConstantFactory.AUTO_COMPL_TOP_NUM);
			String jsonData = List2Json.list2Json(autoComplRet);
		//	System.out.println(jsonData);
			resp.setContentType("text/html;charset=utf-8");
			resp.getWriter().println(jsonData);
		}catch(Exception w){
			w.printStackTrace();
		}
	}
	
	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		doGet(req, resp);
	}
}
