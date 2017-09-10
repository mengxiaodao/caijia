package Beiyesi;

import Beiyesi.ChineseSpliter;
import Beiyesi.ClassConditionalProbability;
import Beiyesi.PriorProbability;
import Beiyesi.TrainingDataManager;
import Beiyesi.StopWordsHandler;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.Vector;

public class BayesClassifier {
	
	private TrainingDataManager tdm;//训练集管理器
	private String trainnigDataPath;//训练集路径
	private static double zoomFactor = 10.0f;
	
	public BayesClassifier(){
		
		tdm =new TrainingDataManager();
	}
	
	float calcProd(String[] X, String Cj){
		float ret = 1.0F;
		
		for (int i = 0; i <X.length; i++){
			
			String Xi = X[i];
			
			ret *=ClassConditionalProbability.calculatePxc(Xi, Cj)*zoomFactor;
		}
		ret *= PriorProbability.calculatePc(Cj);
		return ret;
	}
	
	public String[] DropStopWords(String[] oldWords){
		Vector<String> v1 = new Vector<String>();
		for(int i=0;i<oldWords.length;++i){
			if(StopWordsHandler.IsStopWord(oldWords[i])==false){
				v1.add(oldWords[i]);
			}
			
		}
		String[] newWords = new String[v1.size()];
		v1.toArray(newWords);
		return newWords;
	}
	
	@SuppressWarnings("unchecked")
    public String classify(String text){
		String[] terms = null;
		terms = ChineseSpliter.split(text, " ").split(" ");
		terms = DropStopWords(terms);
		String[] Classes = tdm.getTraningClassifications();
		float probility = 0.0F;
		List<ClassifyResult> crs = new ArrayList<ClassifyResult>();//分类结果
		for (int i = 0; i <Classes.length; i++)
		{
			String Ci = Classes[i];//第i个分类
			probility = calcProd(terms, Ci);
			ClassifyResult cr = new ClassifyResult();
			cr.classification = Ci;//分类
			cr.probility = probility;//关键字在分类的条件概率
			System.out.println("In process.");
			System.out.println(Ci + "：" + probility);
			crs.add(cr);
		}
		
		java.util.Collections.sort(crs,new Comparator()
		{
			public int compare(final Object o1,final Object o2)
			{
				final ClassifyResult m1 = (ClassifyResult) o1;
				final ClassifyResult m2 = (ClassifyResult) o2;
				final double ret = m1.probility - m2.probility;
				if (ret < 0)
				{
					return 1;
				}
				else
				{
					return -1;
				}
			}
			
		});
		return crs.get(0).classification;
	}
	
	public static void main(String[] args)
	{
		
		String text = "优惠代开各地区发票，包真有数据，可验证后付款";
		BayesClassifier classifier = new BayesClassifier();//构造Bayes分类器
		String result = classifier.classify(text);//进行分类
	//	System.out.println("此项属于["+result+"]");
		if(result.equals("垃圾邮件")){
			System.out.println("此项属于垃圾");
		}
		else{
			System.out.println("此项属于["+result+"]");	
		}
	}
}
