package com.caijia.dao;

import java.sql.Connection;
import java.util.List;

import com.caijia.exception.DBException;
import com.caijia.modle.CaiModle;
import com.caijia.modle.CityModle;
import com.caijia.modle.CommunicateModle;
import com.caijia.modle.OrderDetail;
import com.caijia.modle.OrdersModle;
import com.caijia.modle.UserModle;



public interface CaiDao {
	
public List<CaiModle> getAllCai(Connection conn)throws DBException;
	
	public void insertOrder(OrdersModle om,Connection conn) throws DBException;
	
	public void insertUser(UserModle u,Connection conn) throws DBException;
	
	public void updateUser(UserModle u,Connection conn) throws DBException;
	
	public List<CaiModle> getCaiById(String uuid,Connection conn) throws DBException;
	
	public List<CaiModle> getOneCai(String uuid,Connection conn) throws DBException;

	
	public List<UserModle> getUserById(String uuid,Connection conn) throws DBException;
	
	public List<UserModle> getOneWenhua(String uuid,Connection conn) throws DBException;

	public OrdersModle getOrderById(String uuid,Connection conn) throws DBException;
	
	public List<OrderDetail> getOrderDetailById(String uuid,Connection conn) throws DBException;
	
	public boolean insertCommunicate(CommunicateModle c,Connection conn) throws DBException;
	
	public List<CityModle> getCityByRole(String uuid,Connection conn) throws DBException;
	public List<CityModle> getCityById(String uuid,Connection conn) throws DBException;

	public List<CommunicateModle> getComByPu(String uuid,Connection conn) throws DBException;
	public List<CommunicateModle> getComById(String uuid,Connection conn) throws DBException;

	
//	/**
//	 * persist blog entry
//	 * @param conn
//	 * @param entry
//	 * @throws DBException
//	 */
//	public void saveBlogEntry(Connection conn,BlogEntry entry) throws DBException;
//	
//	/**
//	 * batch persist BlogEntry into db
//	 * @param conn
//	 * @param list
//	 * @throws DBException
//	 */
//	public void batchSaveBlogEntry(Connection conn, List<BlogEntry> list) throws DBException;
//	
//	/**
//	 * persist news
//	 * @param conn
//	 * @param news
//	 * @throws DBException
//	 */
//	public void saveNews(Connection conn,News news) throws DBException;
//	
//	/**
//	 * batch persist news into db
//	 * @param conn
//	 * @param newslist
//	 * @throws DBException
//	 */	
//	public void batchSaveNews(Connection conn, List<News> newslist) throws DBException;
//	
//	/**
//	 * persist voice
//	 * @param conn
//	 * @param voice
//	 * @throws DBException
//	 */
//	public void saveVoice(Connection conn,Voice voice) throws DBException;
//	
//	/**
//	 * batch persist voice into db
//	 * @param conn
//	 * @param voices
//	 * @throws DBException
//	 */
//	public void batchSaveVoice(Connection conn, List<Voice> voices) throws DBException;
//	
//	/**
//	 * execute a non-query sql
//	 * @param conn
//	 * @param exsql
//	 * @return
//	 * @throws DBException
//	 */
//	public boolean execute(Connection conn,String exsql) throws DBException;
//	
//	/**
//	 * execute query all voices
//	 * @param conn
//	 * @return ResultSet
//	 * @throws DBException
//	 * @remarks return Parameter.get(Constants.VOICES_MAP) or .get(Constants.VOICES_LIST)
//	 */
//	public Map<String, Object> selAllVoiceInId(Connection conn) throws DBException;
//	/**
//	 * execute query all voices
//	 * @param conn
//	 * @return ResultSet
//	 * @throws DBException
//	 * @remarks return Parameter.get(Constants.NEWS_MAP) or .get(Constants.NEWS_LIST)
//	 */
//	public Map<String, Object> selAllNewsInId(Connection conn) throws DBException;
//	/**
//	 * batch update voice into db
//	 * @param conn
//	 * @param voices
//	 * @throws DBException
//	 */
//	public void batchUpdateVoice(Connection conn, List<Voice> voices) throws DBException;
//	/**
//	 * batch update news into db
//	 * @param conn
//	 * @param news
//	 * @throws DBException
//	 */
//	public void batchUpdateNews(Connection conn, List<News> news) throws DBException;
//	/**
//	 * update or insert blog itmes
//	 * @param conn
//	 * @param BlogEntry
//	 * @return
//	 * @throws DBException
//	 */
//	public void mergeBlogEntry (Connection conn,List<BlogEntry> list) throws DBException;
//	/**
//	 * update or insert News itmes
//	 * @param conn
//	 * @param News
//	 * @return
//	 * @throws DBException
//	 */
//	public void mergeNews(Connection conn, List<News> list)throws DBException;
//	/**
//	 * update or insert Voice itmes
//	 * @param conn
//	 * @param Voice
//	 * @return
//	 * @throws DBException
//	 */
//	public void mergeVoice(Connection conn, List<Voice> list)throws DBException ;
//	
//	/**
//	 * update or insert BlueGroup member list
//	 * @param NetWork member list
//	 * @return
//	 * @throws DBException
//	 */
//	public void mergeUserFollow(Connection conn,List<UserFollow> list)throws DBException;
//	
//	/**
//	 * get report line member according to userId
//	 * @param userId
//	 * @return
//	 * @throws DBException
//	 */
//	public List<UserFollow> getReportLineMembers(Connection conn)throws DBException;
//	
//	/**
//	 * delete report line members
//	 * @param List<ReportLineMember>
//	 * @return
//	 * @throws DBException
//	 */
//	public void deleteReportLineMembers(Connection conn,List<UserFollow> reportLineMemberList)throws DBException;
//	
//	/**get the blue group members from table user_follow
//	 * 
//	 * @param conn
//	 * @return
//	 * @throws DBException
//	 */
//	public List<String> getBlueGroupMembers(Connection conn)throws DBException;
//	
//	/**
//	 * 
//	 * @param conn
//	 * @param source
//	 * @return
//	 * @throws DBException
//	 */
//	public List<String> getUserFollow(Connection conn,String[] source) throws DBException;
//	
//	/**delete the blue group members from table user_follow
//	 * 
//	 * @param conn
//	 * @return
//	 * @throws DBException
//	 */
//	public void deleteBlueGroupMembers(Connection conn,List<String> blueGroupMembers)throws DBException;
//	
//	/**
//	 * delete from user_follow
//	 * @param conn
//	 * @param userIds
//	 * @param source
//	 * @throws DBException
//	 */
//	public void delUserFollow(Connection conn,List<String> userIds,String[] source) throws DBException ;
//	
//	/**get the net work members from table user_follow
//	 * 
//	 * @param conn
//	 * @param userId
//	 * @return NetWork
//	 * @throws DBException
//	 */
//	public List<String> getNetWorkByUserId(Connection conn,String userId)throws DBException;
//	
//	/**delete the net work members from table user_follow
//	 * 
//	 * @param conn
//	 * @param List<UserFollow> netWorKMembers
//	 * @return 
//	 * @throws DBException
//	 */
//	public void deleteUserFollow(Connection conn,List<UserFollow> userFollows)throws DBException;
//	
//	/**
//	 * 
//	 * @param conn
//	 * @param source
//	 * @return
//	 * @throws DBException
//	 */
//	public List<UserFollow> getUserFollow(Connection conn,String userId,String[] source)
//			throws DBException;
//	
//	/**
//	 * recalculate the count of some one's net work
//	 * @param conn
//	 * @throws DBException
//	 */
//	public void reCalNetWorkCount(Connection conn) throws DBException;
		
}
