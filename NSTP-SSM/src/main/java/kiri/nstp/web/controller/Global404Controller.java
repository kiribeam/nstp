package kiri.nstp.web.controller;

import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import kiri.nstp.exception.PageNotFoundException;

@RestController
public class Global404Controller {
	//TODO
	//@RequestMapping("*")
	public void notFound() {
		throw new PageNotFoundException();
	}

}
