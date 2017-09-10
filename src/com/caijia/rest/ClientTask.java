/**
 * 
 */
package com.caijia.rest;

import java.io.UnsupportedEncodingException;
import java.lang.reflect.Type;
import java.net.URLDecoder;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.ws.rs.Consumes;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;







import net.sf.json.JSONObject;

import org.apache.log4j.Logger;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Component;

import Beiyesi.BayesClassifier;

import com.caijia.dao.CaiDao;
import com.caijia.dao.CaiDaoImpl;
import com.caijia.exception.DBException;
import com.caijia.modle.CaiModle;
import com.caijia.modle.CityModle;
import com.caijia.modle.CommunicateModle;
import com.caijia.modle.OrdersModle;
import com.caijia.modle.UserModle;
import com.caijia.rest.consts.CodeTable;
import com.caijia.rest.consts.ErrorMessage;
import com.caijia.rest.model.ErrorReport;
import com.caijia.util.JDBCUtil;
import com.caijia.util.StringUtil;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

/**
 * @author lxy
 *
 */
@Path("rest")
@Component
@Scope("request")
public class ClientTask {
	
	private Gson gson = new Gson();
	private static final Logger logger = Logger.getLogger(ClientTask.class);
	
	private CaiDao dao=new CaiDaoImpl();
	@GET
	@Path("/allNews")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getAllCai (
			@Context HttpServletRequest req){
		ErrorReport errorReport = new ErrorReport();
		List<CaiModle> list=null;
		String s="";
		try {	
			list=dao.getAllCai(JDBCUtil.getConnection());
			Type listOfTestObject = new TypeToken<List<CaiModle>>(){}.getType();
			 s= gson.toJson(list, listOfTestObject);
		} catch (DBException e) {
			logger.error(e.getMessage(),e);
			errorReport.setHttpCode(CodeTable.HTTP_INTERNAL_SERVER_ERROR);
			errorReport.setErrorCode(CodeTable.SQLEXCEPTION);
			errorReport.setErrorMessage(ErrorMessage.SQL_ERROR);
						
			return Response.status(CodeTable.HTTP_INTERNAL_SERVER_ERROR)
					.entity(gson.toJson(errorReport))
					.build();
		}
		if(StringUtil.isNotBlank(s)){
			return Response.status(200).entity(s).build();		
		}else{
			return Response.status(404).entity(s).build();
		}
		
	}
	
	//获取铜仁新闻信息 Byrole
	@GET
	@Path("/getNewsByRole")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getCaiById (
			@QueryParam("role") String id,
			@Context HttpServletRequest req){
		ErrorReport errorReport = new ErrorReport();
		List<CaiModle> list=null;
		String s="";
		try {	
			list = dao.getCaiById(id,JDBCUtil.getConnection());
			Type listOfTestObject = new TypeToken<List<CaiModle>>(){}.getType();
			 s= gson.toJson(list, listOfTestObject);
		} catch (DBException e) {
			logger.error(e.getMessage(),e);
			errorReport.setHttpCode(CodeTable.HTTP_INTERNAL_SERVER_ERROR);
			errorReport.setErrorCode(CodeTable.SQLEXCEPTION);
			errorReport.setErrorMessage(ErrorMessage.SQL_ERROR);
						
			return Response.status(CodeTable.HTTP_INTERNAL_SERVER_ERROR)
					.entity(gson.toJson(errorReport))
					.build();
		}
		if(StringUtil.isNotBlank(s)){
			return Response.status(200).entity(s).build();		
		}else{
			return Response.status(404).entity(s).build();
		}
		
	}
	
	//获取铜仁新闻信息 ById
	@GET
	@Path("/getNewsById")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getOneCai (
			@QueryParam("news_id") String id,
			@Context HttpServletRequest req){
		ErrorReport errorReport = new ErrorReport();
		List<CaiModle> list=null;
		String s="";
		try {	
			list = dao.getOneCai(id,JDBCUtil.getConnection());
			Type listOfTestObject = new TypeToken<List<CaiModle>>(){}.getType();
			 s= gson.toJson(list, listOfTestObject);
		} catch (DBException e) {
			logger.error(e.getMessage(),e);
			errorReport.setHttpCode(CodeTable.HTTP_INTERNAL_SERVER_ERROR);
			errorReport.setErrorCode(CodeTable.SQLEXCEPTION);
			errorReport.setErrorMessage(ErrorMessage.SQL_ERROR);
						
			return Response.status(CodeTable.HTTP_INTERNAL_SERVER_ERROR)
					.entity(gson.toJson(errorReport))
					.build();
		}
		if(StringUtil.isNotBlank(s)){
			return Response.status(200).entity(s).build();		
		}else{
			return Response.status(404).entity(s).build();
		}
		
	}
	
	//获取文化旅游信息 Byrole
	@GET
	@Path("/getWenhuaLvyouByRole")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getUserById (
			@QueryParam("role") String id,
			@Context HttpServletRequest req){
		ErrorReport errorReport = new ErrorReport();
		List<UserModle> modlelist=null;
		String s="";
		try {	
			modlelist= dao.getUserById(id,JDBCUtil.getConnection());
			Type listOfTestObject = new TypeToken<List<UserModle>>(){}.getType();
			 s= gson.toJson(modlelist, listOfTestObject);
		} catch (DBException e) {
			logger.error(e.getMessage(),e);
			errorReport.setHttpCode(CodeTable.HTTP_INTERNAL_SERVER_ERROR);
			errorReport.setErrorCode(CodeTable.SQLEXCEPTION);
			errorReport.setErrorMessage(ErrorMessage.SQL_ERROR);
						
			return Response.status(CodeTable.HTTP_INTERNAL_SERVER_ERROR)
					.entity(gson.toJson(errorReport))
					.build();
		}
		if(StringUtil.isNotBlank(s)){
			return Response.status(200).entity(s).build();
		}else{
			return Response.status(404).entity(s).build();
		}
	}
	
	//获取文化旅游信息 Byid
	@GET
	@Path("/getWenhuaLvyouById")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getOneWenhua (
			@QueryParam("id") String id,
			@Context HttpServletRequest req){
		ErrorReport errorReport = new ErrorReport();
		List<UserModle> modlelist=null;
		String s="";
		try {	
			modlelist= dao.getOneWenhua(id,JDBCUtil.getConnection());
			Type listOfTestObject = new TypeToken<List<UserModle>>(){}.getType();
			 s= gson.toJson(modlelist, listOfTestObject);
		} catch (DBException e) {
			logger.error(e.getMessage(),e);
			errorReport.setHttpCode(CodeTable.HTTP_INTERNAL_SERVER_ERROR);
			errorReport.setErrorCode(CodeTable.SQLEXCEPTION);
			errorReport.setErrorMessage(ErrorMessage.SQL_ERROR);
						
			return Response.status(CodeTable.HTTP_INTERNAL_SERVER_ERROR)
					.entity(gson.toJson(errorReport))
					.build();
		}
		if(StringUtil.isNotBlank(s)){
			return Response.status(200).entity(s).build();		
		}else{
			return Response.status(404).entity(s).build();
		}
	}
	
	//插入community 互动交流   接口	
	@GET
	@Path("/insertCommunicate")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8" )
	public String getOneWenhua1 (
			@QueryParam("communicate")String  com,
			@Context HttpServletRequest req) throws UnsupportedEncodingException{
		boolean resultObj = false;
		String com1 = URLDecoder.decode(com, "UTF-8"); 
		Map<String, Object> map = new HashMap<String, Object>();
		CommunicateModle c = new CommunicateModle();
		String com_role;
		String name;
		String content;
		String title;
		String [] stringArr= com1.split(",");
        /* name =  URLDecoder.decode(name, "UTF-8"); ;
		title =java.net.URLDecoder.decode(stringArr[4],"UTF-8");
		content = java.net.URLDecoder.decode(stringArr[5],"UTF-8");*/
			map.put("com_role", stringArr[0]);
			map.put("com_name", stringArr[1]);
			map.put("com_phone", stringArr[2]);
			map.put("com_public", stringArr[3]);
			map.put("com_title", stringArr[4]);
			map.put("com_content", stringArr[5]);
			//map.put("com_reply", stringArr[6]);
			//map.put("write_time", stringArr[7]);
			//map.put("reply_time", stringArr[8]);
			JSONObject jb = JSONObject.fromObject(map);
			System.out.println(stringArr[1]);
			System.out.println("-jb--role-->"+jb.get("com_role"));

			com_role = (String)jb.get("com_role");
			/*com_name = (String)jb.get("com_name");
			com_phone = (String)jb.get("com_phone");*/
			c.setCom_role(Integer.parseInt(com_role));
			c.setComname((String)jb.get("com_name"));
			c.setComphone((String)jb.get("com_phone"));
			
			c.setCompublic((String)jb.get("com_public"));
			c.setComtitle((String)jb.get("com_title"));
			c.setComcontent((String)jb.get("com_content"));
			//c.setComreply((String)jb.get("com_reply"));
			//c.setWritetime((String)jb.get("write_time"));
			//c.setReplytime((String)jb.get("reply_time"));
		ErrorReport errorReport = new ErrorReport();
		System.out.println("-content-->"+c.getComcontent());
		System.out.println("-name-->"+c.getComname());
		System.out.println("-phone-->"+c.getComphone());
		//Type type = new TypeToken<CommunicateModle>(){}.getType();
		//CommunicateModle modle = gson.fromJson(com, type);
		try {	
			//System.out.println(modle.getComcontent());
			
					resultObj = dao.insertCommunicate(c, JDBCUtil.getConnection());
					
		} catch (DBException e) {
			logger.error(e.getMessage(),e);
			errorReport.setHttpCode(CodeTable.HTTP_INTERNAL_SERVER_ERROR);
			errorReport.setErrorCode(CodeTable.SQLEXCEPTION);
			errorReport.setErrorMessage(ErrorMessage.SQL_ERROR);
			/*return Response.status(CodeTable.HTTP_INTERNAL_SERVER_ERROR)
					.entity(gson.toJson(errorReport))
					.build();*/
		}
		//return Response.status(200).build();
		if (resultObj) {
			return "{\"ok\":\"true\"}";
		} else
			return "{\"ok\":\"false\",\"error\":\"Unknow error\"}";
	}
	
