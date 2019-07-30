package kiri.nstp.web.controller;

import java.util.List;
import java.util.regex.Pattern;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kiri.nstp.dto.EcuGroupBlock;
import kiri.nstp.dto.EcuSearchMessage;
import kiri.nstp.exception.WrongInputException;
import kiri.nstp.util.EcuSearchMessageFactory;
import kiri.nstp.web.service.EcuGroupService;

@RestController
@RequestMapping("/ecuGroup")
public class EcuGroupController {
	
	@Autowired
	private  EcuGroupService egService;
	@Autowired
	private EcuSearchMessageFactory esmFac;
	
	
	@RequiresPermissions("readEcuGroup")
	@RequestMapping(value="/getEcuGroup", method=RequestMethod.GET)
	public List<EcuGroupBlock> getGroupBlock(@Valid EcuSearchMessage esm, 
			Errors errors){
		if(errors.hasErrors()) throw new WrongInputException();
		return egService.list(esm);
	}
	
	@RequiresPermissions("removeEcuFromGroup")
	@RequestMapping(value="/del", method=RequestMethod.DELETE)
	public String del(@RequestBody List<Integer> list) {
		if(list == null || list.isEmpty())
			throw new WrongInputException();
		EcuSearchMessage esm = esmFac.genBlank();
		esm.setId(list.get(0));
		egService.del(esm);
		return "\"success\"";
	}
	
	@RequiresPermissions("addEcuIntoGroup")
	@RequestMapping(value="/add", method=RequestMethod.POST)
	public String add(@RequestParam("id") Integer id,
			@RequestParam("gname") String gname) {
		if(id == null || id < 0) 
			throw new WrongInputException();
		if(gname == null || !Pattern.matches("^[A-Z|a-z|0-9]{1,32}$", gname))
			throw new WrongInputException();
		EcuSearchMessage esm = esmFac.genBlank();
		esm.setUserGroup(gname);
		esm.setId(id);
		egService.add(esm);
		return "\"success\"";
		
	}

}
