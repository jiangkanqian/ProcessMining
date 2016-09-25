package net.ProcessMining.login.service.impl;

import java.util.List;
import java.util.Map;

import net.ProcessMining.login.service.LoginService;
import net.ProcessMining.user.entity.User;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


@Service
@Transactional
public class LoginServiceImpl implements LoginService{

	@Override
	public void checkUser(List<User> users, String email, String password,
			Map<String, Object> map) {
		// TODO Auto-generated method stub
		String userPassword = users.get(0).getPassword();
		
		if(users.size() == 0){
			map.put("root", "userNotExist");
			map.put("email", email);
			map.put("success", true);
		}
		else{
			if( userPassword==null||!userPassword.equals(password) ){
				map.put("root", "userPasswordNotRight");
				map.put("email", email);
				map.put("success", true);
			}
			else{
				map.put("root", "userloginsuccessfully");
				map.put("success", true);
			}
		}
		
	}
	
}