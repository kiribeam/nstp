package kiri.nstp.web.controller;

import java.util.List;
import java.util.regex.Pattern;

import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import kiri.nstp.dto.EcuSearchMessage;
import kiri.nstp.exception.WrongInputException;
import kiri.nstp.pojo.OfflineLog;
import kiri.nstp.util.EcuSearchMessageFactory;
import kiri.nstp.web.service.OfflineDownService;

@Controller
@RequestMapping("/offlineDown")
public class OfflineDownController {
	@Autowired
	private OfflineDownService offService;
	@Autowired
	private EcuSearchMessageFactory esmFac;
	@Value("${offlineDownFilenameSuffix}")
	private String filesuffix;
	@Value("${offlineDownMaxEcuSelectedKazu}")
	private Integer maxNum;
	
	@RequiresPermissions("readOfflineLog")
	@ResponseBody
	@RequestMapping(value="/getLog", method=RequestMethod.GET)
	public List<OfflineLog> getLog(@Valid EcuSearchMessage esm, Errors errors){
		if(errors.hasErrors()) throw new WrongInputException();
		List<OfflineLog> ls = offService.getLog(esm);
		return ls;
	}

	@RequiresPermissions("changeEcuKey")
	@RequestMapping(value={"/getBin/{authId}/{keyId}/{keyFlag}", 
			"/getBin/{authId}/{keyId}/{keyFlag}/{inputKey}"}, method=RequestMethod.POST)
	public ResponseEntity<byte[]> generateOfflineFile(@RequestParam List<Integer> items,
			@PathVariable(value="inputKey", required=false) String inputKey,
			@PathVariable("authId") Integer authId,
			@PathVariable("keyId") Integer keyId,
			@PathVariable("keyFlag") String keyFlag){
		if(items.size()>maxNum) throw new WrongInputException();
		if(keyId < 1 || keyId > 15) 
			throw new WrongInputException(); 
		if(authId < 1 || authId > 15)  
			throw new WrongInputException();
		if(!Pattern.matches("^[0|1]{6}$", keyFlag)) 
			throw new WrongInputException();
		if(inputKey != null && !inputKey.equals("") && !Pattern.matches("^[A-F|0-9]{32}$", inputKey)) 
			throw new WrongInputException();
		EcuSearchMessage esm = esmFac.genBlank();
		byte[] fileBytes = offService.registKey(esm, items, authId, keyId, keyFlag, inputKey);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentDispositionFormData("attachment", 
				SecurityUtils.getSubject().getPrincipal().toString() + filesuffix);
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		return new ResponseEntity<byte[]>(fileBytes, headers, HttpStatus.CREATED);
	}
	
	@RequiresPermissions("verifyEcuKey")
	@ResponseBody
	@RequestMapping(value="/verifyKey/{id}", method=RequestMethod.PUT)
	public String verify(@PathVariable("id") Integer id) {
		EcuSearchMessage esm = esmFac.genBlank();
		esm.setId(id);
		offService.verifyKey(esm);
		return "\"Verify success\"";
	}
}
