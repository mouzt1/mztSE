package servlet;

import java.io.IOException;
import java.net.URLDecoder;
import java.util.List;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jlu.search.SearchByPage;
import jlu.search.SearchResultBean;
import jlu.web.ConstantFactory;
import jlu.web.Pagination;

import org.apache.lucene.queryParser.ParseException;

public class SearchByPageSer extends HttpServlet {

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
		String browerType = req.getParameter("browerType");
		if("chrome".equals(browerType)){
			key = URLDecoder.decode(key, "UTF-8");
			key = new String(key.getBytes("ISO-8859-1"),"UTF-8");
		}
		key = URLDecoder.decode(key, "UTF-8");
		String  pageStr = (String)req.getParameter("page");
		int page = (pageStr==null)?1:Integer.parseInt(pageStr);
		try {
			Pagination pagination = new Pagination();
			List<SearchResultBean> docList = SearchByPage.searchRetList(indexDir,key,pagination,page);
			req.setAttribute("retList", docList);
			req.setAttribute("pagination", pagination);
			req.setAttribute("key", key);
			String pageUrl = "mySearchByPage?keyword="+key;
			req.setAttribute("pageUrl", pageUrl);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		RequestDispatcher rd = req.getRequestDispatcher("ibm.jsp");
		rd.forward(req, resp);
		System.out.println("*****************");
	}
}
