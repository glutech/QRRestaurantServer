package com.qrrest.service;

import com.qrrest.dao.CustomersDao;
import com.qrrest.model.Customer;

public class CustomerService {
	CustomersDao cdao;
	
	public CustomerService(){
		cdao = new CustomersDao();
	}
	
	public Customer getCustomerByDeviceId(String deviceid){
		Customer customer = null;
		customer = cdao.getCustomerByDeviceId(deviceid);
		return customer;
	}
	
	public void createCustomerWithDeviceId(String deviceid){
		Customer cust = new Customer();
		cust.setCutomer_name(deviceid);
		cust.setCustomer_pwd("0");
		cust.setCustomer_deviceid(deviceid);
		cdao.insertCustomer(cust);
	}
	
	public int validateAccount(String username, String pwd){

		Customer cust = this.getCustomerByName(username);
		
		//查无此用户返回0，正确返回1，密码错误返回2
		if(cust.getCustomer_id() == 0){
			return 0;
		}else if(pwd.equals(cust.getCustomer_pwd())){
			return 1;
		}else{
			return 2;
		}
	}
	
	public Customer getCustomerByName(String username){
		return cdao.getCustomerByName(username);
	}
	
	public boolean validateCustomerName(String username){
		return !cdao.checkCustomerName(username);
	}
	
	public boolean createCustomerWithName(String username, String password, String device_id){
		Customer cust = new Customer();
		cust.setCutomer_name(username);
		cust.setCustomer_pwd(password);
		cust.setCustomer_deviceid(device_id);
		
		boolean flag = cdao.insertCustomer(cust);

		return flag;
	}
	
	public Customer updateCustomer(Customer olduser, Customer newuser){
		Customer realcust = cdao.getCustomerById(olduser.getCustomer_id());
		if(olduser.getCustomer_pwd().equals(realcust.getCustomer_pwd())){
			if(cdao.modifyCustomer(newuser)){
				return newuser;
			}
		}
		return olduser;
	}
}
