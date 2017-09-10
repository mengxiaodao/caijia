package com.caijia.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.apache.log4j.Logger;

import com.caijia.exception.DBException;
import com.caijia.modle.CaiModle;
import com.caijia.modle.CityModle;
import com.caijia.modle.CommunicateModle;
import com.caijia.modle.OrderDetail;
import com.caijia.modle.OrdersModle;
import com.caijia.modle.UserModle;
import com.caijia.rest.ClientTask;
import com.caijia.util.Constants;
import com.caijia.util.DateUtil;


public class CaiDaoImpl implements CaiDao {
	
	private static final Logger logger = Logger.getLogger(ClientTask.class);
	
	@Override
	public List<CaiModle> getAllCai(Connection conn) throws DBException{
		logger.info("getAllCai is start");
		Statement stmt = null;
		ResultSet rs=null;
		List<CaiModle> result=new ArrayList<CaiModle>();
		StringBuffer sql =new StringBuffer();
		sql.append("SELECT * FROM ")
		   .append(Constants.DBSCHEMA)
		   .append(".")
		   .append(Constants.CAI_TABLE);
		try {
			stmt = conn.createStatement();
			rs=stmt.executeQuery(sql.toString());
			convertToCaiList(rs, result);
		} catch (SQLException e) {
			logger.info("getAllCai is error");
			throw new DBException("Failed to execute a sql,sql=" + sql,e);
		}finally{
			try {
				if(stmt != null){
					stmt.close();			
				}
				if(rs != null){
					rs.close();			
				}
				if(conn != null){
					conn.close();			
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		logger.info("getAllCai is end");
		return result;
	}

	
	@Override
	public void insertOrder(OrdersModle om, Connection conn) throws DBException{
		
		logger.info("insertOrder is start");
		
		StringBuffer sql = new StringBuffer();
		
	    sql = new StringBuffer();
		sql.append("insert into ")
		   .append(Constants.DBSCHEMA)
		   .append(".")
		   .append(Constants.ORDERS_TABLE);
		sql.append(" values(?,?,?,?,?,?) ");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		String ordersUuid=UUID.randomUUID().toString();
		try {
			conn.setAutoCommit(false);
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString( 1, ordersUuid);
			pstmt.setString( 2, om.getOrderTotal());
			pstmt.setString( 3, om.getAddress());
			pstmt.setString( 4, om.getOwner());
			pstmt.setString( 5, om.getPhone());
			pstmt.setString( 6, DateUtil.formatDate5());
			pstmt.execute();
		} catch (SQLException e) {
			logger.info("insertOrder is error");
			throw new DBException("Failed to insertOrder entry,entry=" + om,e);
		}finally{
			try {
				if(rs != null){
					rs.close();
				}
				if(pstmt != null){
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		logger.info("insertOrder is start->order is done ");
		///OrderDetail is begin
		sql=new StringBuffer();
		sql.append("insert into ")
		   .append(Constants.DBSCHEMA)
		   .append(".")
		   .append(Constants.ORDERDETAIL_TABLE)
	       .append(" values(?,?,?,?,?) ");
		 pstmt = null;
		 rs = null;
		int i = 0;
		try {
			pstmt = conn.prepareStatement(sql.toString());
			for (Iterator<OrderDetail> iterator = om.getOrderDetail().iterator(); iterator.hasNext();) {
				OrderDetail entry = (OrderDetail) iterator.next();
				entry.setOrderUuid(ordersUuid);
				this.addOrderDetail2Batch(pstmt, entry);
				if ((i != 0 && (i%100 == 0)) || (i == (om.getOrderDetail().size()-1)) ){
					pstmt.executeBatch();
					conn.commit();
				}
				i++;
			}
		} catch (SQLException e) {
			logger.info("insertOrder is error");
			throw new DBException("Failed to batch save orderdetail",e);
		}finally{
			try {
				if(rs != null){
					rs.close();
				}
				if(pstmt != null){
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		logger.info("insertOrder is start->orderdetail is done ");
		logger.info("insertOrder is end");
	}

	/*@Override
	public void insertUser(UserModle u, Connection conn)  throws DBException{
		logger.info("insertUser is start");
		StringBuffer sql = new StringBuffer();
		sql.append("insert into ")
		   .append(Constants.DBSCHEMA)
		   .append(".")
		   .append(Constants.USER_TABLE);
		sql.append(" values(?,?,?,?,?) ");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString( 1, UUID.randomUUID().toString());
			pstmt.setString( 2, u.getUserName());
			pstmt.setString( 3, u.getPassword());
			pstmt.setString( 4, u.getEmail());
			pstmt.setString( 5, u.getPhone());
			pstmt.execute();
		} catch (SQLException e) {
			logger.info("insertUser is error");
			throw new DBException("Failed to insertUser entry,entry=" + u,e);
		}finally{
			try {
				if(rs != null){
					rs.close();
				}
				if(pstmt != null){
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		logger.info("insertUser is end");
	}
*/
	/*@Override
	public void updateUser(UserModle u, Connection conn)  throws DBException{
		logger.info("updateUser is start");
		
		StringBuffer sql = new StringBuffer();
		
	    sql = new StringBuffer();
		sql.append("UPDATE ")
		   .append(Constants.DBSCHEMA)
		   .append(".")
		   .append(Constants.USER_TABLE)
		   .append(" SET ")
		   .append(" userName = ? ,passWord = ? , email= ? , phone= ? ")
		   .append(" WHERE userUuid = ?");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql.toString());
			pstmt.setString( 1, u.getUserName());
			pstmt.setString( 2, u.getPassword());
			pstmt.setString( 3, u.getEmail());
			pstmt.setString( 4, u.getPhone());
			pstmt.setString( 5, u.getUserUuid());
			pstmt.execute();
		} catch (SQLException e) {
			logger.info("updateUser is error");
			throw new DBException("Failed to updateUser entry,entry=" + u,e);
		}finally{
			try {
				if(rs != null){
					rs.close();
				}
				if(pstmt != null){
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		logger.info("updateUser is end ");
		
	}*/

	@Override
	public List<CaiModle> getCaiById(String uuid, Connection conn)  throws DBException{
		logger.info("getCaiById is start");
		List<CaiModle> result=new ArrayList<CaiModle>();
		PreparedStatement stmt = null;
		ResultSet rs=null;
		CaiModle cm = null;
		StringBuffer sql =new StringBuffer();
		sql.append("SELECT * FROM ")
		   .append(Constants.DBSCHEMA)
		   .append(".")
		   .append(Constants.CAI_TABLE)
		   .append(" WHERE role = ")
		   .append(uuid);

		try {
			stmt = conn.prepareStatement(sql.toString());
//			stmt.setString( 1,uuid);
			rs=stmt.executeQuery(sql.toString());
			
			while(rs.next()){
				cm=new CaiModle();
				String re = "n" ;				
				if((rs.getString(6))!= null){
					re = rs.getString(6);
				}else{
					re = "";
				}
			cm.setNewsid(rs.getString(1));
			cm.setNewsrole(rs.getString(2));
			cm.setNewstitle(rs.getString(3));
			cm.setNewscontent(rs.getString(4));
			cm.setNewsdate(rs.getString(5));
			cm.setNewsphoto(re);
			result.add(cm);
			}
		} catch (SQLException e) {
			logger.info("getCaiById is error");
			throw new DBException("Failed to execute a sql,sql=" + sql,e);
		}finally{
			try {
				if(rs != null){
					rs.close();
				}
				if(stmt != null){
					stmt.close();
				}
				if(conn != null){
					conn.close();			
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		logger.info("getCaiById is end");
		 for(int i = 0;i < result.size(); i ++){
			 
			 System.out.println(result.get(i));
	        }
		/*System.out.println(result.length);*/
		
		return result;
	}
	
	@Override
	public List<CaiModle> getOneCai(String uuid, Connection conn)  throws DBException{
		logger.info("getOneCai is start");
		List<CaiModle> result=new ArrayList<CaiModle>();
		PreparedStatement stmt = null;
		ResultSet rs=null;
		CaiModle cm = null;
		StringBuffer sql =new StringBuffer();
		sql.append("SELECT * FROM ")
		   .append(Constants.DBSCHEMA)
		   .append(".")
		   .append(Constants.CAI_TABLE)
		   .append(" WHERE news_id = ")
		   .append(uuid);

		try {
			stmt = conn.prepareStatement(sql.toString());
//			stmt.setString( 1,uuid);
			rs=stmt.executeQuery(sql.toString());
			
			while(rs.next()){
				cm=new CaiModle();
				String re = "n" ;				
				if((rs.getString(6))!= null){
					re = rs.getString(6);
				}else{
					re = "";
				}
			cm.setNewsid(rs.getString(1));
			cm.setNewsrole(rs.getString(2));
			cm.setNewstitle(rs.getString(3));
			cm.setNewscontent(rs.getString(4));
			cm.setNewsdate(rs.getString(5));
			cm.setNewsphoto(re);
			result.add(cm);
			}
		} catch (SQLException e) {
			logger.info("getCaiById is error");
			throw new DBException("Failed to execute a sql,sql=" + sql,e);
		}finally{
			try {
				if(rs != null){
					rs.close();
				}
				if(stmt != null){
					stmt.close();
				}
				if(conn != null){
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		logger.info("getCaiById is end");
		 for(int i = 0;i < result.size(); i ++){
			 
			 System.out.println(result.get(i));
	        }
		/*System.out.println(result.length);*/
		
		return result;
	}
	@Override
	public List<UserModle> getUserById(String uuid, Connection conn)  throws DBException{
		logger.info("getUserById is start");
		List<UserModle> result=new ArrayList<UserModle>();
		PreparedStatement stmt = null;
		ResultSet rs=null;
		UserModle md= null;
		StringBuffer sql =new StringBuffer();
		sql.append("SELECT * FROM ")
		   .append(Constants.DBSCHEMA)
		   .append(".")
		   .append(Constants.USER_TABLE)
		   .append(" WHERE role=  ")
		    .append(uuid);
		try {
			stmt = conn.prepareStatement(sql.toString());
		/*	stmt.setString( 1,uuid);*/
			rs=stmt.executeQuery();
			while(rs.next()){
				md=new UserModle();
				String re = "n" ;				
				if((rs.getString(5))!= null){
					re = rs.getString(5);
				}else{
					re = "";
				}
			md.setWenhuaId(rs.getString(1));
			md.setWenhuaRole(rs.getString(2));
			md.setWenhuaTitle(rs.getString(3));
			md.setWenhuaContent(rs.getString(4));
			md.setWenhuaPhoto(re);
			result.add(md);
			}
		} catch (SQLException e) {
			logger.info("getUserById is error");
			throw new DBException("Failed to execute a sql,sql=" + sql,e);
		}finally{
			try {
				if(rs != null){
					rs.close();
				}
				if(stmt != null){
					stmt.close();
				}
				if(conn != null){
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		logger.info("getUserById is end");
		return result;
	}
	
	@Override
	public List<UserModle> getOneWenhua(String uuid, Connection conn)  throws DBException{
		logger.info("getOneWenhua is start");
		List<UserModle> result=new ArrayList<UserModle>();
		PreparedStatement stmt = null;
		ResultSet rs=null;
		UserModle md= null;
		StringBuffer sql =new StringBuffer();
		sql.append("SELECT * FROM ")
		   .append(Constants.DBSCHEMA)
		   .append(".")
		   .append(Constants.USER_TABLE)
		   .append(" WHERE id =  ")
		    .append(uuid);
		try {
			stmt = conn.prepareStatement(sql.toString());
		/*	stmt.setString( 1,uuid);*/
			rs=stmt.executeQuery();
			while(rs.next()){
				md=new UserModle();
				String re = "n" ;				
				if((rs.getString(5))!= null){
					re = rs.getString(5);
				}else{
					re = "";
				}
			md.setWenhuaId(rs.getString(1));
			md.setWenhuaRole(rs.getString(2));
			md.setWenhuaTitle(rs.getString(3));
			md.setWenhuaContent(rs.getString(4));
			md.setWenhuaPhoto(re);
			result.add(md);
			}
		} catch (SQLException e) {
			logger.info("getUserById is error");
			throw new DBException("Failed to execute a sql,sql=" + sql,e);
		}finally{
			try {
				if(rs != null){
					stmt.close();
				}
				if(stmt != null){
					stmt.close();
				}
				if(conn != null){
					stmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		logger.info("getOneWenhua is end");
		return result;
	}
	
	//插入community 互动交流 sql 语句
	@Override
	public boolean insertCommunicate(CommunicateModle c, Connection conn)  throws DBException{
		System.out.println("--dao-name-->"+c.getComname());
		logger.info("insertCommunicate is start");
		
		StringBuffer sql = new StringBuffer();
		sql.append("insert into ")
		//sql.append("insert into (com_role,com_name,com_phone,com_public,com_title,com_content,com_reply,write_time,reply_time)")
		   .append(Constants.DBSCHEMA)
		   .append(".")
		   .append(Constants.COMMUNICATE_TABLE);
		sql.append("(com_role,com_name,com_phone,com_public,com_title,com_content) values(?,?,?,?,?,?) ");
		PreparedStatement pstmt = null;
		ResultSet rs = null;
		try {
			pstmt = conn.prepareStatement(sql.toString());
			//pstmt.setString( 1, UUID.randomUUID().toString());
			pstmt.setInt( 1, c.getCom_role());
			pstmt.setString( 2, c.getComname());
			pstmt.setString( 3, c.getComphone());
			pstmt.setString(4, c.getCompublic());
			pstmt.setString( 5, c.getComtitle());
			pstmt.setString( 6, c.getComcontent());
			//pstmt.setString( 7, c.getComreply());
			//pstmt.setString( 8, c.getWritetime());//c.getReplytime()
			//pstmt.setString( 9,c.getReplytime());
			int row = pstmt.executeUpdate();
			if (row>0){
			 System.out.println("插入成功");
		
			}
		} catch (SQLException e) {
			logger.info("insertCommunicate is error");
			throw new DBException("Failed to insertCommunicate entry,entry=" + c,e);
		}finally{
			try {
				if(rs != null){
					rs.close();
				}
				if(pstmt != null){
					pstmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		logger.info("insertCommunicate is end");
		return true;
	}
	
	// 获取communicate信息 byPublic
	@Override
	public List<CommunicateModle> getComByPu(String uuid, Connection conn)  throws DBException{
		logger.info("getComByPu is start");
		List<CommunicateModle> result=new ArrayList<CommunicateModle>();
		PreparedStatement stmt = null;
		ResultSet rs=null;
		CommunicateModle cm = null;
		StringBuffer sql =new StringBuffer();
		/*sql.append("SELECT * FROM ")
		   .append(Constants.DBSCHEMA)
		   .append(".")
		   .append(Constants.COMMUNICATE_TABLE)
		   .append(" WHERE com_public = ")
		   .append(uuid);*/

		sql.append("SELECT * FROM ")
		   .append(Constants.DBSCHEMA)
		   .append(".")
		   .append(Constants.COMMUNICATE_TABLE);
		try {
			stmt = conn.prepareStatement(sql.toString());
//			stmt.setString( 1,uuid);
			rs=stmt.executeQuery(sql.toString());
			
			while(rs.next()){
				cm=new CommunicateModle();
				String re = "n" ;				
				if((rs.getString(8))!= null){
					re = rs.getString(8);
				}else{
					re = "";
				}
			cm.setComid(rs.getInt(1));
			cm.setCom_role(rs.getInt(2));
			cm.setComname(rs.getString(3));
			cm.setComphone(rs.getString(4));
			cm.setCompublic(rs.getString(5));
			cm.setComtitle(rs.getString(6));
			
			cm.setComcontent(rs.getString(7));
			cm.setComreply(re);

			cm.setWritetime(rs.getString(9));
			cm.setReplytime(rs.getString(10));
			result.add(cm);
			}
		} catch (SQLException e) {
			logger.info("getCaiById is error");
			throw new DBException("Failed to execute a sql,sql=" + sql,e);
		}finally{
			try {
				if(rs != null){
					rs.close();
				}
				if(stmt != null){
					stmt.close();
				}
				if(conn != null){
					conn.close();			
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		logger.info("getComByPu is end");
		 for(int i = 0;i < result.size(); i ++){
			 
			 System.out.println(result.get(i));
	        }
		/*System.out.println(result.length);*/
		
		return result;
	}
	
	
	// 获取communicate信息 byid
	@Override
	public List<CommunicateModle> getComById(String uuid, Connection conn)  throws DBException{
		logger.info("getComById is start");
		List<CommunicateModle> result=new ArrayList<CommunicateModle>();
		PreparedStatement stmt = null;
		ResultSet rs=null;
		CommunicateModle cm = null;
		StringBuffer sql =new StringBuffer();
		sql.append("SELECT * FROM ")
		   .append(Constants.DBSCHEMA)
		   .append(".")
		   .append(Constants.COMMUNICATE_TABLE)
		   .append(" WHERE com_id = ")
		   .append(uuid);

		try {
			stmt = conn.prepareStatement(sql.toString());
//			stmt.setString( 1,uuid);
			rs=stmt.executeQuery(sql.toString());
			
			while(rs.next()){
				cm=new CommunicateModle();
				String re = "n" ;				
				if((rs.getString(8))!= null){
					re = rs.getString(8);
				}else{
					re = "";
				}
			cm.setComid(rs.getInt(1));
			cm.setCom_role(rs.getInt(2));
			cm.setComname(rs.getString(3));
			cm.setComphone(rs.getString(4));
			cm.setCompublic(rs.getString(5));
			cm.setComtitle(rs.getString(6));
			cm.setComcontent(rs.getString(7));
			cm.setComreply(re);
			result.add(cm);
			}
		} catch (SQLException e) {
			logger.info("getComById is error");
			throw new DBException("Failed to execute a sql,sql=" + sql,e);
		}finally{
			try {
				if(rs != null){
					rs.close();
				}
				if(stmt != null){
					stmt.close();
				}
				if(conn != null){
					conn.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		logger.info("getComById is end");
		 for(int i = 0;i < result.size(); i ++){
			 
			 System.out.println(result.get(i));
	        }
		/*System.out.println(result.length);*/
		
		return result;
	}
	
	@Override
	public List<CityModle> getCityByRole(String uuid, Connection conn)  throws DBException{
		logger.info("getCityByRole is start");
		List<CityModle> result=new ArrayList<CityModle>();
		PreparedStatement stmt = null;
		ResultSet rs=null;
		CityModle cm = null;
		StringBuffer sql =new StringBuffer();
		sql.append("SELECT * FROM ")
		   .append(Constants.DBSCHEMA)
		   .append(".")
		   .append(Constants.CITY_TABLE)
		   .append(" WHERE role = ")
		   .append(uuid);

		try {
			stmt = conn.prepareStatement(sql.toString());
//			stmt.setString( 1,uuid);
			rs=stmt.executeQuery(sql.toString());
			
			while(rs.next()){
				cm=new CityModle();
				String re = "n" ;				
				if((rs.getString(6))!= null){
					re = rs.getString(6);
				}else{
					re = "";
				}
			cm.setCityid(rs.getString(1));
			cm.setCityrole(rs.getString(2));
			cm.setCitytitle(rs.getString(3));
			cm.setCitycontent(rs.getString(4));
			cm.setCitydate(rs.getString(5));
			cm.setCityphoto(re);
			result.add(cm);
			}
		} catch (SQLException e) {
			logger.info("getCityByRole is error");
			throw new DBException("Failed to execute a sql,sql=" + sql,e);
		}finally{
			try {
				if(rs != null){
					rs.close();
				}
				if(stmt != null){
					stmt.close();
				}
				if(conn != null){
					conn.close();			
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		logger.info("getCityByRole is end");
		 for(int i = 0;i < result.size(); i ++){
			 
			 System.out.println(result.get(i));
	        }
		/*System.out.println(result.length);*/
		
		return result;
	}
	
	@Override
	public List<CityModle> getCityById(String uuid, Connection conn)  throws DBException{
		logger.info("getCityById is start");
		List<CityModle> result=new ArrayList<CityModle>();
		PreparedStatement stmt = null;
		ResultSet rs=null;
		CityModle cm = null;
		StringBuffer sql =new StringBuffer();
		sql.append("SELECT * FROM ")
		   .append(Constants.DBSCHEMA)
		   .append(".")
		   .append(Constants.CITY_TABLE)
		   .append(" WHERE cb_id = ")
		   .append(uuid);

		try {
			stmt = conn.prepareStatement(sql.toString());
//			stmt.setString( 1,uuid);
			rs=stmt.executeQuery(sql.toString());
			
			while(rs.next()){
				cm=new CityModle();
				String re = "n" ;				
				if((rs.getString(6))!= null){
					re = rs.getString(6);
				}else{
					re = "";
				}
			cm.setCityid(rs.getString(1));
			cm.setCityrole(rs.getString(2));
			cm.setCitytitle(rs.getString(3));
			cm.setCitycontent(rs.getString(4));
			cm.setCitydate(rs.getString(5));
			cm.setCityphoto(re);
			result.add(cm);
			}
		} catch (SQLException e) {
			logger.info("getCityById is error");
			throw new DBException("Failed to execute a sql,sql=" + sql,e);
		}finally{
			try {
				if(rs != null){
					rs.close();
				}
				if(stmt != null){
					stmt.close();
				}
				if(conn != null){
					conn.close();			
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		logger.info("getCityById is end");
		 for(int i = 0;i < result.size(); i ++){
			 
			 System.out.println(result.get(i));
	        }
		/*System.out.println(result.length);*/
		
		return result;
	}
	
	@Override
	public OrdersModle getOrderById(String uuid, Connection conn)  throws DBException{
		logger.info("getOrderById is start");
		PreparedStatement stmt = null;
		ResultSet rs=null;
		OrdersModle modle=new OrdersModle();
		StringBuffer sql =new StringBuffer();
		sql.append("SELECT * FROM ")
		   .append(Constants.DBSCHEMA)
		   .append(".")
		   .append(Constants.ORDERS_TABLE)
		   .append(" WHERE orderUuid= ? ");
		try {
			stmt = conn.prepareStatement(sql.toString());
			stmt.setString( 1,uuid);
			rs=stmt.executeQuery();
			rs.next();
			modle.setOrderUuid(rs.getString(1));
			modle.setOrderTotal(rs.getString(2));
			modle.setAddress(rs.getString(3));
			modle.setOwner(rs.getString(4));
			modle.setPhone(rs.getString(5));
			modle.setCreatTime(rs.getString(6));
		} catch (SQLException e) {
			logger.info("getOrderById is error");
			throw new DBException("Failed to execute a sql,sql=" + sql,e);
		}finally{
			try {
				if(stmt != null){
					stmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		logger.info("getOrderById is end");
		return modle;
	}
/*	public List<OrderDetail> getOrderDetailById(String uuid,Connection conn) throws DBException{
		logger.info("getOrderDetailById is start");
		PreparedStatement stmt = null;
		ResultSet rs=null;
		List<OrderDetail> list=new ArrayList<OrderDetail>();
		StringBuffer sql =new StringBuffer();
		sql.append("SELECT * FROM ")
		   .append(Constants.DBSCHEMA)
		   .append(".")
		   .append(Constants.ORDERDETAIL_TABLE)
		   .append(" WHERE orderUuid= ? ");
		try {
			stmt = conn.prepareStatement(sql.toString());
			stmt.setString( 1,uuid);
			rs=stmt.executeQuery();
			convertToOrderDetailList(rs,list);
			
		} catch (SQLException e) {
			logger.info("getOrderById is error");
			throw new DBException("Failed to execute a sql,sql=" + sql,e);
		}finally{
			try {
				if(stmt != null){
					stmt.close();
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
		logger.info("getOrderById is end");
		return list;
		
	}
	private void convertToOrderDetailList(ResultSet rs,List<OrderDetail> result)throws DBException{
		OrderDetail modle=null;
		try {
			while(rs.next()){
				modle=new OrderDetail();
				modle.setOrderDetailUuid(rs.getString(1));
				modle.setOrderUuid(rs.getString(2));
				modle.setCaiUuid(rs.getString(3));
				modle.setAmount(rs.getString(4));
				modle.setPrice(rs.getString(5));
				result.add(modle);
			}
		} catch (SQLException e) {
			throw new DBException("Failed to convertToOrderDetailList",e);
		}
	}*/
	private void convertToCaiList (ResultSet rs,List<CaiModle> result)throws DBException{
		CaiModle cai=null;
		try {
			while(rs.next()){
				cai=new CaiModle();
				cai.setNewsid(rs.getString(1));
				cai.setNewsrole(rs.getString(2));
				cai.setNewstitle(rs.getString(3));
				cai.setNewscontent(rs.getString(4));
				cai.setNewsdate(rs.getString(5));
				cai.setNewsphoto(rs.getString(6));
				
				result.add(cai);
			}
		} catch (SQLException e) {
			throw new DBException("Failed to convertToCaiList",e);
		}
	}
	private void addOrderDetail2Batch(PreparedStatement pstmt,OrderDetail entry) throws SQLException{
		pstmt.setString( 1, UUID.randomUUID().toString());
		pstmt.setString( 2, entry.getOrderUuid());
		pstmt.setString( 3 , entry.getCaiUuid());
		pstmt.setString( 4, entry.getAmount());
		pstmt.setString( 5, entry.getPrice());
		pstmt.addBatch();
	}
//	public boolean execute(Connection conn,String exsql) throws DBException{
//		Statement stmt = null;
//		try {
//			stmt = conn.createStatement();
//			return stmt.execute(exsql);
//		} catch (SQLException e) {
//			throw new DBException("Failed to execute a sql,sql=" + exsql,e);
//		}finally{
//			try {
//				if(stmt != null){
//					stmt.close();
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//	public void saveBlogEntry(Connection conn,BlogEntry entry) throws DBException{
//		StringBuffer sql = new StringBuffer();
//		sql.append("insert into ").append(PropertiesUtils.getString("db.schema","QRATE")).append(".blog_entry(title,content,tag,blog_url,blog_date,author_name,author_mail,visit,reply,likes,crawl_time) ");
//		sql.append(" values(?,?,?,?,?,?,?,?,?,?,?) ");
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		try {
//			pstmt = conn.prepareStatement(sql.toString());
//			pstmt.setString( 1, entry.getTitle());
//			pstmt.setString( 2, entry.getContent());
//			pstmt.setString( 3, entry.getTag());
//			pstmt.setString( 4, entry.getBlogUrl());
//			pstmt.setTimestamp( 5, new Timestamp(entry.getBlogDate().getTime()));
//			pstmt.setString( 6, entry.getAuthorName());
//			pstmt.setString( 7, entry.getAuthorMail());
//			pstmt.setInt(8, entry.getVisit());
//			pstmt.setInt(9, entry.getReply());
//			pstmt.setInt(10, entry.getLikes());
//			pstmt.setTimestamp(11, new Timestamp(entry.getCrawlTime().getTime()));
//			pstmt.execute();
//		} catch (SQLException e) {
//			throw new DBException("Failed to insert blog entry,entry=" + entry,e);
//		}finally{
//			try {
//				if(rs != null){
//					rs.close();
//				}
//				if(pstmt != null){
//					pstmt.close();
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//	
//	public void batchSaveBlogEntry(Connection conn, List<BlogEntry> list) throws DBException{
//		StringBuffer sql = new StringBuffer();
//		sql.append("insert into ").append(PropertiesUtils.getString("db.schema","QRATE")).append(".blog_entry(title,content,tag,blog_url,blog_date,author_name,author_mail,visit,reply,likes,crawl_time) ");
//		sql.append(" values(?,?,?,?,?,?,?,?,?,?,?) ");
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		int i = 0;
//		try {
//			conn.setAutoCommit(false);
//			pstmt = conn.prepareStatement(sql.toString());
//			for (Iterator<BlogEntry> iterator = list.iterator(); iterator.hasNext();) {
//				BlogEntry entry = (BlogEntry) iterator.next();
//				this.addBlogEntry2Batch(pstmt, entry);
//				if ((i != 0 && (i%100 == 0)) || (i == (list.size()-1)) ){
//					pstmt.executeBatch();
//					conn.commit();
//				}
//				i++;
//			}
//		} catch (SQLException e) {
//			throw new DBException("Failed to batch save news",e);
//		}finally{
//			try {
//				if(rs != null){
//					rs.close();
//				}
//				if(pstmt != null){
//					pstmt.close();
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//	
//	public void batchSaveNews(Connection conn, List<News> newslist) throws DBException{
//		StringBuffer sql = new StringBuffer();
//		sql.append("insert into ").append(PropertiesUtils.getString("db.schema","QRATE")).append(".news(title,content,DESC_TEXT,NEWS_URL,IMAGE_URL,CRAWL_TIME,CREATOR_NAME,OWNER_EMAIL,OWNER_NAME,PERIOD_STARTDATE,PERIOD_ENDDATE,ADDITIONAL_TEXT,NEWS_ID_INDICATOR,LAST_UPDATE_TIME) ");
//		sql.append(" values(?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");
//		ResultSet rs = null;
//		PreparedStatement pstmt = null;
//		int i = 0;
//		try {
//			conn.setAutoCommit(false);
//			pstmt = conn.prepareStatement(sql.toString());
//			for (Iterator<News> iterator = newslist.iterator(); iterator.hasNext();) {
//				News news = (News) iterator.next();
//				this.addNews2Batch(pstmt, news);
//				if ((i != 0 && (i%100 == 0)) || (i == (newslist.size()-1)) ){
//					pstmt.executeBatch();
//					conn.commit();
//				}
//				i++;
//			}
//		} catch (SQLException e) {
//			throw new DBException("Failed to batch save news",e);
//		}finally{
//			try {
//				if(rs != null){
//					rs.close();
//				}
//				if(pstmt != null){
//					pstmt.close();
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//	
//	
//
//	@Override
//	public void saveNews(Connection conn, News news) throws DBException {
//		StringBuffer sql = new StringBuffer();
//		sql.append("insert into ").append(PropertiesUtils.getString("db.schema","QRATE")).append(".news(title,content,DESC_TEXT,NEWS_URL,IMAGE_URL,CRAWL_TIME,CREATOR_NAME,OWNER_EMAIL,OWNER_NAME,PERIOD_STARTDATE,PERIOD_ENDDATE,ADDITIONAL_TEXT,NEWS_ID_INDICATOR,LAST_UPDATE_TIME) ");
//		sql.append(" values(?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		try {
//			pstmt = conn.prepareStatement(sql.toString());
//			pstmt.setString( 1, news.getTitle());
//			pstmt.setString( 2, news.getContent());
//			pstmt.setString( 3, news.getDescText());
//			pstmt.setString( 4, news.getNewsUrl());
//			pstmt.setString( 5, news.getImageUrl());
//			pstmt.setTimestamp(6, new Timestamp(news.getCrawTime().getTime()));
//			pstmt.setString( 7, news.getCreatorName());
//			pstmt.setString(8, news.getOwnerEmail());
//			pstmt.setString(9, news.getOwnerName());
//			pstmt.setDate(10, new Date(news.getPeriodStartDate().getTime()));
//			pstmt.setDate(11, new Date(news.getPeriodEndDate().getTime()));
//			pstmt.setString(12, news.getAdditionalText());
//			pstmt.setString(13, news.getNewsIdIndicator());
//			pstmt.setTimestamp(14,  new Timestamp(new java.util.Date().getTime()));
//			pstmt.execute();
//		} catch (SQLException e) {
//			throw new DBException("Failed to insert news,news=" + news,e);
//		}finally{
//			try {
//				if(rs != null){
//					rs.close();
//				}
//				if(pstmt != null){
//					pstmt.close();
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//
//	@Override
//	public void saveVoice(Connection conn, Voice voice) throws DBException {
//		StringBuffer sql = new StringBuffer();
//		sql.append("insert into ").append(PropertiesUtils.getString("db.schema","QRATE")).append(".voice(title,DESC_TEXT,CONTENT,TYPE,URL,MEDIA_URL,PUBLISH_DATE,CRAWL_TIME,FOLLOW,MENTION,MENTION,SCORE,RANK,REF_TWEETER,DOMAIN,VOICE_ID_INDICATOR,LAST_UPDATE_TIME,ICON,ICON_TEXT) ");
//		sql.append(" values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");
//		PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		try {
//			pstmt = conn.prepareStatement(sql.toString());
//			pstmt.setString( 1, voice.getTitle());
//			pstmt.setString( 2, voice.getDescText());
//			pstmt.setString( 3, voice.getContent());
//			pstmt.setString( 4, voice.getType());
//			pstmt.setString( 5, voice.getUrl());
//			pstmt.setString( 6, voice.getMediaUrl());
//			pstmt.setTimestamp( 7, new Timestamp(voice.getPublishDate().getTime()));
//			pstmt.setTimestamp(8,new Timestamp(voice.getCrawTime().getTime()));
//			pstmt.setInt(9, voice.getFollow());
//			pstmt.setInt(10, voice.getMetion());
//			pstmt.setInt(11, voice.getReply());
//			pstmt.setDouble(12, voice.getScore());
//			pstmt.setDouble(13, voice.getRank());
//			pstmt.setString(14, voice.getRefTweeter());
//			pstmt.setString(15, voice.getDomain());
//			pstmt.setString(16, voice.getVoiceIdIndicator());
//			pstmt.setTimestamp(17, new Timestamp(new java.util.Date().getTime()));
//			pstmt.setString(18, voice.getIcon());
//			pstmt.setString(19, voice.getIconText());
//			pstmt.execute();
//		} catch (SQLException e) {
//			throw new DBException("Failed to insert voice,voice=" + voice,e);
//		}finally{
//			try {
//				if(rs != null){
//					rs.close();
//				}
//				if(pstmt != null){
//					pstmt.close();
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//	
//	public void batchSaveVoice(Connection conn, List<Voice> voices) throws DBException{
//		StringBuffer sql = new StringBuffer();
//		sql.append("insert into ").append(PropertiesUtils.getString("db.schema","QRATE")).append(".voice(title,DESC_TEXT,CONTENT,TYPE,URL,MEDIA_URL,PUBLISH_DATE,CRAWL_TIME,FOLLOW,MENTION,REPLY,SCORE,RANK,REF_TWEETER,DOMAIN,VOICE_ID_INDICATOR,LAST_UPDATE_TIME,ICON,ICON_TEXT) ");
//		sql.append(" values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");
//		ResultSet rs = null;
//		PreparedStatement pstmt = null;
//		int i = 0;
//		try {
//			conn.setAutoCommit(false);
//			pstmt = conn.prepareStatement(sql.toString());
//			for (Iterator<Voice> iterator = voices.iterator(); iterator.hasNext();) {
//				Voice voice = (Voice) iterator.next();
//				this.addVoice2Batch(pstmt, voice);
//				if ((i != 0 && (i%100 == 0)) || (i == (voices.size()-1)) ){
//					pstmt.executeBatch();
//					conn.commit();
//				}
//				i++;
//			}
//		} catch (SQLException e) {
//			throw new DBException("Failed to batch save voices",e);
//		}finally{
//			try {
//				if(rs != null){
//					rs.close();
//				}
//				if(pstmt != null){
//					pstmt.close();
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//	public Map<String,Object> selAllVoiceInId(Connection conn)throws DBException{
//		Statement stmt = null;
//		ResultSet rs=null;
//		List<Voice> result=new ArrayList<Voice>();
//		Map<String,Object> map=new HashMap<String,Object>();
//		StringBuffer sql =new StringBuffer();
//		sql.append("SELECT VOICE_ID_INDICATOR FROM ")
//		   .append(PropertiesUtils.getString("db.schema","QRATE"))
//		   .append(".")
//		   .append(PropertiesUtils.getString("voice.table","VOICE"));
//		try {
//			stmt = conn.createStatement();
//			rs=stmt.executeQuery(sql.toString());
//			convertToVoiceList(rs, result, map);
//		} catch (SQLException e) {
//			throw new DBException("Failed to execute a sql,sql=" + sql,e);
//		}finally{
//			try {
//				if(stmt != null){
//					stmt.close();
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		Map<String,Object> temp=new HashMap<String,Object>();
//		temp.put(Constants.VOICES_LIST, result);
//		temp.put(Constants.VOICES_MAP, map);
//		return temp;
//	}
//	public Map<String, Object> selAllNewsInId(Connection conn) throws DBException{
//		Statement stmt = null;
//		ResultSet rs=null;
//		List<News> result=new ArrayList<News>();
//		Map<String,Object> map=new HashMap<String,Object>();
//		StringBuffer sql =new StringBuffer();
//		sql.append("SELECT NEWS_ID_INDICATOR FROM ")
//		   .append(PropertiesUtils.getString("db.schema","QRATE"))
//		   .append(".")
//		   .append(PropertiesUtils.getString("news.table","NEWS"));
//		try {
//			stmt = conn.createStatement();
//			rs=stmt.executeQuery(sql.toString());
//			convertToNewsList(rs, result, map);
//		} catch (SQLException e) {
//			throw new DBException("Failed to execute a sql,sql=" + sql,e);
//		}finally{
//			try {
//				if(stmt != null){
//					stmt.close();
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		Map<String,Object> temp=new HashMap<String,Object>();
//		temp.put(Constants.NEWS_LIST, result);
//		temp.put(Constants.NEWS_MAP, map);
//		return temp;
//		
//		
//	}
//	
//	public void batchUpdateVoice(Connection conn, List<Voice> voices) throws DBException{
//		StringBuffer sql = new StringBuffer();
//		sql.append("UPDATE ")
//		   .append(PropertiesUtils.getString("db.schema","QRATE"))
//		   .append(".")
//		   .append(PropertiesUtils.getString("voice.table","VOICE"))
//		   .append(" SET ")
//		   .append(" SCORE = ? ,").append(" RANK = ? ,").append(" REF_TWEETER = ? ,").append(" LAST_UPDATE_TIME = ? ")
//		   .append(" WHERE VOICE_ID_INDICATOR = ? ");
//		ResultSet rs = null;
//		PreparedStatement pstmt = null;
//		int i = 0;
//		try {
//			conn.setAutoCommit(false);
//			pstmt = conn.prepareStatement(sql.toString());
//			Voice voice=null;
//			for (Iterator<Voice> iterator = voices.iterator(); iterator.hasNext();) {
//				voice = (Voice) iterator.next();
//				pstmt.setDouble(1, voice.getScore());
//				pstmt.setDouble(2, voice.getRank());
//				pstmt.setString(3, voice.getRefTweeter());
//				pstmt.setTimestamp(4, new Timestamp(new java.util.Date().getTime()));
//				pstmt.setString(5, voice.getVoiceIdIndicator());
//				pstmt.addBatch();
//				if ((i != 0 && (i%100 == 0)) || (i == (voices.size()-1)) ){
//					pstmt.executeBatch();
//					conn.commit();
//				}
//				i++;
//			}
//		} catch (SQLException e) {
//			throw new DBException("Failed to batch save voices",e);
//		}finally{
//			try {
//				if(rs != null){
//					rs.close();
//				}
//				if(pstmt != null){
//					pstmt.close();
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//	
//	public void batchUpdateNews(Connection conn, List<News> news) throws DBException{
//		StringBuffer sql = new StringBuffer();
//		sql.append("UPDATE ")
//		   .append(PropertiesUtils.getString("db.schema","QRATE"))
//		   .append(".")
//		   .append(PropertiesUtils.getString("news.table","NEWS"))
//		   .append(" SET ")
////		   CRAWL_TIME,NEWS_ID_INDICATOR,
//		   .append(" TITLE = ? ,").append(" CONTENT = ? ,").append(" DESC_TEXT = ? ,")
//		   .append(" NEWS_URL = ? ,").append(" IMAGE_URL = ? ,").append(" OWNER_EMAIL = ? ,")
//		   .append(" CREATOR_NAME = ? ,").append(" OWNER_NAME = ? ,").append(" PERIOD_STARTDATE = ? ,")
//		   .append(" PERIOD_ENDDATE = ? ,").append(" ADDITIONAL_TEXT = ? ,").append(" LAST_UPDATE_TIME = ? ")
//		   .append(" WHERE NEWS_ID_INDICATOR = ? ");
//		ResultSet rs = null;
//		PreparedStatement pstmt = null;
//		int i = 0;
//		try {
//			conn.setAutoCommit(false);
//			pstmt = conn.prepareStatement(sql.toString());
//			News newsTemp=null;
//			for (Iterator<News> iterator = news.iterator(); iterator.hasNext();) {
//				newsTemp= (News) iterator.next();
//				pstmt.setString(1, newsTemp.getTitle());
//				pstmt.setString(2, newsTemp.getContent());
//				pstmt.setString(3, newsTemp.getDescText());
//				pstmt.setString(4, newsTemp.getNewsUrl());
//				pstmt.setString(5, newsTemp.getImageUrl());
//				pstmt.setString(6, newsTemp.getOwnerEmail());
//				pstmt.setString(7, newsTemp.getCreatorName());
//				pstmt.setString(8, newsTemp.getOwnerName());
//				pstmt.setTimestamp(9, new Timestamp(newsTemp.getPeriodStartDate().getTime()));
//				pstmt.setTimestamp(10, new Timestamp(newsTemp.getPeriodEndDate().getTime()));
//				pstmt.setString(11, newsTemp.getAdditionalText());
//				pstmt.setTimestamp(12, new Timestamp(new java.util.Date().getTime()));
//				pstmt.setString(13, newsTemp.getNewsIdIndicator());
//				pstmt.addBatch();
//				if ((i != 0 && (i%100 == 0)) || (i == (news.size()-1)) ){
//					pstmt.executeBatch();
//					conn.commit();
//				}
//				i++;
//			}
//		} catch (SQLException e) {
//			throw new DBException("Failed to batch save news",e);
//		}finally{
//			try {
//				if(rs != null){
//					rs.close();
//				}
//				if(pstmt != null){
//					pstmt.close();
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//	
//	
//	@Override
//	public void mergeBlogEntry(Connection conn, List<BlogEntry> list)
//			throws DBException {
//		StringBuffer sql = new StringBuffer();
//		sql.append("merge into ").append(PropertiesUtils.getString("db.schema","QRATE")).append(".BLOG_ENTRY blog")
//		    .append(" USING (SELECT 1 from sysibm.dual where 1=1) blog_new ")
//		    .append(" ON (blog.uuid=?) ")
//            .append(" WHEN  matched  THEN ")
//            .append(" update  SET (title,tag,blog_url,blog_date,author_name,author_mail,visit,reply,likes,crawl_time,LAST_UPDATE_TIME,summary,handle) = (?,?,?,?,?,?,?,?,?,?,?,?,?) ")
//            .append(" WHEN NOT MATCHED THEN ")
//            .append(" INSERT(title,content,tag,blog_url,blog_date,author_name,author_mail,visit,reply,likes,crawl_time,LAST_UPDATE_TIME,summary,uuid,handle) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");
//		
//            PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		int i = 0;
//		try {
//			conn.setAutoCommit(false);
//			pstmt = conn.prepareStatement(sql.toString());
//			for (Iterator<BlogEntry> iterator = list.iterator(); iterator.hasNext();) {
//				BlogEntry entry = (BlogEntry) iterator.next();
//				//uuid
//				pstmt.setString( 1, entry.getUuid());
//				//update parameter
//				pstmt.setString( 2, entry.getTitle());
//				//pstmt.setString( 3, entry.getContent());
//				pstmt.setString( 3, entry.getTag());
//				pstmt.setString( 4, entry.getBlogUrl());
//				pstmt.setTimestamp( 5, new Timestamp(entry.getBlogDate().getTime()));
//				pstmt.setString( 6, entry.getAuthorName());
//				pstmt.setString( 7, entry.getAuthorMail());
//				pstmt.setInt(8, entry.getVisit());
//				pstmt.setInt(9, entry.getReply());
//				pstmt.setInt(10, entry.getLikes());
//				pstmt.setTimestamp(11, new Timestamp(entry.getCrawlTime().getTime()));
//				pstmt.setTimestamp(12, new Timestamp(entry.getUpdateDate().getTime()));
//				pstmt.setString(13, entry.getSummary());
//				pstmt.setString(14, entry.getHandle());
//				//insert parameter
//				pstmt.setString( 15, entry.getTitle());
//				pstmt.setString( 16, entry.getContent());
//				pstmt.setString( 17, entry.getTag());
//				pstmt.setString( 18, entry.getBlogUrl());
//				pstmt.setTimestamp( 19, new Timestamp(entry.getBlogDate().getTime()));
//				pstmt.setString( 20, entry.getAuthorName());
//				pstmt.setString( 21, entry.getAuthorMail());
//				pstmt.setInt(22, entry.getVisit());
//				pstmt.setInt(23, entry.getReply());
//				pstmt.setInt(24, entry.getLikes());
//				pstmt.setTimestamp(25, new Timestamp(entry.getCrawlTime().getTime()));
//				pstmt.setTimestamp(26, new Timestamp(entry.getUpdateDate().getTime()));
//				pstmt.setString(27, entry.getSummary());
//				pstmt.setString(28, entry.getUuid());
//				pstmt.setString(29, entry.getHandle());
//				
//				pstmt.addBatch();
//				if ((i != 0 && (i%100 == 0)) || (i == (list.size()-1)) ){
//					pstmt.executeBatch();
//					conn.commit();
//				}
//				i++;
//			}
//		} catch (SQLException e) {
//			throw new DBException("Failed to batch saveOrUpdate blogs",e);
//		}finally{
//			try {
//				if(rs != null){
//					rs.close();
//				}
//				if(pstmt != null){
//					pstmt.close();
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		}
//	
//	
//	
//	public void mergeNews(Connection conn, List<News> list)throws DBException {
//		StringBuffer sql = new StringBuffer();
//		sql.append("merge into ").append(PropertiesUtils.getString("db.schema","QRATE"))
//		   .append(".")
//		   .append(PropertiesUtils.getString("news.table","NEWS"))
//		   .append(" news ")
//		   .append(" USING (SELECT 1 from sysibm.dual where 1=1) blog_new ")
//		   .append(" ON (news.NEWS_ID_INDICATOR=?) ")
//           .append(" WHEN  matched  THEN ")
//           .append(" update  SET (TITLE,CONTENT,DESC_TEXT,NEWS_URL,IMAGE_URL,CRAWL_TIME,CREATOR_NAME,OWNER_EMAIL,OWNER_NAME,PERIOD_STARTDATE,PERIOD_ENDDATE,ADDITIONAL_TEXT,NEWS_ID_INDICATOR,LAST_UPDATE_TIME) = (?,?,?,?,?,?,?,?,?,?,?,?,?,?) ")
//           .append(" WHEN NOT MATCHED THEN ")
//           .append(" INSERT(TITLE,CONTENT,DESC_TEXT,NEWS_URL,IMAGE_URL,CRAWL_TIME,CREATOR_NAME,OWNER_EMAIL,OWNER_NAME,PERIOD_STARTDATE,PERIOD_ENDDATE,ADDITIONAL_TEXT,NEWS_ID_INDICATOR,LAST_UPDATE_TIME) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");
//		
//            PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		int i = 0;
//		try {
//			conn.setAutoCommit(false);
//			pstmt = conn.prepareStatement(sql.toString());
//			for (Iterator<News> iterator = list.iterator(); iterator.hasNext();) {
//				News news = (News) iterator.next();
//				//uuid
//				pstmt.setString( 1, news.getNewsIdIndicator());
//				//update parameter
//				pstmt.setString( 2, news.getTitle());
//				pstmt.setString( 3, news.getContent());
//				pstmt.setString( 4, news.getDescText());
//				pstmt.setString( 5, news.getNewsUrl());
//				pstmt.setString( 6, news.getImageUrl());
//				pstmt.setTimestamp(7, new Timestamp(news.getCrawTime().getTime()));
//				pstmt.setString( 8, news.getCreatorName());
//				pstmt.setString(9, news.getOwnerEmail());
//				pstmt.setString(10, news.getOwnerName());
//				pstmt.setDate(11, new Date(news.getPeriodStartDate().getTime()));
//				pstmt.setDate(12, new Date(news.getPeriodEndDate().getTime()));
//				pstmt.setString(13, news.getAdditionalText());
//				pstmt.setString(14,news.getNewsIdIndicator());
//				pstmt.setTimestamp(15,  new Timestamp(new java.util.Date().getTime()));
//				//insert parameter
//				pstmt.setString( 16, news.getTitle());
//				pstmt.setString( 17, news.getContent());
//				pstmt.setString( 18, news.getDescText());
//				pstmt.setString( 19, news.getNewsUrl());
//				pstmt.setString( 20, news.getImageUrl());
//				pstmt.setTimestamp(21, new Timestamp(news.getCrawTime().getTime()));
//				pstmt.setString( 22, news.getCreatorName());
//				pstmt.setString(23, news.getOwnerEmail());
//				pstmt.setString(24, news.getOwnerName());
//				pstmt.setDate(25, new Date(news.getPeriodStartDate().getTime()));
//				pstmt.setDate(26, new Date(news.getPeriodEndDate().getTime()));
//				pstmt.setString(27, news.getAdditionalText());
//				pstmt.setString(28,news.getNewsIdIndicator());
//				pstmt.setTimestamp(29,  new Timestamp(new java.util.Date().getTime()));
//				
//				pstmt.addBatch();
//				if ((i != 0 && (i%100 == 0)) || (i == (list.size()-1)) ){
//					pstmt.executeBatch();
//					conn.commit();
//				}
//				i++;
//			}
//		} catch (SQLException e) {
//			throw new DBException("Failed to batch saveOrUpdate news",e);
//		}finally{
//			try {
//				if(rs != null){
//					rs.close();
//				}
//				if(pstmt != null){
//					pstmt.close();
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		}
//	
//	public void mergeVoice(Connection conn, List<Voice> list)throws DBException {
//		StringBuffer sql = new StringBuffer();
//		sql.append("merge into ").append(PropertiesUtils.getString("db.schema","QRATE"))
//		   .append(".")
//		   .append(PropertiesUtils.getString("voice.table","VOICE"))
//		   .append(" voice ")
//		   .append(" USING (SELECT 1 from sysibm.dual where 1=1) blog_new ")
//		   .append(" ON (voice.VOICE_ID_INDICATOR= ? ) ")
//           .append(" WHEN  matched  THEN ")
//           .append(" UPDATE  SET (SCORE,RANK,REF_TWEETER,LAST_UPDATE_TIME) = (?,?,?,?) ")
//           .append(" WHEN NOT MATCHED THEN ")
//           .append(" INSERT(TITLE,DESC_TEXT,CONTENT,TYPE,URL,MEDIA_URL,PUBLISH_DATE,CRAWL_TIME,FOLLOW,MENTION,REPLY,SCORE,RANK,REF_TWEETER,DOMAIN,VOICE_ID_INDICATOR,LAST_UPDATE_TIME,ICON,ICON_TEXT) VALUES (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
//		
//            PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		int i = 0;
//		try {
//			conn.setAutoCommit(false);
//			pstmt = conn.prepareStatement(sql.toString());
//			for (Iterator<Voice> iterator = list.iterator(); iterator.hasNext();) {
//				Voice voice = (Voice) iterator.next();
//				//uuid
//				pstmt.setString( 1, voice.getVoiceIdIndicator());
//				//update parameter
//				pstmt.setFloat( 2, voice.getScore());
//				pstmt.setDouble( 3, voice.getRank());
//				pstmt.setString( 4, voice.getRefTweeter());
//				pstmt.setTimestamp( 5,  new Timestamp(voice.getLastUpdateTime().getTime()));
//				//insert parameter
//				pstmt.setString( 6, voice.getTitle());
//				pstmt.setString(7, voice.getDescText());
//				pstmt.setString( 8, voice.getContent());
//				pstmt.setString(9, voice.getType());
//				pstmt.setString(10, voice.getUrl());
//				pstmt.setString(11, voice.getMediaUrl());
//				pstmt.setDate(12, new Date(voice.getPublishDate().getTime()));
//				pstmt.setDate(13, new Date(voice.getCrawTime().getTime()));
//				pstmt.setInt(14,voice.getFollow());
//				pstmt.setInt(15,  voice.getMetion());
//				pstmt.setInt( 16, voice.getReply());
//				pstmt.setFloat( 17, voice.getScore());
//				pstmt.setDouble( 18, voice.getRank());
//				pstmt.setString( 19, voice.getRefTweeter());
//				pstmt.setString( 20, voice.getDomain());
//				pstmt.setString(21, voice.getVoiceIdIndicator());
//				pstmt.setTimestamp(22,  new Timestamp(new java.util.Date().getTime()));
//				pstmt.setString(23, voice.getIcon());
//				pstmt.setString(24, voice.getIconText());
//				
//				pstmt.addBatch();
//				if ((i != 0 && (i%100 == 0)) || (i == (list.size()-1)) ){
//					pstmt.executeBatch();
//					conn.commit();
//				}
//				i++;
//			}
//		} catch (SQLException e) {
//			throw new DBException("Failed to batch saveOrUpdate voice",e);
//		}finally{
//			try {
//				if(rs != null){
//					rs.close();
//				}
//				if(pstmt != null){
//					pstmt.close();
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		}
//	private void addBlogEntry2Batch(PreparedStatement pstmt,BlogEntry entry) throws SQLException{
//		pstmt.setString( 1, entry.getTitle());
//		pstmt.setString( 2, entry.getContent());
//		pstmt.setString( 3, entry.getTag());
//		pstmt.setString( 4, entry.getBlogUrl());
//		pstmt.setTimestamp( 5, new Timestamp(entry.getBlogDate().getTime()));
//		pstmt.setString( 6, entry.getAuthorName());
//		pstmt.setString( 7, entry.getAuthorMail());
//		pstmt.setInt(8, entry.getVisit());
//		pstmt.setInt(9, entry.getReply());
//		pstmt.setInt(10, entry.getLikes());
//		pstmt.setTimestamp(11, new Timestamp(entry.getCrawlTime().getTime()));
//		pstmt.addBatch();
//	}
//	private void addNews2Batch(PreparedStatement pstmt,News news) throws SQLException{
//		pstmt.setString( 1, news.getTitle());
//		pstmt.setString( 2, news.getContent());
//		pstmt.setString( 3, news.getDescText());
//		pstmt.setString( 4, news.getNewsUrl());
//		pstmt.setString( 5, news.getImageUrl());
//		pstmt.setTimestamp(6, new Timestamp(news.getCrawTime().getTime()));
//		pstmt.setString( 7, news.getCreatorName());
//		pstmt.setString(8, news.getOwnerEmail());
//		pstmt.setString(9, news.getOwnerName());
//		pstmt.setDate(10, new Date(news.getPeriodStartDate().getTime()));
//		pstmt.setDate(11, new Date(news.getPeriodEndDate().getTime()));
//		pstmt.setString(12, news.getAdditionalText());
//		pstmt.setString(13,news.getNewsIdIndicator());
//		pstmt.setTimestamp(14,  new Timestamp(new java.util.Date().getTime()));
//		pstmt.addBatch();
//	}
//	
//	private void addVoice2Batch(PreparedStatement pstmt,Voice voice) throws SQLException{
//		pstmt.setString( 1, voice.getTitle());
//		pstmt.setString( 2, voice.getDescText());
//		pstmt.setString( 3, voice.getContent());
//		pstmt.setString( 4, voice.getType());
//		pstmt.setString( 5, voice.getUrl());
//		pstmt.setString( 6, voice.getMediaUrl());
//		pstmt.setTimestamp( 7, new Timestamp(voice.getPublishDate().getTime()));
//		pstmt.setTimestamp(8,new Timestamp(voice.getCrawTime().getTime()));
//		pstmt.setInt(9, voice.getFollow());
//		pstmt.setInt(10, voice.getMetion());
//		pstmt.setInt(11, voice.getReply());
//		pstmt.setDouble(12, voice.getScore());
//		pstmt.setDouble(13, voice.getRank());
//		pstmt.setString(14, voice.getRefTweeter());
//		pstmt.setString(15, voice.getDomain());
//		pstmt.setString(16, voice.getVoiceIdIndicator());
//		pstmt.setTimestamp(17, new Timestamp((new java.util.Date().getTime())));
//		pstmt.setString(18, voice.getIcon());
//		pstmt.setString(19, voice.getIconText());
//		pstmt.addBatch();
//	}
//	private void convertToVoiceList(ResultSet rs,List<Voice> result,Map<String,Object> map)throws DBException{
//		Voice voice=null;
//		try {
//			while(rs.next()){
//				voice=new Voice();
//				voice.setVoiceId(rs.getInt(1));
//				voice.setTitle(rs.getString(2));
//				voice.setDescText(rs.getString(3));
//				voice.setContent(rs.getString(4));
//				voice.setType(rs.getString(5));
//				voice.setUrl(rs.getString(6));
//				voice.setMediaUrl(rs.getString(7));
//				voice.setPublishDate(rs.getDate(8));
//				voice.setCrawTime(rs.getDate(9));
//				voice.setFollow(rs.getInt(10));
//				voice.setMetion(rs.getInt(11));
//				voice.setReply(rs.getInt(12));
//				voice.setScore(rs.getInt(13));
//				voice.setRank(rs.getInt(14));
//				voice.setRefTweeter(rs.getString(15));
//				voice.setDomain(rs.getString(16));
//				voice.setVoiceIdIndicator(rs.getString(1));
//				result.add(voice);
//				map.put(voice.getVoiceIdIndicator(), voice);
//			}
//		} catch (SQLException e) {
//			throw new DBException("Failed to query all voices",e);
//		}
//	}
//	private void convertToNewsList(ResultSet rs,List<News> result,Map<String,Object> map)throws DBException{
//		News news=null;
//		try {
//			while(rs.next()){
//				news=new News();
//				news.setNewsIdIndicator(rs.getString(1));
//				result.add(news);
//				map.put(news.getNewsIdIndicator(), news);
//			}
//		} catch (SQLException e) {
//			throw new DBException("Failed to query all voices",e);
//		}
//	}
//	
//	@Override
//	public void mergeUserFollow(Connection conn, List<UserFollow> list) throws DBException {
//		
//		StringBuffer sql = new StringBuffer();
//		sql.append("merge into ").append(PropertiesUtils.getString("db.schema","QRATE"))
//		   .append(".user_follow follow")
//		   .append(" USING (SELECT 1 from sysibm.dual where 1=1) blog_new ")
//		   .append(" ON (follow.USER_ID= ? AND follow.CODE=? AND follow.source=? AND follow.type=? ) ")
//           //.append(" WHEN  matched  THEN ")
//          // .append(" UPDATE  SET (NAME,TYPE,SOURCE) = (?,?,?) ")
//           .append(" WHEN NOT MATCHED THEN ")
//           .append(" INSERT(USER_ID,CODE,NAME,TYPE,SOURCE,IS_DISPLAYED,ORDER_INDEX,PRIORITY,TITLE) VALUES (?,?,?,?,?,?,?,?,?)");
//		
//        PreparedStatement pstmt = null;
//		ResultSet rs = null;
//		int i = 0;
//		try {
//			conn.setAutoCommit(false);
//			pstmt = conn.prepareStatement(sql.toString());
//			for (Iterator<UserFollow> iterator = list.iterator(); iterator.hasNext();) {
//				UserFollow member = (UserFollow) iterator.next();
//				pstmt.setString( 1, member.getUserId());	            
//				pstmt.setString( 2, member.getCode());	
//				pstmt.setString( 3, member.getSource());
//				pstmt.setString( 4, member.getType());
//				pstmt.setString( 5, member.getUserId());	            
//				pstmt.setString( 6, member.getCode());
//				pstmt.setString( 7, member.getName());
//				pstmt.setString( 8, member.getType());
//				pstmt.setString( 9, member.getSource());
//				pstmt.setString( 10, member.getDisplayed());
//				pstmt.setInt( 11, member.getOrder_index());
//				pstmt.setInt( 12, member.getPriority());
//				pstmt.setString( 13, member.getTitle());
//				
//				pstmt.addBatch();
//				if ((i != 0 && (i%100 == 0)) || (i == (list.size()-1)) ){
//					pstmt.executeBatch();
//					conn.commit();
//				}
//				i++;								
//			}
//		} catch (SQLException e) {
//			throw new DBException("Failed to batch saveOrUpdate User Follow",e);
//		}finally{
//			try {
//				if(rs != null){
//					rs.close();
//				}
//				if(pstmt != null){
//					pstmt.close();
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//	
//	@Override
//	public List<UserFollow> getReportLineMembers(Connection conn)
//			throws DBException {
//		PreparedStatement pstmt = null;
//		ResultSet rs=null;
//		List<UserFollow> reportLineMemberList=new ArrayList<UserFollow>();
//		UserFollow reportLineMember;
//		StringBuffer sql =new StringBuffer();
//		sql.append("SELECT user_id,code FROM ")
//		   .append(PropertiesUtils.getString("db.schema","QRATE"))
//		   .append(".user_follow where  source in (?,?,?) ");
//		try {
//			pstmt = conn.prepareStatement(sql.toString());
//			pstmt.setString( 1, UserFollowSourceEnum.REPORT_LINE.toString());
//			pstmt.setString( 2, UserFollowSourceEnum.LINE_LEADER.toString());
//			pstmt.setString( 3, UserFollowSourceEnum.GINNI.toString());
//			
//			rs=pstmt.executeQuery();
//			
//			while(rs.next()){
//				reportLineMember=new UserFollow();
//				reportLineMember.setUserId(rs.getString(1));
//				reportLineMember.setCode(rs.getString(2));
//				reportLineMemberList.add(reportLineMember);
//			}
//		} catch (SQLException e) {
//			throw new DBException("Failed to execute a sql,sql=" + sql,e);
//		}finally{
//			try {
//				if(pstmt != null){
//					pstmt.close();
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		return reportLineMemberList;		
//	}
//	@Override
//	public void deleteReportLineMembers(Connection conn,
//			List<UserFollow> reportLineMemberList) throws DBException {
//		PreparedStatement pstmt = null;
//		StringBuffer sql =new StringBuffer();
//		sql.append("delete ")
//		   .append(PropertiesUtils.getString("db.schema","QRATE"))
//		   .append(".user_follow where user_id=? and code=? and  source in (?,?,?) ");
//		int i=0;
//		try {
//			conn.setAutoCommit(false);
//			pstmt = conn.prepareStatement(sql.toString());
//			
//			for(UserFollow reportLineMember:reportLineMemberList){
//				pstmt.setString( 1, reportLineMember.getUserId());
//				pstmt.setString( 2, reportLineMember.getCode());
//			
//			    pstmt.setString( 3, UserFollowSourceEnum.REPORT_LINE.toString());
//			    pstmt.setString( 4, UserFollowSourceEnum.LINE_LEADER.toString());
//			    pstmt.setString( 5, UserFollowSourceEnum.GINNI.toString());
//			    
//			    pstmt.addBatch();
//			    
//			    if ((i != 0 && (i%50 == 0)) || (i == (reportLineMemberList.size()-1)) ){
//					pstmt.executeBatch();
//					conn.commit();
//				}
//				i++;				
//			}
//			
//		} catch (SQLException e) {
//			throw new DBException("Failed to execute a sql,sql=" + sql,e);
//		}finally{
//			try {
//				if(pstmt != null){
//					pstmt.close();
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//	@Override
//	public List<String> getBlueGroupMembers(Connection conn) throws DBException {
//		PreparedStatement pstmt = null;
//		ResultSet rs=null;
//		List<String> blueGroupMembers=new ArrayList<String>();
//		StringBuffer sql =new StringBuffer();
//		sql.append("SELECT distinct user_id FROM ")
//		   .append(PropertiesUtils.getString("db.schema","QRATE"))
//		   .append(".user_follow where source in (?,?,?,?,?,?) ");
//		try {
//			pstmt = conn.prepareStatement(sql.toString());
//			pstmt.setString( 1, UserFollowSourceEnum.REPORT_LINE.toString());
//			pstmt.setString( 2, UserFollowSourceEnum.LINE_LEADER.toString());
//			pstmt.setString( 3, UserFollowSourceEnum.GINNI.toString());
//			pstmt.setString( 4, UserFollowSourceEnum.NET_WORK.toString());
//			pstmt.setString( 5, UserFollowSourceEnum.DR.toString());
//			pstmt.setString( 6, UserFollowSourceEnum.PEER.toString());
//			rs=pstmt.executeQuery();
//			
//			while(rs.next()){
//				blueGroupMembers.add(rs.getString(1));
//			}
//		} catch (SQLException e) {
//			throw new DBException("Failed to execute a sql,sql=" + sql,e);
//		}finally{
//			try {
//				if(pstmt != null){
//					pstmt.close();
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		return blueGroupMembers;
//	}
//	@Override
//	public void deleteBlueGroupMembers(Connection conn,List<String> blueGroupMembers)
//			throws DBException {
//		PreparedStatement pstmt = null;
//		StringBuffer sql =new StringBuffer();
//		sql.append("delete from ")
//		   .append(PropertiesUtils.getString("db.schema","QRATE"))
//		   .append(".user_follow where user_id=? and source in (?,?,?,?,?,?) ");
//		int i=0;
//		try {
//			conn.setAutoCommit(false);
//			pstmt = conn.prepareStatement(sql.toString());
//			
//			for(String member:blueGroupMembers){
//				pstmt.setString( 1, member);
//				pstmt.setString( 2, UserFollowSourceEnum.REPORT_LINE.toString());
//				pstmt.setString( 3, UserFollowSourceEnum.LINE_LEADER.toString());
//				pstmt.setString( 4, UserFollowSourceEnum.NET_WORK.toString());
//				pstmt.setString( 5, UserFollowSourceEnum.GINNI.toString());
//				pstmt.setString( 6, UserFollowSourceEnum.DR.toString());
//				pstmt.setString( 7, UserFollowSourceEnum.PEER.toString());
//			    pstmt.addBatch();
//			    
//			    if ((i != 0 && (i%50 == 0)) || (i == (blueGroupMembers.size()-1)) ){
//					pstmt.executeBatch();
//					conn.commit();
//				}
//				i++;				
//			}
//			
//		} catch (SQLException e) {
//			throw new DBException("Failed to execute a sql,sql=" + sql,e);
//		}finally{
//			try {
//				if(pstmt != null){
//					pstmt.close();
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//	
//	@Override
//	public void delUserFollow(Connection conn,List<String> userIds,String[] source) throws DBException {
//		PreparedStatement pstmt = null;
//		StringBuffer sql =new StringBuffer();
//		sql.append("delete from ")
//		   .append(PropertiesUtils.getString("db.schema","QRATE"))
//		   .append(".user_follow where user_id=? and source = (");
//		for(int i = 0 ; i < source.length ;i++){
//			if(i == (source.length-1)){//last
//				sql.append("'").append(source[i]).append("'");
//			}else{
//				sql.append("'").append(source[i]).append("'").append(",");
//			}
//		}
//		sql.append(")");
//		int i=0;
//		try {
//			conn.setAutoCommit(false);
//			pstmt = conn.prepareStatement(sql.toString());
//			
//			for(String userId:userIds){
//				pstmt.setString( 1, userId);
//			    pstmt.addBatch();
//			    if ((i != 0 && (i%50 == 0)) || (i == (userIds.size()-1)) ){
//					pstmt.executeBatch();
//					conn.commit();
//				}
//				i++;				
//			}
//			
//		} catch (SQLException e) {
//			throw new DBException("Failed to execute a sql,sql=" + sql,e);
//		}finally{
//			try {
//				if(pstmt != null){
//					pstmt.close();
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//	}
//	
//	@Override
//	public List<String> getUserFollow(Connection conn,String[] source) throws DBException {
//		Statement pstmt = null;
//		ResultSet rs=null;
//		List<String> list = new ArrayList<String>();
//		StringBuffer sql =new StringBuffer();
//		sql.append("SELECT distinct user_id FROM ")
//		   .append(PropertiesUtils.getString("db.schema","QRATE"))
//		   .append(".user_follow where source = (");
//		for(int i = 0 ; i < source.length ;i++){
//			if(i == (source.length-1)){//last
//				sql.append("'").append(source[i]).append("'");
//			}else{
//				sql.append("'").append(source[i]).append("'").append(",");
//			}
//		}
//		sql.append(")");
//		try {
//			pstmt = conn.createStatement();
//			rs=pstmt.executeQuery(sql.toString());
//			while(rs.next()){
//				list.add(rs.getString(1));
//			}
//		} catch (SQLException e) {
//			throw new DBException("Failed to execute a sql,sql=" + sql,e);
//		}finally{
//			try {
//				if(pstmt != null){
//					pstmt.close();
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		return list;
//	}
//	
//	@Override
//	public List<String> getNetWorkByUserId(Connection conn, String userId)
//			throws DBException {
//		PreparedStatement pstmt = null;
//		ResultSet rs=null;
//		List<String> netWorMembers=new ArrayList<String>();
//		StringBuffer sql =new StringBuffer();
//		sql.append("SELECT code FROM ")
//		   .append(PropertiesUtils.getString("db.schema","QRATE"))
//		   .append(".user_follow where user_id=? and  source =? ");
//		try {
//			pstmt = conn.prepareStatement(sql.toString());
//			pstmt.setString( 1, userId);
//			pstmt.setString( 2, UserFollowSourceEnum.NET_WORK.toString());
//			
//			rs=pstmt.executeQuery();
//			
//			while(rs.next()){
//				netWorMembers.add(rs.getString(1));
//			}
//		} catch (SQLException e) {
//			throw new DBException("Failed to execute a sql,sql=" + sql,e);
//		}finally{
//			try {
//				if(pstmt != null){
//					pstmt.close();
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		return netWorMembers;
//	}
//	@Override
//	public void deleteUserFollow(Connection conn,
//			List<UserFollow> userFollows) throws DBException {
//		PreparedStatement pstmt = null;
//		StringBuffer sql =new StringBuffer();
//		sql.append("delete ")
//		   .append(PropertiesUtils.getString("db.schema","QRATE"))
//		   .append(".user_follow where user_id=? and code=?  and source=? ");
//		int i=0;
//		try {
//			conn.setAutoCommit(false);
//			pstmt = conn.prepareStatement(sql.toString());
//			
//			for(UserFollow  follow:userFollows){
//				pstmt.setString( 1, follow.getUserId());
//				pstmt.setString( 2, follow.getCode());
//				pstmt.setString( 3, follow.getSource());
//			    
//			    pstmt.addBatch();
//			    
//			    if ((i != 0 && (i%50 == 0)) || (i == (userFollows.size()-1)) ){
//					pstmt.executeBatch();
//					conn.commit();
//				}
//				i++;				
//			}
//			
//		} catch (SQLException e) {
//			throw new DBException("Failed to execute a sql,sql=" + sql,e);
//		}finally{
//			try {
//				if(pstmt != null){
//					pstmt.close();
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		
//	}
//	
//	@Override
//	public List<UserFollow> getUserFollow(Connection conn,String userId,String[] source)
//			throws DBException {
//		Statement pstmt = null;
//		ResultSet rs=null;
//		List<UserFollow> reportLineMemberList=new ArrayList<UserFollow>();
//		UserFollow reportLineMember;
//		StringBuffer sql =new StringBuffer();
//		sql.append("SELECT user_id,code FROM ")
//		   .append(PropertiesUtils.getString("db.schema","QRATE"))
//		   .append(".user_follow where  source = (");
//		for(int i = 0 ; i < source.length ;i++){
//			if(i == (source.length-1)){//last
//				sql.append("'").append(source[i]).append("'");
//			}else{
//				sql.append("'").append(source[i]).append("'").append(",");
//			}
//		}
//		sql.append(")");
//		try {
//			pstmt = conn.createStatement();
//			rs=pstmt.executeQuery(sql.toString());
//			
//			while(rs.next()){
//				reportLineMember=new UserFollow();
//				reportLineMember.setUserId(rs.getString(1));
//				reportLineMember.setCode(rs.getString(2));
//				reportLineMemberList.add(reportLineMember);
//			}
//		} catch (SQLException e) {
//			throw new DBException("Failed to query,sql=" + sql,e);
//		}finally{
//			try {
//				if(pstmt != null){
//					pstmt.close();
//				}
//			} catch (SQLException e) {
//				e.printStackTrace();
//			}
//		}
//		return reportLineMemberList;		
//	}
//	
//	public void reCalNetWorkCount(Connection conn) throws DBException{
//		StringBuffer sql = new StringBuffer();
//		sql.append("UPDATE ").append(PropertiesUtils.getString("db.schema","QRATE")).append(".user_follow a SET NETWORK_COUNT= ( ");
//		sql.append("SELECT cnt FROM ( SELECT user_id,COUNT(*) cnt FROM  qrate.user_follow WHERE type='connect' AND source='net work' ");
//		sql.append(" 	GROUP BY user_id) temp ");
//		sql.append("    where a.user_id=temp.user_id     ");
//		sql.append(" ) ");
//		this.execute(conn, sql.toString());
//	}

	

	
	@Override
	public List<OrderDetail> getOrderDetailById(String uuid, Connection conn)
			throws DBException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public void insertUser(UserModle u, Connection conn) throws DBException {
		// TODO Auto-generated method stub
		
	}


	@Override
	public void updateUser(UserModle u, Connection conn) throws DBException {
		// TODO Auto-generated method stub
		
	}

}
