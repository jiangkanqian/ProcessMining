package net.ProcessMining.util;

import java.io.Serializable;

import org.apache.log4j.Logger;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class JacksonUtil {


	private static final Logger logger = Logger.getLogger(JacksonUtil.class);
	
	public static String beanToString(Serializable bean,Include include){
		String str="";
		ObjectMapper mapper = JacksonMapper.newInstance();
		if(logger.isDebugEnabled()){
			logger.info("JsonInclude = "+include);
		}
		mapper.setSerializationInclusion(include);
		try {
			str = mapper.writeValueAsString(bean);
			if(logger.isDebugEnabled()){
				logger.info("ResultStr = "+str);
			}
		} catch (JsonProcessingException e) {
			if(logger.isDebugEnabled()){
				logger.error("Error.message = "+e.getMessage());
			}
			e.printStackTrace();
		}
		return str;
	}
	
	
	public static String beanToString(Serializable bean){
		return beanToString(bean, Include.NON_NULL);
	}

}
