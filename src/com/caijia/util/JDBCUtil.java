package com.caijia.util;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.Properties;

import org.apache.commons.dbcp.BasicDataSource;
import org.apache.commons.dbcp.BasicDataSourceFactory;

import com.caijia.exception.DBException;

public class JDBCUtil {
    private static BasicDataSource dataSource = null;

    public JDBCUtil() {
    }

    public static void init() throws DBException{

        if (dataSource != null) {
            try {
                dataSource.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
            dataSource = null;
        }

        try {
            Properties p = new Properties();
            p.setProperty("driverClassName", Constants.JDBC_DRIVER);
            p.setProperty("url", Constants.JDBC_URL);
            p.setProperty("password", Constants.JDBC_PASSWORD);
            p.setProperty("username", Constants.JDBC_USERNAME);
            p.setProperty("maxActive", Constants.POOL_MAX_ACTIVE);
            p.setProperty("maxIdle", Constants.POOL_MAX_IDLE);
            p.setProperty("maxWait", Constants.POOL_MAX_WAIT);
            p.setProperty("removeAbandoned", Constants.REMOVEABANDONED);
            p.setProperty("removeAbandonedTimeout", Constants.REMOVEABANDONEDTIMEOUT);
            p.setProperty("testOnBorrow", Constants.TESTONBORROW);
            p.setProperty("logAbandoned", Constants.LOGABANDONED);

            dataSource = (BasicDataSource) BasicDataSourceFactory.createDataSource(p);

        } catch (Exception e) {
            throw new DBException("init fiailed",e);
        }
    }

    /**
     * 关闭连接
     * @param conn
     * @throws DBException
     */
    public static final void close(Connection conn) throws DBException {
		try {
			if ((conn != null) && (!(conn.isClosed()))){
				conn.close();
				conn = null;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

    public static synchronized Connection getConnection() throws  DBException {
    	Connection conn = null;
    	try{
    		if (dataSource == null) {
                init();
            }
            if (dataSource != null) {
                conn = dataSource.getConnection();
            }
    	}catch (SQLException e) {
			throw new DBException("get connection fialed",e);
		}
        return conn;
    }
    
    public static void main(String[] args) {
    	Connection conn = null;
    	try {
			conn = getConnection();
		} catch (DBException e) {
			e.printStackTrace();
		}finally{
			try {
				close(conn);
			} catch (DBException e) {
				e.printStackTrace();
			}
		}
	}
}
