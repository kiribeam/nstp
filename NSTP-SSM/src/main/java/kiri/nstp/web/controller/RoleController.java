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

import kiri.nstp.dto.RolePermissionBlock;
import kiri.nstp.dto.RoleSearchMessage;
import kiri.nstp.dto.UserGroupRoleBlock;
import kiri.nstp.exception.WrongInputException;
import kiri.nstp.util.RoleSearchMessageFactory;
import kiri.nstp.web.service.RolePermissionService;
import kiri.nstp.web.service.UserGroupRoleService;
import kiri.nstp.web.service.UserRoleService;

@RestController
@RequestMapping("/roleControl")
public class RoleController {
	@Autowired
	private RolePermissionService rpService;
	@Autowired
	private UserRoleService urService;
	@Autowired
	private UserGroupRoleService ugrService;
	
	@Autowired
	private RoleSearchMessageFactory rsmFac;
	
	//Caution ! Biggest Right
	@RequiresPermissions("deletePrivateRole")
	@RequestMapping(value = "/delRole", method = RequestMethod.DELETE)
	public String delRole(@RequestBody List<Integer> list) {
		if(list == null || list.isEmpty()) {
			throw new WrongInputException();
		}
		urService.delUserRole(list.get(0));
		return "\"success\"";
		
	}

	@RequiresPermissions("grantPrivateRole")
	@RequestMapping(value = "/addRole", method = RequestMethod.POST)
	public String addRole(@Valid UserGroupRoleBlock block, Errors errors){
		if(errors.hasErrors()) throw new WrongInputException();
		urService.addUserRole(block);
		return "\"success\"";
	}

	@RequiresPermissions("deleteGroupRole")
	@RequestMapping(value = "/delGroupRole", method = RequestMethod.DELETE)
	public String delGroupRole(@RequestBody List<Integer> list) {
		if(list == null || list.isEmpty()) {
			throw new WrongInputException();
		}
		RoleSearchMessage rsm = rsmFac.genBlank();
		rsm.setId(list.get(0));
		ugrService.delUserGroupRole(rsm);
		return "\"success\"";
	}

	@RequiresPermissions("grantGroupRole")
	@RequestMapping(value = "/addGroupRole", method = RequestMethod.POST)
	public String addGroupRole(@Valid UserGroupRoleBlock block, Errors errors){
		if(errors.hasErrors()) throw new WrongInputException();
		RoleSearchMessage rsm = rsmFac.genBlank();
		rsm.setUserGroup(block.getGname());
		ugrService.addUserGroupRole(rsm, block);
		return "\"success\"";
	}

	@RequiresPermissions("readRolePermission")
	@RequestMapping(value="/getRole/{page}", method=RequestMethod.GET)
	public List<RolePermissionBlock> getRole(@PathVariable("page") Integer page,
			@RequestParam("role") String role){
		if(role != null && !Pattern.matches("^[a-z|A-Z|0-9]{0,20}$", role))
			throw new WrongInputException();
		return rpService.list(page, role);
	}
	
	@RequiresPermissions("readGroupRole")
	@RequestMapping(value="/getGroupRole", method=RequestMethod.GET)
	public List<UserGroupRoleBlock> getGroupRole(@Valid RoleSearchMessage rsm,
		Errors errors){
		if(errors.hasErrors()) throw new WrongInputException();
		List<UserGroupRoleBlock> list = ugrService.getList(rsm);
		return list;	
	}
	
	@RequiresPermissions("readPrivateRole")
	@RequestMapping(value="/getUserRole", method=RequestMethod.GET)
	public List<UserGroupRoleBlock> getUserRole(@Valid RoleSearchMessage rsm,
		Errors errors){
		if(errors.hasErrors()) throw new WrongInputException();
		List<UserGroupRoleBlock> list = urService.getList(rsm);
		return list;	
	}
	

}
