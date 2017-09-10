package Beiyesi;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

public class TrainingDataManager {

	
	private String[] traningFileClassifications;//训练语料分类集合
	private File traningTextDir;//训练语料存放目录
	private static String defaultPath = "D:\\TrainningSet";
	
	
	public TrainingDataManager(){
		
		traningTextDir = new File(defaultPath);
		if(!traningTextDir.isDirectory()){
			
			throw new IllegalArgumentException("训练语料库搜索失败！ [" +defaultPath + "]");
		}
		this.traningFileClassifications = traningTextDir.list();
	}
	
	public String[] getTraningClassifications(){
		
		return this.traningFileClassifications;
		
	}
	
	

	public String[] getFilesPath(String classification){
		
		File classDir = new File(traningTextDir.getPath() +File.separator +classification);
		String[] ret = classDir.list();
		for (int i = 0; i < ret.length;i++)
		{
			ret[i] = traningTextDir.getPath() +File.separator +classification +File.separator +ret[i];	
		}
		return ret;
	}
	
	public static String getText(String filePath) throws FileNotFoundException,IOException{
		
		
		InputStreamReader isReader =new InputStreamReader(new FileInputStream(filePath),"GBK");
		BufferedReader reader = new BufferedReader(isReader);
		String aline;
		StringBuilder sb = new StringBuilder();
		while ((aline = reader.readLine()) != null)
		{
			sb.append(aline + " ");
		}
		isReader.close();
		reader.close();
		return sb.toString();
	}
	
	
	
	public int getTrainingFileCount(){
		
		int ret = 0;
		for (int i = 0; i < traningFileClassifications.length; i++){
			
			ret += getTrainingFileCountOfClassification(traningFileClassifications[i]);
			
		}
		return ret;
		
	}
	
	public int getTrainingFileCountOfClassification(String classification){
		
		File classDir = new File(traningTextDir.getPath() +File.separator +classification);
		return classDir.list().length;
	}
	
	
	public int getCountContainKeyOfClassification(String classification,String key){
		
		int ret = 0;
		try{
			String[] filePath = getFilesPath(classification);
			for (int j = 0; j < filePath.length; j++){
				
				String text = getText(filePath[j]);
				if (text.contains(key))
				{
					ret++;
				}
			}
		}
		catch(FileNotFoundException ex)	
		{
			Logger.getLogger(TrainingDataManager.class.getName()).log(Level.SEVERE, null,ex);
		}
		
		catch(IOException ex)
		{
			Logger.getLogger(TrainingDataManager.class.getName()).log(Level.SEVERE, null,ex);
			
		}
		
		return  ret;
	}
	
	
	
	
	
}
