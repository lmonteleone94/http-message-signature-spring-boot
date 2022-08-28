package com.lmonteleone.model;

import java.util.ArrayList;

public class Signature {
	
	public boolean valid;
	
	public ArrayList<String> headers;
	
	public int iteration_count;
	
	public String key_id;
	
	public String signature;
	
	public boolean resign_required;
	
	public String algorithm;

}
