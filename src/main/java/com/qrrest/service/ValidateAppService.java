package com.qrrest.service;

import com.qrrest.util.QrConstants;

public class ValidateAppService {
	public ValidateAppService(){}
	
	public static boolean validateAppToken(String apptoken){
		boolean result = false;
		if(apptoken.equals(QrConstants.ACCESS_TOKEN))
			result = true;
		return result;
	}
}
