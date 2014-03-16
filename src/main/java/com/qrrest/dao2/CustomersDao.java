package com.qrrest.dao2;

import java.sql.ResultSet;
import java.sql.SQLException;

import com.qrrest.model2.Customer;


public class CustomersDao {
	private SQLExecution sqlE = new SQLExecution();

	/**
	 * 通过顾客id获取顾客
	 * 
	 */
	public Customer getCustomerById(long customerid) {
		Customer customer = new Customer();
		String sql = "select * from customers where customer_id=?";
		Object[] params = { customerid };
		ResultSet rs = sqlE.execSqlWithRS(sql, params);
		try {
			while (rs.next()) {
				customer.setCustomer_id(rs.getLong("customer_id"));
				customer.setCutomer_name(rs.getString("customer_name"));
				customer.setCustomer_pwd(rs.getString("customer_pwd"));
				customer.setCustomer_deviceid(rs.getString("customer_deviceid"));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return customer;
	}
	
	public Customer getCustomerByDeviceId(String deviceid) {
		Customer customer = new Customer();
		String sql = "select * from customers where customer_deviceid=?";
		Object[] params = { deviceid };
		ResultSet rs = sqlE.execSqlWithRS(sql, params);
		try {
			while (rs.next()) {
				customer.setCustomer_id(rs.getLong("customer_id"));
				customer.setCutomer_name(rs.getString("customer_name"));
				customer.setCustomer_pwd(rs.getString("customer_pwd"));
				customer.setCustomer_deviceid(rs.getString("customer_deviceid"));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return customer;
	}
	
	public Customer getCustomerByName(String username){
		Customer customer = new Customer();
		String sql = "select * from customers where customer_name=?";
		Object[] params = { username };
		ResultSet rs = sqlE.execSqlWithRS(sql, params);
		try {
			while (rs.next()) {
				customer.setCustomer_id(rs.getLong("customer_id"));
				customer.setCutomer_name(rs.getString("customer_name"));
				customer.setCustomer_pwd(rs.getString("customer_pwd"));
				customer.setCustomer_deviceid(rs.getString("customer_deviceid"));
				
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		
		return customer;
	}
	
	/**
	 * 新增顾客
	 */
	public boolean insertCustomer(Customer customer) {
		boolean flag = false;
		String sql = "insert into customers (customer_name, customer_pwd, customer_deviceid) values(?, ?, ?)";
		Object[] params = {customer.getCutomer_name(), customer.getCustomer_pwd(), customer.getCustomer_deviceid()};

		flag = sqlE.execSqlWithoutRS(sql, params);
		
		return flag;
	}
	
	/**
	 * 获取特定餐厅的顾客统计总数(暂时为取全部顾客数)
	 * 
	 * @return
	 */
	public int getCustomersCountByRestId(long restid) {
		int i = -1;
		String sql = "select count(*) from customers";
		Object[] params = {};
		ResultSet rs = sqlE.execSqlWithRS(sql, params);
		try {
			while (rs.next()) {
				i = rs.getInt(1);
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return i;
	}

	/**
	 * 根据顾客id删除顾客
	 * 
	 */
	public boolean deleteCustomer(long customerid) {
		boolean flag = false;
		String sql = "delete from customers where customer_id = ?";
		Object[] params = { customerid };
		if (sqlE.execSqlWithoutRS(sql, params)) {
			flag = true;
		}
		return flag;
	}
	
	/**
	 * 修改顾客信息
	 * 
	 */
	public boolean modifyCustomer(Customer customer) {
		boolean flag = false;
		String sql = "update customers set customer_name=?, customer_pwd=?, customer_deviceid=? where customer_id=?";
		Object[] params = {customer.getCutomer_name(), customer.getCustomer_pwd(), customer.getCustomer_deviceid(), customer.getCustomer_id()};
		flag = sqlE.execSqlWithoutRS(sql, params);
		
		return flag;
	}
	
	public boolean checkCustomerName(String name){
		boolean flag = false;
		String sql = "select customer_name from customers where customer_name = ?";
		Object[] params = { name };
		ResultSet rs = sqlE.execSqlWithRS(sql, params);
		try {
			while (rs.next()) {
				flag = true;
				return flag;
			}
		} catch (SQLException e) {
			e.printStackTrace();
		}
		return flag;
	}
}
