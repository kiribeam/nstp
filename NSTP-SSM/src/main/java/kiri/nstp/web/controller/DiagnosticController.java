package kiri.nstp.web.controller;

import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kiri.nstp.dto.EcuSearchMessage;
import kiri.nstp.exception.WrongInputException;
import kiri.nstp.pojo.DiagnosticLog;
import kiri.nstp.util.EcuSearchMessageFactory;
import kiri.nstp.web.service.DiagnosticService;

@RestController
@RequestMapping("/diagnostic")
public class DiagnosticController {
	
	@Autowired
	DiagnosticService diagService;
	@Autowired
	EcuSearchMessageFactory esf;
	
	@RequiresPermissions("readDiagLog")
	@RequestMapping(value= {"/getLog"}, method=RequestMethod.GET)
	public List<DiagnosticLog> go(@Valid EcuSearchMessage esm, Errors errors){
		if(errors.hasErrors()) throw new WrongInputException();
		List<DiagnosticLog> list = diagService.getLog(esm);
		return list;	
	}

	@RequiresPermissions("doDiagExec")
	@RequestMapping(value = "exec/{id}/{head}/{rand}/{keyId}", method=RequestMethod.PUT)
	public Map<String, String> ret(
			@PathVariable("id") Integer id, 
			@PathVariable("head") String head,
			@PathVariable("keyId") Integer keyId,
			@PathVariable("rand") String rand){
		if(!Pattern.matches("^[0-9|A-F]{2}$", head)) 
			throw new WrongInputException();
		if(keyId<1 || keyId>20)
			throw new WrongInputException();
		if(!Pattern.matches("^[0-9|A-F]{30}$", rand))
			throw new WrongInputException();
		EcuSearchMessage esm = esf.genBlank();
		esm.setId(id);
		esm.setKeyId(keyId);
		Map<String, String> map = diagService.diagnostic(esm, head, rand);
		return map;
	}
}
