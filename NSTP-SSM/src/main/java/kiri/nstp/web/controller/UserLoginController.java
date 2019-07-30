package kiri.nstp.web.controller;

import java.util.HashMap;
import java.util.Map;

import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authc.UsernamePasswordToken;
import org.apache.shiro.subject.Subject;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kiri.nstp.dto.UserSuite;
import kiri.nstp.exception.WrongInputException;

@RestController
public class UserLoginController {
	@RequestMapping(value="/user/username", method=RequestMethod.GET)
	public Map<String, Object> name(){
		Map<String, Object> map = new HashMap<>();
		map.put("username", SecurityUtils.getSubject().getPrincipal().toString());
		return map;
	}
	//by Shiro return 401
	@RequestMapping(value="/logout", method=RequestMethod.GET)
	public void logoff() {
	}
	@RequestMapping(value="/login", method=RequestMethod.POST)
	public String login(@Valid UserSuite us, Errors errors){
		if(errors.hasErrors()) throw new WrongInputException();
		Subject subject = SecurityUtils.getSubject();
		UsernamePasswordToken token = new UsernamePasswordToken(us.getUsername(), us.getPassword());
		subject.logout();
		subject.login(token);
		return "\"success\"";
	}

}
