package kiri.nstp.useshiro;

import java.io.PrintWriter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.shiro.web.filter.authc.FormAuthenticationFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.http.HttpStatus;

public class MyShiroAuthcFilter extends FormAuthenticationFilter {

	public MyShiroAuthcFilter() {
		super();
	}
	
	@Override
	protected boolean onAccessDenied(ServletRequest request, 
			ServletResponse response) throws Exception {
		if(isLoginRequest(request, response)) {
			return super.onAccessDenied(request, response);
		}else {
			if(isAjax((HttpServletRequest) request)) {
				HttpServletResponse httpServletResponse = WebUtils.toHttp(response);
				httpServletResponse.addHeader("REQUIRE_AUTH", "true");
				httpServletResponse.setStatus(HttpStatus.UNAUTHORIZED.value());
				httpServletResponse.setCharacterEncoding("utf-8");
				httpServletResponse.setContentType("application/json");
				try(PrintWriter writer = httpServletResponse.getWriter()){
					writer.write("\"Please login!\"");
					
				}catch(Exception e) {
				}
			}
			else {
				saveRequestAndRedirectToLogin(request, response);
			}
		}
		return false;
	}
	private boolean isAjax(HttpServletRequest request) {
		String requestedWithHeader = request.getHeader("X-Requested-With");
		return "XMLHttpRequest".equals(requestedWithHeader);
	}

}
