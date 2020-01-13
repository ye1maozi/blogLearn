package com.learn.blog.filter;

import com.learn.blog.service.PermissionService;
import com.learn.blog.util.SpringContextUtils;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.authz.UnauthorizedException;
import org.apache.shiro.subject.Subject;
import org.apache.shiro.web.filter.PathMatchingFilter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import java.util.Set;

public class URLPathMatchingFilter extends PathMatchingFilter {

    @Autowired
    PermissionService permissionService;

    @Override
    protected boolean onPreHandle(ServletRequest request, ServletResponse response, Object mappedValue) throws Exception {

        if (null == permissionService) {
            permissionService = SpringContextUtils.getContext().getBean(PermissionService.class);
        }

        String requestURI = getPathWithinApplication(request);

        Subject subject = SecurityUtils.getSubject();
        if ( checkLoginPath()){
            //跳转登录
            return false;
        }
        // 看看这个路径权限里有没有维护，如果没有维护，一律放行(也可以改为一律不放行)
        boolean needInterceptor = permissionService.needInterceptor(requestURI);
        if (!needInterceptor){
            return true;
        }else{
            boolean hasPermission  = false;
            String userName = subject.getPrincipal().toString();
            Set<String> permissionUrls = permissionService.listPermissionURLs(userName);
            for(String url : permissionUrls){
                if (url.equals(requestURI)){
                    hasPermission = true;
                    break;
                }
            }
            if (hasPermission) {
                return true;
            }else{
                UnauthorizedException ex = new  UnauthorizedException("当前用户没有访问路径 " + requestURI + " 的权限");
                subject.getSession().setAttribute("ex",ex);

                //跳转权限不足
                return false;
            }
        }
    }

    private boolean checkLoginPath() {
        return false;
    }

}
