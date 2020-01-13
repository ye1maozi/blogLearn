package com.learn.blog.shiro.filter;

import com.alibaba.fastjson.JSON;
import com.learn.blog.pojo.Permission;
import com.learn.blog.service.PermissionService;
import com.learn.blog.service.RoleService;
import com.learn.blog.util.Constant;
import com.learn.blog.util.Result;
import com.learn.blog.util.SpringContextUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.AccessControlFilter;
import org.apache.shiro.web.util.WebUtils;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class ShiroAuthFilter extends AccessControlFilter {

    @Autowired
    PermissionService permissionService;

    @Autowired
    RoleService roleService;
    //，是否允许访问
    @Override
    protected boolean isAccessAllowed(ServletRequest servletRequest, ServletResponse servletResponse, Object o) throws Exception {
       return false;
    }

    //不允许访问的情况
    @Override
    protected boolean onAccessDenied(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
      System.out.println("onAccessDenied");
        Subject subject = SecurityUtils.getSubject();
        if (!checkLogin(servletRequest,servletResponse)) {
//            throw new UnauthorizedException();
            sendBackJson(servletResponse,servletRequest,"没有登录",Constant.ResultCode.UNLOGIN);
            return false;
        }
        if (null == permissionService) {
            permissionService = SpringContextUtils.getContext().getBean(PermissionService.class);
            roleService = SpringContextUtils.getContext().getBean(RoleService.class);
        }
        String requestURI = getPathWithinApplication(servletRequest);
        HttpServletRequest req = (HttpServletRequest) servletRequest;
        String restful = requestURI + "=" + req.getMethod().toLowerCase();
        boolean needInterceptor = permissionService.needInterceptor(restful);
        if (!needInterceptor){
            return true;
        }else{
            System.out.println(" check peim " + restful );
            boolean hasPermission  = false;
            String userName = subject.getPrincipal().toString();
            Set<String> roles = roleService.listRoleNames(userName);
            Set<String> permissions = null;
            if (roles.contains("admin")){
                List<Permission> list =permissionService.selectAll();
                permissions = new HashSet<>();
                Set<String> finalPermissions = permissions;
                list.forEach(p -> {
                    finalPermissions.add(p.getApiurl());
                });

            }else{
                permissions =permissionService.listPermissionURLs(userName);
            }
            if (permissions != null && permissions.contains(restful)) {
                hasPermission = true;
            }
            System.out.println(" check peim " + restful + (hasPermission?" pass":" fail") );
            if (hasPermission) {
                return true;
            }else{
                //跳转权限不足
                sendBackJson(servletResponse,servletRequest,"权限不足",Constant.ResultCode.UAUTH);
                return false;
            }
        }
    }

    private boolean checkLogin(ServletRequest servletRequest, ServletResponse servletResponse) {
        Subject subject = SecurityUtils.getSubject();
       if ( subject.isAuthenticated())
       {
           return true;
       }


       return false;
    }

    private void sendBackJson(ServletResponse response, ServletRequest servletRequest, String msg, Constant.ResultCode code) throws IOException {
        HttpServletResponse httpResponse = (HttpServletResponse) response;
        response.setContentType("text/html;charset=UTF-8");
        httpResponse.setHeader("Access-Control-Allow-Origin", ((HttpServletRequest)servletRequest).getHeader("Origin"));
        String s = JSON.toJSONString(Result.fail(msg,code));
        System.out.println("shiro " + msg);
        response.getWriter().write(s);
    }

    @Override
    public boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {
        System.out.println( WebUtils.getPathWithinApplication(WebUtils.toHttp(request)));
        System.out.println(((HttpServletRequest) request).getMethod());
        if (request instanceof HttpServletRequest) {
            if (((HttpServletRequest) request).getMethod().toUpperCase().equals("OPTIONS")) {
                return true;
            }
        }
        return super.onPreHandle(request,response,mappedValue);
    }

}
