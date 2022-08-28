package com.lmonteleone.service;

import java.util.LinkedHashMap;

import org.json.JSONObject;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.lmonteleone.model.ResponseObject;
import com.lmonteleone.utily.Constants;
import com.lmonteleone.utily.Constants.HttpMethods;
import com.lmonteleone.utily.HttpMessageSignature;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

@Service
public class TestService implements ITestService {

	public ResponseObject testHttpMessageSignature(HttpMethods method) throws Exception {
		OkHttpClient client = new OkHttpClient();
		Request request;
		Response response;

		LinkedHashMap<String, String> header;
		String signingString;
		String signatureBase64;
		String authHeader;

		// set header
		String payload;
		if (method.name().equals(Constants.HttpMethods.GET.name())
				|| method.name().equals(Constants.HttpMethods.DELETE.name())) {
			payload = Constants.EMPTY_BODY;
		} else {
			payload = Constants.BODY;
		}
		header = HttpMessageSignature.getMessageHeader(Constants.HOST, Constants.DATE, payload);

		System.out.println(Constants.SEPARETOR);
		System.out.println(method.name() + " Request Logs\n" + Constants.SEPARETOR + "\n");
		System.out.println("Request Payload:");
		System.out.println(payload != "" ?  new JSONObject(payload).toString(4) + "\n" : "Empty" + "\n");

		// compute Signing String
		signingString = HttpMessageSignature.createSigningString(method.name(), header, Constants.PATH);
		System.out.println("Signing String Header:\n" + signingString + "\n");

		// compute Signature
		signatureBase64 = HttpMessageSignature.createSignature(signingString);

		// compute Auth Header
		authHeader = HttpMessageSignature.createAuthHeader(Constants.KEY_ID, Constants.ALGOTITHM, header,
				signatureBase64);
		System.out.println("Authorization header:\n" + authHeader + "\n");

		// make Http Request
		RequestBody body = RequestBody.create(MediaType.get("application/json; charset=utf-8"), Constants.BODY);
		request = selectHttpMethod(method, header.get("Digest"), authHeader, body);
		System.out.println("Request Header:\n" + request.headers());

		response = client.newCall(request).execute();
		System.out.println("Response Code: " + response.code());
		
		//Parse string json response to java object
		JSONObject jsonResponse = new JSONObject(response.body().string());
		
		System.out.println("Response:\n" + jsonResponse.toString(4) + "\n");
		System.out.println("End of " + method.name() + " Request\n\n");

		Gson gson = new GsonBuilder().create();
		return gson.fromJson(jsonResponse.toString(4), ResponseObject.class);
	}

	public Request selectHttpMethod(HttpMethods method, String digest, String authHeader, RequestBody body) {
		switch (method) {
		case GET:
			return new Request.Builder().url(Constants.URL).get().header("Accept", Constants.CONTENT_TYPE)
					.header("Host", Constants.HOST).header("Date", Constants.DATE).header("Digest", digest)
					.header("Authorization", authHeader).build();

		case DELETE:
			return new Request.Builder().url(Constants.URL).delete().header("Accept", Constants.CONTENT_TYPE)
					.header("Host", Constants.HOST).header("Date", Constants.DATE).header("Digest", digest)
					.header("Authorization", authHeader).build();

		case POST:
			return new Request.Builder().url(Constants.URL).post(body).header("Accept", Constants.CONTENT_TYPE)
					.header("Host", Constants.HOST).header("Date", Constants.DATE).header("Digest", digest)
					.header("Authorization", authHeader).build();

		case PUT:
			return new Request.Builder().url(Constants.URL).put(body).header("Accept", Constants.CONTENT_TYPE)
					.header("Host", Constants.HOST).header("Date", Constants.DATE).header("Digest", digest)
					.header("Authorization", authHeader).build();

		case PATCH:
			return new Request.Builder().url(Constants.URL).patch(body).header("Accept", Constants.CONTENT_TYPE)
					.header("Host", Constants.HOST).header("Date", Constants.DATE).header("Digest", digest)
					.header("Authorization", authHeader).build();

		default:
			return null;
		}
	}
}
