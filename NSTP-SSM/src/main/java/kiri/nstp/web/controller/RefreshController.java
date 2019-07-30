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
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.ResponseBody;

import kiri.nstp.dto.EcuSearchMessage;
import kiri.nstp.exception.WrongInputException;
import kiri.nstp.pojo.RefreshLog;
import kiri.nstp.util.EcuSearchMessageFactory;
import kiri.nstp.web.service.RefreshService;

@Controller
@RequestMapping("/refresh")
public class RefreshController {
	
	@Autowired
	RefreshService rs;
	@Autowired
	private EcuSearchMessageFactory esmFac;

	@Value("${refreshFilenameSuffix}")
	private String filesuffix;	

	@RequiresPermissions("readRefreshLog")
	@ResponseBody
	@RequestMapping(value="/getLog", method=RequestMethod.GET)
	public List<RefreshLog> getLog( @Valid EcuSearchMessage esm, Errors errors){
		if(errors.hasErrors()) throw new WrongInputException();
		List<RefreshLog> ls = rs.getLog(esm);
		return ls;
	}
	
	@RequiresPermissions("signRefreshFile")
	@RequestMapping(value="/upload/{id}", method=RequestMethod.POST)
	public ResponseEntity<byte[]> doRefresh(
			@PathVariable("id") Integer id,
			@RequestPart("file") byte[] uploadFile,
			@RequestParam("header") String header,
			@RequestParam("app") String app,
			@RequestParam("signInfo") String signInfo) {
		if(id == null || id < 0) 
			throw new WrongInputException();
		if(uploadFile == null || uploadFile.length <1)
			throw new WrongInputException();
		if(header == null || !Pattern.matches("^[A-F|0-9]{24}$", header))
			throw new WrongInputException();
		if(app == null || !Pattern.matches("^[A-F|0-9]*$", app))
			throw new WrongInputException();
		if(app.length() != 0 && app.length()%8 != 0) 
			throw new WrongInputException();
		else if(app.length() != 0){
			int appLen = Integer.parseInt(app.substring(4,8), 16);
			if(app.length() != appLen*16+8)
				throw new WrongInputException();
		}
		if(signInfo == null || !Pattern.matches("^[A-F|0-9]{52}$", signInfo)) 
				throw new WrongInputException();
		EcuSearchMessage esm = esmFac.genBlank();
		esm.setId(id);
		byte[] result = rs.generateSignedFile(uploadFile, header, app, signInfo, esm);
		HttpHeaders headers = new HttpHeaders();
		headers.setContentDispositionFormData("attachment", 
				SecurityUtils.getSubject().getPrincipal().toString() + filesuffix);
		headers.setContentType(MediaType.APPLICATION_OCTET_STREAM);
		return new ResponseEntity<byte[]>(result, headers, HttpStatus.CREATED);
	}

}
