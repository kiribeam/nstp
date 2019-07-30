package kiri.nstp.web.controller;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kiri.nstp.dto.UserSuite;
import kiri.nstp.exception.GoToLoginException;
import kiri.nstp.exception.WrongInputException;
import kiri.nstp.web.service.UserService;

@RestController
public class UserRegistController {
	@Autowired
	private UserService userService;
	
	
	@RequestMapping(value="/regist", method = RequestMethod.POST)
	public void regist(@Valid UserSuite us, Errors errors){
		if(errors.hasErrors()) throw new WrongInputException();
		userService.regist(us);
		throw new GoToLoginException();
	}
	
	@RequestMapping(value="/changePass", method = RequestMethod.PUT)
	public void changePass() {
		
	}

	@RequestMapping(value="/changePhone", method = RequestMethod.PUT)
	public void changePhone() {
		;
	}
	@RequestMapping(value="/changeMail", method = RequestMethod.PUT)
	public void changeMail() {
		
	}

}
