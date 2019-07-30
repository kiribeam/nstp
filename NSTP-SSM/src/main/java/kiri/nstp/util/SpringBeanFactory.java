package kiri.nstp.util;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringBeanFactory implements ApplicationContextAware{
	private static ApplicationContext context;
	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException{
		if(SpringBeanFactory.context == null) {
			SpringBeanFactory.context = context;
		}
	}
	public static ApplicationContext getApplicationContext() {
		return context;
	}
	
	public static<T> T getBean(Class<T> clazz) {
		return context.getBean(clazz);
	}
	

}
