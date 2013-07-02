package com.searchStar.search;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.NumberFormat;
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
	
	public static void main(String args[]) throws IOException{
		String[] extToSearch={".xqy"};
		Search.search("C:\\Program Files\\MarkLogic\\Modules\\Xplana2", "C:\\Users\\vhq\\Desktop", "bookSetting", extToSearch);
	}
	
	public static void search(String inputFolder,String outputFolderPath,String keyword,String[] extToSearch) throws IOException{
		String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(Calendar.getInstance().getTime());
			
		File outputFolder =new File(outputFolderPath+"//"+keyword+"-"+timeStamp+".txt");
		//if file doesnt exists, then create it
		if(!outputFolder.exists()){
			outputFolder.createNewFile();
		}
		
		fw=new FileWriter(outputFolder);
		long start = System.currentTimeMillis();
		int resultCount=listFilesForFolder(new File(inputFolder),keyword,extToSearch);
		long end = System.currentTimeMillis();
		NumberFormat formatter = new DecimalFormat("#0.00000");
		System.out.print(resultCount+" results in " + formatter.format((end - start) / 1000d) + " seconds");
		fw.close();
	}



	private static int listFilesForFolder(File folder, String keyWord, String[] extToSearch) throws IOException {
		
		int totalCount=0;
		String resultBuffer="";
		
		
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
	            			boolean found=false;
	            			String fileScopedResult="";
	            			
	            			while (line != null)
	            			{
	            			    line = input.readLine(); 
	            			    if(line!=null){
	            			    	if(Pattern.compile(Pattern.quote(keyWord), Pattern.CASE_INSENSITIVE).matcher(line).find()){
	            			    		found=true;
	            			    		totalCount++;
	            			    		fileScopedResult=fileScopedResult+line+"\n";
	            			    		//fw.write("Found at location="+fileEntry.getAbsolutePath()+"\n");
	            			    		//fw.write(line+"\n");
		            				}	
	            			    }
	            				
	            			}
	            			
	            			if(found){
	            				fileScopedResult="FOUND AT LOCATION="+fileEntry.getAbsolutePath()+"\n"+fileScopedResult+"\n";
	            				resultBuffer=resultBuffer+fileScopedResult;
	            			}
	            			
	            			}catch(Exception e){
	            				System.out.println("In exception"+e);
	            				fw.close();
	            			}
	            	}
	            }
	            }
	    }
	    fw.write(resultBuffer);
	    return totalCount;
	}
	
	
}
