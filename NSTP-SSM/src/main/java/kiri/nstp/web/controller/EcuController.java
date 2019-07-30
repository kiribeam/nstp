//Basic ECU Finder
package kiri.nstp.web.controller;

import java.util.List;
import java.util.regex.Pattern;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kiri.nstp.dto.EcuSearchMessage;
import kiri.nstp.exception.WrongInputException;
import kiri.nstp.pojo.EcuInfo;
import kiri.nstp.util.EcuSearchMessageFactory;
import kiri.nstp.web.service.EcuService;

@RestController
@RequestMapping("/ecuMessage")
public class EcuController {

	@Autowired
	private EcuService ecuMessageService;
	@Autowired
	private EcuSearchMessageFactory esmFac;
	
	@Value("${maxEcuCreateKazu}")
	private Integer max;

	@RequiresPermissions("readEcuInfo")	
	@RequestMapping(value="/getEcu", method=RequestMethod.GET)
	public List<EcuInfo> findEcu(@Valid EcuSearchMessage ecu,
			Errors errors){
		if(errors.hasErrors()) throw new WrongInputException();
		List<EcuInfo> list = ecuMessageService.getEcuList(ecu);
		return list;	
	}

	@RequiresPermissions("createDummyEcu")
	@RequestMapping(value= {"/createEcu", "/createEcu/{ecuid}"}, method=RequestMethod.POST)
	public String  createEcu(
			@PathVariable(value="ecuid", required=false) String customEcu,
			@RequestParam(value="number", defaultValue="1") Integer number) {
		if(customEcu!=null && customEcu.length()!=0 
				&& !Pattern.matches("^[A-Z|0-9]{32}$", customEcu)) 
			throw new WrongInputException();
		if(number < 0 || number > max)
				throw new WrongInputException();

		if(customEcu != null && customEcu.length()!=0) { 
			ecuMessageService.createCustomEcu(customEcu);
		}
		else{
			ecuMessageService.createEcu(number);
		}
		return "\"ok\"";
	}
	
	
	@RequiresPermissions("deleteEcu")
	@RequestMapping(value="/delEcu", method=RequestMethod.DELETE)
	public String delEcu(
			@RequestBody List<Integer> idList) {
		EcuSearchMessage esm = esmFac.genBlank();
		ecuMessageService.delEcu(idList, esm);
		return "\"success\"";
	}

}
