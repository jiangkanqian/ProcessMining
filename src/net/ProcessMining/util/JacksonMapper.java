package net.ProcessMining.util;

import com.fasterxml.jackson.databind.ObjectMapper;

public class JacksonMapper {
	private static ObjectMapper mapper = new  ObjectMapper();
	
	private JacksonMapper(){}
	
	public static ObjectMapper newInstance(){
		return mapper;
	}
}
