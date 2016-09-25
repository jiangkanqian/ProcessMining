package net.ProcessMining.logManagement.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import net.ProcessMining.base.dao.BaseDao;
import net.ProcessMining.base.service.impl.BaseServiceImpl;
import net.ProcessMining.logManagement.dao.LogDao;
import net.ProcessMining.logManagement.entity.Log;
import net.ProcessMining.logManagement.service.LogService;
import net.ProcessMining.util.HttpRequest;

@Service("LogService")
public class LogServiceImpl extends BaseServiceImpl<Log,String> implements LogService{
	
	@Autowired
	private LogDao logDao;
	private HttpRequest httpRequest;
	
	@Autowired
	public void setBaseDao(BaseDao<Log, String> baseDao) {
		// TODO Auto-generated method stub
		this.baseDao = logDao;
		this.logDao = (LogDao)logDao;
		this.httpRequest = new HttpRequest();
	}
	
	final String url = "http://master:50070/webhdfs/v1/ProcessMining/";
	final String LISTSTATUS = "op=LISTSTATUS";
	final String MKDIRS = "op=MKDIRS";
	final String CREATE = "op=CREATE";
	final String DELETE = "op=DELETE";
	
	@Override
	public boolean isDirectoryExist(String user,int type) {
		// TODO Auto-generated method stub
		String reqUrl;
		String locationStr = getLocationString(type);
		Map<Integer, String> map = new HashMap<Integer,String>();
		reqUrl = locationStr + "?" + LISTSTATUS;
		map = httpRequest.httpGet(reqUrl);
		if(map.containsKey(200)){
			return true;
		}
		else{
			return false;
		}
	}

	@Override
	public boolean makeDirecroty(String user, int type) {
		// TODO Auto-generated method stub		
		String reqUrl;
		String locationStr = getLocationString(type);
		reqUrl = locationStr + "?" + MKDIRS + "&user.name=" + user;
		int result = httpRequest.httpPut(reqUrl,null);
		if(result == 200){
			return true;
		}
		else{
			return false;
		}
	}
	
	public String getLocationString(int type){
		String locationStr;
		String typeStr = null;
		if(type == 0){
			typeStr = "RawLog";
		}
		else if(type == 1){
			typeStr = "EventLog";
		}
		locationStr = url + typeStr;
		return locationStr;
	}

	@Override
	public boolean uploadFile(String user, int type, String name,String path) {
		// TODO Auto-generated method stub
		String reqUrl;
		String locationStr = getLocationString(type);
		reqUrl = locationStr + "/" + name + "?" + CREATE + "&user.name=" + user + "&overwrite=true";
		int result = httpRequest.httpPut(reqUrl,path);
		if(result == 201){
			return true;
		}
		else{
			return false;
		}
	}

	@Override
	public boolean deleteFile(int type,String name) {
		String reqUrl;
		String locationStr = getLocationString(type);
		Map<Integer, String> map = new HashMap<Integer,String>();
		reqUrl = locationStr + "/" + name + "?" + DELETE;
		if(map.containsKey(200)){
			return true;
		}
		else{
			return false;
		}
	}

}
