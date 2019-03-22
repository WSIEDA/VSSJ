package cc.com.jsoft.config;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import cc.com.jsoft.pojo.ResponseManager;

/**
 * TODO
 * @{param}
 * @{return}
 * @{date}
 * @author wangshuai
 */
@Configuration
@Component
public class InterceptorFilter implements HandlerInterceptor  {

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.HandlerInterceptor#afterCompletion(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, java.lang.Exception)
	 */
	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3)
			throws Exception {
		
	}

	/* (non-Javadoc)
	 * @see org.springframework.web.servlet.HandlerInterceptor#postHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object, org.springframework.web.servlet.ModelAndView)
	 */
	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3)
			throws Exception {
		
	}
  
	
	/* (non-Javadoc)
	 * http https 请求调用执行 在Controller之前执行， 主要做一些登录 权限 等的拦截操作 
	 *  只有返回为true之后才会执行其他的操作
	 * @see org.springframework.web.servlet.HandlerInterceptor#preHandle(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse, java.lang.Object)
	 */
	@Override
	public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html;charset=UTF-8");
		// 获取请求头中的Authorization信息， 然后解密，获取 头信息、 载荷、 签名 ，逐一校验信息返回结果。
		String token = request.getHeader("Authorization");
		System.out.println(request.getHeader("Authorization")+"---------"+request.getSession().getAttribute("Authorization"));
//		Object token = request.getSession().getAttribute("Authorization");
		if (null == token) {
//			return reLogin(response);
			response.sendError(401);
			return false;
		}
		else {
			return true;
		}
	}
	
	@SuppressWarnings("unused")
	private boolean reLogin(HttpServletResponse response) throws IOException {
        PrintWriter out;
        try{
        	ResponseManager resManager = new ResponseManager();
    		resManager.setCode(201);
    		resManager.setMessage("用户需要重新登录");
    		resManager.setState(false);
    		out = response.getWriter();
    		out.append((CharSequence) resManager);
            return false;
        } catch (Exception e){
            e.printStackTrace();
            response.sendError(500);
            return false;
        }
    }
}
