package com.qrrest.dao;

import java.beans.PropertyVetoException;
import java.io.InputStream;
import java.sql.*;
import java.util.Properties;

import com.mchange.v2.c3p0.ComboPooledDataSource;

import com.qrrest.dao.ConnectionFactory;

public class ConnectionFactory {

	private static String CONFIG_FILE_NAME =  "c3p0.properties"; 
	private final ThreadLocal<Connection> threadLocal = new ThreadLocal<Connection>();
	private String configFile = CONFIG_FILE_NAME;
	private ComboPooledDataSource ds;
	private static ConnectionFactory instance;

	public static ConnectionFactory getInstance(String filename) {

		if (instance == null) {
			if (filename == null) {
				filename = CONFIG_FILE_NAME;
			}

			instance = new ConnectionFactory(filename);
		}

		return instance;
	}

	public static ConnectionFactory getInstance() {
		return getInstance(CONFIG_FILE_NAME);
	}

	private void init() {
		Properties dbProps = new Properties();
		try {

//			InputStream is = new FileInputStream(configFile);
			InputStream is = this.getClass().getResourceAsStream(configFile);
			dbProps.load(is);
			System.out.println("db config load success!");

		} catch (Exception e) {

			e.printStackTrace();
			System.out.println("DB config load failed.");
			throw new RuntimeException("DB config load failed.");

		}

		ds = new ComboPooledDataSource();

		try {

			ds.setDriverClass(dbProps.getProperty("c3p0.DriverClass").trim());

		} catch (PropertyVetoException e) {

			throw new RuntimeException("com.mysql.jdbc.Driver加载失败");

		}

//		System.out.println(dbProps.toString());

		ds.setJdbcUrl(dbProps.getProperty("c3p0.JdbcUrl").trim());
		ds.setUser(dbProps.getProperty("c3p0.user").trim());

		String password = dbProps.getProperty("c3p0.password").trim();
		ds.setPassword(password);

		// 连接关闭时默认将所有未提交的操作回滚
		ds.setAutoCommitOnClose(true);

		// 定义所有连接测试都执行的测试语句。在使用连接测试的情况下这个一显著提高测试速度。注意：
		// 测试的表必须在初始数据源的时候就存在。Default: null preferredTestQuery
		ds.setPreferredTestQuery("select 1");
		// 因性能消耗大请只在需要的时候使用它。如果设为true那么在每个connection提交的
		// 时候都将校验其有效性。建议使用idleConnectionTestPeriod或automaticTestTable
		// 等方法来提升连接测试的性能。Default: false testConnectionOnCheckout
		ds.setTestConnectionOnCheckout(false);
		// 如果设为true那么在取得连接的同时将校验连接的有效性。Default: false testConnectionOnCheckin
		ds.setTestConnectionOnCheckin(false);
		// 获取连接失败将会引起所有等待连接池来获取连接的线程抛出异常。但是数据源仍有效
		// 保留，并在下次调用getConnection()的时候继续尝试获取连接。如果设为true，那么在尝试
		// 获取连接失败后该数据源将申明已断开并永久关闭。Default: false breakAfterAcquireFailure
		ds.setBreakAfterAcquireFailure(false);

		try {
			// 初始化时获取三个连接，取值应在minPoolSize与maxPoolSize之间。Default: 3
			// initialPoolSize
			ds.setInitialPoolSize(Integer.parseInt(dbProps.getProperty(
					"c3p0.initialPoolSize").trim()));
			// ds.setInitialPoolSize(3);
			// 连接池中保留的最大连接数。Default: 15 maxPoolSize
			ds.setMaxPoolSize(Integer.parseInt(dbProps.getProperty(
					"c3p0.maxPoolSize").trim()));
			// ds.setMaxPoolSize(10);
			// 连接池中保留的最小连接数。
			ds.setMinPoolSize(Integer.parseInt(dbProps.getProperty(
					"c3p0.maxPoolSize").trim()));
			// ds.setMinPoolSize(1);
			// 当连接池中的连接耗尽的时候c3p0一次同时获取的连接数。Default: 3 acquireIncrement
			ds.setAcquireIncrement(Integer.parseInt(dbProps.getProperty(
					"c3p0.acquireIncrement").trim()));
			// ds.setAcquireIncrement(1);
			// 每60秒检查所有连接池中的空闲连接。Default: 0 idleConnectionTestPeriod
			ds.setIdleConnectionTestPeriod(Integer.parseInt(dbProps
					.getProperty("c3p0.idleConnectionTestPeriod").trim()));
			// ds.setIdleConnectionTestPeriod(60);
			// 最大空闲时间,25000秒内未使用则连接被丢弃。若为0则永不丢弃。Default: 0 maxIdleTime
			ds.setMaxIdleTime(Integer.parseInt(dbProps.getProperty(
					"c3p0.maxIdleTime").trim()));
			// ds.setMaxIdleTime(25000);
			// 定义在从数据库获取新连接失败后重复尝试的次数。Default: 30 acquireRetryAttempts
			ds.setAcquireRetryAttempts(Integer.parseInt(dbProps.getProperty(
					"c3p0.acquireRetryAttempts").trim()));
			// ds.setAcquireRetryAttempts(30);
			// 两次连接中间隔时间，单位毫秒。Default: 1000 acquireRetryDelay
			ds.setAcquireRetryDelay(Integer.parseInt(dbProps.getProperty(
					"c3p0.acquireRetryDelay").trim()));
			// ds.setAcquireRetryDelay(1000);
			System.out.println("db set config success!");
		} catch (Exception e) {
			System.out.println("oh, db set config failed!");
			e.printStackTrace();
		}
	}

	private ConnectionFactory() {
		init();
		System.out.println(threadLocal);
	}

	private ConnectionFactory(String dbFilePath) {
		configFile = dbFilePath;
		System.out.println(threadLocal);
		init();
	}

	public Connection getConnection() {
		Connection connection = threadLocal.get();

		if (connection == null) {
			try {
				connection = ds.getConnection();
			} catch (Exception e) {
				System.out.println(e.getMessage());
			}

			threadLocal.set(connection);
		}

		return connection;
	}

	public void freeConnection() {
		Connection connection = threadLocal.get();

		try {
			if (connection != null && !connection.isClosed()) {
				connection.close();
				threadLocal.set(null);
			}
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}
}
