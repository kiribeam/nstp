package kiri.nstp.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import kiri.nstp.dto.EcuSearchMessage;
import kiri.nstp.exception.WrongInputException;
import kiri.nstp.pojo.EcuKey;
import kiri.nstp.pojo.EcuKeyChange;
import kiri.nstp.web.service.EcuKeyService;

@RestController
@RequestMapping("/ecuKey")
public class EcuKeyController {
	
	@Autowired
	EcuKeyService ecuKeyService;

	@RequiresPermissions("readEcuKey")
	@RequestMapping(value="/getEcuKey", method=RequestMethod.GET)
	public List<EcuKey> getEcuKeys(@Valid EcuSearchMessage esm, Errors errors){
		if(errors.hasErrors()) throw new WrongInputException();
		return ecuKeyService.getEcuKeys(esm);
	}
	
	@RequiresPermissions("readEcuKeyChange")
	@RequestMapping(value="/getKeyChange", method=RequestMethod.GET)
	public List<EcuKeyChange> getKeyChange(@Valid EcuSearchMessage esm, Errors errors){
		if(errors.hasErrors()) throw new WrongInputException();
		return ecuKeyService.getKeyChange(esm);
		
	}
	
	
}
