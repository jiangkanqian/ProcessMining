package net.ProcessMining.logManagement.service;

import net.ProcessMining.base.service.BaseService;
import net.ProcessMining.logManagement.entity.Log;

public interface LogService extends BaseService<Log,String>{
	
	public boolean isDirectoryExist(String user,int type);
	
	public boolean makeDirecroty(String user,int type);
	
	public boolean uploadFile(String user,int type,String name,String path);
	
	public boolean deleteFile(int type,String name);
	
}
