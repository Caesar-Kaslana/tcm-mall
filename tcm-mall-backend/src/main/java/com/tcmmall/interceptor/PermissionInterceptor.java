package com.tcmmall.interceptor;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tcmmall.entity.User;
import com.tcmmall.vo.Result;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.util.List;

public class PermissionInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        HttpSession session = request.getSession();
        User user = (User) session.getAttribute("user");
        if (user == null) {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(new ObjectMapper().writeValueAsString(Result.error(401, "未登录或登录已过期")));
            return false;
        }
        List<String> permissions = (List<String>) session.getAttribute("permissions");
        if (permissions == null || permissions.isEmpty()) {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(new ObjectMapper().writeValueAsString(Result.error(403, "无权限访问")));
            return false;
        }
        String uri = request.getRequestURI();
        boolean hasPermission = permissions.stream().anyMatch(uri::contains);
        if (!hasPermission && !permissions.contains("*")) {
            response.setContentType("application/json;charset=UTF-8");
            response.getWriter().write(new ObjectMapper().writeValueAsString(Result.error(403, "无权限访问")));
            return false;
        }
        return true;
    }
}
