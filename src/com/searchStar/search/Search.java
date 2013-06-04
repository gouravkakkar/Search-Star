package com.searchStar.search;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.regex.Pattern;

/**
 * 
 */

/**
 * @author YANTRA
 *
 */
public class Search {
	
	static FileWriter fw;
	
	public static void search(String inputFolder,String outputFolderPath,String keyword,String[] extToSearch) throws IOException{
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
			
		File outputFolder =new File(outputFolderPath+"//"+timeStamp+".txt");
		//if file doesnt exists, then create it
		if(!outputFolder.exists()){
			outputFolder.createNewFile();
		}
		
		fw=new FileWriter(outputFolder);
		listFilesForFolder(new File(inputFolder),keyword,extToSearch);
		fw.close();
	}



	private static void listFilesForFolder(File folder, String keyWord, String[] extToSearch) throws IOException {
	    for (File fileEntry : folder.listFiles()) {
	    	String fileName=fileEntry.getName();
	    	
	        if (fileEntry.isDirectory()) {
	            listFilesForFolder(fileEntry, keyWord, extToSearch);
	        } else {
	            
	            if(fileName.contains(".")){
	            String extName = fileEntry.getName().substring(fileEntry.getName().lastIndexOf("."));
	            for(String ext : extToSearch)
	            	if(ext.equalsIgnoreCase(extName)){
	            		
	            		try{
	            			BufferedReader input = new BufferedReader(new FileReader(fileEntry));
	            			String line="";
	            			
	            			while (line != null)
	            			{
	            			    line = input.readLine(); 
	            			    if(line!=null){
	            			    	if(Pattern.compile(Pattern.quote(keyWord), Pattern.CASE_INSENSITIVE).matcher(line).find()){
	            			    		fw.write("Found at location="+fileEntry.getAbsolutePath()+"\n");
		            					System.out.println("Found at location="+fileEntry.getAbsolutePath());
		            				}	
	            			    }
	            				
	            			}
	            			}catch(Exception e){
	            				System.out.println("In exception"+e);
	            				fw.close();
	            			}
	            	}
	            }
	            }
	    }
	}
	
	
}