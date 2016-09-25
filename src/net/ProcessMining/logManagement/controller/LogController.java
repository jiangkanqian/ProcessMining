package net.ProcessMining.logManagement.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.log4j.Logger;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;


import net.ProcessMining.base.dao.util.CommonConditionQuery;
import net.ProcessMining.logManagement.entity.Log;
import net.ProcessMining.logManagement.service.LogService;
import net.ProcessMining.user.entity.User;
import net.ProcessMining.base.dao.util.ConditionQuery;
import net.ProcessMining.base.dao.util.OrderBy;
import java.util.UUID;
import com.fasterxml.jackson.databind.ObjectMapper;


@Controller
@RequestMapping("/log")
public class LogController {
	
	private static final Logger logger = Logger.getLogger(LogController.class);
	
	@Autowired
	private LogService logService;
	
	@RequestMapping(value = "getLog", method = RequestMethod.GET)
	public @ResponseBody
	void getLog(HttpServletRequest request,HttpServletResponse response) throws Exception{
		List<Log> log = new ArrayList<Log>();
//		String user = request.getSession().getAttribute("user").toString();
		ConditionQuery query = new ConditionQuery();
		OrderBy orderBy = new OrderBy();
//		query.add(Restrictions.eq("user", user));
		log = logService.list(query, orderBy);
		if (logger.isDebugEnabled()) {
			logger.info("log=" + log);
		}
		Map<String, Object> map = new HashMap<String, Object>();
		ObjectMapper mapper = new ObjectMapper();
		map.put("rows", log);
		map.put("total",log.size());
		System.out.println(mapper.writeValueAsString(map));
//		return mapper.writeValueAsString(map);
		response.setContentType("application/json");
		try {
			PrintWriter out = response.getWriter();
			out.write(mapper.writeValueAsString(map));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	@RequestMapping(value = "uploadLog", method = RequestMethod.POST)
	public @ResponseBody
	Object uploadLog(HttpServletRequest request,
			@RequestParam(value = "path") String path,
			@RequestParam(value = "type") Integer type,
			@RequestParam(value = "isShare", required = false) Integer isShare
			) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		Log log = new Log();
		String userID = request.getSession().getAttribute("user").toString();
		String[] splitPath = path.split("/");
		String name = splitPath[splitPath.length-1].split(".")[0];
		String format = splitPath[splitPath.length-1].split(".")[1];
		String uuid = UUID.randomUUID().toString();
		log.setId(uuid);
		log.setName(name);
		log.setType(type);
		log.setFormat(format);
		User user = new User();
		user.setID(userID);
//		log.setUser(user);
		log.setIsShare(isShare);
		if(!logService.isDirectoryExist(userID, type)){
			logService.makeDirecroty(userID, type);
		}
		if(logService.uploadFile(userID, type, uuid, path)){
			map.put("saveHDFS", true);
			if (logService.save(log) != null) {
				map.put("saveDataBase", true);
			}
			else{
				map.put("saveDataBase", false);
			}
		}
		else{
			map.put("saveHDFS", false);
		}
		
		return map;
	}
	
	@RequestMapping(value = "deleteLog", method = RequestMethod.POST)
	public @ResponseBody
	Object deleteLog(HttpServletRequest request,
			@RequestParam(value = "ID") String id,
			@RequestParam(value = "type") Integer type
			) throws Exception{
		Map<String, Object> map = new HashMap<String, Object>();
		if(logService.deleteFile(type, id)){
			map.put("deleteHDFS", true);
			logService.deleteByPK(id);
		}
		else{
			map.put("deleteHDFS", false);
		}
		return map;
	}
	
}
