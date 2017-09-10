package Beiyesi;

import java.io.IOException;
import jeasy.analysis.MMAnalyzer;

public class ChineseSpliter {

	public static String split(String text,String splitToken){
			String result = null;
			MMAnalyzer analyzer = new MMAnalyzer();
			try{			
				result = analyzer.segment(text, splitToken);
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
			return result;
	}
}
