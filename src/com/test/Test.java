package com.test;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpException;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;

import com.caijia.modle.OrderDetail;
import com.caijia.modle.OrdersModle;
import com.caijia.modle.UserModle;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

public class Test {
	
	Gson gson=new Gson();
	public void getID() throws HttpException, IOException{
		HttpClient client = new HttpClient( );
		String getMyTasksUrl = "http://localhost:8080/caijia/rest/getCaiById";
		getMyTasksUrl = "http://localhost:8080/caijia/rest/getUserById";
		getMyTasksUrl = "http://localhost:8080/caijia/rest/getOrdersById";
		
		GetMethod getMethod = new GetMethod (getMyTasksUrl);
		getMethod.setQueryString(new NameValuePair[] { 
				new NameValuePair("id","1")
		});
		getMethod.getParams().setContentCharset("UTF-8");
		client.executeMethod( getMethod );
		int statusCode = getMethod.getStatusCode();

		if(statusCode==200){
			String resp = getMethod.getResponseBodyAsString();
			System.out.println(resp);
//			Type listOfTestObject = new TypeToken<(){}.getType();		
//			List<SchTriggerAndProView> schTriggerPros = gson.fromJson(resp, listOfTestObject);
		}
		getMethod.releaseConnection();
	}
	
	/*public void insertUser() throws HttpException, IOException{
		HttpClient client = new HttpClient( );
		String getMyTasksUrl = "http://localhost:8080/caijia/rest/insertUser";
		
		PostMethod postMethod = new PostMethod(getMyTasksUrl);
		
		UserModle u=new UserModle();
		u.setUserName("bbbbb");
		u.setPassword("1234");
		u.setEmail("XXX@XX.com");
		u.setPhone("13466665547");
		Type type = new TypeToken<UserModle>(){}.getType();
		 String s= gson.toJson(u, type);
		postMethod.setParameter( "user", s );
		postMethod.getParams().setContentCharset("UTF-8");
		client.executeMethod( postMethod );			
		int statusCode = postMethod.getStatusCode();
		if(statusCode==200){
			System.out.println("Yes");
		}else{
			System.out.println("no");
		}
		postMethod.releaseConnection();
		
	}*/
	
	
	/*public void updateUser() throws HttpException, IOException{
		HttpClient client = new HttpClient( );
		String getMyTasksUrl = "http://localhost:8080/caijia/rest/updateUser";
		
		PostMethod postMethod = new PostMethod(getMyTasksUrl);
		
		UserModle u=new UserModle();
		u.setUserUuid("fb91cf5c-d541-4f43-ba35-683535bf16b5");
		u.setUserName("cccccc");
		u.setPassword("1234");
		u.setEmail("XXX@XX.com");
		u.setPhone("13466665547");
		Type type = new TypeToken<UserModle>(){}.getType();
		 String s= gson.toJson(u, type);
		postMethod.setParameter( "user", s );
		
		
		postMethod.getParams().setContentCharset("UTF-8");
		client.executeMethod( postMethod );			
		int statusCode = postMethod.getStatusCode();
		if(statusCode==200){
			System.out.println("Yes");
		}else{
			System.out.println("no");
		}
		postMethod.releaseConnection();
	}*/
	
	
	public void insertOrder() throws HttpException, IOException{
		HttpClient client = new HttpClient( );
		String getMyTasksUrl = "http://localhost:8080/caijia/rest/insertOrder";
		
		PostMethod postMethod = new PostMethod(getMyTasksUrl);
		
		OrderDetail a=new OrderDetail();
		a.setAmount("500");
		a.setCaiUuid("1");
		a.setPrice("20");
		OrderDetail b=new OrderDetail();
		b.setAmount("1000");
		b.setCaiUuid("2");
		b.setPrice("30");
		List<OrderDetail> list=new ArrayList<OrderDetail>();
		list.add(a);
		list.add(b);
		
		
		
		OrdersModle om=new OrdersModle();
		
		om.setAddress("北京");
		om.setOrderDetail(list);
		om.setOrderTotal("2000");
		om.setOwner("jack");
		om.setPhone("13456789");
		Type type = new TypeToken<OrdersModle>(){}.getType();
		String s= gson.toJson(om, type);
		postMethod.setParameter( "orders", s );
		postMethod.getParams().setContentCharset("UTF-8");
		client.executeMethod( postMethod );			
		int statusCode = postMethod.getStatusCode();
		if(statusCode==200){
			System.out.println("Yes");
		}else{
			System.out.println("no");
		}
		postMethod.releaseConnection();
	}
	
	public static void main(String[] args) throws HttpException, IOException {
	new Test()
	.insertOrder();
//	.updateUser();
//	.insertUser();
//	.getID();
	}
	
	


}
