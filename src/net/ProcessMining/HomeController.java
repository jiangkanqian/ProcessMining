package net.ProcessMining;

import javax.servlet.http.HttpServletRequest;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;


@Controller
public class HomeController {
	
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String home(HttpServletRequest request) {
		return "/home";
	}
	
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String login(HttpServletRequest request) {
		request.getSession().invalidate();
		return "/login";
	}
	
	@RequestMapping(value = "/checkCode", method = RequestMethod.GET)
	public String checkCode(HttpServletRequest request) {
		return "/checkCode";
	}
	
}