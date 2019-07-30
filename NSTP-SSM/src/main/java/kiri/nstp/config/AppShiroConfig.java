package kiri.nstp.config;

import java.util.LinkedHashMap;
import java.util.Map;

import javax.servlet.Filter;

import org.apache.shiro.authc.credential.CredentialsMatcher;
import org.apache.shiro.authc.credential.HashedCredentialsMatcher;
import org.apache.shiro.session.mgt.ExecutorServiceSessionValidationScheduler;
import org.apache.shiro.session.mgt.eis.EnterpriseCacheSessionDAO;
import org.apache.shiro.session.mgt.eis.JavaUuidSessionIdGenerator;
import org.apache.shiro.spring.web.ShiroFilterFactoryBean;
import org.apache.shiro.web.filter.authc.AnonymousFilter;
import org.apache.shiro.web.filter.authc.LogoutFilter;
import org.apache.shiro.web.mgt.DefaultWebSecurityManager;
import org.apache.shiro.web.servlet.SimpleCookie;
import org.apache.shiro.web.session.mgt.DefaultWebSessionManager;
import org.springframework.beans.factory.config.MethodInvokingFactoryBean;
import org.springframework.context.annotation.Bean;

import kiri.nstp.useshiro.MyRealm;
import kiri.nstp.useshiro.MyShiroAuthcFilter;


public class AppShiroConfig {
	
	public AppShiroConfig() {
		System.out.println("Init shiro");
	}

	@Bean
	public MyRealm getRealm() {
		MyRealm bean = new MyRealm();
		bean.setCredentialsMatcher(getMatcher());
		return bean;
	}
	@Bean("securityManager")
	public org.apache.shiro.mgt.SecurityManager getSm() {
		DefaultWebSecurityManager securityManager = new DefaultWebSecurityManager();
		securityManager.setRealm(getRealm());
		securityManager.setSessionManager(getSessionManager());
		return securityManager;
	}
	//Here use @Value inject?
	@Bean("shiroFilterFactoryBean")
	public ShiroFilterFactoryBean getFilterFacBean(org.apache.shiro.mgt.SecurityManager sm) {
		ShiroFilterFactoryBean bean = new ShiroFilterFactoryBean();
		bean.setSecurityManager(sm);
		Map<String, Filter> filters = bean.getFilters();
		filters.put("authd",  new MyShiroAuthcFilter());
		filters.put("anno", new AnonymousFilter());
		filters.put("logout", new LogoutFilter());
		bean.setFilters(filters);

		bean.setLoginUrl("/UserLogin.html");
		LinkedHashMap<String, String> map = new LinkedHashMap<>();
		map.put("/user/logout", "logout");
		map.put("/login", "anon");
		map.put("/regist", "anon");
		map.put("/UserLogin.html", "anon");
		map.put("/UserRegist.html", "anon");
		map.put("/index.html", "anon");
		//change later
		map.put("/testjs/*", "anon");
		map.put("/css/*", "anon");
		map.put("/serverpk/*", "anon");
		map.put("/images/*", "anon");
		map.put("/fonts/*", "anon");
		//caution here! use new filter
		map.put("/**", "authd");
		bean.setFilterChainDefinitionMap(map);
		return bean;
	}
	@Bean("sessionIdGenerator")
	public JavaUuidSessionIdGenerator getSessionIdGen() {
		return new JavaUuidSessionIdGenerator();
	}
	@Bean("sessionIdCookie")
	public SimpleCookie getSimpleCookie() {
		SimpleCookie sc = new SimpleCookie("sid");
		sc.setHttpOnly(true);
		sc.setMaxAge(-1);
		return sc;
	}
	@Bean
	public EnterpriseCacheSessionDAO getSessionDao() {
		EnterpriseCacheSessionDAO ec = new EnterpriseCacheSessionDAO();
		ec.setSessionIdGenerator(getSessionIdGen());
		return ec;
	}
	@Bean
	public ExecutorServiceSessionValidationScheduler getScheduler() {
		ExecutorServiceSessionValidationScheduler es = new ExecutorServiceSessionValidationScheduler();
		es.setInterval(1800000);
		return es;
	}
	@Bean
	public DefaultWebSessionManager getSessionManager(
			) {
		DefaultWebSessionManager dsm = new DefaultWebSessionManager();
		dsm.setGlobalSessionTimeout(1800000);
		dsm.setDeleteInvalidSessions(true);
		ExecutorServiceSessionValidationScheduler svs = getScheduler();
		svs.setSessionManager(dsm);
		dsm.setSessionValidationScheduler(svs);
		dsm.setSessionValidationSchedulerEnabled(true);
		dsm.setSessionDAO(getSessionDao());
		dsm.setSessionIdCookie(getSimpleCookie());
		dsm.setSessionIdCookieEnabled(true);
		dsm.setSessionIdUrlRewritingEnabled(false);
		return dsm;
	}
	@Bean
	public MethodInvokingFactoryBean getMIF() {
		MethodInvokingFactoryBean bean = new MethodInvokingFactoryBean();
		bean.setStaticMethod("org.apache.shiro.SecurityUtils.setSecurityManager");
		bean.setArguments(getSm());
		return bean;
	}
	@Bean
	public CredentialsMatcher getMatcher() {
		HashedCredentialsMatcher bean = new HashedCredentialsMatcher();
		bean.setHashAlgorithmName("md5");
		bean.setHashIterations(2);
		bean.setStoredCredentialsHexEncoded(true);
		return bean;
	}

}
