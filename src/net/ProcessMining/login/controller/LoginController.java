package net.ProcessMining.login.controller;

import java.lang.annotation.Annotation;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;













import net.ProcessMining.login.service.LoginService;





import net.ProcessMining.user.entity.User;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.criterion.Restrictions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;

@Controller
@RequestMapping("/login")
public class LoginController{
	@Autowired
	private LoginService loginService;
	
	@Autowired
    private SessionFactory sessionFactory;

	@RequestMapping(value = "Verification", method = RequestMethod.POST)
	// 请求url地址映射
	public String Verification(
			@RequestParam(value = "email") String email,
			@RequestParam(value = "password") String password,
			@RequestParam(value = "checkcode") String checkcode,
			HttpServletRequest req, ModelMap map) throws Exception{
		String code = (String) req.getSession().getAttribute("rand")
				.toString();
		if (!checkcode.equals(code)) {
			map.put("success", false);
			map.put("msg", "checkcodeNotRight");
			map.put("email", email);
			return "/login";
		}
		List<User> users = new ArrayList<User>();
		Session session = sessionFactory.getCurrentSession();
		Criteria criteria = session.createCriteria(User.class);
		criteria.add(Restrictions.eq("email", email));
		users = criteria.list();
		loginService.checkUser(users, email, password, map);
//		if (map.get("root").toString().equals("userloginsuccessfully")) {
//			req.getSession().setAttribute("root", "userloginsuccessfully");
//		}
		
		return "/login";
	}
	
}