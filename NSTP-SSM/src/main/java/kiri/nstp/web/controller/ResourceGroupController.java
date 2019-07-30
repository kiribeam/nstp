package kiri.nstp.web.controller;

import java.util.List;
import java.util.regex.Pattern;

import javax.validation.Valid;

import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.Errors;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import kiri.nstp.exception.WrongInputException;
import kiri.nstp.pojo.ResourceGroup;
import kiri.nstp.web.service.ResourceGroupService;

@RestController
public class ResourceGroupController {

	@Autowired
	private ResourceGroupService rgService;

	@RequiresPermissions("readResourceGroup")
	@RequestMapping(value="/getResourceGroup/{page}", method=RequestMethod.GET)
	public List<ResourceGroup> list(@PathVariable("page") Integer page,
			@RequestParam("rGroup") String group){
		if(group != null && !Pattern.matches("^[a-z|A-Z|0-9]{0,32}$", group))
			throw new WrongInputException();
		return rgService.list(group, page);
	}
	
	@RequiresPermissions("createResourceGroup")
	@RequestMapping(value="/addResourceGroup", method=RequestMethod.POST)
	public String add(@Valid ResourceGroup rg, Errors errors){
		if(errors.hasErrors()) throw new WrongInputException();
		rgService.add(rg);
		return "\"success\"";
	}
	
	@RequiresPermissions("deleteResourceGroup")
	@RequestMapping(value="/delResourceGroup", method=RequestMethod.DELETE)
	public String del(@RequestBody List<Integer> list) {
		if(list == null || list.isEmpty())
			throw new WrongInputException();
		rgService.del(list.get(0));
		return "\"success\"";
	}

}
