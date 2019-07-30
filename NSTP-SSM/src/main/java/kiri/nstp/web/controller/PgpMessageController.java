package kiri.nstp.web.controller;

import java.util.List;

import javax.validation.Valid;

import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.bind.annotation.RestController;

import kiri.nstp.dto.PgpSearchMessage;
import kiri.nstp.exception.WrongInputException;
import kiri.nstp.pojo.UserPgp;
import kiri.nstp.util.PgpSearchMessageFactory;
import kiri.nstp.web.service.PgpService;

@RestController
@RequestMapping("/pgp")
public class PgpMessageController {
	

	@Value("${pgpMaxListKazu}")
	private Integer max;
	
	@Autowired
	private PgpService pgpService;
	@Autowired
	private PgpSearchMessageFactory psmFac;
	
	@RequiresPermissions("readPgpPublicKey")
	@RequestMapping(value="/getPk", method=RequestMethod.GET)
	public List<UserPgp> getPks(
			@Valid PgpSearchMessage psm, Errors errors){
		if(errors.hasErrors()) {
			throw new WrongInputException();
		}
		List<UserPgp> ls = pgpService.getPgpRecord(psm);
		return ls;
	}
	
	@RequiresPermissions("untrustPgpPublicKey")
	@RequestMapping(value="/untrust", method=RequestMethod.PUT)
	public String untrustPk(@RequestBody List<Integer> list) {
		if(list.size()>max) throw new WrongInputException();
		PgpSearchMessage psm = psmFac.genBlank();
		pgpService.pgpUntrust(psm, list);
		return "\"success\"";
	}
	
	@RequiresPermissions("deletePgpPublicKey")
	@RequestMapping(value="/del", method=RequestMethod.DELETE)
	public String delPk(@RequestBody List<Integer> list) {
		if(list.size()>max) throw new WrongInputException();
		PgpSearchMessage psm = psmFac.genBlank();
		pgpService.pgpDelete(psm, list);
		return "\"success\"";
	}
	
	@RequiresPermissions("trustPgpPublicKey")
	@RequestMapping(value="/trust", method=RequestMethod.PUT)
	public String trustPk(@RequestBody List<Integer> list) {
		if(list.size()>max) throw new WrongInputException();
		PgpSearchMessage psm = psmFac.genBlank();
		pgpService.pgpTrust(psm, list);
		return "\"success\"";
	}
	
	@RequiresPermissions("uploadPgpPublicKey")
	@RequestMapping(value="/uploadPk", method=RequestMethod.POST)
	public String storePk(@RequestPart("file") byte[] file) {
		PgpSearchMessage psm = psmFac.genBlank();
		String username = SecurityUtils.getSubject().getPrincipal().toString();
		psm.setUsername(username);
		pgpService.pgpUploadPublicKey(psm, file);
		return "\"success\"";
	}
	
	@RequiresPermissions("revokePgpPublicKey")
	@RequestMapping(value="/revoke/{id}", method=RequestMethod.DELETE)
	public String revokePk(@PathVariable("id") Integer id) {
		if(id ==null || id < 0) throw new WrongInputException();
		PgpSearchMessage psm = psmFac.genBlank();
		psm.setId(id);
		pgpService.pgpDropPublicKey(psm);
		return "\"success\"";
	}
}
