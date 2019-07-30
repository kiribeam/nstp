package kiri.nstp.config;

import java.io.IOException;

import org.apache.shiro.spring.LifecycleBeanPostProcessor;
import org.apache.shiro.spring.security.interceptor.AuthorizationAttributeSourceAdvisor;
import org.springframework.aop.framework.autoproxy.DefaultAdvisorAutoProxyCreator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.DependsOn;
import org.springframework.web.multipart.MultipartResolver;
import org.springframework.web.multipart.support.StandardServletMultipartResolver;
import org.springframework.web.servlet.ViewResolver;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.view.InternalResourceViewResolver;

@SuppressWarnings("deprecation")
@Configuration
@ComponentScan(value="kiri.nstp.web.controller")
@EnableWebMvc
public class AppServletConfig extends WebMvcConfigurerAdapter{
	@Bean
	public ViewResolver viewResolver() {
		InternalResourceViewResolver resolver =
				new InternalResourceViewResolver();
		resolver.setPrefix("/WEB-INF/views/");
		resolver.setSuffix(".jsp");
		resolver.setExposeContextBeansAsAttributes(true);
		return resolver;
	}
	
	@Bean
	public MultipartResolver multipartResolver() throws IOException{
		return new StandardServletMultipartResolver();
		
	}
	
	@Override
	public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
		configurer.enable();
	}
	
	@Bean("lifecycleBeanPostProcessor")
	public LifecycleBeanPostProcessor lfcbpp() {
		return new LifecycleBeanPostProcessor();
	}
	
	@DependsOn(value="lifecycleBeanPostProcessor")
	@Bean
	public DefaultAdvisorAutoProxyCreator daapc() {
		return new DefaultAdvisorAutoProxyCreator();
	}
	
	@Bean
	public AuthorizationAttributeSourceAdvisor aasa(org.apache.shiro.mgt.SecurityManager sm) {
		AuthorizationAttributeSourceAdvisor bean = new AuthorizationAttributeSourceAdvisor();
		bean.setSecurityManager(sm);
		return bean;
	}

}
