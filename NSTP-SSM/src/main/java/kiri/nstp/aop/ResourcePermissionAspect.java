package kiri.nstp.aop;

import java.lang.reflect.Method;
import java.util.HashSet;
import java.util.Set;

import org.apache.shiro.SecurityUtils;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.Signature;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import kiri.nstp.dto.ResourceSearchMessage;
import kiri.nstp.exception.NoResourcePermissionException;
import kiri.nstp.pojo.ResourceGroup;
import kiri.nstp.web.service.ResourceGroupService;

@Aspect
@Component("resourcePermissionAspect")
public class ResourcePermissionAspect {
	@Autowired
	ResourceGroupService rgService;

	@Pointcut("@annotation(kiri.nstp.aop.ResourcePermission)")
	public void pointcut() {}
	@Value("${rootGroup}")
	private String rootGroup;
	
	@Around("pointcut()")
	public Object testBefore(ProceedingJoinPoint pjp){
		Signature signature = pjp.getSignature();
		MethodSignature ms = (MethodSignature) signature;
		Method method = ms.getMethod();
		//System.out.println(method.getDeclaringClass() + ":" + method.getName());
		Method root;
		try {
			root = pjp.getTarget().getClass().getDeclaredMethod(ms.getName(), method.getParameterTypes()); 
		}catch(Exception e) {
			throw new RuntimeException();
		}
		ResourcePermission rp = root.getAnnotation(ResourcePermission.class);
		//if(rp == null) System.out.println("null rp");
		//System.out.println(rp.permission() + ":" + SecurityUtils.getSubject().getPrincipal()); 
		Object[] args = pjp.getArgs();
		for(Object obj : args) {
			setRestrict(obj, rp.permission());
		}
		try{
			return pjp.proceed(args);
		}catch(Throwable t) {
			throw new RuntimeException(t);
		}
	}
	
	private void setRestrict(Object obj, String permission){
		if(!(obj instanceof ResourceSearchMessage)) return;
		ResourceSearchMessage rsm = (ResourceSearchMessage) obj;
		String username = SecurityUtils.getSubject().getPrincipal().toString();
		String searchName = rsm.getUsername();
		Set<String> searchGroupSet = rsm.getUserGroupSet();
		String searchGroup = rsm.getUserGroup();
		//Phase 1 Single Owner? Then all will be ok!
		if(searchName != null && searchName.length()>0 && searchName.equals(username)) {
			if(searchGroup!= null && searchGroup.length()>0) {
				rsm.setUserGroupSet(new HashSet<>());
				rsm.getUserGroupSet().add(searchGroup);
			}
			return;
		}
		//System.out.println("searchName is" + searchName);
		Set<String> pGroupSet = new HashSet<>(); 
		for(ResourceGroup rg : rgService.getPermittedGroup(username, permission))
			pGroupSet.add(rg.getGname());
		//System.out.println("pg:" + pGroupSet);
		//Phase 2 Not a Group member? Set only himself!
		if(pGroupSet == null || pGroupSet.size()==0) {
			//System.out.println("No group!");
			if(searchGroup != null && searchGroup.length()>0)
				throw new NoResourcePermissionException();
			rsm.setUserGroupSet(null);
			if(searchName == null || searchName.length()==0) { 
				rsm.setUsername(username);
				return;
			}else 
				throw new NoResourcePermissionException();
		}
		//Phase 3 Super root? Then all ok!
		if(pGroupSet.contains(rootGroup)) {
			//System.out.println("root group user");
			pGroupSet.clear();
			if(searchGroup != null && searchGroup.length()>0) pGroupSet.add(searchGroup);
			rsm.setUserGroupSet(pGroupSet);
			return;
		}
		//Phase 4 Special Group required? Test the permission!
		if(searchGroup != null && searchGroup.length()>0) {
			if(!pGroupSet.contains(searchGroup)) {
				//System.out.println("No permission group");
				throw new NoResourcePermissionException();
			}else {
				//System.out.println("legal single group");
				pGroupSet.clear();
				pGroupSet.add(searchGroup);
				rsm.setUserGroupSet(pGroupSet);
				//System.out.println(pGroupSet);
				return;
			}
		}
		//System.out.println("No special group");
		//Phase 5 Serveral group search ? 
		if(searchGroupSet != null && searchGroupSet.size()>0) {
			searchGroupSet.retainAll(pGroupSet);
			//System.out.println("retain all : " + searchGroupSet);
		}else {
			rsm.setUserGroupSet(pGroupSet);
			//System.out.println("All available set");
		}
	}
}
