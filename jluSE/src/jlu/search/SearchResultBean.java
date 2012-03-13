package jlu.search;

public class SearchResultBean {
	private String title;
	private String highlighted_abstract;
	private String fullpath;
	
	public void setTitle(String title){
		this.title = title;
	}
	
	public void setHighlightedAbstract(String hAbstract){
		this.highlighted_abstract = hAbstract;
	}
	
	public void setFullpath(String fullpath){
		this.fullpath = fullpath;
	}
	
	public String getTitle(){
		return this.title;
	}
	
	public String getHighlightedAbstract(){
		return this.highlighted_abstract;
	}
	
	public String getFullpath(){
		return this.fullpath;
	}
	public String toJSON(){
		return "{\"content\": \""+highlighted_abstract+"\"}";
	}
}
