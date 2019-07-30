package kiri.nstp.config;

import javax.servlet.Filter;
import javax.servlet.FilterRegistration;
import javax.servlet.MultipartConfigElement;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletRegistration.Dynamic;

import org.springframework.web.filter.CharacterEncodingFilter;
import org.springframework.web.filter.DelegatingFilterProxy;
import org.springframework.web.servlet.support.AbstractAnnotationConfigDispatcherServletInitializer;

public class WebAppInitializer extends AbstractAnnotationConfigDispatcherServletInitializer{
	@Override
	public void onStartup(ServletContext servletContext) throws ServletException{
		registerContextLoaderListener(servletContext);

		registerShiroFilter(servletContext);
		registerDispatcherServlet(servletContext);
	}
	@Override
	protected Filter[] getServletFilters() {
		CharacterEncodingFilter filter = new CharacterEncodingFilter();
		filter.setEncoding("utf-8");
		filter.setForceEncoding(true);
		return new Filter[] {filter};
	}
	
	private void registerShiroFilter(ServletContext servletContext) {
		FilterRegistration.Dynamic dy = servletContext.addFilter("filterProxy", DelegatingFilterProxy.class);
		dy.setInitParameter("targetBeanName", "shiroFilterFactoryBean");
		dy.addMappingForUrlPatterns(null, false, "/*");
	}
	
	@Override
	protected Class<?>[] getRootConfigClasses(){
		return new Class[] {AppRootConfig.class, AppShiroConfig.class, AppRedisConfig.class};
	}
	
	@Override
	protected Class<?>[] getServletConfigClasses(){
		return new Class[] {AppServletConfig.class};
	}
	
	@Override
	protected String[] getServletMappings() {
		return new String[] {"/"};
	}
	
	@Override
	protected void customizeRegistration(Dynamic registration) {
		registration.setMultipartConfig(
				new MultipartConfigElement("/tmp", 
						2097152,4194304,0));
		//max file size, max request size, write on disk
	}


}
