package jlu.index;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.jsoup.Jsoup;

public class TextProcessor {
	private String rawContents;
	
	public TextProcessor(){
		rawContents = null;
		
	}
	
	public TextProcessor(File f){
		rawContents = readFile(f);
	}
	
	public String getTitle(){
		String title = "No Title";
//		String regex = "标  题: ([\u0000-\uFFFDh]+)";
        String regex = "标  题: ([^\n]+)";
		Pattern pattern = Pattern.compile(regex);
		Matcher matcher = pattern.matcher(rawContents);
		if (matcher.find()){
			title =  matcher.group(1);
		}
		return title;
	}
	
	public String getContents(){
		//html->text
		return Jsoup.parse(this.rawContents).text();
	}
	
	// For future plan
	/*
	public String getAuthor();
	public String getDate();
	public String getURL();
	 */
	
	
	private String readFile(File f) {
		String s;
		StringBuilder sb = new StringBuilder();
		try {
		BufferedReader in  = new BufferedReader(
				new FileReader(f));
		while ((s = in.readLine()) != null) {
			sb.append(s + "\n");
		}
		in.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return sb.toString();
	}
	
	public void setFile(File f){
		rawContents = readFile(f);
	}
	
	public static void main(String args[]){
		String filePath = "C:/Users/zzl/Workspaces/MyEclipse9/data/55.txt";
		TextProcessor tp = new TextProcessor(new File(filePath));
		System.out.println(tp.getTitle());
		System.out.println(tp.getContents());
		
	
	}
}
