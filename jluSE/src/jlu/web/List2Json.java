package jlu.web;

import java.util.List;

import jlu.search.SearchResultBean;

import org.json.JSONStringer;

public class List2Json {
	public static String list2Json (List<SearchResultBean> arr)throws Exception{
		StringBuffer ret =new StringBuffer();
		ret.append("{\"autoComplete\":[");
		for(int i = 0 ; i < arr.size()-1 ; i++){
			String content = arr.get(i).getHighlightedAbstract().trim().replace(" ", "");
			String itemJson = new JSONStringer().object()
								.key("content").value(content)
								.endObject().toString();
			if(i==0){
				ret.append(itemJson);				
			}else{
				ret.append("," + itemJson);
			}
		}
		ret.append("]}");
		return ret.toString();
	}
}