	//获取communication信息      Bypublic("Y"or"N")
		@GET
		@Path("/getCommunicationByPublic")
		@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
		public Response getComByPu (
				@QueryParam("pu") String pu,
				@Context HttpServletRequest req){
			ErrorReport errorReport = new ErrorReport();
			List<CommunicateModle> modlelist=null;
			String s="";
			try {	
				modlelist= dao.getComByPu(pu,JDBCUtil.getConnection());
				Type listOfTestObject = new TypeToken<List<CommunicateModle>>(){}.getType();
				 s= gson.toJson(modlelist, listOfTestObject);
			} catch (DBException e) {
				logger.error(e.getMessage(),e);
				errorReport.setHttpCode(CodeTable.HTTP_INTERNAL_SERVER_ERROR);
				errorReport.setErrorCode(CodeTable.SQLEXCEPTION);
				errorReport.setErrorMessage(ErrorMessage.SQL_ERROR);
							
				return Response.status(CodeTable.HTTP_INTERNAL_SERVER_ERROR)
						.entity(gson.toJson(errorReport))
						.build();
			}
			if(StringUtil.isNotBlank(s)){
				return Response.status(200).entity(s).build();
			}else{
				return Response.status(404).entity(s).build();
			}
		}
		
		// 获取互动交流信息byid
		@GET
		@Path("/getCommunicationById")
		@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
		public Response getComById (
				@QueryParam("com_id") String com_id,
				@Context HttpServletRequest req){
			ErrorReport errorReport = new ErrorReport();
			List<CommunicateModle> list=null;
			String s="";
			try {	
				list = dao.getComById(com_id,JDBCUtil.getConnection());
				Type listOfTestObject = new TypeToken<List<CommunicateModle>>(){}.getType();
				 s= gson.toJson(list, listOfTestObject);
			} catch (DBException e) {
				logger.error(e.getMessage(),e);
				errorReport.setHttpCode(CodeTable.HTTP_INTERNAL_SERVER_ERROR);
				errorReport.setErrorCode(CodeTable.SQLEXCEPTION);
				errorReport.setErrorMessage(ErrorMessage.SQL_ERROR);
							
				return Response.status(CodeTable.HTTP_INTERNAL_SERVER_ERROR)
						.entity(gson.toJson(errorReport))
						.build();
			}
			if(StringUtil.isNotBlank(s)){
				return Response.status(200).entity(s).build();		
			}else{
				return Response.status(404).entity(s).build();
			}
			
		}
		
	
	@GET
	@Path("/getOrdersById")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getOrderById (
			@QueryParam("id") String id,
			@Context HttpServletRequest req){
		ErrorReport errorReport = new ErrorReport();
		OrdersModle modle=null;
		String s="";
		try {	
			modle=dao.getOrderById(id,JDBCUtil.getConnection());
			modle.setOrderDetail(dao.getOrderDetailById(id,JDBCUtil.getConnection()));
			Type listOfTestObject = new TypeToken<OrdersModle>(){}.getType();
			 s= gson.toJson(modle, listOfTestObject);
		} catch (DBException e) {
			logger.error(e.getMessage(),e);
			errorReport.setHttpCode(CodeTable.HTTP_INTERNAL_SERVER_ERROR);
			errorReport.setErrorCode(CodeTable.SQLEXCEPTION);
			errorReport.setErrorMessage(ErrorMessage.SQL_ERROR);
						
			return Response.status(CodeTable.HTTP_INTERNAL_SERVER_ERROR)
					.entity(gson.toJson(errorReport))
					.build();
		}
		if(StringUtil.isNotBlank(s)){
			return Response.status(200).entity(s).build();		
		}else{
			return Response.status(404).entity(s).build();
		}
	}
	
	
	
	//获取城镇建设信息 Byrole
	@GET
	@Path("/getCityBuildingByRole")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response getCityByRole (
			@QueryParam("role") String role,
			@Context HttpServletRequest req){
		ErrorReport errorReport = new ErrorReport();
		List<CityModle> modlelist=null;
		String s="";
		try {	
			modlelist= dao.getCityByRole(role,JDBCUtil.getConnection());
			Type listOfTestObject = new TypeToken<List<CityModle>>(){}.getType();
			 s= gson.toJson(modlelist, listOfTestObject);
		} catch (DBException e) {
			logger.error(e.getMessage(),e);
			errorReport.setHttpCode(CodeTable.HTTP_INTERNAL_SERVER_ERROR);
			errorReport.setErrorCode(CodeTable.SQLEXCEPTION);
			errorReport.setErrorMessage(ErrorMessage.SQL_ERROR);
						
			return Response.status(CodeTable.HTTP_INTERNAL_SERVER_ERROR)
					.entity(gson.toJson(errorReport))
					.build();
		}
		if(StringUtil.isNotBlank(s)){
			return Response.status(200).entity(s).build();		
		}else{
			return Response.status(404).entity(s).build();
		}
	}
	
	//获取城镇建设信息 ById
		@GET
		@Path("/getCityBuildingById")
		@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
		public Response getCityById (
				@QueryParam("cb_id") String cb_id,
				@Context HttpServletRequest req){
			ErrorReport errorReport = new ErrorReport();
			List<CityModle> modlelist=null;
			String s="";
			try {	
				modlelist= dao.getCityById(cb_id,JDBCUtil.getConnection());
				Type listOfTestObject = new TypeToken<List<CityModle>>(){}.getType();
				 s= gson.toJson(modlelist, listOfTestObject);
			} catch (DBException e) {
				logger.error(e.getMessage(),e);
				errorReport.setHttpCode(CodeTable.HTTP_INTERNAL_SERVER_ERROR);
				errorReport.setErrorCode(CodeTable.SQLEXCEPTION);
				errorReport.setErrorMessage(ErrorMessage.SQL_ERROR);
							
				return Response.status(CodeTable.HTTP_INTERNAL_SERVER_ERROR)
						.entity(gson.toJson(errorReport))
						.build();
			}
			if(StringUtil.isNotBlank(s)){
				return Response.status(200).entity(s).build();		
			}else{
				return Response.status(404).entity(s).build();
			}
		}
	
	@POST
	@Path("/insertOrder")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED+ ";charset=utf-8")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response insertOrder (	
			@FormParam("orders") String orders,
			@Context HttpServletRequest req){
		ErrorReport errorReport = new ErrorReport();
		Type type = new TypeToken<OrdersModle>(){}.getType();
		OrdersModle modle = gson.fromJson(orders, type);
		try {	
			dao.insertOrder(modle, JDBCUtil.getConnection());
		} catch (DBException e) {
			logger.error(e.getMessage(),e);
			errorReport.setHttpCode(CodeTable.HTTP_INTERNAL_SERVER_ERROR);
			errorReport.setErrorCode(CodeTable.SQLEXCEPTION);
			errorReport.setErrorMessage(ErrorMessage.SQL_ERROR);
			return Response.status(CodeTable.HTTP_INTERNAL_SERVER_ERROR)
					.entity(gson.toJson(errorReport))
					.build();
		}
		return Response.status(200).build();
	}
	
	@POST
	@Path("/insertUser")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED+ ";charset=utf-8")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response insertUser (
			@FormParam("user") String user,
			@Context HttpServletRequest req){
		ErrorReport errorReport = new ErrorReport();
		Type type = new TypeToken<UserModle>(){}.getType();
		UserModle modle = gson.fromJson(user, type);
		try {	
			dao.insertUser(modle, JDBCUtil.getConnection());
		} catch (DBException e) {
			logger.error(e.getMessage(),e);
			errorReport.setHttpCode(CodeTable.HTTP_INTERNAL_SERVER_ERROR);
			errorReport.setErrorCode(CodeTable.SQLEXCEPTION);
			errorReport.setErrorMessage(ErrorMessage.SQL_ERROR);
			return Response.status(CodeTable.HTTP_INTERNAL_SERVER_ERROR)
					.entity(gson.toJson(errorReport))
					.build();
		}
		return Response.status(200).build();
	}
	
	
	@POST
	@Path("/updateUser")
	@Consumes(MediaType.APPLICATION_FORM_URLENCODED+ ";charset=utf-8")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response updateUser (
			@FormParam("user") String user,
			@Context HttpServletRequest req){
		ErrorReport errorReport = new ErrorReport();
		Type type = new TypeToken<UserModle>(){}.getType();
		UserModle modle = gson.fromJson(user, type);
		try {	
			dao.updateUser(modle, JDBCUtil.getConnection());
		} catch (DBException e) {
			logger.error(e.getMessage(),e);
			errorReport.setHttpCode(CodeTable.HTTP_INTERNAL_SERVER_ERROR);
			errorReport.setErrorCode(CodeTable.SQLEXCEPTION);
			errorReport.setErrorMessage(ErrorMessage.SQL_ERROR);
			return Response.status(CodeTable.HTTP_INTERNAL_SERVER_ERROR)
					.entity(gson.toJson(errorReport))
					.build();
		}
		return Response.status(200).build();
	}
	
	
	
	
	
	@GET
	@Path("/test")
	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
	public Response helloWould (
			@Context HttpServletRequest req) {
		String result="hellowould";
		return Response.status(200).entity(gson.toJson(result, String.class)).build();
	}
	
	
	
