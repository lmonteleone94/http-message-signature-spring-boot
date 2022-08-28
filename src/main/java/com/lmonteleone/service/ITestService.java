package com.lmonteleone.service;


import com.lmonteleone.model.ResponseObject;
import com.lmonteleone.utily.Constants.HttpMethods;

import okhttp3.Request;
import okhttp3.RequestBody;

public interface ITestService {
	
	public ResponseObject testHttpMessageSignature(HttpMethods method) throws Exception;
	
	public Request selectHttpMethod(HttpMethods method, String digest, String authHeader, RequestBody body);
}
