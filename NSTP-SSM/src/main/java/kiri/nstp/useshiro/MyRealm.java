package kiri.nstp.useshiro;

import java.util.HashSet;

import org.apache.shiro.authc.AuthenticationException;
import org.apache.shiro.authc.AuthenticationInfo;
import org.apache.shiro.authc.AuthenticationToken;
import org.apache.shiro.authc.SimpleAuthenticationInfo;
import org.apache.shiro.authz.AuthorizationInfo;
import org.apache.shiro.authz.SimpleAuthorizationInfo;
import org.apache.shiro.realm.AuthorizingRealm;
import org.apache.shiro.subject.PrincipalCollection;
import org.apache.shiro.util.ByteSource;
import org.springframework.beans.factory.annotation.Autowired;

import kiri.nstp.pojo.Permission;
import kiri.nstp.pojo.Role;
import kiri.nstp.pojo.User;
import kiri.nstp.util.SpringBeanFactory;
import kiri.nstp.web.service.PermissionService;
import kiri.nstp.web.service.RoleService;
import kiri.nstp.web.service.UserService;

public class MyRealm extends AuthorizingRealm{

	@Autowired
	private UserService userService;
	@Autowired
	private RoleService roleService;
	@Autowired
	private PermissionService perService;

	@Override
	protected AuthorizationInfo doGetAuthorizationInfo(PrincipalCollection principals) {
		String username = (String) principals.getPrimaryPrincipal();
		if(roleService == null) roleService = SpringBeanFactory.getBean(RoleService.class);
		if(perService == null) perService = SpringBeanFactory.getBean(PermissionService.class);
		HashSet<String> roles = new HashSet<>();
		HashSet<String> permissions = new HashSet<>();
		for(Role r : roleService.getRoleByUserName(username)) {
			roles.add(r.getRname());
		}
		for(Permission p : perService.getByUsername(username)) {
			permissions.add(p.getPname());
		}
		SimpleAuthorizationInfo s = new SimpleAuthorizationInfo();
		s.setStringPermissions(permissions);
		s.setRoles(roles);
		return s;
	}
	@Override
	protected AuthenticationInfo doGetAuthenticationInfo(AuthenticationToken token) throws AuthenticationException {
		String username = token.getPrincipal().toString();
		if(userService == null) userService = SpringBeanFactory.getBean(UserService.class);
		User user = userService.getByName(username);
		String pass = user.getPassword();
		String salt = user.getSalt();
		SimpleAuthenticationInfo au = new 
				SimpleAuthenticationInfo(username, pass, ByteSource.Util.bytes(salt), getName());
		return au;
	}

}