//	/**
//		 * <p>Title: </p>
//		 * <p>Description:获取调度任我</p>
//	     * @param condition
//	     * @param req
//	     * @return
//	*/
//	@GET
//	@Path("/myTasks")
//	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
//	public Response getMyScheduledTasks(
//			@QueryParam("condition") String condition,
//			@Context HttpServletRequest req) {
//
//		logger.info("getMyScheduledTasks (req=" + req + ") - start");
//		Gson gson = new Gson();
//		ErrorReport errorReport = new ErrorReport();
//		String token = req.getHeader("Auth_Token");
//		String remoteAddr = req.getRemoteAddr();	
//		String userCode = "";
//		try {
//			userCode = authenticationManager.getAuthenticatedUserCode(token, remoteAddr);
//		} catch (NoTokenProvidedException e) {
//			logger.error(e.getMessage());
//			
//			errorReport.setHttpCode(CodeTable.HTTP_NOT_ALLOWED);
//			errorReport.setErrorCode(CodeTable.NO_TOKEN_PROVIDED_ERROR);
//			errorReport.setErrorMessage(ErrorMessage.NO_TOKEN_PROVIDED_ERROR);
//						
//			return Response.status(CodeTable.HTTP_NOT_ALLOWED)
//					.entity(gson.toJson(errorReport))					
//					.build();
//		} catch (TokenErrorException e) {
//		
//			logger.error(e.getMessage());
//			
//			errorReport.setHttpCode(CodeTable.HTTP_INTERNAL_SERVER_ERROR);
//			errorReport.setErrorCode(CodeTable.AUTHENTICATION_TOKEN_ERROR);
//			errorReport.setErrorMessage(e.getMessage());
//						
//			return Response.status(CodeTable.HTTP_INTERNAL_SERVER_ERROR)
//					.entity(gson.toJson(errorReport))					
//					.build();
//		} catch (Exception e) {
//			logger.error(e.getMessage(),e);
//			
//			errorReport.setHttpCode(CodeTable.HTTP_INTERNAL_SERVER_ERROR);
//			errorReport.setErrorCode(CodeTable.UNDEFINED_ERROR);
//			errorReport.setErrorMessage(ErrorMessage.AUTHENTICATION_DATABASE_ERROR);
//						
//			return Response.status(CodeTable.HTTP_INTERNAL_SERVER_ERROR)
//					.entity(gson.toJson(errorReport))
//					.build();
//		}
//		
//		try {
//			
//			Map<String, String> condition2 =gson.fromJson(condition, HashMap.class);
//			List<SchTriggerAndProView> tasks = taskSchedulerService.getScheduledTriggerPors(condition2,0,1000,userCode);
//			Type listOfTestObject = new TypeToken<List<SchTriggerAndProView>>(){}.getType();
//			String s = gson.toJson(tasks, listOfTestObject);
//			
//			return Response.status(200).entity(s).build();
//		} catch (Exception e){
//			logger.error(e.getMessage(),e);
//			
//			errorReport.setHttpCode(CodeTable.HTTP_INTERNAL_SERVER_ERROR);
//			errorReport.setErrorCode(CodeTable.UNDEFINED_ERROR);
//			errorReport.setErrorMessage(e.getMessage());
//					
//			logger.info("getMyScheduledTasks (req=" + req + ") - end");
//			return Response.status(CodeTable.HTTP_INTERNAL_SERVER_ERROR)
//					.entity(gson.toJson(errorReport))
//					.build();
//		}		
//
//	}
//	
//@GET
//@Path("/getTriggerProByTriggerId")
//@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
//public Response getTriggerProByTriggerId(
//		@QueryParam("triggerId") String triggerId,
//		@Context HttpServletRequest req) {
//
//	logger.info("getTriggerProByTriggerId (req=" + req + ") - start");
//	Gson gson = new Gson();
//	ErrorReport errorReport = new ErrorReport();
//	String token = req.getHeader("Auth_Token");
//	String remoteAddr = req.getRemoteAddr();	
//	String userCode = "";
//	try {
//		userCode = authenticationManager.getAuthenticatedUserCode(token, remoteAddr);
//	} catch (NoTokenProvidedException e) {
//		logger.error(e.getMessage());
//		
//		errorReport.setHttpCode(CodeTable.HTTP_NOT_ALLOWED);
//		errorReport.setErrorCode(CodeTable.NO_TOKEN_PROVIDED_ERROR);
//		errorReport.setErrorMessage(ErrorMessage.NO_TOKEN_PROVIDED_ERROR);
//					
//		return Response.status(CodeTable.HTTP_NOT_ALLOWED)
//				.entity(gson.toJson(errorReport))					
//				.build();
//	} catch (TokenErrorException e) {
//	
//		logger.error(e.getMessage());
//		
//		errorReport.setHttpCode(CodeTable.HTTP_INTERNAL_SERVER_ERROR);
//		errorReport.setErrorCode(CodeTable.AUTHENTICATION_TOKEN_ERROR);
//		errorReport.setErrorMessage(e.getMessage());
//					
//		return Response.status(CodeTable.HTTP_INTERNAL_SERVER_ERROR)
//				.entity(gson.toJson(errorReport))					
//				.build();
//	} catch (Exception e) {
//		logger.error(e.getMessage(),e);
//		
//		errorReport.setHttpCode(CodeTable.HTTP_INTERNAL_SERVER_ERROR);
//		errorReport.setErrorCode(CodeTable.UNDEFINED_ERROR);
//		errorReport.setErrorMessage(ErrorMessage.AUTHENTICATION_DATABASE_ERROR);
//					
//		return Response.status(CodeTable.HTTP_INTERNAL_SERVER_ERROR)
//				.entity(gson.toJson(errorReport))
//				.build();
//	}
//	
//	try {
//		
//		SchTriggerPro taskPor = taskSchedulerService.findTriggerProByTriggerId(triggerId);
//		Type object = new TypeToken<List<SchTriggerPro>>(){}.getType();
//		String s = gson.toJson(taskPor, object);
//		
//		return Response.status(200).entity(s).build();
//	} catch (Exception e){
//		logger.error(e.getMessage(),e);
//		
//		errorReport.setHttpCode(CodeTable.HTTP_INTERNAL_SERVER_ERROR);
//		errorReport.setErrorCode(CodeTable.UNDEFINED_ERROR);
//		errorReport.setErrorMessage(e.getMessage());
//				
//		logger.info("getMyScheduledTasks (req=" + req + ") - end");
//		return Response.status(CodeTable.HTTP_INTERNAL_SERVER_ERROR)
//				.entity(gson.toJson(errorReport))
//				.build();
//	}		
//
//}
//	
//	/**
//	 * 更新调度任务
//	 * @param scheduledTaskData
//	 * @param req
//	 * @return
//	 */
//	@POST
//	@Path("/updateMyTask")
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED+ ";charset=utf-8")
//	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
//	@Deprecated
//	public Response updateScheduledTask(
//			@FormParam("scheduledTaskData") String scheduledTaskData,
//			@Context HttpServletRequest req){
//		logger.info("updateScheduledTask (scheduledTaskData=" + scheduledTaskData + ") - start");
//		ErrorReport errorReport = new ErrorReport();
//		String remoteAddr = req.getRemoteAddr();	
//		String token = req.getHeader("Auth_Token");
//		String userCode = "";
//		try {
//			userCode = authenticationManager.getAuthenticatedUserCode(token, remoteAddr);
//		} catch (NoTokenProvidedException e) {
//			logger.error(e.getMessage());
//			
//			errorReport.setHttpCode(CodeTable.HTTP_NOT_ALLOWED);
//			errorReport.setErrorCode(CodeTable.NO_TOKEN_PROVIDED_ERROR);
//			errorReport.setErrorMessage(ErrorMessage.NO_TOKEN_PROVIDED_ERROR);
//						
//			return Response.status(CodeTable.HTTP_NOT_ALLOWED)
//					.entity(gson.toJson(errorReport))					
//					.build();
//		} catch (TokenErrorException e) {
//		
//			logger.error(e.getMessage());
//			
//			errorReport.setHttpCode(CodeTable.HTTP_INTERNAL_SERVER_ERROR);
//			errorReport.setErrorCode(CodeTable.AUTHENTICATION_TOKEN_ERROR);
//			errorReport.setErrorMessage(e.getMessage());
//						
//			return Response.status(CodeTable.HTTP_INTERNAL_SERVER_ERROR)
//					.entity(gson.toJson(errorReport))					
//					.build();
//		} catch (Exception e) {
//			logger.error(e.getMessage(),e);
//			
//			errorReport.setHttpCode(CodeTable.HTTP_INTERNAL_SERVER_ERROR);
//			errorReport.setErrorCode(CodeTable.UNDEFINED_ERROR);
//			errorReport.setErrorMessage(ErrorMessage.AUTHENTICATION_DATABASE_ERROR);
//						
//			return Response.status(CodeTable.HTTP_INTERNAL_SERVER_ERROR)
//					.entity(gson.toJson(errorReport))
//					.build();
//		}
//		
//		int res = 0;
//		SchTriggerAndProView schTriggerAndProView = new SchTriggerAndProView();
//		schTriggerAndProView = gson.fromJson(scheduledTaskData, SchTriggerAndProView.class);
//		SchTrigger schTrigger = new SchTrigger();
//		SchTriggerPro triggerPro = new SchTriggerPro();
//		
//		try {
//			if(schTriggerAndProView != null){
//				schTrigger = schTriggerAndProView;
//				triggerPro.setInsertDt(schTriggerAndProView.getInsertDt());
//				triggerPro.setIsDataDir(schTriggerAndProView.getIsDataDir());
//				triggerPro.setIsDeleted(schTriggerAndProView.getIsDeleted());
//				triggerPro.setTriggerproCreaterId(schTriggerAndProView.getTriggerCreaterId());
//				triggerPro.setTriggerId(schTriggerAndProView.getTriggerId());
//				triggerPro.setTriggerproDesc(schTriggerAndProView.getTriggerproDesc());
//				triggerPro.setTriggerproLocalDir(schTriggerAndProView.getTriggerproLocalDir());
//				triggerPro.setTriggerproId(schTriggerAndProView.getTriggerproId());
//				triggerPro.setTriggerproKeyword(schTriggerAndProView.getTriggerproKeyword());
//				triggerPro.setTriggerproLoginIp(schTriggerAndProView.getTriggerproLoginIp());
//				triggerPro.setTriggerproLoginMac(schTriggerAndProView.getTriggerproLoginMac());
//				triggerPro.setTriggerproPersonId(schTriggerAndProView.getTriggerproPersonId());
//				triggerPro.setTriggerproPersonName(schTriggerAndProView.getTriggerproPersonName());
//				triggerPro.setFileUploadtypeValue(schTriggerAndProView.getFileUploadtypeValue());
//				triggerPro.setActivatedBy(schTriggerAndProView.getActivatedBy());
//				triggerPro.setDisabledBy(schTriggerAndProView.getDisabledBy());
//				triggerPro.setMachineName(schTriggerAndProView.getMachineName());
//			}
//			res = taskSchedulerService.updateTriggerAndManager(schTrigger, triggerPro,userCode);
//		} catch (Exception e) {
//			logger.error(e.getMessage(),e);
//			
//			errorReport.setHttpCode(CodeTable.HTTP_INTERNAL_SERVER_ERROR);
//			errorReport.setErrorCode(CodeTable.UNDEFINED_ERROR);
//			errorReport.setErrorMessage(e.getMessage());
//						
//			return Response.status(CodeTable.HTTP_INTERNAL_SERVER_ERROR)
//					.entity(gson.toJson(errorReport))
//					.build();
//		}
//		logger.info("updateScheduledTask (scheduledTaskData=" + scheduledTaskData + ") - end");
//		return Response.status(200).entity(gson.toJson(res)).build();
//	}
//	
//	
//	/**
//		 * <p>Title: </p>
//		 * <p>Description:添加调度监控</p>
//	     * @param monitorData
//	     * @param req
//	     * @return
//	*/
//	@POST
//	@Path("/addTriggerMonitor")
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED+ ";charset=utf-8")
//	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
//	public Response addTriggerMonitor(@FormParam("monitorData") String monitorData,
//			@Context HttpServletRequest req){
//		logger.info("addTriggerMonitor (monitorData=" + monitorData + ") - start");
//		ErrorReport errorReport = new ErrorReport();
//		String remoteAddr = req.getRemoteAddr();	
//		String token = req.getHeader("Auth_Token");
//		String userCode = "";
//		try {
//			userCode = authenticationManager.getAuthenticatedUserCode(token, remoteAddr);
//		} catch (NoTokenProvidedException e) {
//			logger.error(e.getMessage());
//			
//			errorReport.setHttpCode(CodeTable.HTTP_NOT_ALLOWED);
//			errorReport.setErrorCode(CodeTable.NO_TOKEN_PROVIDED_ERROR);
//			errorReport.setErrorMessage(ErrorMessage.NO_TOKEN_PROVIDED_ERROR);
//						
//			return Response.status(CodeTable.HTTP_NOT_ALLOWED)
//					.entity(gson.toJson(errorReport))					
//					.build();
//		} catch (TokenErrorException e) {
//		
//			logger.error(e.getMessage());
//			
//			errorReport.setHttpCode(CodeTable.HTTP_INTERNAL_SERVER_ERROR);
//			errorReport.setErrorCode(CodeTable.AUTHENTICATION_TOKEN_ERROR);
//			errorReport.setErrorMessage(e.getMessage());
//						
//			return Response.status(CodeTable.HTTP_INTERNAL_SERVER_ERROR)
//					.entity(gson.toJson(errorReport))					
//					.build();
//		} catch (Exception e) {
//			logger.error(e.getMessage(),e);
//			
//			errorReport.setHttpCode(CodeTable.HTTP_INTERNAL_SERVER_ERROR);
//			errorReport.setErrorCode(CodeTable.UNDEFINED_ERROR);
//			errorReport.setErrorMessage(ErrorMessage.AUTHENTICATION_DATABASE_ERROR);
//						
//			return Response.status(CodeTable.HTTP_INTERNAL_SERVER_ERROR)
//					.entity(gson.toJson(errorReport))
//					.build();
//		}
//		
//		Map<String, Object> monitorMap = new HashMap<String, Object>();
//		if(!StringUtils.isBlank(monitorData)){
//			monitorMap = gson.fromJson(monitorData, HashMap.class);
//		}		
//		
//		//检查用户是否有权限进行该操作
//		String triggerId = (String) monitorMap.get(TaskConsts.TRIGGER_ID);
//		errorReport = RestUtil.checkTaskOperationPrivilege(triggerId, userCode);
//		if (errorReport.getHttpCode() != CodeTable.HTTP_SUCCESS_CODE) {
//			return Response.status(errorReport.getHttpCode())
//					.entity(gson.toJson(errorReport))
//					.build();
//		}	
//				
//		monitorMap.put("CREATER_ID", userCode);
//		int flag=0;
//		try {
//			flag = taskSchMonitorService.addMonitor(monitorMap);
//		} catch (Exception e) {
//			logger.error(e.getMessage());
//			
//			errorReport.setHttpCode(CodeTable.HTTP_INTERNAL_SERVER_ERROR);
//			errorReport.setErrorCode(CodeTable.UNDEFINED_ERROR);
//			errorReport.setErrorMessage("新建调度监控信息时出错");
//						
//			return Response.status(CodeTable.HTTP_INTERNAL_SERVER_ERROR)
//					.entity(gson.toJson(errorReport))					
//					.build();
//		}
//		logger.info("addTriggerMonitor (monitorData=" + monitorData + ") - end");		
//		return Response.status(200).entity(gson.toJson(flag, Integer.class)).build();
//	}
//	
//	/**
//		 * <p>Title: </p>
//		 * <p>Description:更新调度监控</p>
//	     * @param monitorData
//	     * @param req
//	     * @return
//	*/
//	@POST
//	@Path("/updateTriggerMonitor")
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED+ ";charset=utf-8")
//	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
//	public Response updateTriggerMonitor(@FormParam("monitorData") String monitorData,
//			@Context HttpServletRequest req){
//		logger.info("updateTriggerMonitor (monitorData=" + monitorData + ") - start");
//		ErrorReport errorReport = new ErrorReport();
//		String remoteAddr = req.getRemoteAddr();	
//		String token = req.getHeader("Auth_Token");
//		String userCode = "";
//		try {
//			userCode = authenticationManager.getAuthenticatedUserCode(token, remoteAddr);
//		} catch (NoTokenProvidedException e) {
//			logger.error(e.getMessage());
//			
//			errorReport.setHttpCode(CodeTable.HTTP_NOT_ALLOWED);
//			errorReport.setErrorCode(CodeTable.NO_TOKEN_PROVIDED_ERROR);
//			errorReport.setErrorMessage(ErrorMessage.NO_TOKEN_PROVIDED_ERROR);
//						
//			return Response.status(CodeTable.HTTP_NOT_ALLOWED)
//					.entity(gson.toJson(errorReport))					
//					.build();
//		} catch (TokenErrorException e) {
//		
//			logger.error(e.getMessage());
//			
//			errorReport.setHttpCode(CodeTable.HTTP_INTERNAL_SERVER_ERROR);
//			errorReport.setErrorCode(CodeTable.AUTHENTICATION_TOKEN_ERROR);
//			errorReport.setErrorMessage(e.getMessage());
//						
//			return Response.status(CodeTable.HTTP_INTERNAL_SERVER_ERROR)
//					.entity(gson.toJson(errorReport))					
//					.build();
//		} catch (Exception e) {
//			logger.error(e.getMessage(),e);
//			
//			errorReport.setHttpCode(CodeTable.HTTP_INTERNAL_SERVER_ERROR);
//			errorReport.setErrorCode(CodeTable.UNDEFINED_ERROR);
//			errorReport.setErrorMessage(ErrorMessage.AUTHENTICATION_DATABASE_ERROR);
//						
//			return Response.status(CodeTable.HTTP_INTERNAL_SERVER_ERROR)
//					.entity(gson.toJson(errorReport))
//					.build();
//		}
//		
//		Map<String, Object> monitorMap = new HashMap<String, Object>();
//		if(!StringUtils.isBlank(monitorData)){
//			monitorMap = gson.fromJson(monitorData, HashMap.class);
//		}
//		
//		//检查用户是否有权限进行该操作
//		String triggerId = (String) monitorMap.get(TaskConsts.TRIGGER_ID);
//		errorReport = RestUtil.checkTaskOperationPrivilege(triggerId, userCode);
//		if (errorReport.getHttpCode() != CodeTable.HTTP_SUCCESS_CODE) {
//			return Response.status(errorReport.getHttpCode())
//					.entity(gson.toJson(errorReport))
//					.build();
//		}	
//		
//		int flag=0;
//		try {
//			flag = taskSchMonitorService.modifyMonitor(monitorMap,"N");
//		} catch (Exception e) {
//			logger.error(e.getMessage());
//			
//			errorReport.setHttpCode(CodeTable.HTTP_INTERNAL_SERVER_ERROR);
//			errorReport.setErrorCode(CodeTable.UNDEFINED_ERROR);
//			errorReport.setErrorMessage("更新调度监控信息时出错");
//						
//			return Response.status(CodeTable.HTTP_INTERNAL_SERVER_ERROR)
//					.entity(gson.toJson(errorReport))					
//					.build();
//		}
//		logger.info("updateTriggerMonitor (monitorData=" + monitorData + ") - end");		
//		return Response.status(200).entity(gson.toJson(flag, Integer.class)).build();
//	}
//	
//	/**
//		 * <p>Title: </p>
//		 * <p>Description:添加调度规则</p>
//	     * @param monitorRuleData
//	     * @param req
//	     * @return
//	*/
//	@POST
//	@Path("/addTriggerRule")
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED+ ";charset=utf-8")
//	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
//	public Response addTriggerRule(@FormParam("monitorRuleData") String monitorRuleData,
//			@Context HttpServletRequest req){
//		logger.info("addTriggerRule (monitorRuleData=" + monitorRuleData + ") - start");
//		ErrorReport errorReport = new ErrorReport();
//		String remoteAddr = req.getRemoteAddr();	
//		String token = req.getHeader("Auth_Token");
//		String userCode = "";
//		try {
//			userCode = authenticationManager.getAuthenticatedUserCode(token, remoteAddr);
//		} catch (NoTokenProvidedException e) {
//			logger.error(e.getMessage());
//			
//			errorReport.setHttpCode(CodeTable.HTTP_NOT_ALLOWED);
//			errorReport.setErrorCode(CodeTable.NO_TOKEN_PROVIDED_ERROR);
//			errorReport.setErrorMessage(ErrorMessage.NO_TOKEN_PROVIDED_ERROR);
//						
//			return Response.status(CodeTable.HTTP_NOT_ALLOWED)
//					.entity(gson.toJson(errorReport))					
//					.build();
//		} catch (TokenErrorException e) {
//		
//			logger.error(e.getMessage());
//			
//			errorReport.setHttpCode(CodeTable.HTTP_INTERNAL_SERVER_ERROR);
//			errorReport.setErrorCode(CodeTable.AUTHENTICATION_TOKEN_ERROR);
//			errorReport.setErrorMessage(e.getMessage());
//						
//			return Response.status(CodeTable.HTTP_INTERNAL_SERVER_ERROR)
//					.entity(gson.toJson(errorReport))					
//					.build();
//		} catch (Exception e) {
//			logger.error(e.getMessage(),e);
//			
//			errorReport.setHttpCode(CodeTable.HTTP_INTERNAL_SERVER_ERROR);
//			errorReport.setErrorCode(CodeTable.UNDEFINED_ERROR);
//			errorReport.setErrorMessage(ErrorMessage.AUTHENTICATION_DATABASE_ERROR);
//						
//			return Response.status(CodeTable.HTTP_INTERNAL_SERVER_ERROR)
//					.entity(gson.toJson(errorReport))
//					.build();
//		}
//		
//		Map<String, Object> monitorRuleMap = new HashMap<String, Object>();
//		if(!StringUtils.isBlank(monitorRuleData)){
//			monitorRuleMap = gson.fromJson(monitorRuleData, HashMap.class);
//		}
//		
//		// 检查用户是否有权限进行该操作
//		String triggerId = (String) monitorRuleMap.get(TaskConsts.TRIGGER_ID);
//		errorReport = RestUtil.checkTaskOperationPrivilege(triggerId, userCode);
//		if (errorReport.getHttpCode() != CodeTable.HTTP_SUCCESS_CODE) {
//			return Response.status(errorReport.getHttpCode())
//					.entity(gson.toJson(errorReport)).build();
//		}
//		
//		int flag=0;
//		try {
//			flag = taskSchMonitorRuleService.addMonitorRule(monitorRuleMap);
//		} catch (Exception e) {
//			logger.error(e.getMessage());
//			
//			errorReport.setHttpCode(CodeTable.HTTP_INTERNAL_SERVER_ERROR);
//			errorReport.setErrorCode(CodeTable.UNDEFINED_ERROR);
//			errorReport.setErrorMessage("新增调度规则信息时出错");
//						
//			return Response.status(CodeTable.HTTP_INTERNAL_SERVER_ERROR)
//					.entity(gson.toJson(errorReport))					
//					.build();
//		}
//		logger.info("addTriggerRule (monitorRuleData=" + monitorRuleData + ") - end");		
//		return Response.status(200).entity(gson.toJson(flag, Integer.class)).build();
//	}
//	
//	
//	/**
//		 * <p>Title: </p>
//		 * <p>Description:更新调度规则</p>
//	     * @param monitorRuleData
//	     * @param req
//	     * @return
//	*/
//	@POST
//	@Path("/updateTriggerRule")
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED+ ";charset=utf-8")
//	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
//	public Response updateTriggerRule(@FormParam("monitorRuleData") String monitorRuleData,
//			@Context HttpServletRequest req){
//		logger.info("updateTriggerRule (monitorRuleData=" + monitorRuleData + ") - start");
//		ErrorReport errorReport = new ErrorReport();
//		String remoteAddr = req.getRemoteAddr();	
//		String token = req.getHeader("Auth_Token");
//		String userCode = "";
//		try {
//			userCode = authenticationManager.getAuthenticatedUserCode(token, remoteAddr);
//		} catch (NoTokenProvidedException e) {
//			logger.error(e.getMessage());
//			
//			errorReport.setHttpCode(CodeTable.HTTP_NOT_ALLOWED);
//			errorReport.setErrorCode(CodeTable.NO_TOKEN_PROVIDED_ERROR);
//			errorReport.setErrorMessage(ErrorMessage.NO_TOKEN_PROVIDED_ERROR);
//						
//			return Response.status(CodeTable.HTTP_NOT_ALLOWED)
//					.entity(gson.toJson(errorReport))					
//					.build();
//		} catch (TokenErrorException e) {
//		
//			logger.error(e.getMessage());
//			
//			errorReport.setHttpCode(CodeTable.HTTP_INTERNAL_SERVER_ERROR);
//			errorReport.setErrorCode(CodeTable.AUTHENTICATION_TOKEN_ERROR);
//			errorReport.setErrorMessage(e.getMessage());
//						
//			return Response.status(CodeTable.HTTP_INTERNAL_SERVER_ERROR)
//					.entity(gson.toJson(errorReport))					
//					.build();
//		} catch (Exception e) {
//			logger.error(e.getMessage(),e);
//			
//			errorReport.setHttpCode(CodeTable.HTTP_INTERNAL_SERVER_ERROR);
//			errorReport.setErrorCode(CodeTable.UNDEFINED_ERROR);
//			errorReport.setErrorMessage(ErrorMessage.AUTHENTICATION_DATABASE_ERROR);
//						
//			return Response.status(CodeTable.HTTP_INTERNAL_SERVER_ERROR)
//					.entity(gson.toJson(errorReport))
//					.build();
//		}
//		
//		Map<String, Object> monitorRuleMap = new HashMap<String, Object>();
//		if(!StringUtils.isBlank(monitorRuleData)){
//			monitorRuleMap = gson.fromJson(monitorRuleData, HashMap.class);
//		}
//		
//		// 检查用户是否有权限进行该操作
//		String triggerId = (String) monitorRuleMap.get(TaskConsts.TRIGGER_ID);
//		errorReport = RestUtil.checkTaskOperationPrivilege(triggerId, userCode);
//		if (errorReport.getHttpCode() != CodeTable.HTTP_SUCCESS_CODE) {
//			return Response.status(errorReport.getHttpCode())
//					.entity(gson.toJson(errorReport)).build();
//		}
//		
//		int flag=0;
//		try {
//			flag = taskSchMonitorRuleService.modifyMonitorRule(monitorRuleMap);
//		} catch (Exception e) {
//			logger.error(e.getMessage());
//			
//			errorReport.setHttpCode(CodeTable.HTTP_INTERNAL_SERVER_ERROR);
//			errorReport.setErrorCode(CodeTable.UNDEFINED_ERROR);
//			errorReport.setErrorMessage("更新调度规则信息时出错");
//						
//			return Response.status(CodeTable.HTTP_INTERNAL_SERVER_ERROR)
//					.entity(gson.toJson(errorReport))					
//					.build();
//		}
//		logger.info("updateTriggerRule (monitorRuleData=" + monitorRuleData + ") - end");		
//		return Response.status(200).entity(gson.toJson(flag, Integer.class)).build();
//	}
//	
//	/**
//		 * <p>Title: </p>
//		 * <p>Description:按照调度ID查询调度规则</p>
//	     * @param triggerId
//	     * @param req
//	     * @return
//	*/
//	@GET
//	@Path("/getTriggerRuleByTriggerId")
//	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
//	public Response getTriggerRuleByTriggerId  (
//			@QueryParam("triggerId") String triggerId,			
//			@Context HttpServletRequest req) {
//		logger.info("getTriggerRuleByTriggerId (triggerId=" + triggerId + ") - start ");	
//		ErrorReport errorReport = new ErrorReport();
//		String remoteAddr = req.getRemoteAddr();	
//		String token = req.getHeader("Auth_Token");
//		String userCode = "";
//		try {
//			userCode = authenticationManager.getAuthenticatedUserCode(token, remoteAddr);
//		} catch (NoTokenProvidedException e) {
//			logger.error(e.getMessage());
//			
//			errorReport.setHttpCode(CodeTable.HTTP_NOT_ALLOWED);
//			errorReport.setErrorCode(CodeTable.NO_TOKEN_PROVIDED_ERROR);
//			errorReport.setErrorMessage(ErrorMessage.NO_TOKEN_PROVIDED_ERROR);
//						
//			return Response.status(CodeTable.HTTP_NOT_ALLOWED)
//					.entity(gson.toJson(errorReport))					
//					.build();
//		} catch (TokenErrorException e) {
//		
//			logger.error(e.getMessage());
//			
//			errorReport.setHttpCode(CodeTable.HTTP_INTERNAL_SERVER_ERROR);
//			errorReport.setErrorCode(CodeTable.AUTHENTICATION_TOKEN_ERROR);
//			errorReport.setErrorMessage(e.getMessage());
//						
//			return Response.status(CodeTable.HTTP_INTERNAL_SERVER_ERROR)
//					.entity(gson.toJson(errorReport))					
//					.build();
//		} catch (Exception e) {
//			logger.error(e.getMessage(),e);
//			
//			errorReport.setHttpCode(CodeTable.HTTP_INTERNAL_SERVER_ERROR);
//			errorReport.setErrorCode(CodeTable.UNDEFINED_ERROR);
//			errorReport.setErrorMessage(ErrorMessage.AUTHENTICATION_DATABASE_ERROR);
//						
//			return Response.status(CodeTable.HTTP_INTERNAL_SERVER_ERROR)
//					.entity(gson.toJson(errorReport))
//					.build();
//		}
//		
//		Map results = new HashMap();
//		try {
//			results = taskSchMonitorRuleService.findMonitorRuleByTriggerId(triggerId);
//		} catch(Exception e) {
//			logger.error(e.getMessage());
//			
//			errorReport.setHttpCode(CodeTable.HTTP_INTERNAL_SERVER_ERROR);
//			errorReport.setErrorCode(CodeTable.UNDEFINED_ERROR);
//			errorReport.setErrorMessage("按照调度ID查询调度规则时出错");
//						
//			return Response.status(CodeTable.HTTP_INTERNAL_SERVER_ERROR)
//					.entity(gson.toJson(errorReport))					
//					.build();
//		}
//		logger.info("getTriggerRuleByTriggerId (triggerId=" + triggerId + ") - end ");	
//		
//		return Response.status(200).entity(gson.toJson(results, HashMap.class)).build();
//		
//	}
//	
//	/**
//		 * <p>Title: </p>
//		 * <p>Description:按照调度ID查询调度监控信息</p>
//	     * @param triggerId
//	     * @param req
//	     * @return
//	*/
//	@GET
//	@Path("/getTriggerMonitorByTriggerId")
//	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
//	public Response getTriggerMonitorByTriggerId  (
//			@QueryParam("triggerId") String triggerId,			
//			@Context HttpServletRequest req) {
//		logger.info("getTriggerMonitorByTriggerId (triggerId=" + triggerId + ") - start ");	
//		ErrorReport errorReport = new ErrorReport();
//		String remoteAddr = req.getRemoteAddr();	
//		String token = req.getHeader("Auth_Token");
//		String userCode = "";
//		try {
//			userCode = authenticationManager.getAuthenticatedUserCode(token, remoteAddr);
//		} catch (NoTokenProvidedException e) {
//			logger.error(e.getMessage());
//			
//			errorReport.setHttpCode(CodeTable.HTTP_NOT_ALLOWED);
//			errorReport.setErrorCode(CodeTable.NO_TOKEN_PROVIDED_ERROR);
//			errorReport.setErrorMessage(ErrorMessage.NO_TOKEN_PROVIDED_ERROR);
//						
//			return Response.status(CodeTable.HTTP_NOT_ALLOWED)
//					.entity(gson.toJson(errorReport))					
//					.build();
//		} catch (TokenErrorException e) {
//		
//			logger.error(e.getMessage());
//			
//			errorReport.setHttpCode(CodeTable.HTTP_INTERNAL_SERVER_ERROR);
//			errorReport.setErrorCode(CodeTable.AUTHENTICATION_TOKEN_ERROR);
//			errorReport.setErrorMessage(e.getMessage());
//						
//			return Response.status(CodeTable.HTTP_INTERNAL_SERVER_ERROR)
//					.entity(gson.toJson(errorReport))					
//					.build();
//		} catch (Exception e) {
//			logger.error(e.getMessage(),e);
//			
//			errorReport.setHttpCode(CodeTable.HTTP_INTERNAL_SERVER_ERROR);
//			errorReport.setErrorCode(CodeTable.UNDEFINED_ERROR);
//			errorReport.setErrorMessage(ErrorMessage.AUTHENTICATION_DATABASE_ERROR);
//						
//			return Response.status(CodeTable.HTTP_INTERNAL_SERVER_ERROR)
//					.entity(gson.toJson(errorReport))
//					.build();
//		}
//		
//		Map results = new HashMap();
//		try {
//			results = taskSchMonitorService.findMonitorByTriggerId(triggerId);
//		} catch(Exception e) {
//			logger.error(e.getMessage());
//			
//			errorReport.setHttpCode(CodeTable.HTTP_INTERNAL_SERVER_ERROR);
//			errorReport.setErrorCode(CodeTable.UNDEFINED_ERROR);
//			errorReport.setErrorMessage("按照调度ID查询调度监控信息时出错");
//						
//			return Response.status(CodeTable.HTTP_INTERNAL_SERVER_ERROR)
//					.entity(gson.toJson(errorReport))					
//					.build();
//		}
//		logger.info("getTriggerMonitorByTriggerId (triggerId=" + triggerId + ") - end ");	
//		
//		return Response.status(200).entity(gson.toJson(results, HashMap.class)).build();
//		
//	}
//	
//	@POST
//	@Path("/disableTask")
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED+ ";charset=utf-8")
//	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
//	public Response disableTask(
//			@FormParam("triggerId") String triggerId,
//			@FormParam("triggerName") String triggerName,
//			@FormParam("isForcedToDisable") String isForcedToDisable,
//			@FormParam("emailCoppiers") String emailCoppiers,
//			@Context HttpServletRequest req) {
//		
//		logger.info("disableTask (triggerId=" + triggerId + " isForced="+isForcedToDisable+") - start ");	
//		ErrorReport errorReport = new ErrorReport();
//		String remoteAddr = req.getRemoteAddr();	
//		String remoteHost = req.getRemoteHost();
//		String token = req.getHeader("Auth_Token");
//		
//		errorReport = RestUtil.getUserCodeByToken(token,remoteAddr);
//		if (errorReport.getHttpCode() != CodeTable.HTTP_SUCCESS_CODE) {
//			return Response.status(errorReport.getHttpCode())
//					.entity(gson.toJson(errorReport))
//					.build();
//		}
//		String userCode = errorReport.getErrorMessage();		
//		
//		//检查用户是否有权限进行该操作
//		errorReport = RestUtil.checkTaskOperationPrivilege(triggerId, userCode);
//		if (errorReport.getHttpCode() != CodeTable.HTTP_SUCCESS_CODE) {
//			return Response.status(errorReport.getHttpCode())
//					.entity(gson.toJson(errorReport))
//					.build();
//		}	
//
//		Map<String,String> result = new HashMap<String,String>();
//		try {
//			result = taskSchedulerService.getTriggerStatus(triggerId);
//		} catch (Exception e) {
//			logger.error(e.toString());
//			
//			errorReport.setHttpCode(CodeTable.HTTP_INTERNAL_SERVER_ERROR);
//			errorReport.setErrorCode(CodeTable.UNDEFINED_ERROR);
//			errorReport.setErrorMessage("查询调度状态时出错");
//						
//			return Response.status(CodeTable.HTTP_INTERNAL_SERVER_ERROR)
//					.entity(gson.toJson(errorReport))					
//					.build();
//		}
//		String isEnabled = result.get("is_enabled");
//		String activatedBy= result.get("activate_by");
//		String ip = result.get("triggerpro_login_ip");
//		String host = result.get("machine_name");
//		
//		if ("N".equals(isEnabled)) {
//			//trigger的状态已经是停用时，通知用户停用成功，不做任何处理
//			return Response.status(200).build();
//		} else if ("Y".equals(isForcedToDisable)) {
//			//trigger需要被强制停用
//			try {
//				taskSchedulerService.updateTaskStatus(triggerId, triggerName, userCode, remoteAddr, remoteHost, false, true, TaskSchedulerService.OP_FROM_CLIENT, emailCoppiers);
//			} catch (Exception e) {
//				logger.error(e.toString());
//				
//				errorReport.setHttpCode(CodeTable.HTTP_INTERNAL_SERVER_ERROR);
//				errorReport.setErrorCode(CodeTable.UNDEFINED_ERROR);
//				errorReport.setErrorMessage("停用调度时出错");
//							
//				return Response.status(CodeTable.HTTP_INTERNAL_SERVER_ERROR)
//						.entity(gson.toJson(errorReport))					
//						.build();
//			} 
//			
//		} else if (userCode.equals(activatedBy) && remoteAddr.equals(ip)) {
//			//trigger已经被本人在本客户端启用的情况，正常停用后通知用户
//			try {
//				taskSchedulerService.updateTaskStatus(triggerId, triggerName, userCode, remoteAddr, remoteHost, false, false, TaskSchedulerService.OP_FROM_CLIENT, emailCoppiers);
//			} catch (Exception e) {
//				logger.error(e.toString());
//				
//				errorReport.setHttpCode(CodeTable.HTTP_INTERNAL_SERVER_ERROR);
//				errorReport.setErrorCode(CodeTable.UNDEFINED_ERROR);
//				errorReport.setErrorMessage("停用调度时出错");
//							
//				return Response.status(CodeTable.HTTP_INTERNAL_SERVER_ERROR)
//						.entity(gson.toJson(errorReport))					
//						.build();
//			}
//			
//		} else {
//			errorReport.setHttpCode(CodeTable.HTTP_INTERNAL_SERVER_ERROR);
//			errorReport.setErrorCode(CodeTable.TRIGGER_ALREADY_ENABLED);
//			errorReport.setErrorMessage("此任务已经被"+activatedBy+"在"+ip +"的名为 "+host+"  的电脑上启用");
//						
//			return Response.status(CodeTable.HTTP_INTERNAL_SERVER_ERROR)
//					.entity(gson.toJson(errorReport))					
//					.build();
//		}
//		
//		return Response.status(200).build();
//	}
//
//	@POST
//	@Path("/enableTask")
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED+ ";charset=utf-8")
//	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
//	public Response enableTask(
//			@FormParam("triggerId") String triggerId,
//			@FormParam("triggerName") String triggerName,
//			@FormParam("isForcedToEnable") String isForcedToEnable,
//			@FormParam("emailCoppiers") String emailCoppiers,
//			@Context HttpServletRequest req) {
//		logger.info("enableTask (triggerId=" + triggerId + " isForced="+isForcedToEnable+") - start ");	
//		ErrorReport errorReport = new ErrorReport();
//		String remoteAddr = req.getRemoteAddr();	
//		String remoteHost = req.getRemoteHost();
//		String token = req.getHeader("Auth_Token");
//		errorReport = RestUtil.getUserCodeByToken(token,remoteAddr);
//		if (errorReport.getHttpCode() != CodeTable.HTTP_SUCCESS_CODE) {
//			return Response.status(errorReport.getHttpCode())
//					.entity(gson.toJson(errorReport))
//					.build();
//		}
//		String userCode = errorReport.getErrorMessage();
//		
//		//检查用户是否有权限进行该操作
//		errorReport = RestUtil.checkTaskOperationPrivilege(triggerId, userCode);
//		if (errorReport.getHttpCode() != CodeTable.HTTP_SUCCESS_CODE) {
//			return Response.status(errorReport.getHttpCode())
//					.entity(gson.toJson(errorReport))
//					.build();
//		}	
//		
//		Map<String,String> result = new HashMap<String,String>();
//		try {
//			result = taskSchedulerService.getTriggerStatus(triggerId);
//		} catch (Exception e) {
//			logger.error(e.toString());
//			
//			errorReport.setHttpCode(CodeTable.HTTP_INTERNAL_SERVER_ERROR);
//			errorReport.setErrorCode(CodeTable.UNDEFINED_ERROR);
//			errorReport.setErrorMessage("查询调度状态时出错");
//						
//			return Response.status(CodeTable.HTTP_INTERNAL_SERVER_ERROR)
//					.entity(gson.toJson(errorReport))					
//					.build();
//		}
//		String isEnabled = result.get("is_enabled");
//		String activatedBy= result.get("activate_by");
//		String ip = result.get("triggerpro_login_ip");
//		String host = result.get("machine_name");		
//
//		
//		if ("N".equals(isEnabled) || StringUtils.isEmpty(isEnabled)) {
//			//trigger的状态是停用时，正常启用
//			try {
//				taskSchedulerService.updateTaskStatus(triggerId, triggerName, userCode, remoteAddr, remoteHost, true, false, TaskSchedulerService.OP_FROM_CLIENT,emailCoppiers);
//			} catch (Exception e) {
//				logger.error(e.toString());
//				
//				errorReport.setHttpCode(CodeTable.HTTP_INTERNAL_SERVER_ERROR);
//				errorReport.setErrorCode(CodeTable.UNDEFINED_ERROR);
//				errorReport.setErrorMessage("启用调度时出错");
//							
//				return Response.status(CodeTable.HTTP_INTERNAL_SERVER_ERROR)
//						.entity(gson.toJson(errorReport))					
//						.build();
//			}
//		} else if ("Y".equals(isForcedToEnable)) {
//			//trigger需要被强制启用
//			try {
//				taskSchedulerService.updateTaskStatus(triggerId, triggerName, userCode, remoteAddr, remoteHost, true, true, TaskSchedulerService.OP_FROM_CLIENT, emailCoppiers);
//			} catch (Exception e) {
//				logger.error(e.toString());
//				
//				errorReport.setHttpCode(CodeTable.HTTP_INTERNAL_SERVER_ERROR);
//				errorReport.setErrorCode(CodeTable.UNDEFINED_ERROR);
//				errorReport.setErrorMessage("启用调度时出错");
//							
//				return Response.status(CodeTable.HTTP_INTERNAL_SERVER_ERROR)
//						.entity(gson.toJson(errorReport))					
//						.build();
//			} 
//			
//		} else if (userCode.equals(activatedBy) && remoteAddr.equals(ip)) {
//			//trigger的状态为已经被本人在本机器上启用，通知用户启用成功，不做任何事情
//			return Response.status(200).build();
//			
//		} else {
//			errorReport.setHttpCode(CodeTable.HTTP_INTERNAL_SERVER_ERROR);
//			errorReport.setErrorCode(CodeTable.TRIGGER_ALREADY_ENABLED);
//			errorReport.setErrorMessage("此任务已经被"+activatedBy+"在"+ip +"的名为 "+host+"  的电脑上启用");
//						
//			return Response.status(CodeTable.HTTP_INTERNAL_SERVER_ERROR)
//					.entity(gson.toJson(errorReport))					
//					.build();
//		}
//		
//		return Response.status(200).build();
//	}
//	
//	@POST
//	@Path("/checkTaskExecutability")
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED+ ";charset=utf-8")
//	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
//	public Response checkTaskExecutability(
//			@FormParam("triggerId") String triggerId,			
//			@Context HttpServletRequest req) {
//		logger.info("checkTaskExecutability (triggerId=" + triggerId +") - start ");	
//		ErrorReport errorReport = new ErrorReport();
//		String remoteAddr = req.getRemoteAddr();			
//		String token = req.getHeader("Auth_Token");
//		
//		//检查用户是否登录
//		errorReport = RestUtil.getUserCodeByToken(token,remoteAddr);
//		if (errorReport.getHttpCode() != CodeTable.HTTP_SUCCESS_CODE) {
//			return Response.status(errorReport.getHttpCode())
//					.entity(gson.toJson(errorReport))
//					.build();
//		}
//		String userCode = errorReport.getErrorMessage();
//		
//		//检查用户是否有权限进行该操作
//		errorReport = RestUtil.checkTaskOperationPrivilege(triggerId, userCode);
//		if (errorReport.getHttpCode() != CodeTable.HTTP_SUCCESS_CODE) {
//			return Response.status(errorReport.getHttpCode())
//					.entity(gson.toJson(errorReport))
//					.build();
//		}	
//		
//		Map<String,String> result = new HashMap<String,String>();
//		try {
//			result = taskSchedulerService.getTriggerStatus(triggerId);
//		} catch (Exception e) {
//			logger.error(e.toString());
//			
//			errorReport.setHttpCode(CodeTable.HTTP_INTERNAL_SERVER_ERROR);
//			errorReport.setErrorCode(CodeTable.UNDEFINED_ERROR);
//			errorReport.setErrorMessage("查询调度状态时出错");
//						
//			return Response.status(CodeTable.HTTP_INTERNAL_SERVER_ERROR)
//					.entity(gson.toJson(errorReport))					
//					.build();
//		}
//		String isEnabled = result.get("is_enabled");
//		String activatedBy= result.get("activate_by");
//		String ip = result.get("triggerpro_login_ip");
//		
//		/*调度能够执行的条件		
//		 * - 调度的远程状态为启用 && 启用人为当前登录人 && 启用的机器IP为当前机器的IP
//		 */
//		if (("Y".equals(isEnabled)) && userCode.equals(activatedBy) && remoteAddr.equals(ip)) {
//			
//			//判断是否是以月的方式来执行
//			if("5".equals(result.get("DATE_TYPE").toString())){
//				String dateValue=result.get("DATE_VALUE").toString().substring(1);
//				int day=Integer.parseInt(dateValue);
//				String tempDate="";
//				try {
//					if("L".equals(dateValue)){
//						tempDate=tradeDateServiceForES.getMonthLastDay(new SimpleDateFormat("yyyyMM").format(new Date()));
//					}else{
//						tempDate=tradeDateServiceForES.getNextTradeDate(new SimpleDateFormat("yyyyMM").format(new Date()),day);
//					}
//				} catch (Exception e) {
//					logger.error(e.getMessage());
//					errorReport.setHttpCode(CodeTable.HTTP_INTERNAL_SERVER_ERROR);
//					errorReport.setErrorCode(CodeTable.UNDEFINED_ERROR);
//					errorReport.setErrorMessage(e.getMessage());
//					return Response.status(CodeTable.HTTP_INTERNAL_SERVER_ERROR)
//							.entity(gson.toJson(errorReport))					
//							.build();
//				}
//				
//				if(DateUtil.formatDate2().equals(tempDate)){
//					logger.info("checkTaskExecutability (triggerId=" + triggerId + " userCode=" + userCode + " IP="+ remoteAddr +") - 可以执行 ");	
//				}else{
//					errorReport.setHttpCode(CodeTable.HTTP_INTERNAL_SERVER_ERROR);
//					errorReport.setErrorCode(CodeTable.TRIGGER_NOT_EXECUTABLE);
//					errorReport.setErrorMessage("上传调度任务无法执行");
//					
//					logger.info("checkTaskExecutability (triggerId=" + triggerId + " userCode=" + userCode + " IP="+ remoteAddr +") - 不可以执行,原因：你设置的每月交易日形式的调度未满足条件 ");
//
//					return Response.status(errorReport.getHttpCode())
//							.entity(gson.toJson(errorReport))
//							.build();
//				}
//				
//			}else{
//				logger.info("checkTaskExecutability (triggerId=" + triggerId + " userCode=" + userCode + " IP="+ remoteAddr +") - 可以执行 ");	
//
//			}
//		} else {						
//			errorReport.setHttpCode(CodeTable.HTTP_INTERNAL_SERVER_ERROR);
//			errorReport.setErrorCode(CodeTable.TRIGGER_NOT_EXECUTABLE);
//			errorReport.setErrorMessage("上传调度任务无法执行");
//			
//			logger.info("checkTaskExecutability (triggerId=" + triggerId + " userCode=" + userCode + " IP="+ remoteAddr +") - 不可以执行 ");
//
//			return Response.status(errorReport.getHttpCode())
//					.entity(gson.toJson(errorReport))
//					.build();
//		}
//		
//		return Response.status(200).build();
//	}
//	
//	@POST
//	@Path("/updateTaskClientConfig")
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED+ ";charset=utf-8")
//	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
//	public Response updateTaskClientConfig (
//			@FormParam("triggerId") String triggerId,
//			@FormParam("triggerName") String triggerName,
//			@FormParam("folder") String folder,
//			@FormParam("keyword") String keyword,
//			@FormParam("fileNameIndicator") String fileNameIndicator,
//			@FormParam("emailCoppiers") String emailCoppiers,
//			@Context HttpServletRequest req){
//		logger.info("updateTaskFolderAndKeyword (triggerId=" + triggerId +" folder="+folder+" keyword="+keyword+") - start ");
//		
//		ErrorReport errorReport = new ErrorReport();
//		String remoteAddr = req.getRemoteAddr();	
//		String remoteHost = req.getRemoteHost();
//		String token = req.getHeader("Auth_Token");
//		errorReport = RestUtil.getUserCodeByToken(token,remoteAddr);
//		if (errorReport.getHttpCode() != CodeTable.HTTP_SUCCESS_CODE) {
//			return Response.status(errorReport.getHttpCode())
//					.entity(gson.toJson(errorReport))
//					.build();
//		}
//		String userCode = errorReport.getErrorMessage();
//		
//		//检查用户是否有权限进行该操作
//		errorReport = RestUtil.checkTaskOperationPrivilege(triggerId, userCode);
//		if (errorReport.getHttpCode() != CodeTable.HTTP_SUCCESS_CODE) {
//			return Response.status(errorReport.getHttpCode())
//					.entity(gson.toJson(errorReport))
//					.build();
//		}	
//
//		Map<String,String> result = new HashMap<String,String>();
//		try {
//			result = taskSchedulerService.getTriggerStatus(triggerId);
//		} catch (Exception e) {
//			logger.error(e.toString());
//			
//			errorReport.setHttpCode(CodeTable.HTTP_INTERNAL_SERVER_ERROR);
//			errorReport.setErrorCode(CodeTable.UNDEFINED_ERROR);
//			errorReport.setErrorMessage("查询调度状态时出错");
//						
//			return Response.status(CodeTable.HTTP_INTERNAL_SERVER_ERROR)
//					.entity(gson.toJson(errorReport))					
//					.build();
//		}
//		String isEnabled = result.get("is_enabled");
//		String activatedBy= result.get("activate_by");
//		String ip = result.get("triggerpro_login_ip");
//		
//		/*调度能够更新客户端本地路径和关键字的条件		
//		 * - 调度的远程状态为非启用 || 调度的远程状态为启用&& 启用人为当前登录人 && 启用的机器IP为当前机器的IP &&
//		 */
//		if (!"Y".equals(isEnabled) || ("Y".equals(isEnabled) && userCode.equals(activatedBy) && remoteAddr.equals(ip))) {
//			try {
//				taskSchedulerService.updateTaskClientConfig(triggerId, triggerName, folder, keyword,fileNameIndicator, userCode, remoteAddr, remoteHost,"客户端", emailCoppiers);
//			} catch (Exception e) {
//				logger.error(e.toString());
//				
//				errorReport.setHttpCode(CodeTable.HTTP_INTERNAL_SERVER_ERROR);
//				errorReport.setErrorCode(CodeTable.UNDEFINED_ERROR);
//				errorReport.setErrorMessage("更新调度任务的本地路径和关键字时出错");
//							
//				return Response.status(CodeTable.HTTP_INTERNAL_SERVER_ERROR)
//						.entity(gson.toJson(errorReport))					
//						.build();
//			}	
//			
//		} else {						
//			errorReport.setHttpCode(CodeTable.HTTP_INTERNAL_SERVER_ERROR);
//			errorReport.setErrorCode(CodeTable.TRIGGER_NOT_CONFIGURABLE);
//			errorReport.setErrorMessage("调度任务无法在此客户端上配置");
//			
//			logger.info("checkTaskExecutability (triggerId=" + triggerId + " userCode=" + userCode + " IP="+ remoteAddr +") - 不可以执行 ");
//
//			return Response.status(errorReport.getHttpCode())
//					.entity(gson.toJson(errorReport))
//					.build();
//		}
//		
//		logger.info("updateTaskFolderAndKeyword (triggerId=" + triggerId +" folder="+folder+" keyword="+keyword+") - end ");
//		return Response.status(200).build();
//	}
//	
//
//	/**
//		 * <p>Title: </p>
//		 * <p>Description:删除错误退出Session</p>
//	     * @param flag
//	     * @param req
//	     * @return
//	*/
//	@POST
//	@Path("/deleteTriggerErrorExit")
//	@Consumes(MediaType.APPLICATION_FORM_URLENCODED+ ";charset=utf-8")
//	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
//	public Response deleteTriggerErrorExit (
//			@FormParam("flag") String flag,
//			@Context HttpServletRequest req){
//		logger.info("deleteTriggerErrorExit () - start ");
//		
//		ErrorReport errorReport = new ErrorReport();
//		String remoteAddr = req.getRemoteAddr();	
//		String remoteHost = req.getRemoteHost();
//		String token = req.getHeader("Auth_Token");
//		errorReport = RestUtil.getUserCodeByToken(token,remoteAddr);
//		if (errorReport.getHttpCode() != CodeTable.HTTP_SUCCESS_CODE) {
//			return Response.status(errorReport.getHttpCode())
//					.entity(gson.toJson(errorReport))
//					.build();
//		}
//		String userCode = errorReport.getErrorMessage();
//		
//		List<Map> errorList=new ArrayList<Map>();
//		try {
//			errorList=taskSchedulerService.findTriggerErrorExitByConditon(userCode, remoteAddr,"");
//		} catch (Exception e) {
//			logger.error (e.toString());
//			errorReport.setHttpCode(CodeTable.HTTP_INTERNAL_SERVER_ERROR);
//			errorReport.setErrorCode(CodeTable.UNDEFINED_ERROR);
//			errorReport.setErrorMessage(e.getMessage());
//			return Response.status(CodeTable.HTTP_INTERNAL_SERVER_ERROR)
//					.entity(gson.toJson(errorReport))
//					.build();
//		}
//		String triggerId="";
//		String triggerName="";
//		String emailCoppiers="";
//		String path="";
//		String keyword="";
//		String errorIp="";
//		String fileNameIndicator="";
//		try{
//			for(Map error:errorList){
//				triggerId=error.get(ScTriggerConsts.TRIGGER_ID).toString();
//				triggerName=error.get(ScTriggerConsts.TRIGGER_NAME).toString();
//				path=error.get(TaskConsts.ERROR_PATH).toString();
//				keyword=error.get(TaskConsts.ERROR_KEYWORD).toString();
//
//				fileNameIndicator=error.get(TaskConsts.ERROR_FILENAME_INDICATOR)==null?"": error.get(TaskConsts.ERROR_FILENAME_INDICATOR).toString();
//				emailCoppiers=error.get(ScTriggerProConsts.TRIGGERPRO_PERSON_ID).toString();
//				errorIp=error.get(TaskConsts.ERROR_IP).toString();
//				if("Y".equals(flag)){
//					taskSchedulerService.updateTaskClientConfig(triggerId, triggerName, path, keyword,fileNameIndicator, userCode, remoteAddr, remoteHost, "客户端", emailCoppiers);
//					taskSchedulerService.updateTaskStatus(triggerId, triggerName, userCode, remoteAddr, remoteHost, true, true, "客户端", emailCoppiers);
//				}
//					taskSchedulerService.deleteTriggerErrorExit(userCode, errorIp,"");
//			}
//			
//		}catch (Exception e) {
//			logger.error (e.toString());
//			errorReport.setHttpCode(CodeTable.HTTP_INTERNAL_SERVER_ERROR);
//			errorReport.setErrorCode(CodeTable.UNDEFINED_ERROR);
//			errorReport.setErrorMessage(e.getMessage());
//			return Response.status(CodeTable.HTTP_INTERNAL_SERVER_ERROR)
//					.entity(gson.toJson(errorReport))
//					.build();
//		}
//		logger.info("deleteTriggerErrorExit () - start ");
//		return Response.status(200).build();
//	}
//	
//	@GET
//	@Path("/getTriggerErrorExitByIPAndUserCode")
//	@Produces(MediaType.APPLICATION_JSON + ";charset=utf-8")
//	public Response getTriggerErrorExitByIPAndUserCode  (
//			@Context HttpServletRequest req) {
//		logger.info("getTriggerErrorExitByIPAndUserCode () - start ");	
//		ErrorReport errorReport = new ErrorReport();
//		String remoteAddr = req.getRemoteAddr();	
//		String remoteHost = req.getRemoteHost();
//		String token = req.getHeader("Auth_Token");
//		errorReport = RestUtil.getUserCodeByToken(token,remoteAddr);
//		if (errorReport.getHttpCode() != CodeTable.HTTP_SUCCESS_CODE) {
//			return Response.status(errorReport.getHttpCode())
//					.entity(gson.toJson(errorReport))
//					.build();
//		}
//		String userCode = errorReport.getErrorMessage();
//		
//		
//		List<Map> errorList=new ArrayList<Map>();
//		try {
//			errorList=taskSchedulerService.findTriggerErrorExitByConditon(userCode, remoteAddr,"");
//		} catch (Exception e) {
//			logger.error (e.toString());
//			errorReport.setHttpCode(CodeTable.HTTP_INTERNAL_SERVER_ERROR);
//			errorReport.setErrorCode(CodeTable.UNDEFINED_ERROR);
//			errorReport.setErrorMessage(e.getMessage());
//			return Response.status(CodeTable.HTTP_INTERNAL_SERVER_ERROR)
//					.entity(gson.toJson(errorReport))
//					.build();
//		}
//		logger.info("getTriggerErrorExitByIPAndUserCode () - end ");	
//		return Response.status(200).entity(gson.toJson(errorList, ArrayList.class)).build();
//		
//	}
//	
	
}
