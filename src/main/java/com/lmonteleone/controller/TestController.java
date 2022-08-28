package com.lmonteleone.controller;

import com.lmonteleone.model.ResponseObject;
import com.lmonteleone.service.TestService;
import com.lmonteleone.utily.Constants;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RequestMapping("/api")
@RestController
public class TestController {
	
	@Autowired
	TestService testService;
	
	@RequestMapping(value = "/test-get-method", method = RequestMethod.GET, produces = "application/json")
	public ResponseObject testGetMethod() {
		try {
			return testService.testHttpMessageSignature(Constants.HttpMethods.GET);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value = "/test-delete-method", method = RequestMethod.DELETE, produces = "application/json")
	public ResponseObject testDeleteMethod() {
		try {
			return testService.testHttpMessageSignature(Constants.HttpMethods.DELETE);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value = "/test-post-method", method = RequestMethod.POST, produces = "application/json")
	public ResponseObject testPostMethod() {
		try {
			return testService.testHttpMessageSignature(Constants.HttpMethods.POST);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value = "/test-put-method", method = RequestMethod.PUT, produces = "application/json")
	public ResponseObject testPutMethod() {
		try {
			return testService.testHttpMessageSignature(Constants.HttpMethods.PUT);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}
	
	@RequestMapping(value = "/test-patch-method", method = RequestMethod.PATCH, produces = "application/json")
	public ResponseObject testPatchMethod() {
		try {
			return testService.testHttpMessageSignature(Constants.HttpMethods.PATCH);
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}
	}

}
