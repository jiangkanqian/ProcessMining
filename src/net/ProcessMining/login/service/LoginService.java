package net.ProcessMining.login.service;

import java.util.List;
import java.util.Map;

import net.ProcessMining.user.entity.User;


public interface LoginService{
	public void checkUser(List<User> users,String email,String password,Map<String,Object> map);
}